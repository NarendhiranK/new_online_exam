//package com.vastpro.onlineexam.events;
//
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.ofbiz.base.util.Debug;
//import org.apache.ofbiz.base.util.UtilHttp;
//import org.apache.ofbiz.base.util.UtilMisc;
//import org.apache.ofbiz.entity.Delegator;
//import org.apache.ofbiz.entity.GenericEntityException;
//import org.apache.ofbiz.entity.GenericValue;
//import org.apache.ofbiz.entity.util.EntityQuery;
//import org.apache.ofbiz.service.GenericServiceException;
//import org.apache.ofbiz.service.LocalDispatcher;
//
//import com.vastpro.onlineexam.constants.ConstantValue;
//
//public class UserExamMappingMasterEvent {
//	public static final String module = UserExamMappingMasterEvent.class.getName();
//
//	public static String createOrUpdateExamMasterEvent(HttpServletRequest request, HttpServletResponse response) {
//		Map<String, Object> combinedMap = UtilHttp.getCombinedMap(request);
//		Delegator delegator = (Delegator) combinedMap.get(ConstantValue.DELEGATOR);
//		LocalDispatcher dispatcher = (LocalDispatcher) combinedMap.get(ConstantValue.DISPATCHER);
//		String partyId = (String) combinedMap.get(ConstantValue.PARTY_ID);
//		String examId = (String) combinedMap.get(ConstantValue.EXAM_ID);
//		Object allowedAttempts = (String) combinedMap.get(ConstantValue.ALLOWED_ATTEMPTS);
//		String noOfAttempts = (String) combinedMap.get(ConstantValue.NO_OF_ATTEMPTS);
//		String lastPerformanceDate = (String) combinedMap.get(ConstantValue.LAST_PERFORMANCE_DATE);
//		String timeoutDays = (String) combinedMap.get(ConstantValue.TIME_OUT_DAYS);
//		String durationMinutes = (String) combinedMap.get(ConstantValue.DURATION_MINUTES);
//		String passPercentage = (String) combinedMap.get(ConstantValue.PASS_PERCENTAGE);
//		String questionsRandomized = (String) combinedMap.get(ConstantValue.QUESTIONS_RANDOMIZED);
//		String answersMust = (String) combinedMap.get(ConstantValue.ANSWERS_MUST);
//		String enableNegativeMark = (String) combinedMap.get(ConstantValue.ENABLE_NEGATIVE_MARK);
//		String negativeMarkValue = (String) combinedMap.get(ConstantValue.NEGATIVE_MARK_VALUE);
//		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
//		
//		Map<String, Object> userExamMappingMasterParameters = UtilMisc.toMap(ConstantValue.EXAM_ID, examId,
//				ConstantValue.ALLOWED_ATTEMPTS, allowedAttempts, ConstantValue.NO_OF_ATTEMPTS, noOfAttempts,
//				ConstantValue.LAST_PERFORMANCE_DATE, lastPerformanceDate, ConstantValue.TIME_OUT_DAYS, timeoutDays,
//				ConstantValue.DURATION_MINUTES, durationMinutes, ConstantValue.PASS_PERCENTAGE, passPercentage,
//				ConstantValue.QUESTIONS_RANDOMIZED, questionsRandomized, ConstantValue.ANSWERS_MUST, answersMust,
//				ConstantValue.ENABLE_NEGATIVE_MARK, enableNegativeMark, ConstantValue.NEGATIVE_MARK_VALUE,
//				negativeMarkValue);
//		try {
//			GenericValue genericvalue = EntityQuery.use(delegator).from(ConstantValue.EXAM_MASTER).where(ConstantValue.EXAM_ID, examId).cache()
//					.queryOne();
//
//			if (genericvalue != null) {
//				String myexamId = delegator.getNextSeqId(examId);
//				Debug.logInfo("=====update service called...... =========", module);
//				dispatcher.runSync("updateExamMaster", userExamMappingMasterParameters);
//			} else {
//				Debug.logInfo("=====create service called.....! =========", module);
//				dispatcher.runSync("createExamMaster", userExamMappingMasterParameters);
//			}
//
//		} catch (GenericServiceException | GenericEntityException e) {
//			String errMsg = "Unable to create or update records in user exam mapping master: " + e.toString();
//			request.setAttribute("_ERROR_MESSAGE_", errMsg);
//			return "error";
//		}
//		request.setAttribute("_EVENT_MESSAGE_", "user exam mapping master created or updated succesfully.");
//		return "success";
//	}
//
//}

package com.vastpro.onlineexam.events;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import com.vastpro.onlineexam.constants.ConstantValue;

public class UserExamMappingMasterEvent {
	public static final String MODULE = UserExamMappingMasterEvent.class.getName();

	public static String createUserExam(HttpServletRequest request, HttpServletResponse response) {
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute(ConstantValue.USERLOGIN);
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute(ConstantValue.DISPATCHER);
		Delegator delegator = (Delegator) request.getAttribute(ConstantValue.DELEGATOR);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String result = ConstantValue.ERROR;

		String partyId = (String) request.getAttribute(ConstantValue.PARTY_ID);
		String examId = (String) request.getAttribute(ConstantValue.EXAM_ID);
		String allowedAttempts = (String) request.getAttribute(ConstantValue.ALLOWED_ATTEMPTS);
		String noOfAttempts = (String) request.getAttribute(ConstantValue.NO_OF_ATTEMPTS);
		String lastPerformanceDate = (String) request.getAttribute(ConstantValue.LAST_PERFORMANCE_DATE);
		String timeoutDays = (String) request.getAttribute(ConstantValue.TIME_OUT_DAYS);
		String passwordChangesAuto = (String) request.getAttribute(ConstantValue.PASSWORD_CHANGES_AUTO);
		String canSplitExams = (String) request.getAttribute(ConstantValue.CAN_SPLIT_EXAMS);
		String canSeeDetailedResults = (String) request.getAttribute(ConstantValue.CAN_SEE_DETAILED_RESULTS);
		String maxSplitAttempts = (String) request.getAttribute(ConstantValue.MAX_SPLIT_ATTEMPTS);

		Map<String, Object> registerMap = new HashMap<String, Object>();
		registerMap.put(ConstantValue.PARTY_ID, partyId);
		registerMap.put(ConstantValue.EXAM_ID, examId);
		registerMap.put(ConstantValue.ALLOWED_ATTEMPTS, allowedAttempts);
		registerMap.put(ConstantValue.NO_OF_ATTEMPTS, noOfAttempts);
		registerMap.put(ConstantValue.LAST_PERFORMANCE_DATE, lastPerformanceDate);
		registerMap.put(ConstantValue.TIME_OUT_DAYS, timeoutDays);
		registerMap.put(ConstantValue.PASSWORD_CHANGES_AUTO, passwordChangesAuto);
		registerMap.put(ConstantValue.CAN_SPLIT_EXAMS, canSplitExams);
		registerMap.put(ConstantValue.CAN_SEE_DETAILED_RESULTS, canSeeDetailedResults);
		registerMap.put(ConstantValue.MAX_SPLIT_ATTEMPTS, maxSplitAttempts);
		registerMap.put(ConstantValue.USERLOGIN, userLogin);

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> examIdAndName = new ArrayList<Map<String, Object>>();

		Map<String, Object> userExamResultMap = null;
		try {
			userExamResultMap = dispatcher.runSync("createUserExamMappingMaster", registerMap);

			if (ServiceUtil.isSuccess(userExamResultMap)) {
				result = ConstantValue.SUCCESS;
				// request.setAttribute("SERVICE STATUS", "Record created successfully in
				// entity");
				resultMap.put("SERVICE STATUS", userExamResultMap);
			} else {
				// request.setAttribute("SERVICE STATUS", "Fail to create record in entity");
				resultMap.put("SERVICE STATUS", userExamResultMap);
				result = ConstantValue.ERROR;
			}

			try {
				List<GenericValue> getExams = EntityQuery.use(delegator).from(ConstantValue.EXAM_MASTER).cache()
						.queryList();
				if (UtilValidate.isNotEmpty(getExams)) {

					for (GenericValue genericValue : getExams) {
						String examIdFromExamMaster = (String) genericValue.get(ConstantValue.EXAM_ID);

						String examNameFromExamMaster = (String) genericValue.get(ConstantValue.EXAM_NAME);
						examIdAndName.add(UtilMisc.toMap("Exam ID :", examIdFromExamMaster));
						examIdAndName.add(UtilMisc.toMap("Exam Name :", examNameFromExamMaster));

					}

					request.setAttribute(ConstantValue.SUCCESS_MESSAGE, "Fetched all the datas");

				}
			} catch (GenericEntityException e) {
				request.setAttribute(ConstantValue.ERROR_MESSAGE, "There are no records in ExamMaster");

			}

			resultList.add(userExamResultMap);
			resultList.add(UtilMisc.toMap("examIdAndName ", examIdAndName));
			request.setAttribute("resultMap", resultMap);
		} catch (GenericServiceException e) {

			e.printStackTrace();
			return result;
		}
		request.setAttribute("resultOfMessageAndExamDetails", resultList);
		return result;

	}
}
