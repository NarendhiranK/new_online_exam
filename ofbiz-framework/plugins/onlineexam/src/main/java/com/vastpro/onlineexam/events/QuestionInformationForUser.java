package com.vastpro.onlineexam.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import com.vastpro.onlineexam.constants.ConstantValue;

public class QuestionInformationForUser {

	public static String getQuestionsForUser(HttpServletRequest request, HttpServletResponse response) {

		Delegator delegator = (Delegator) request.getAttribute(ConstantValue.DELEGATOR);
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute(ConstantValue.DISPATCHER);
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute(ConstantValue.USERLOGIN);
		Debug.log("This is user login............." + userLogin);
		String examId = (String) request.getAttribute(ConstantValue.EXAM_ID);
		Debug.log("ExamId..............>" + examId);
		String partyId = (String) userLogin.get(ConstantValue.PARTY_ID);
		int sequenceNum = 1;
		Integer performanceId = null;
		List<Map<String, Object>> resultMapOfattemptsMaster = new LinkedList<Map<String, Object>>();
		LocalDateTime now = LocalDateTime.now();
		System.out.println(now);

		if (performanceId == null) {
			try {
				GenericValue examDetails = EntityQuery.use(delegator).from(ConstantValue.EXAM_MASTER)
						.where(ConstantValue.EXAM_ID, examId).queryOne();
				Long noOFQuestions = (Long) examDetails.get(ConstantValue.NO_OF_QUESTIONS);
				GenericValue userExamDetails = EntityQuery.use(delegator).from(ConstantValue.USER_EXAM_MAPPING_MASTER)
						.where(ConstantValue.EXAM_ID, examId, ConstantValue.PARTY_ID, partyId).queryFirst();
				Long allowedAttempts = (Long) userExamDetails.get(ConstantValue.ALLOWED_ATTEMPTS);
				String questionsRandomized = (String) examDetails.get(ConstantValue.QUESTIONS_RANDOMIZED);
				if (questionsRandomized.equalsIgnoreCase("y")) {

					
					
					
				} else {

					
					
					
					
				}
				Long noOfattemptsbyUser = (Long) userExamDetails.get(ConstantValue.NO_OF_ATTEMPTS);
//				Long noOfattempts=Long.parseLong(noOfattemptsbyUser);
				noOfattemptsbyUser = noOfattemptsbyUser + 1;
				Map<String, Object> resultOfUserAttemptMaster = dispatcher.runSync("createUserAttemptMaster",
						UtilMisc.toMap(ConstantValue.EXAM_ID, examId, ConstantValue.PARTY_ID, partyId,
								ConstantValue.NO_OF_QUESTIONS, noOFQuestions, ConstantValue.ATTEMPT_NUMBER,
								noOfattemptsbyUser));
				performanceId = (Integer) resultOfUserAttemptMaster.get(ConstantValue.PERFORMANCE_ID);
				Debug.log("Performance ID :::::::::::::::::::::::::" + performanceId);
				List<GenericValue> topicsListforExam = EntityQuery.use(delegator)
						.from(ConstantValue.EXAM_TOPIC_MAPPING_MASTER).where(ConstantValue.EXAM_ID, examId).queryList();
				if (UtilValidate.isNotEmpty(topicsListforExam)) {
					for (GenericValue oneTopic : topicsListforExam) {

						String topicId = (String) oneTopic.get(ConstantValue.TOPIC_ID);
						BigDecimal topicPassPercentage = (BigDecimal) oneTopic.get(ConstantValue.TOPIC_PASS_PERCENTAGE);
						Long totalQuestionsInThisTopic = (Long) oneTopic.get(ConstantValue.QUESTION_PER_EXAM);

						Map<String, Object> resultOfUserTopicMaster = dispatcher.runSync("createUserAttemptTopicMaster",
								UtilMisc.toMap(ConstantValue.PERFORMANCE_ID, performanceId, ConstantValue.TOPIC_ID,
										topicId, ConstantValue.TOPIC_PASS_PERCENTAGE, topicPassPercentage,
										ConstantValue.QUESTION_PER_EXAM, totalQuestionsInThisTopic));
						if (!ServiceUtil.isSuccess(resultOfUserAttemptMaster)) {
							request.setAttribute(ConstantValue.ERROR_MESSAGE,
									"Error in the creating the service recordds");
						} else {
							request.setAttribute(ConstantValue.SUCCESS_MESSAGE,
									"Record is created successfully in the usertopic attempt master");
						}

					}
				} else {
					request.setAttribute(ConstantValue.ERROR_MESSAGE, "There are no topics under this exam");
				}
				Map<String, Object> mapOfQuestions = new HashMap<>();
				Map<String, Object> mapOfQuestionsForTheTopic = new HashMap<>();
				Map<String, Object> callQuestionsService = null;

				List<GenericValue> listOfTopics = EntityQuery.use(delegator)
						.from(ConstantValue.EXAM_TOPIC_MAPPING_MASTER).where(ConstantValue.EXAM_ID, examId).queryList();
				Debug.log("List of topics for this exam..........." + listOfTopics);

				if (UtilValidate.isNotEmpty(listOfTopics)) {

					for (GenericValue oneTopicDetail : listOfTopics) {

						String topicId = (String) oneTopicDetail.get(ConstantValue.TOPIC_ID);

						String questionsPerExamS = oneTopicDetail.get(ConstantValue.QUESTION_PER_EXAM).toString();
						System.out.println("questionsPerExam=======" + questionsPerExamS + "topicId======" + topicId);
						Integer questionsPerExam = Integer.parseInt(questionsPerExamS);

						if (topicId != null && questionsPerExam != null) {

							List<GenericValue> listOfQuestions = EntityQuery.use(delegator)
									.from(ConstantValue.QUESTION_MASTER).where(ConstantValue.TOPIC_ID, topicId)
									.queryList();

//								
							Debug.log("List of questions................." + listOfQuestions);
							Debug.log("questions of the size........." + listOfQuestions.size());

							if (UtilValidate.isEmpty(listOfQuestions))
								for (GenericValue oneQuestion : listOfQuestions) {
									Long questionId = (Long) oneQuestion.get(ConstantValue.QUESTION_ID);
									Debug.log("Question Id's" + questionId);
								}

							Random random = new Random();
							List<GenericValue> listOfQuestionsForThisTopic = new ArrayList<>();
//								

							for (int i = 0; i < questionsPerExam; i++) {
								Debug.log("Inside for looop........................");
//									Long questionId=(Long)question.get(ConstantValue.QUESTION_ID);
//									Debug.log("List of questions" + questionId);
								int rand = random.nextInt(listOfQuestions.size());
								Debug.log("random value........" + rand);
								listOfQuestionsForThisTopic.add(listOfQuestions.get(rand));
								listOfQuestions.remove(rand);
							}
//								Debug.log("List of this questions for this topic", listOfQuestionsForThisTopic,"==========");

							// Add the selected questions to this map
							if (UtilValidate.isNotEmpty(listOfQuestionsForThisTopic)) {
								mapOfQuestions.put(topicId, listOfQuestionsForThisTopic);
							}
//								Debug.log("mapOfQuestions for this topic by map" + mapOfQuestions);

							List<List<GenericValue>> questionTopicList = new ArrayList<>();
							for (Entry<String, Object> entry : mapOfQuestions.entrySet()) {
								questionTopicList.add((List<GenericValue>) entry.getValue());
							}

							if (UtilValidate.isNotEmpty(questionTopicList)) {
								mapOfQuestionsForTheTopic.put("examQuestions", questionTopicList);
//								Debug.log("examQuestions........>" + questionTopicList);
								request.getSession().setAttribute("selectedQuestions", questionTopicList);
							}
						}

					}
					Debug.log("Map Of Questions..........................>" + mapOfQuestionsForTheTopic);
				}

				else {
					request.setAttribute(ConstantValue.ERROR_MESSAGE, "No topics under this topic");
				}

				List<List<GenericValue>> questionsInformationList = (List<List<GenericValue>>) request.getSession()
						.getAttribute("selectedQuestions");
				Debug.log("QuestionsInformationList................................................."
						+ questionsInformationList);
				List<GenericValue> detailsOfQuestion = new LinkedList<>();
				for (List<GenericValue> questions : questionsInformationList) {
					for (GenericValue oneQuestion : questions) {
						Long questionId = (Long) oneQuestion.get(ConstantValue.QUESTION_ID);
						Debug.log("Question id's................>" + questionId);
						GenericValue questionDetail = EntityQuery.use(delegator).from(ConstantValue.QUESTION_MASTER)
								.where(ConstantValue.QUESTION_ID, questionId).queryOne();
						detailsOfQuestion.add(questionDetail);
						Map<String, Object> resultMap = dispatcher.runSync("createUserAttemptAnswerMaster",
								UtilMisc.toMap(ConstantValue.QUESTION_ID, questionId, ConstantValue.PERFORMANCE_ID,
										performanceId, "sequenceNum", sequenceNum, ConstantValue.USERLOGIN, userLogin));
						// Increment sequenceNum
						++sequenceNum;

						if (!ServiceUtil.isSuccess(resultMap)) {

							request.setAttribute(ConstantValue.SUCCESS_MESSAGE, resultMap);
							return "error";
						}
					}

				}

				List<GenericValue> userAttemptAnswerMasterList = EntityQuery.use(delegator)
						.from("UserAttemptAnswerMaster").where(ConstantValue.PERFORMANCE_ID, performanceId).queryList();
				request.setAttribute("questionDetails", detailsOfQuestion);
				Debug.log("QuestionInformationList.............." + questionsInformationList);
				request.setAttribute("listOfQUestionsforUser", userAttemptAnswerMasterList);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return "success";
		}
//		 else {
//			try {
//				GenericValue userattemptmasterDetails = EntityQuery.use(delegator)
//						.from(ConstantValue.USER_ATTEMPT_MASTER).where(ConstantValue.PERFORMANCE_ID, performanceId)
//						.queryOne();
//				if (UtilValidate.isNotEmpty(userattemptmasterDetails)) {
//					Map<String, Object> userattemptDetails = new HashMap<String, Object>();
//					userattemptDetails.put(ConstantValue.PERFORMANCE_ID,
//							userattemptmasterDetails.get(ConstantValue.PERFORMANCE_ID));
//					userattemptDetails.put(ConstantValue.ATTEMPT_NUMBER,
//							userattemptmasterDetails.get(ConstantValue.ATTEMPT_NUMBER));
//					userattemptDetails.put(ConstantValue.PARTY_ID,
//							userattemptmasterDetails.get(ConstantValue.PARTY_ID));
//					userattemptDetails.put(ConstantValue.SCORE, userattemptmasterDetails.get(ConstantValue.SCORE));
//					userattemptDetails.put(ConstantValue.COMPLETED_DATE,
//							userattemptmasterDetails.get(ConstantValue.COMPLETED_DATE));
//					userattemptDetails.put(ConstantValue.NO_OF_QUESTIONS,
//							userattemptmasterDetails.get(ConstantValue.NO_OF_QUESTIONS));
//					userattemptDetails.put(ConstantValue.TOTAL_CORRECT,
//							userattemptmasterDetails.get(ConstantValue.TOTAL_CORRECT));
//					userattemptDetails.put(ConstantValue.TOTAL_WRONG,
//							userattemptmasterDetails.get(ConstantValue.TOTAL_WRONG));
//					userattemptDetails.put(ConstantValue.USER_PASSED,
//							userattemptmasterDetails.get(ConstantValue.USER_PASSED));
//					resultMapOfattemptsMaster.add(userattemptDetails);
//				} else {
//					request.setAttribute(ConstantValue.ERROR_MESSAGE,
//							"there is no records in the user attempt master record entity");
//				}
//
//				List<GenericValue> userAttemptTopicMasterDetails = EntityQuery.use(delegator)
//						.from(ConstantValue.USER_ATTEMPT_TOPIC_MASTER)
//						.where(ConstantValue.PERFORMANCE_ID, performanceId).queryList();
//				if (UtilValidate.isNotEmpty(userAttemptTopicMasterDetails)) {
//
//					for (GenericValue oneRecord : userAttemptTopicMasterDetails) {
//						Map<String, Object> oneRecordDetails = new HashMap<String, Object>();
////							Integer performanceId=(Integer)oneRecord.get(ConstantValue.PERFORMANCE_ID);
//						String topicId = (String) oneRecord.get(ConstantValue.TOPIC_ID);
//						String topicPassPercentage = (String) oneRecord.get(ConstantValue.TOPIC_PASS_PERCENTAGE);
//						String totalQuestionsInThisTopic = (String) oneRecord
//								.get(ConstantValue.TOTAL_QUESTIONS_IN_THIS_TOPIC);
//						String correctQuestionsInThisTopic = (String) oneRecord
//								.get(ConstantValue.CORRECT_QUESTIONS_IN_THIS_TOPIC);
//						String userTopicPercentage = (String) oneRecord.get(ConstantValue.USER_TOPIC_PERCENTAGE);
//
//					}
//
//				} else {
//					request.setAttribute(ConstantValue.ERROR_MESSAGE,
//							"there is no records in the user attempt topic master entity");
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//		}
		return "error";
		// return partyId;
	}
}
