package com.vastpro.onlineexam.events;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.Debug;
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

public class UserAttemptEvent {

	public static String updateUserExamAttemptTables(HttpServletRequest request, HttpServletResponse response) {

		// Getting userlogin for authentication
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute(ConstantValue.USERLOGIN);
		// Getting Dispatcher to dispatch and call services
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute(ConstantValue.DISPATCHER);
		// Getting Delegator to connect with entities
		Delegator delegator = (Delegator) request.getAttribute(ConstantValue.DELEGATOR);

		// Map<String, Object> combinedMap = UtilHttp.getCombinedMap(request);
		// String performanceId = (String)
		// combinedMap.get(ConstantValue.PERFORMANCE_ID);
		Integer performanceId = 10280;

		// Differentiating Correct Questions,Wrong Questions and Unanswered Questions
		Map<String, Object> totalCorrectQuestions = new HashMap<String, Object>();
		Map<String, Object> totalWrongQuestions = new HashMap<String, Object>();
		Map<String, Object> totalUnansweredQuestions = new HashMap<String, Object>();

		// ResultMap
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<GenericValue> listOfUserAttemptAnswer = null;

		Long totalScore = null;
		Long totalNegativeValue = null;
		Long userScore = null;
		Long userPercentage = null;
		Integer totalCorrect = 0;
		Integer totalWrong = 0;
		String userPassed = null;

		try {
			listOfUserAttemptAnswer = EntityQuery.use(delegator).from(ConstantValue.USER_ATTEMPT_ANSWER_MASTER)
					.where(ConstantValue.PERFORMANCE_ID, performanceId).queryList();

			if (UtilValidate.isNotEmpty(listOfUserAttemptAnswer)) {

				for (GenericValue perUserAttemptAnswerData : listOfUserAttemptAnswer) {
					Long questionId = perUserAttemptAnswerData.getLong(ConstantValue.QUESTION_ID);
					if (UtilValidate.isNotEmpty(questionId)) {

						GenericValue questionDetails = EntityQuery.use(delegator).from(ConstantValue.QUESTION_MASTER)
								.where(ConstantValue.QUESTION_ID, questionId).cache().queryOne();

						if (UtilValidate.isNotEmpty(questionDetails)) {
							totalScore = totalScore + questionDetails.getLong(ConstantValue.ANSWER_VALUE);

							String topicId = questionDetails.getString(ConstantValue.TOPIC_ID);

							if (perUserAttemptAnswerData.getString(ConstantValue.SUBMITTED_ANSWER)
									.equalsIgnoreCase(questionDetails.getString(ConstantValue.ANSWER))) {

								userScore = userScore + questionDetails.getLong(ConstantValue.ANSWER_VALUE);

								if (totalCorrectQuestions.containsKey(topicId)) {
									totalCorrectQuestions.put(topicId,
											(Integer) totalCorrectQuestions.get(topicId) + 1);
								} else {
									totalCorrectQuestions.put(topicId, 1);
								}
							} else if (UtilValidate
									.isEmpty(perUserAttemptAnswerData.getString(ConstantValue.SUBMITTED_ANSWER))) {

								if (totalUnansweredQuestions.containsKey(topicId)) {
									totalUnansweredQuestions.put(topicId,
											(Integer) totalUnansweredQuestions.get(topicId) + 1);
								} else {
									totalUnansweredQuestions.put(topicId, 1);
								}

							} else {

								if (totalWrongQuestions.containsKey(topicId)) {
									totalWrongQuestions.put(topicId, (Integer) totalWrongQuestions.get(topicId) + 1);

								} else {
									totalWrongQuestions.put(topicId, 1);
								}
							}
						}
					}
				}

				List<GenericValue> listOfUserAttemptTopic = EntityQuery.use(delegator)
						.from(ConstantValue.USER_ATTEMPT_TOPIC_MASTER)
						.where(ConstantValue.PERFORMANCE_ID, performanceId).cache().queryList();

				String topicId = null;
				Integer correctQuestionsInThisTopic = null;
				Integer userTopicPercentage = null;
				String userPassedThisTopic = null;

				if (UtilValidate.isNotEmpty(listOfUserAttemptTopic)) {

					for (GenericValue perUserAttemptTopicData : listOfUserAttemptTopic) {
						topicId = perUserAttemptTopicData.getString(ConstantValue.TOPIC_ID);
						Long topicPassPercentage = perUserAttemptTopicData.getLong(ConstantValue.TOPIC_PASS_PERCENTAGE);

						Integer totalQuestionsInThisTopic = (Integer) perUserAttemptTopicData
								.get(ConstantValue.TOTAL_QUESTIONS_IN_THIS_TOPIC);

						correctQuestionsInThisTopic = (Integer) totalCorrectQuestions.get(topicId);

						userTopicPercentage = (correctQuestionsInThisTopic / totalQuestionsInThisTopic) * 100;

						if (userTopicPercentage < topicPassPercentage) {
							userPassedThisTopic = ConstantValue.NO;
						} else {
							userPassedThisTopic = ConstantValue.YES;
						}

					}

					try {
						Map<String, Object> updateAttributesForUserAttemptTopicMaster = new HashMap<String, Object>();

						updateAttributesForUserAttemptTopicMaster.put(ConstantValue.PERFORMANCE_ID, performanceId);
						updateAttributesForUserAttemptTopicMaster.put(ConstantValue.TOPIC_ID, topicId);
						updateAttributesForUserAttemptTopicMaster.put(ConstantValue.CORRECT_QUESTIONS_IN_THIS_TOPIC,
								correctQuestionsInThisTopic);
						updateAttributesForUserAttemptTopicMaster.put(ConstantValue.USER_TOPIC_PERCENTAGE,
								userTopicPercentage);
						updateAttributesForUserAttemptTopicMaster.put(ConstantValue.USER_PASSED_THIS_TOPIC,
								userPassedThisTopic);
						updateAttributesForUserAttemptTopicMaster.put(ConstantValue.USERLOGIN, userLogin);

						Map<String, Object> updateUserAttemptTopic = dispatcher.runSync("updateUserAttemptTopicMaster",
								updateAttributesForUserAttemptTopicMaster);

						if (ServiceUtil.isSuccess(updateUserAttemptTopic)) {
							Debug.logVerbose(ConstantValue.SUCCESS_MESSAGE,
									"UserAttemptTopicMaster Updated Successfully");
//							request.setAttribute(ConstantValue.SUCCESS_MESSAGE,
//									"UserAttemptTopicMaster Updated Successfully");
						} else {
							Debug.logVerbose(ConstantValue.ERROR_MESSAGE, "UserAttemptTopicMaster could not update");
						}
					} catch (GenericServiceException e) {

						e.printStackTrace();
					}

				}

				LocalDateTime dateValue = LocalDateTime.now();
				Timestamp completedDate = Timestamp.valueOf(dateValue);

				for (Object correctQuestion : totalCorrectQuestions.values()) {
					totalCorrect = totalCorrect + (Integer) correctQuestion;
				}

				for (Object wrongQuestion : totalWrongQuestions.values()) {
					totalWrong = totalWrong + (Integer) wrongQuestion;
				}

				GenericValue userAttemptMasterData = EntityQuery.use(delegator).from(ConstantValue.USER_ATTEMPT_MASTER)
						.where(ConstantValue.PERFORMANCE_ID, performanceId).cache().queryOne();
				if (UtilValidate.isNotEmpty(userAttemptMasterData)) {
					String partyId = userAttemptMasterData.getString(ConstantValue.PARTY_ID);
					String examId = userAttemptMasterData.getString(ConstantValue.EXAM_ID);

					GenericValue examMasterData = EntityQuery.use(delegator).from(ConstantValue.EXAM_MASTER)
							.where(ConstantValue.EXAM_ID, examId).cache().queryOne();

					Long examPassPercentage = examMasterData.getLong(ConstantValue.PASS_PERCENTAGE);
					Long negativeMarkValue = examMasterData.getLong(ConstantValue.NEGATIVE_MARK_VALUE);

					userPercentage = (userScore / totalScore) * 100;

					if (examPassPercentage > userPercentage) {
						userPassed = ConstantValue.NO;
					} else {
						userPassed = ConstantValue.YES;
					}

					totalNegativeValue = totalWrong * negativeMarkValue;

					userScore -= totalNegativeValue;

					Map<String, Object> updateAttributesForUserAttemptMaster = new HashMap<String, Object>();
					updateAttributesForUserAttemptMaster.put(ConstantValue.PERFORMANCE_ID, performanceId);
					updateAttributesForUserAttemptMaster.put(ConstantValue.SCORE, userScore);
					updateAttributesForUserAttemptMaster.put(ConstantValue.COMPLETED_DATE, completedDate);
					updateAttributesForUserAttemptMaster.put(ConstantValue.TOTAL_CORRECT, totalCorrect);
					updateAttributesForUserAttemptMaster.put(ConstantValue.TOTAL_WRONG, totalWrong);
					updateAttributesForUserAttemptMaster.put(ConstantValue.USER_PASSED, userPassed);
					updateAttributesForUserAttemptMaster.put(ConstantValue.USERLOGIN, userLogin);

					Map<String, Object> updateUserAttemptMaster = dispatcher.runSync("updateUserAttemptMaster",
							updateAttributesForUserAttemptMaster);
					if (ServiceUtil.isSuccess(updateUserAttemptMaster)) {
						Debug.logVerbose(ConstantValue.SUCCESS_MESSAGE, "UserAttemptMaster Updated Successfully");
						request.setAttribute(ConstantValue.SUCCESS_MESSAGE, "UserAttemptMaster Updated Successfully");
					} else {
						Debug.logVerbose(ConstantValue.ERROR_MESSAGE, "UserAttemptMaster table failed to Update");
						request.setAttribute(ConstantValue.ERROR_MESSAGE, "UserAttemptMaster table failed to Update ");
					}

					GenericValue userExamMappingData = EntityQuery.use(delegator)
							.from(ConstantValue.USER_EXAM_MAPPING_MASTER).where(ConstantValue.PARTY_ID, partyId).cache()
							.queryOne();

					if (UtilValidate.isNotEmpty(userExamMappingData)) {
						String canSeeDetailedResults = userExamMappingData
								.getString(ConstantValue.CAN_SEE_DETAILED_RESULTS);

						if (canSeeDetailedResults.equalsIgnoreCase(ConstantValue.YES)) {

							GenericValue userAttemptMasterResultData = EntityQuery.use(delegator)
									.from(ConstantValue.USER_ATTEMPT_MASTER)
									.where(ConstantValue.PERFORMANCE_ID, performanceId).cache().queryOne();

							List<GenericValue> listOfUserAttemptTopicMasterResultData = EntityQuery.use(delegator)
									.from(ConstantValue.USER_ATTEMPT_TOPIC_MASTER)
									.where(ConstantValue.PERFORMANCE_ID, performanceId).cache().queryList();

							List<GenericValue> listOfUserAttemptAnswerMasterResultData = EntityQuery.use(delegator)
									.from(ConstantValue.USER_ATTEMPT_ANSWER_MASTER)
									.where(ConstantValue.PERFORMANCE_ID, performanceId).queryList();

							if (UtilValidate.isNotEmpty(userAttemptMasterResultData)
									&& UtilValidate.isNotEmpty(listOfUserAttemptTopicMasterResultData)
									&& UtilValidate.isNotEmpty(listOfUserAttemptAnswerMasterResultData)) {

								resultMap.put(ConstantValue.USER_ATTEMPT_MASTER, userAttemptMasterResultData);

								resultMap.put(ConstantValue.USER_ATTEMPT_TOPIC_MASTER,
										listOfUserAttemptTopicMasterResultData);

								resultMap.put(ConstantValue.USER_ATTEMPT_ANSWER_MASTER,
										listOfUserAttemptAnswerMasterResultData);

								request.setAttribute(ConstantValue.RESULT_MAP, resultMap);

							}

						}

					}

				}

			} else {
				request.setAttribute(ConstantValue.ERROR_MESSAGE, "There are no such records in the entity.");
			}

		} catch (GenericEntityException | GenericServiceException e) {

			e.printStackTrace();
		}

		return ConstantValue.SUCCESS;
	}
}
