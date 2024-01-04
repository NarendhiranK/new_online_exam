package com.vastpro.onlineexam.events;
import java.util.HashMap;
import com.vastpro.onlineexam.checks.RegisterFormCheck;
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
	 * This event is used to either create or update the questions on the question master entity.
	 * 
	 * */
	public static final String MODULE = LoginRegisterEvent.class.getName();
	public static String resource_error = "OnlineexamUiLabels";
	
	/*
	 * This method  is used to login the user or admin
	 * 
	 * */
	public static String loginEvent(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> combinedMap = UtilHttp.getCombinedMap(request);
		String userName = (String) combinedMap.get("USERNAME");
		String password = (String) combinedMap.get("PASSWORD");
		Debug.log(userName);
		Debug.log(password);
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Locale locale = UtilHttp.getLocale(request);
		try {
			List<GenericValue> checkGenericValue = null;
			String result = LoginWorker.login(request, response);
			Debug.log("My result........" + result);
			GenericValue userLogin = EntityQuery.use(delegator).from("UserLogin").where("userLoginId", userName).cache()
					.queryFirst();
			String roleTypeId = null;
			String firstName = null;
			if (UtilValidate.isNotEmpty(userLogin)) {
				
				String partyId = (String) userLogin.get("partyId");
				Debug.log(partyId);
				GenericValue personFirstName = EntityQuery.use(delegator).from("Person").where("partyId", partyId)
						.cache().queryOne();
				firstName = (String) personFirstName.get("firstName");
				Debug.log(firstName);
				List<GenericValue> partyRole = EntityQuery.use(delegator).from("PartyRole").where("partyId", partyId)
						.cache().queryList();
				for (GenericValue usergenericvalue : partyRole) {
					String usersRoletypeId = (String) usergenericvalue.get("roleTypeId");
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
				// Hibernate Validation
				Map<String, Object> userLoginMap = UtilMisc.toMap("username", userName, "password", password);
				LoginFormValidator loginForm = HibernateValidatorHelper.populateBeanFromMap(userLoginMap,
						LoginFormValidator.class);
				Set<ConstraintViolation<LoginFormValidator>> errors = HibernateValidatorHelper.checkValidationErrors(loginForm,
						LoginFormCheck.class);
				boolean hasErrors = HibernateValidatorHelper.validateFormSubmission(delegator, errors, request, locale,
						"MandatoryFieldErrMsgLoginForm", resource_error, false);
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
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Locale locale = UtilHttp.getLocale(request);
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String userLoginId = request.getParameter("userLoginId");
		String currentPassword = request.getParameter("currentPassword");
		String currentPasswordVerify = request.getParameter("currentPasswordVerify");
		if (firstName == null) {
			firstName = (String) request.getAttribute("firstName");
		}
		if (lastName == null) {
			lastName = (String) request.getAttribute("lastName");
		}
		if (userLoginId == null) {
			userLoginId = (String) request.getAttribute("userLoginId");
		}
		if (currentPassword == null) {
			currentPassword = (String) request.getAttribute("currentPassword");
		}
		if (currentPasswordVerify == null) {
			currentPasswordVerify = (String) request.getAttribute("currentPasswordVerify");
		}
		Map<String, Object> registerMap = new HashMap<String, Object>();
		registerMap.put("firstName", firstName);
		registerMap.put("lastName", lastName);
		registerMap.put("userLoginId", userLoginId);
		registerMap.put("currentPassword", currentPassword);
		registerMap.put("currentPasswordVerify", currentPasswordVerify);
		registerMap.put("userLogin", userLogin);
		Map<String, Object> registerResultMap = null;
		try {
//			Map<String, Object> userRegisterMap = UtilMisc.toMap("firstName", firstName, "lastName", lastName,
//					"username", userLoginId, "password", currentPassword, "confirmPassword", currentPasswordVerify);
//			RegisterFormValidator registerForm = HibernateHelper.populateBeanFromMap(userRegisterMap,
//					RegisterFormValidator.class);
//			Set<ConstraintViolation<RegisterFormValidator>> errors = HibernateHelper.checkValidationErrors(registerForm,
//					RegisterFormCheck.class);
//			boolean hasErrors = HibernateHelper.validateFormSubmission(delegator, errors, request, locale,
//					"MandatoryFieldErrMsgLoginForm", resource_error, false);
//			request.setAttribute("hasErrors", hasErrors);
			registerResultMap = dispatcher.runSync("createPersonAndUserLogin", registerMap);
		} catch (GenericServiceException e) {
			e.printStackTrace();
			return "error";
		}
		if (ServiceUtil.isSuccess(registerResultMap)) {
			request.setAttribute("result1", registerResultMap);
			String partyId = (String) registerResultMap.get("partyId");
			Map<String, Object> partyRoleMap = new HashMap<String, Object>();
			partyRoleMap.put("partyId", partyId);
			partyRoleMap.put("userLogin", userLogin);
			partyRoleMap.put("roleTypeId", "PERSON_ROLE");
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