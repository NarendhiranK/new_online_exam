package com.vastpro.onlineexam.events;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;

import com.vastpro.onlineexam.constants.ConstantValue;

public class AddorUpdateQuestionMasterEvent {

	
	
	public static final String module = AddorUpdateQuestionMasterEvent.class.getName();
	
	/*
	 * 
	 * This event is used to either create or update the questions on the question master entity.
	 * 
	 * 
	 * */
	public static String createOrUpdateQuestionEvent(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute(ConstantValue.DELEGATOR);
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute(ConstantValue.DISPATCHER);
		
		String questionIId = (String) request.getAttribute(ConstantValue.QUESTION_ID);
		Long questionId = Long.parseLong(questionIId);
		Debug.log("QuestinoId..." + questionId);
		String questionDetail = (String) request.getAttribute(ConstantValue.QUESTION_DETAIL);
		String optionA = (String) request.getAttribute(ConstantValue.OPTIONA);
		String optionB = (String) request.getAttribute(ConstantValue.OPTIONB);
		String optionC = (String) request.getAttribute(ConstantValue.OPTIONC);
		String optionD = (String) request.getAttribute(ConstantValue.OPTIOND);
		String optionE = (String) request.getAttribute(ConstantValue.OPTIONE);
		String answer = (String) request.getAttribute(ConstantValue.ANSWER);
		String numAnswers = (String) request.getAttribute(ConstantValue.NUM_ANSWERS);
		String questionType = (String) request.getAttribute(ConstantValue.QUESTION_TYPE);
		String difficultyLevel = (String) request.getAttribute(ConstantValue.DIFFICULTY_LEVEL);
		String answerValue = (String) request.getAttribute(ConstantValue.ANSWER_VALUE);
		String topicId = (String) request.getAttribute(ConstantValue.TOPIC_ID);
		String negativeMarkValue = (String) request.getAttribute(ConstantValue.NEGATIVE_MARK_VALUE);
		try {
			GenericValue genericvalue = EntityQuery.use(delegator).from("QuestionMaster")
					.where("questionId", questionId).cache().queryOne();

			if (genericvalue != null) {
				Debug.logInfo("=====update service called...... =========", module);
				dispatcher.runSync("updateQuestionMaster",
						UtilMisc.toMap("questionId", questionId, "questionDetail", questionDetail, "optionA", optionA,
								"optionB", optionB, "optionC", optionC, "optionD", optionD, "optionE", optionE,
								"answer", answer, "numAnswers", numAnswers, "questionType", questionType,
								"difficultyLevel", difficultyLevel, "answerValue", answerValue, "topicId", topicId,
								"negativeMarkValue", negativeMarkValue));
			} else {
				Debug.logInfo("=====create service called.....! =========", module);
				dispatcher.runSync("createQuestionMaster",
						UtilMisc.toMap("questionId", questionId, "questionDetail", questionDetail, "optionA", optionA,
								"optionB", optionB, "optionC", optionC, "optionD", optionD, "optionE", optionE,
								"answer", answer, "numAnswers", numAnswers, "questionType", questionType,
								"difficultyLevel", difficultyLevel, "answerValue", answerValue, "topicId", topicId,
								"negativeMarkValue", negativeMarkValue));
			}

		} catch (GenericServiceException | GenericEntityException e) {
			String errMsg = "Unable to create or update records in QuestionMasterentity: " + e.toString();
			request.setAttribute("_ERROR_MESSAGE_", errMsg);
			return "error";
		}
		request.setAttribute("_EVENT_MESSAGE_", "QuestionMaster created or updated succesfully.");
		return "success";
	}

}
