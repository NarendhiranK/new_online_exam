package com.vastpro.onlineexam.events;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilHttp;
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

public class AddorUpdateQuestionMasterEvent {

	public static final String MODULE = AddorUpdateQuestionMasterEvent.class.getName();

	/*
	 * 
	 * This event is used to either create or update the questions in the question
	 * master entity.
	 * 
	 * 
	 */
	public static String createOrUpdateQuestionEvent(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> combinedMap = UtilHttp.getCombinedMap(request);

		Delegator delegator = (Delegator) combinedMap.get(ConstantValue.DELEGATOR);
		LocalDispatcher dispatcher = (LocalDispatcher) combinedMap.get(ConstantValue.DISPATCHER);

		String questionIId = (String) combinedMap.get(ConstantValue.QUESTION_ID);
		Long questionId = Long.parseLong(questionIId);
		Debug.logInfo("QuestionId...", "=" + questionId);
		String questionDetail = (String) combinedMap.get(ConstantValue.QUESTION_DETAIL);
		String optionA = (String) combinedMap.get(ConstantValue.OPTION_A);
		String optionB = (String) combinedMap.get(ConstantValue.OPTION_B);
		String optionC = (String) combinedMap.get(ConstantValue.OPTION_C);
		String optionD = (String) combinedMap.get(ConstantValue.OPTION_D);
		String optionE = (String) combinedMap.get(ConstantValue.OPTION_E);
		String answer = (String) combinedMap.get(ConstantValue.ANSWER);
		String numAnswers = (String) combinedMap.get(ConstantValue.NUM_ANSWERS);
		String questionType = (String) combinedMap.get(ConstantValue.QUESTION_TYPE);
		String difficultyLevel = (String) combinedMap.get(ConstantValue.DIFFICULTY_LEVEL);
		String answerValue = (String) combinedMap.get(ConstantValue.ANSWER_VALUE);
		String topicId = (String) combinedMap.get(ConstantValue.TOPIC_ID);
		String negativeMarkValue = (String) combinedMap.get(ConstantValue.NEGATIVE_MARK_VALUE);
		try {
			GenericValue getQuestionMasterData = EntityQuery.use(delegator).from(ConstantValue.QUESTION_MASTER)
					.where(ConstantValue.QUESTION_ID, questionId).cache().queryOne();

			if (UtilValidate.isNotEmpty(getQuestionMasterData)) {
				Map<String, Object> updateQuestionMasterMap = new HashMap<String, Object>();
				Debug.logInfo("=====update service called...... =========", MODULE);
				updateQuestionMasterMap = dispatcher.runSync("updateQuestionMaster", UtilMisc.toMap(
						ConstantValue.QUESTION_ID, questionId, ConstantValue.QUESTION_DETAIL, questionDetail,
						ConstantValue.OPTION_A, optionA, ConstantValue.OPTION_B, optionB, ConstantValue.OPTION_C,
						optionC, ConstantValue.OPTION_D, optionD, ConstantValue.OPTION_E, optionE, ConstantValue.ANSWER,
						answer, ConstantValue.NUM_ANSWERS, numAnswers, ConstantValue.QUESTION_TYPE, questionType,
						ConstantValue.DIFFICULTY_LEVEL, difficultyLevel, ConstantValue.ANSWER_VALUE, answerValue,
						ConstantValue.TOPIC_ID, topicId, ConstantValue.NEGATIVE_MARK_VALUE, negativeMarkValue));
				if (ServiceUtil.isSuccess(updateQuestionMasterMap)) {
					request.setAttribute(ConstantValue.SUCCESS_MESSAGE, ConstantValue.SUCCESS);
					request.setAttribute(ConstantValue.SUCCESS_MESSAGE, "Updated Question in Question master table");
					return "success";
				} else {
					request.setAttribute(ConstantValue.ERROR_MESSAGE, ConstantValue.ERROR);
					request.setAttribute(ConstantValue.ERROR_MESSAGE, ConstantValue.SERVICE_FAILED);
					request.setAttribute(ConstantValue.SUCCESS_MESSAGE,
							"Could not update Question in Question master table");
					return "error";
				}
			} else {

				Debug.logInfo("=====create service called.....! =========", MODULE);
				Map<String, Object> createQuestionMasterMap = new HashMap<String, Object>();
				createQuestionMasterMap = dispatcher.runSync("createQuestionMaster", UtilMisc.toMap(
						ConstantValue.QUESTION_ID, questionId, ConstantValue.QUESTION_DETAIL, questionDetail,
						ConstantValue.OPTION_A, optionA, ConstantValue.OPTION_B, optionB, ConstantValue.OPTION_C,
						optionC, ConstantValue.OPTION_D, optionD, ConstantValue.OPTION_E, optionE, ConstantValue.ANSWER,
						answer, ConstantValue.NUM_ANSWERS, numAnswers, ConstantValue.QUESTION_TYPE, questionType,
						ConstantValue.DIFFICULTY_LEVEL, difficultyLevel, ConstantValue.ANSWER_VALUE, answerValue,
						ConstantValue.TOPIC_ID, topicId, ConstantValue.NEGATIVE_MARK_VALUE, negativeMarkValue));
				if (ServiceUtil.isSuccess(createQuestionMasterMap)) {
					request.setAttribute(ConstantValue.SUCCESS_MESSAGE, ConstantValue.SUCCESS);
					request.setAttribute(ConstantValue.SUCCESS_MESSAGE, "Created Question in Question master table");
					return "success";
				} else {
					request.setAttribute(ConstantValue.ERROR_MESSAGE, ConstantValue.ERROR);
					request.setAttribute(ConstantValue.ERROR_MESSAGE, ConstantValue.SERVICE_FAILED);
					request.setAttribute(ConstantValue.SUCCESS_MESSAGE,
							"Could not create Question in Question master table");
					return "error";
				}
			}

		} catch (GenericServiceException | GenericEntityException e) {
			String errMsg = "Unable to create or update records in QuestionMasterentity: " + e.toString();
			request.setAttribute(ConstantValue.ERROR_MESSAGE, errMsg);
			return "error";
		}

	}

}
