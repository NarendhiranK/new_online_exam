package com.vastpro.onlineexam.events;


import java.util.List;

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

public class QuestionMasterEvent {
	public static final String module = QuestionMasterEvent.class.getName();
	// create or update
	static String entityname = "QuestionMasterentity : ";

	public static String createQuestionMasterEvent(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute(ConstantValue.DELEGATOR);
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute(ConstantValue.DISPATCHER);
		Map<String, Object> combinedMap = UtilHttp.getCombinedMap(request);
//		String questionId = (String) combinedMap.get(ConstantValue.QUESTION_ID);
		String questionDetail = (String) combinedMap.get(ConstantValue.QUESTION_DETAIL);
		Debug.log("questionDetail..."+ questionDetail);
		String optionA = (String) combinedMap.get(ConstantValue.OPTION_A);
		Debug.log("option A" + optionA);
		String optionB = (String) combinedMap.get(ConstantValue.OPTION_B);
		Debug.log("optionB" + optionB);
		String optionC = (String) combinedMap.get(ConstantValue.OPTION_C);
		Debug.log("optionC"+optionC);
		String optionD = (String) combinedMap.get(ConstantValue.OPTION_D);
		Debug.log("optionD" + optionD);
		String optionE = (String) combinedMap.get(ConstantValue.OPTION_E);
		Debug.log("optionE" + optionE);
		String answer = (String) combinedMap.get(ConstantValue.ANSWER);
		Debug.log("answer" + answer);
		String numAnswers = (String) combinedMap.get(ConstantValue.NUM_ANSWERS);
		Debug.log("numAnswers" + numAnswers);
		String questionType = (String) combinedMap.get(ConstantValue.QUESTION_TYPE);
		Debug.log("questionTYpe" + questionType);
		String difficultyLevel = (String) combinedMap.get(ConstantValue.DIFFICULTY_LEVEL);
		Debug.log("difficulty level" + difficultyLevel);
		String answerValue = (String) combinedMap.get(ConstantValue.ANSWER_VALUE);
		Debug.log("answerValue..."+answerValue);
		String topicId = (String) combinedMap.get(ConstantValue.TOPIC_ID);
		Debug.log("topicid" + topicId);
		String negativeMarkValue=(String)combinedMap.get(ConstantValue.NEGATIVE_MARK_VALUE);
		try {		
			List<GenericValue> questionMasterList = EntityQuery.use(delegator).select(ConstantValue.QUESTION_ID)
					.from(ConstantValue.QUESTION_MASTER).where(ConstantValue.TOPIC_ID, topicId).queryList();
			GenericValue genericExamTopicMappingMasterValue = EntityQuery.use(delegator).from(ConstantValue.EXAM_TOPIC_MAPPING_MASTER)
					.where("topicId", topicId).queryOne();
			Long questionsPerExam = (Long) genericExamTopicMappingMasterValue.get(ConstantValue.QUESTION_PER_EXAM);
			Debug.logInfo("no of questions", questionsPerExam.toString());
			
            Map<String,Object> questionMasterParameters =UtilMisc.toMap("questionDetail", questionDetail, "optionA", optionA,
					"optionB", optionB, "optionC", optionC, "optionD", optionD, "optionE", optionE,
					"answer", answer, "numAnswers", numAnswers, "questionType", questionType,
					"difficultyLevel", difficultyLevel,"answerValue",answerValue,"topicId",topicId,ConstantValue.NEGATIVE_MARK_VALUE,negativeMarkValue);
			int count = 0;
			if (questionMasterList != null) {

				for (GenericValue oneQuestionRecord : questionMasterList) {
					Long questionNo = (Long) oneQuestionRecord.get(ConstantValue.QUESTION_ID);
					Debug.logInfo("questionNo", questionNo.toString());
					count++;

				}
				if (count <questionsPerExam) {
					Map<String,Object> result=dispatcher.runSync("createQuestionMaster",questionMasterParameters);
					request.setAttribute(ConstantValue.SUCCESS_MESSAGE, result);

				} else {
					System.out.println("No questions to be added for this topic...............................>");
					request.setAttribute("Error_Msg", "error to add the questions");
				}
			} 
			else {
				Map<String,Object> createQuestionMasterResult =dispatcher.runSync("createQuestionMaster",questionMasterParameters);
				    Debug.logInfo("questionMasterResult", createQuestionMasterResult.toString());
			}
		}

		catch (GenericServiceException | GenericEntityException e) {
			String errMsg = "Unable to create or update records in" + entityname + e.toString();
			request.setAttribute("_ERROR_MESSAGE_", errMsg);
			return "error";
		}
		return "success";
	}

	// update
	public static String updateQuestionMasterEvent(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> combinedMap = UtilHttp.getCombinedMap(request);
		Delegator delegator = (Delegator) combinedMap.get(ConstantValue.DELEGATOR);
		LocalDispatcher dispatcher = (LocalDispatcher) combinedMap.get(ConstantValue.DISPATCHER);
		
		String questionId = (String) combinedMap.get(ConstantValue.QUESTION_ID);
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
		try {
			Debug.logInfo("===== =========", module);
			dispatcher.runSync("updateQuestionMaster",
					UtilMisc.toMap("questionId", questionId, "questionDetail", questionDetail, "optionA", optionA,
							"optionB", optionB, "optionC", optionC, "optionD", optionD, "optionE", optionE, "answer",
							answer, "numAnswers", numAnswers));
		} catch (GenericServiceException e) {
			String errMsg = "Unable to update records in QuestionMasterentity: " + e.toString();
			request.setAttribute("_ERROR_MESSAGE_", errMsg);
			return "error";
		}
		request.setAttribute("_EVENT_MESSAGE_", "QuestionMaster updated succesfully.");
		return "success";
	}

	// delete
	public static String deleteQuestionMasterEvent(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute(ConstantValue.DELEGATOR);
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute(ConstantValue.DISPATCHER);
		// String ofbizDemoTypeId = request.getParameter("ofbizDemoTypeId");
		String questionId = (String) request.getAttribute("questionId");
//		        String questionDetail = (String)request.getAttribute("questionDetail");
//		        String optionA = (String)request.getAttribute("optionA");
//		        String optionB = (String)request.getAttribute("optionB");
//		        String optionC = (String)request.getAttribute("optionC");
//		        String optionD = (String)request.getAttribute("optionD");
//		        String optionE = (String)request.getAttribute("optionE");
//		        String answer =(String) request.getAttribute("answer");
//		        String numAnswers = (String)request.getAttribute("numAnswers");
//		        String questionType =(String) request.getAttribute("questionType");
//		        String difficultyLevel =(String)request.getAttribute("difficultyLevel");
		try {
			Debug.logInfo("===== =========", module);
			dispatcher.runSync("deleteQuestionMaster", UtilMisc.toMap("questionId", questionId));
		} catch (GenericServiceException e) {
			String errMsg = "Unable to delete records in QuestionMasterentity: " + e.toString();
			request.setAttribute("_ERROR_MESSAGE_", errMsg);
			return "error";
		}
		request.setAttribute("_EVENT_MESSAGE_", "QuestionMaster deleted succesfully.");
		return "success";
	}

}

