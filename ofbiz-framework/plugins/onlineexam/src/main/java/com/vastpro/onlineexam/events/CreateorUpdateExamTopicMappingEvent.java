package com.vastpro.onlineexam.events;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class CreateorUpdateExamTopicMappingEvent {

	public static final String module = CreateorUpdateExamTopicMappingEvent.class.getName();

	/*
	 * 
	 * This event is used to either create or update a topics on the topic master
	 * entity and it should create or update a record of the exam master mapping
	 * entity.
	 * 
	 */

	public static String createOrUpdateExamTopicEvent(HttpServletRequest request, HttpServletResponse response) {

		Delegator delegator = (Delegator) request.getAttribute(ConstantValue.DELEGATOR);
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute(ConstantValue.DISPATCHER);
		String examId = (String) request.getAttribute(ConstantValue.EXAM_ID);
		String topicId =null;
		String topicName = (String) request.getAttribute(ConstantValue.TOPIC_NAME);
		String percentage = (String) request.getAttribute(ConstantValue.PERCENTAGE);
		Long onetopicPercentage = Long.parseLong(percentage);
		String topicPassPercentage = (String) request.getAttribute(ConstantValue.TOPIC_PASS_PERCENTAGE);
		GenericValue userLogin=(GenericValue)request.getSession().getAttribute("userLogin");
	
		//String questionsPerExam = (String) request.getAttribute(ConstantValue.QUESTION_PER_EXAM);
	
			try {
				Debug.logVerbose("create service called............",module);
				Map<String,Object> result=dispatcher.runSync("createTopicMaster", UtilMisc.toMap("topicName",topicName,"userLogin",userLogin));
				topicId=(String) result.get("topicId");
				Debug.logInfo("topic Id",topicId);
				request.setAttribute("service output...", result);
			} catch (GenericServiceException e) {
				request.setAttribute(ConstantValue.ERROR_MESSAGE, "value should not be added");
				e.printStackTrace();
			}
		try {

			long totalpercentage = 0;
			List<GenericValue> examDetails = EntityQuery.use(delegator).from("ExamTopicMappingMaster")
					.where("examId", examId).queryList();
			System.out.println("This is list of exam details...."+ examDetails);
			if (examDetails != null) {
				for (GenericValue oneGenericvalues : examDetails) {
					BigDecimal percentageValue = (BigDecimal) oneGenericvalues.get("percentage");
					Long percentagevalue=percentageValue.longValue();
					totalpercentage = totalpercentage + percentagevalue;
				}
				System.out.println("The total percentage are.................>" + totalpercentage);
				if (totalpercentage <= 100) {
					if ((totalpercentage + onetopicPercentage > 100)) {
						request.setAttribute(ConstantValue.ERROR_MESSAGE, "remaining percentage should be" + (100-totalpercentage));
						request.setAttribute("totalpercentage", totalpercentage);
						return "error";
					} else {
						GenericValue oneGenericValue = EntityQuery.use(delegator).from("ExamMaster")
								.where("examId", examId).cache().queryOne();
						long noOfquestions = (Long) oneGenericValue.get("noOfQuestions");
						System.out.println("No of questions..." + noOfquestions);
//						long noOfQuestionPerExam = Long.parseLong(noOfquestions);
						Long topicPercentage = Long.parseLong(percentage);
						System.out.println("Percentage" + topicPercentage);
						long questionsPerExam = (topicPercentage * noOfquestions)/100;
						System.out.println("noofQuestions" + questionsPerExam);
						Map<String, Object> examTopicResultMap = null;
						Debug.logInfo("=====create service called...... =========", module);
						dispatcher.runSync("createExamTopicMappingMaster",
								UtilMisc.toMap("examId", examId,"topicId",topicId,"percentage", percentage,
										"topicPassPercentage", topicPassPercentage, "questionsPerExam",
										questionsPerExam,"userLogin",userLogin));
						request.setAttribute("totalpercentage", totalpercentage);
						return "success";
					}
				}

				else {
					request.setAttribute(ConstantValue.TOPIC_PASS_PERCENTAGE,
							"No more the topic percentage should be added because you already entered details");
					return "error";
				}
			}

			else {
				GenericValue oneGenericValue = EntityQuery.use(delegator).from("ExamMaster").where("examId", examId)
						.cache().queryOne();
				String noOfquestions = (String) oneGenericValue.get("noOfQuestions");
				System.out.println("no of wues" + noOfquestions);
				long noOfQuestionPerExam = Long.parseLong(noOfquestions);
				System.out.println("noOfQuestionPerExam"+ noOfQuestionPerExam);
				Long topicPercentage = Long.parseLong(percentage);
				long questionsPerExam = topicPercentage / 100 * noOfQuestionPerExam;
				System.out.println("noofQuestions" + questionsPerExam);
				Map<String, Object> examTopicResultMap = null;
				Debug.logInfo("=====create service called...... =========", module);
				dispatcher.runSync("createExamTopicMappingMaster",
						UtilMisc.toMap("examId", examId,"topicId",topicId,"percentage", percentage,
								"topicPassPercentage", topicPassPercentage, "questionsPerExam", questionsPerExam,"userLogin",userLogin));
				return "success";
			}

		} catch (GenericServiceException | GenericEntityException e) {
			String errMsg = "Unable to create or update records in examTopicMappingmasterentity: " + e.toString();
			request.setAttribute("_ERROR_MESSAGE_", errMsg);
			return "error";
		}

	}
	
	public static String updateExamTopicEvent(HttpServletRequest request,HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute(ConstantValue.DELEGATOR);
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute(ConstantValue.DISPATCHER);
		String examId = (String) request.getAttribute(ConstantValue.EXAM_ID);
		String topicId = (String) request.getAttribute(ConstantValue.TOPIC_ID);
		String topicName = (String) request.getAttribute(ConstantValue.TOPIC_NAME);
		String percentage = (String) request.getAttribute(ConstantValue.PERCENTAGE);
		Long onetopicPercentage = Long.parseLong(percentage);
		String topicPassPercentage = (String) request.getAttribute(ConstantValue.TOPIC_PASS_PERCENTAGE);
//		String questionsPerExam = (String) request.getAttribute(ConstantValue.QUESTION_PER_EXAM);

		try {

			long totalpercentage = 0;
			List<GenericValue> examDetails = EntityQuery.use(delegator).from("ExamTopicMappingMaster")
					.where("examId", examId).queryList();
			System.out.println("This is list of exam details...."+ examDetails);
			if (examDetails != null) {
				for (GenericValue oneGenericvalues : examDetails) {
					BigDecimal percentageValue = (BigDecimal) oneGenericvalues.get("percentage");
					Long percentagevalue=percentageValue.longValue();
					totalpercentage = totalpercentage + percentagevalue;
				}
				System.out.println("The total percentage are.................>" + totalpercentage);
				if (totalpercentage <= 100) {
					if ((totalpercentage + onetopicPercentage > 100)) {
						request.setAttribute(ConstantValue.ERROR_MESSAGE, "remaining percentage should be" + (100-totalpercentage));
						return "error";
					} else {
						GenericValue oneGenericValue = EntityQuery.use(delegator).from("ExamMaster")
								.where("examId", examId).cache().queryOne();
						long noOfquestions = (Long) oneGenericValue.get("noOfQuestions");
						Long topicPercentage = Long.parseLong(percentage);
						System.out.println("Percentage" + topicPercentage);
						long questionsPerExam = (topicPercentage * noOfquestions)/100;
						System.out.println("noofQuestions" + questionsPerExam);
						Map<String, Object> examTopicResultMap = null;
						Debug.logInfo("=====create service called...... =========", module);
						dispatcher.runSync("updateExamTopicMappingMaster",
								UtilMisc.toMap("examId", examId, "topicId", topicId, "percentage", percentage,
										"topicPassPercentage", topicPassPercentage, "questionsPerExam",
										questionsPerExam));
						return "success";
					}
				}

				else {
					request.setAttribute(ConstantValue.TOPIC_PASS_PERCENTAGE,
							"No more the topic percentage should be added because you already entered details");
					return "error";
				}
			}

			else {
				GenericValue oneGenericValue = EntityQuery.use(delegator).from("ExamMaster").where("examId", examId)
						.cache().queryOne();
				String noOfquestions = (String) oneGenericValue.get("noOfQuestions");
				System.out.println("no of wues" + noOfquestions);
				long noOfQuestionPerExam = Long.parseLong(noOfquestions);
				System.out.println("noOfQuestionPerExam"+ noOfQuestionPerExam);
				Long topicPercentage = Long.parseLong(percentage);
				long questionsPerExam = topicPercentage / 100 * noOfQuestionPerExam;
				System.out.println("noofQuestions" + questionsPerExam);
				Map<String, Object> examTopicResultMap = null;
				Debug.logInfo("=====create service called...... =========", module);
				dispatcher.runSync("createExamTopicMappingMaster",
						UtilMisc.toMap("examId", examId, "topicId", topicId, "percentage", percentage,
								"topicPassPercentage", topicPassPercentage, "questionsPerExam", questionsPerExam));
				return "success";
			}

		} catch (GenericServiceException | GenericEntityException e) {
			String errMsg = "Unable to create or update records in examTopicMappingmasterentity: " + e.toString();
			request.setAttribute("_ERROR_MESSAGE_", errMsg);
			return "error";
		}

	}

}
