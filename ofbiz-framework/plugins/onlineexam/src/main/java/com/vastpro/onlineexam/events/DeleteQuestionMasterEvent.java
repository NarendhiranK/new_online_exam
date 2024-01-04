package com.vastpro.onlineexam.events;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilHttp;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;
import com.vastpro.onlineexam.constants.ConstantValue;

public class DeleteQuestionMasterEvent {
	
	/*
	 * This event is used to delete a record  on the question master entity.
	 * 
	 * */

	public static String deleteQuestion(HttpServletRequest request, HttpServletResponse response) {
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute(ConstantValue.DISPATCHER);

		Map<String, Object> combinedMap = UtilHttp.getCombinedMap(request);
		String questionId = combinedMap.get(ConstantValue.QUESTION_ID).toString();

		Debug.log("questionId.............."+questionId);
		Map<String, Object> deleteQuestion = null;
		try {
			deleteQuestion = dispatcher.runSync("deleteQuestionMaster",
					UtilMisc.toMap("questionId", questionId, "userLogin", userLogin));

			if (ServiceUtil.isSuccess(deleteQuestion)) {
				request.setAttribute(ConstantValue.SUCCESS_MESSAGE, ConstantValue.RECORD_DELETE);

			} else {
				request.setAttribute(ConstantValue.ERROR_MESSAGE, ConstantValue.SERVICE_FAILED);
				return "error";
			}

		} catch (GenericServiceException e) {
			request.setAttribute(ConstantValue.ERROR_MESSAGE, ConstantValue.SERVICE_FAILED);
			e.printStackTrace();
		}

		return "success";

	}
}
