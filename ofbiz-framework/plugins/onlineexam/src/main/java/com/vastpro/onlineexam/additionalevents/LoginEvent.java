package com.vastpro.onlineexam.additionalevents;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.webapp.control.LoginWorker;

public class LoginEvent {

	public static String loginEvent(HttpServletRequest request, HttpServletResponse response) {
		String username = (String) request.getAttribute("userName");
		String password = (String) request.getAttribute("password");
		Debug.log(username);
		Debug.log(password);
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		try {
			String result = LoginWorker.login(request, response);
			Debug.log("My result........" + result);
			GenericValue userLogin = EntityQuery.use(delegator).from("UserLogin").where("userLoginId", username).cache()
					.queryFirst();
			String roleTypeId = null;
			if (UtilValidate.isNotEmpty(userLogin)) {
				String partyId = (String) userLogin.get("partyId");
				Debug.log("partyId...>"+partyId);
				Debug.log(partyId);
				List<GenericValue> partyRole = EntityQuery.use(delegator).from("PartyRole").where("partyId", partyId)
						.cache().queryList();

				for (GenericValue usergenericvalue : partyRole) {
					String usersRoletypeId = (String) usergenericvalue.get("roleTypeId");
//						Debug.log("UserRoleTypeId............"+ usersRoletypeId);
					if (usersRoletypeId.equalsIgnoreCase("ADMIN")) {
						roleTypeId = usersRoletypeId;
					} else if (usersRoletypeId.equalsIgnoreCase("USER")) {
						roleTypeId = usersRoletypeId;
					}
				}
				Debug.log("roletypeId"+roleTypeId);
			
			}
			Map<String, String> resultMap = new HashMap<String, String>();
			if (result.equalsIgnoreCase("success")) {
				resultMap.put("usersRoletypeId", roleTypeId);
				resultMap.put("login_condition", result);
				request.setAttribute("resultMap", resultMap);
			} else {
				resultMap.put("login_condition", result);
				request.setAttribute("resultMap", resultMap);
			}
			// Debug.log(userLogin.toString());
			// request.setAttribute("EVENT_MESSAGE", "username and password succesfully." );
			return result;
		} catch (GenericEntityException e) {
			e.printStackTrace();
			request.setAttribute(" Error_Message", "username and password is error");
			return "error";
		}
	}

}
