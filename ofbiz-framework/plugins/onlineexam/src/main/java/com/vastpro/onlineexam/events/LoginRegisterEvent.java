package com.vastpro.onlineexam.events;

import java.util.HashMap;
import com.vastpro.onlineexam.checks.RegisterFormCheck;
import com.vastpro.onlineexam.constants.ConstantValue;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.apache.ofbiz.base.util.Debug;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import org.apache.ofbiz.base.util.GeneralException;
import org.apache.ofbiz.base.util.UtilHttp;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.EntityCryptoException;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.model.ModelField;
import org.apache.ofbiz.entity.util.EntityCrypto;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;
import org.apache.ofbiz.webapp.control.LoginWorker;
import com.vastpro.onlineexam.checks.LoginFormCheck;
import com.vastpro.onlineexam.forms.LoginFormValidator;
import com.vastpro.onlineexam.forms.RegisterFormValidator;
import com.vastpro.onlineexam.hibernatehelper.HibernateValidatorHelper;

public class LoginRegisterEvent {

	/*
	 * This event is used to either create or update the questions on the question
	 * master entity.
	 * 
	 */
	public static final String MODULE = LoginRegisterEvent.class.getName();
	public static String resource_error = "OnlineexamUiLabels";

	/*
	 * This method is used to login the user or admin
	 * 
	 */
	public static String loginEvent(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute(ConstantValue.DELEGATOR);
		Locale locale = UtilHttp.getLocale(request);
		Map<String, Object> combinedMap = UtilHttp.getCombinedMap(request);
		String userName = (String) combinedMap.get(ConstantValue.USERNAME);
		String password = (String) combinedMap.get(ConstantValue.PASSWORD);
		Debug.logInfo("", userName);
		Debug.logInfo("", password);

		// Hibernate Validation
		Map<String, Object> userLoginMap = UtilMisc.toMap("username", userName, "password", password);
		LoginFormValidator loginForm = HibernateValidatorHelper.populateBeanFromMap(userLoginMap,
				LoginFormValidator.class);
		Set<ConstraintViolation<LoginFormValidator>> errors = HibernateValidatorHelper.checkValidationErrors(loginForm,
				LoginFormCheck.class);
		boolean hasErrors = HibernateValidatorHelper.validateFormSubmission(delegator, errors, request, locale,
				"MandatoryFieldErrMsgLoginForm", resource_error, false);

		try {
			List<GenericValue> checkGenericValue = null;
			String result = LoginWorker.login(request, response);
			GenericValue userLogin = (GenericValue) request.getSession().getAttribute(ConstantValue.USERLOGIN);
			String partyId = (String) userLogin.get(ConstantValue.PARTY_ID);
			Debug.log("My result........" + result);
			String roleTypeId = null;
			String firstName = null;
			if (UtilValidate.isNotEmpty(userLogin)) {

				Debug.log(partyId);
				GenericValue personFirstName = EntityQuery.use(delegator).from(ConstantValue.PERSON)
						.where(ConstantValue.PARTY_ID, partyId).cache().queryOne();
				firstName = (String) personFirstName.get(ConstantValue.FIRST_NAME);
				Debug.log(firstName);
				List<GenericValue> partyRole = EntityQuery.use(delegator).from(ConstantValue.PARTY_ROLE)
						.where(ConstantValue.PARTY_ID, partyId).cache().queryList();
				for (GenericValue usergenericvalue : partyRole) {
					String usersRoletypeId = (String) usergenericvalue.get(ConstantValue.ROLE_TYPE_ID);
					if (usersRoletypeId.equalsIgnoreCase("ADMIN")) {
						checkGenericValue = partyRole;
						roleTypeId = usersRoletypeId;
					} else if (usersRoletypeId.equalsIgnoreCase("PERSON_ROLE")) {
						roleTypeId = usersRoletypeId;
					}
				}
				Debug.log(roleTypeId);
			}
			Map<String, Object> resultMap = new HashMap<String, Object>();
			boolean isFlag = false;
			if (result.equalsIgnoreCase("success")) {

				resultMap.put("hasErrors", hasErrors);

				isFlag = true;
				resultMap.put("isFlag", isFlag);
				resultMap.put("usersRoletypeId", roleTypeId);
				resultMap.put("login_condition", result);
				resultMap.put("firstName", firstName);
				resultMap.put("checkGenericValue", checkGenericValue);
				request.setAttribute("resultMap", resultMap);
			} else {
				resultMap.put("login_condition", result);
				resultMap.put("isFlag", isFlag);
				// resultMap.put("firstName", firstName);
				request.setAttribute("resultMap", resultMap);
			}
			// System.out.println(userLogin.toString());
			// request.setAttribute("EVENT_MESSAGE", "username and password succesfully." );
			return result;
		} catch (GenericEntityException e) {
			e.printStackTrace();
			request.setAttribute(" Error_Message", "username and password is error");
			return "error";
		}
	}

	public static String onUserRegister(HttpServletRequest request, HttpServletResponse response) {
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute(ConstantValue.USERLOGIN);
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute(ConstantValue.DISPATCHER);
		Delegator delegator = (Delegator) request.getAttribute(ConstantValue.DELEGATOR);
		Locale locale = UtilHttp.getLocale(request);
		Map<String, Object> combinedMap = UtilHttp.getCombinedMap(request);
		String firstName = (String) combinedMap.get(ConstantValue.FIRST_NAME);
		String lastName = (String) combinedMap.get(ConstantValue.LAST_NAME);
		String userLoginId = (String) combinedMap.get(ConstantValue.USER_LOGIN_ID);
		String currentPassword = (String) combinedMap.get(ConstantValue.CURRENT_PASSWORD);
		String currentPasswordVerify = (String) combinedMap.get(ConstantValue.CURRENT_PASSWORD_VERIFY);

		Map<String, Object> registerMap = new HashMap<String, Object>();
		registerMap.put(ConstantValue.FIRST_NAME, firstName);
		registerMap.put(ConstantValue.LAST_NAME, lastName);
		registerMap.put(ConstantValue.USER_LOGIN_ID, userLoginId);
		registerMap.put(ConstantValue.CURRENT_PASSWORD, currentPassword);
		registerMap.put(ConstantValue.CURRENT_PASSWORD_VERIFY, currentPasswordVerify);
		registerMap.put("userLogin", userLogin);
		Map<String, Object> registerResultMap = null;
		try {
			Map<String, Object> userRegisterMap = UtilMisc.toMap("firstName", firstName, "lastName", lastName,
					"username", userLoginId, "password", currentPassword, "confirmPassword", currentPasswordVerify);
			RegisterFormValidator registerForm = HibernateValidatorHelper.populateBeanFromMap(userRegisterMap,
					RegisterFormValidator.class);
			Set<ConstraintViolation<RegisterFormValidator>> errors = HibernateValidatorHelper
					.checkValidationErrors(registerForm, RegisterFormCheck.class);
			boolean hasErrors = HibernateValidatorHelper.validateFormSubmission(delegator, errors, request, locale,
					"MandatoryFieldErrMsgLoginForm", resource_error, false);
			request.setAttribute("hasErrors", hasErrors);
			if (hasErrors == false) {
				registerResultMap = dispatcher.runSync("createPersonAndUserLogin", registerMap);
			}
		} catch (GenericServiceException e) {
			e.printStackTrace();
			return "error";
		}
		if (ServiceUtil.isSuccess(registerResultMap)) {
			request.setAttribute("result1", registerResultMap);
			String partyId = (String) registerResultMap.get(ConstantValue.PARTY_ID);
			Map<String, Object> partyRoleMap = new HashMap<String, Object>();
			partyRoleMap.put(ConstantValue.PARTY_ID, partyId);
			partyRoleMap.put(ConstantValue.USERLOGIN, userLogin);
			partyRoleMap.put(ConstantValue.ROLE_TYPE_ID, "PERSON_ROLE");
			Map<String, Object> resultPartyRole = null;
			try {
				resultPartyRole = dispatcher.runSync("createPartyRoleRecord", partyRoleMap);
			} catch (GenericServiceException e) {
				e.printStackTrace();
				return "error";
			}
			if (ServiceUtil.isSuccess(resultPartyRole)) {
				request.setAttribute("result2", resultPartyRole);
			}
		}
		return "success";
	}

	public static String logoutEvent(HttpServletRequest request, HttpServletResponse response) {
		boolean isLogout = false;
		String result = LoginWorker.logout(request, response);
		if (result.equalsIgnoreCase("success")) {
			isLogout = true;
		}
		request.setAttribute("LogoutCondition", result);
		request.setAttribute("isLogout", isLogout);
		return result;
	}
}