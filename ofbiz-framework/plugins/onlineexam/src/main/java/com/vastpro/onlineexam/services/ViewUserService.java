package com.vastpro.onlineexam.services;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.GenericValidator;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.ServiceUtil;

public class ViewUserService {

	public static final String module = ViewUserService.class.getName();

	public static Map<String, Object> viewUsers(DispatchContext dctx, Map<String, ? extends Object> context) {

		Map<String, Object> result = ServiceUtil.returnSuccess();
		Delegator delegator = dctx.getDelegator();

		List<Map<String, Object>> userList = new LinkedList<Map<String, Object>>();
		List<GenericValue> userGenericValue = null;

		try {
			userGenericValue = EntityQuery.use(delegator).from("UserMaster").where("roleTypeId", "PERSON_ROLE")
					.queryList();
		} catch (GenericEntityException e) {

			e.printStackTrace();
		}

		if (UtilValidate.isNotEmpty(userGenericValue)) {
			for (GenericValue genericValue : userGenericValue) {
				String firstName = genericValue.getString("firstName");
				String lastName = genericValue.getString("lastName");
				String partyId = genericValue.getString("partyId");
				String roleTypeId = genericValue.getString("roleTypeId");

				Map<String, Object> user = new HashMap<String, Object>();
				user.put("firstName", firstName);
				user.put("lastName", lastName);
				user.put("partyId", partyId);
				user.put("roleTypeId", roleTypeId);

				userList.add(user);
			}
			result.put("userList", userList);
		}

		return result;
	}
}