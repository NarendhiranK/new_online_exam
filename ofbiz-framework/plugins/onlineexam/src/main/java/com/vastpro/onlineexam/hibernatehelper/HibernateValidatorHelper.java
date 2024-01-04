package com.vastpro.onlineexam.hibernatehelper;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.text.StringSubstitutor;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;

public class HibernateValidatorHelper {
	private static String module = HibernateValidatorHelper.class.getName();

	private static Validator getDefaultValidator() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		return factory.getValidator();
	}

	private static Validator validator = getDefaultValidator();

	@SuppressWarnings("deprecation")
	public static <T> T populateBeanFromMap(Map<String, ? extends Object> map, Class<T> clazz) {
		Object bean = null;
		try {
			bean = clazz.newInstance();
			BeanUtils.populate(bean, map);
		} catch (IllegalAccessException | InvocationTargetException | InstantiationException e1) {
			Debug.logError(e1, e1.getMessage(), module);
		}
		return (T) bean;
	}

	/**
	 * Given the formBean T and a Group, this will invoke the Hibernate validator
	 * and will provide the set of ConstraintViolation objects
	 *
	 * @param form
	 * @param clazz
	 * @return
	 */
	public static <T> Set<ConstraintViolation<T>> checkValidationErrors(T form, Class clazz) {
		if (UtilValidate.isEmpty(validator)) {
			validator = getDefaultValidator();
		}
		if (clazz != null) {
			return validator.validate(form, clazz);
		} else {
			// no group is passed
			return validator.validate(form);
		}
	}

	public static String getMessageFromEntity(Delegator delegator, String resourceBundle, String key, Locale locale,
			boolean fetchFromEntity, Map<String, Object> replacementParams) {
		String message = getMessageFromEntity(delegator, resourceBundle, key, Locale.getDefault(), fetchFromEntity);
		return StringSubstitutor.replace(message, replacementParams);
	}

	public static String getMessageFromEntity(Delegator delegator, String resourceBundle, String key, Locale locale,
			boolean fetchFromEntity) {
		if (UtilValidate.isEmpty(locale)) {
			locale = Locale.getDefault();
		}
		if (fetchFromEntity) {
			String[] entityAndKey = resourceBundle.split(":");
			GenericValue oneMsg = null;
			try {
				oneMsg = EntityQuery.use(delegator).from(entityAndKey[0]).where(entityAndKey[1], key).cache()
						.queryFirst();
			} catch (GenericEntityException e) {
				Debug.logError(e, module);
			}
			String message = key;
			if (UtilValidate.isNotEmpty(oneMsg)) {
				message = oneMsg.getString("message");
			}
			return message;
		} else {
			return getErrorMessage(resourceBundle, key, locale);
		}
	}

	public static String getErrorMessage(String resourceBundle, String key, Locale locale) {
		if (locale == null) {
			locale = Locale.getDefault();
		}
		return UtilProperties.getMessage(resourceBundle, key, locale);
	}

	public static <T> boolean validateFormSubmission(Delegator delegator, Set<ConstraintViolation<T>> validationErrors,
			HttpServletRequest request, Locale locale, String generalErrorMsgKey, String resourceBundle,
			boolean fetchFromEntity) {
		boolean hasFormErrors = false;
		int noOfViolations = 0;
		if (UtilValidate.isNotEmpty(validationErrors)) {
			noOfViolations = validationErrors.size();
		}
		Map<String, Object> combinedMap = null;
		if (request.getAttribute("combinedMap") != null) {
			combinedMap = (Map<String, Object>) request.getAttribute("combinedMap");
			request.removeAttribute("combinedMap");
		}
		if (noOfViolations > 0) {
			List<String> erroredFieldNames = new ArrayList<String>();
			request.setAttribute("ERRORED_FIELD_NAMES", erroredFieldNames);
			for (ConstraintViolation<T> constraintViolation : validationErrors) {
				String errorField = constraintViolation.getPropertyPath().toString();
				String errorMesg = getMessageFromEntity(delegator, resourceBundle, constraintViolation.getMessage(),
						request.getLocale(), fetchFromEntity);
				Debug.logVerbose(constraintViolation.getMessage(), module);
				Debug.logVerbose(errorField, module);
				request.setAttribute(errorField, errorMesg);
				erroredFieldNames.add(errorField);
			}
//			 if combinedMap is not empty, then it is a multipart submission and the
//			 request will already contain
//			 all the submitted parameters as attributes as well. we need to remove them as
//			 otherwise upon
//			 conversion to JSON all those non-error fields also will go in the response to
//			 the front-end.
			if (UtilValidate.isNotEmpty(combinedMap)) {
				for (String paramKey : combinedMap.keySet()) {
					if (!erroredFieldNames.contains(paramKey)) {
						request.removeAttribute(paramKey);
					}
				}
			}
			request.setAttribute("result", "HELPER-ERROR");
			hasFormErrors = true;
		}
		return hasFormErrors;
	}
}