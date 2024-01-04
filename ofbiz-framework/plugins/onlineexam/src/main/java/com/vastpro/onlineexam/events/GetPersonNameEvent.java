package com.vastpro.onlineexam.events;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;

import com.vastpro.onlineexam.constants.ConstantValue;

public class GetPersonNameEvent {

	public static String getPersonName(HttpServletRequest request, HttpServletResponse response) {
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		Delegator delegator = (Delegator) request.getAttribute(ConstantValue.DELEGATOR);
		String result = ConstantValue.SUCCESS;
		try {
			if (UtilValidate.isNotEmpty(userLogin)) {
				GenericValue party = EntityQuery.use(delegator).from(ConstantValue.PERSON)
						.where(ConstantValue.PARTY_ID, userLogin.get(ConstantValue.PARTY_ID)).cache().queryOne();
				String userNameLogin = party.get("firstName") + " " + party.get("lastName");
				request.setAttribute("userNameLogin", userNameLogin);
				request.setAttribute(ConstantValue.SUCCESS_MESSAGE, "username rendered successfully");
			} else {
				request.setAttribute(ConstantValue.ERROR, "UserLogin is null");
			}

		} catch (GenericEntityException e) {
			e.printStackTrace();
			request.setAttribute(ConstantValue.ERROR_MESSAGE, "failed in rendering username");
			return ConstantValue.ERROR;
		}

		return result;

	}

}