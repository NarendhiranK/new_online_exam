package com.vastpro.onlineexam.events;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.UtilHttp;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import com.vastpro.onlineexam.constants.ConstantValue;

public class DeleteUserExamMappingEvent {

	public static String deleteUserExamMapping(HttpServletRequest request, HttpServletResponse response) {
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute(ConstantValue.USERLOGIN);
		Delegator delegator = (Delegator) request.getAttribute(ConstantValue.DELEGATOR);
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute(ConstantValue.DISPATCHER);
		Map<String, Object> combinedMap = UtilHttp.getCombinedMap(request);
		String examId = (String) combinedMap.get(ConstantValue.EXAM_ID);
		String partyId = (String) combinedMap.get(ConstantValue.PARTY_ID);
		Map<String, Object> deleteResultMap = null;
		try {

			deleteResultMap = dispatcher.runSync("deleteUserExamMappingMaster", UtilMisc.toMap(ConstantValue.EXAM_ID,
					examId, ConstantValue.PARTY_ID, partyId, ConstantValue.USERLOGIN, userLogin));
			if (ServiceUtil.isSuccess(deleteResultMap)) {
				request.setAttribute(ConstantValue.SUCCESS_MESSAGE, "Record has been successfully deleted.");
				return "success";
			} else {
				request.setAttribute(ConstantValue.ERROR_MESSAGE, ConstantValue.SERVICE_FAILED);
				request.setAttribute(ConstantValue.ERROR_MESSAGE,
						"Could not delete record in the entity. There is no mapping between the examId and userId.");
				return "error";
			}
		} catch (GenericServiceException e) {

			e.printStackTrace();
			return "error";
		}

	}

}
