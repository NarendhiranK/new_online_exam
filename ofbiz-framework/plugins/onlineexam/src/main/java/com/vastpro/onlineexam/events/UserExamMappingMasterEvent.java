package com.vastpro.onlineexam.events;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilHttp;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;

import com.vastpro.onlineexam.constants.ConstantValue;

public class UserExamMappingMasterEvent {
	public static final String module = UserExamMappingMasterEvent.class.getName();

	public static String createOrUpdateExamMasterEvent(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> combinedMap = UtilHttp.getCombinedMap(request);
		Delegator delegator = (Delegator) combinedMap.get(ConstantValue.DELEGATOR);
		LocalDispatcher dispatcher = (LocalDispatcher) combinedMap.get(ConstantValue.DISPATCHER);
		String partyId = (String) combinedMap.get(ConstantValue.PARTY_ID);
		String examId = (String) combinedMap.get(ConstantValue.EXAM_ID);
		Object allowedAttempts = (String) combinedMap.get(ConstantValue.ALLOWED_ATTEMPTS);
		String noOfAttempts = (String) combinedMap.get(ConstantValue.NO_OF_ATTEMPTS);
		String lastPerformanceDate = (String) combinedMap.get(ConstantValue.LAST_PERFORMANCE_DATE);
		String timeoutDays = (String) combinedMap.get(ConstantValue.TIME_OUT_DAYS);
		String durationMinutes = (String) combinedMap.get(ConstantValue.DURATION_MINUTES);
		String passPercentage = (String) combinedMap.get(ConstantValue.PASS_PERCENTAGE);
		String questionsRandomized = (String) combinedMap.get(ConstantValue.QUESTIONS_RANDOMIZED);
		String answersMust = (String) combinedMap.get(ConstantValue.ANSWERS_MUST);
		String enableNegativeMark = (String) combinedMap.get(ConstantValue.ENABLE_NEGATIVE_MARK);
		String negativeMarkValue = (String) combinedMap.get(ConstantValue.NEGATIVE_MARK_VALUE);
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		
		Map<String, Object> userExamMappingMasterParameters = UtilMisc.toMap(ConstantValue.EXAM_ID, examId,
				ConstantValue.ALLOWED_ATTEMPTS, allowedAttempts, ConstantValue.NO_OF_ATTEMPTS, noOfAttempts,
				ConstantValue.LAST_PERFORMANCE_DATE, lastPerformanceDate, ConstantValue.TIME_OUT_DAYS, timeoutDays,
				ConstantValue.DURATION_MINUTES, durationMinutes, ConstantValue.PASS_PERCENTAGE, passPercentage,
				ConstantValue.QUESTIONS_RANDOMIZED, questionsRandomized, ConstantValue.ANSWERS_MUST, answersMust,
				ConstantValue.ENABLE_NEGATIVE_MARK, enableNegativeMark, ConstantValue.NEGATIVE_MARK_VALUE,
				negativeMarkValue);
		try {
			GenericValue genericvalue = EntityQuery.use(delegator).from(ConstantValue.EXAM_MASTER).where(ConstantValue.EXAM_ID, examId).cache()
					.queryOne();

			if (genericvalue != null) {
				String myexamId = delegator.getNextSeqId(examId);
				Debug.logInfo("=====update service called...... =========", module);
				dispatcher.runSync("updateExamMaster", userExamMappingMasterParameters);
			} else {
				Debug.logInfo("=====create service called.....! =========", module);
				dispatcher.runSync("createExamMaster", userExamMappingMasterParameters);
			}

		} catch (GenericServiceException | GenericEntityException e) {
			String errMsg = "Unable to create or update records in ExamMasterentity: " + e.toString();
			request.setAttribute("_ERROR_MESSAGE_", errMsg);
			return "error";
		}
		request.setAttribute("_EVENT_MESSAGE_", "ExamMaster created or updated succesfully.");
		return "success";
	}

}
