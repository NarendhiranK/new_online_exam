package com.vastpro.onlineexam.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import com.vastpro.onlineexam.constants.ConstantValue;

public class QuestionsForUserRetrieveEvent {

	public static String questionsForUser(HttpServletRequest request,HttpServletResponse response) {
		Delegator delegator=(Delegator)request.getAttribute(ConstantValue.DELEGATOR);
		LocalDispatcher dispatcher=(LocalDispatcher)request.getAttribute(ConstantValue.DISPATCHER);
		GenericValue userLogin=(GenericValue)request.getSession().getAttribute(ConstantValue.USERLOGIN);
		String examId=(String)request.getAttribute(ConstantValue.EXAM_ID);
		int sequenceNum=1;
		Map<String, Object> mapOfQuestions = new HashMap<>();
		Map<String, Object> mapOfQuestionsForTheTopic = new HashMap<>();
		Map<String, Object> callQuestionsService=null;

		try {
			
			GenericValue examDetails=EntityQuery.use(delegator).from(ConstantValue.EXAM_MASTER).where(ConstantValue.EXAM_ID,examId).queryOne();
			request.setAttribute("examDetails", examDetails);
			
			List<GenericValue> listOfTopics = EntityQuery.use(delegator).from(ConstantValue.EXAM_TOPIC_MAPPING_MASTER)
					.where(ConstantValue.EXAM_ID, examId).queryList();
			Debug.log("List of topics for this exam..........."+listOfTopics);

			if (UtilValidate.isNotEmpty(listOfTopics)) {

				for (GenericValue oneTopicDetail : listOfTopics) {

					String topicId = (String) oneTopicDetail.get(ConstantValue.TOPIC_ID);
			
					String questionsPerExamS = oneTopicDetail.get(ConstantValue.QUESTION_PER_EXAM).toString();
						System.out.println("questionsPerExam======="+questionsPerExamS+"topicId======"+topicId);
						Integer questionsPerExam=Integer.parseInt(questionsPerExamS);
						
						
						if(topicId!=null && questionsPerExam!=null) {
						
							List<GenericValue> listOfQuestions = EntityQuery.use(delegator)
									.from(ConstantValue.QUESTION_MASTER).where(ConstantValue.TOPIC_ID, topicId).queryList();

//							
							Debug.log("List of questions................." + listOfQuestions);
							Debug.log("questions of the size........." + listOfQuestions.size());
							
							if(UtilValidate.isEmpty(listOfQuestions))
							for(GenericValue oneQuestion : listOfQuestions) {
								Long questionId=(Long)oneQuestion.get(ConstantValue.QUESTION_ID);
								Debug.log("Question Id's" + questionId);
							}
							
							Random random = new Random();
							List<GenericValue> listOfQuestionsForThisTopic = new ArrayList<>();
//							
							
							for(int i=0;i<questionsPerExam;i++) {
								Debug.log("Inside for looop........................");
//								Long questionId=(Long)question.get(ConstantValue.QUESTION_ID);
//								Debug.log("List of questions" + questionId);
								int rand=random.nextInt(listOfQuestions.size());
								Debug.log("random value........" + rand);
								listOfQuestionsForThisTopic.add(listOfQuestions.get(rand));
								listOfQuestions.remove(rand);
							}
//							Debug.log("List of this questions for this topic", listOfQuestionsForThisTopic,"==========");
							
							// Add the selected questions to this map
							if (UtilValidate.isNotEmpty(listOfQuestionsForThisTopic)) {
								mapOfQuestions.put(topicId, listOfQuestionsForThisTopic);
							}
//							Debug.log("mapOfQuestions for this topic by map" + mapOfQuestions);
						

					
						List<List<GenericValue>> questionTopicList = new ArrayList<>();
						for (Entry<String, Object> entry : mapOfQuestions.entrySet()) {
							questionTopicList.add((List<GenericValue>) entry.getValue());
						}

						if (UtilValidate.isNotEmpty(questionTopicList)) {
							mapOfQuestionsForTheTopic.put("examQuestions", questionTopicList);
//							Debug.log("examQuestions........>" + questionTopicList);
							request.getSession().setAttribute("selectedQuestions", mapOfQuestionsForTheTopic);
						}
						}
						
//					return mapOfQuestionsForTheTopic;
				}
				Debug.log("Map Of Questions..........................>" + mapOfQuestionsForTheTopic);
			
			}

			else {
				request.setAttribute(ConstantValue.ERROR_MESSAGE, "No topics under this topic");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Debug.log("The questions retrieve service called..." + callQuestionsService);
		if(ServiceUtil.isError(callQuestionsService)) {
			String errorMessage=ServiceUtil.getErrorMessage(callQuestionsService);
			request.setAttribute(ConstantValue.ERROR_MESSAGE,errorMessage);
			return "Error";
		}
		else {
			request.setAttribute(ConstantValue.SUCCESS_MESSAGE, callQuestionsService);
			
			return "success";
		}
		
//		List<List<GenericValue>>questionsInformationList=(List<List<GenericValue>>)request.getSession().getAttribute("selectedQuestions");
//		Debug.log("QuestionsInformationList................................................." + questionsInformationList);
		
//		for(List<GenericValue>questions : questionsInformationList) {
//			for(GenericValue oneQuestion : questions) {
//				Long questionId = (Long)oneQuestion.get(ConstantValue.QUESTION_ID);
//				
//				Map<String,Object>resultMap=dispatcher.runSync("createUserAttemptTopicMaster", UtilMisc.toMap(ConstantValue.PERFORMANCE_ID,performanceId,"sequenceNum",sequenceNum,ConstantValue.USERLOGIN,userLogin));
//				//Increment sequenceNum
//				++ sequenceNum;
//				
//				if (!ServiceUtil.isSuccess(resultMap)) {
//					
//					request.setAttribute(ConstantValue.SUCCESS_MESSAGE, resultMap);
//					return "error";
//				}				
//				}
//			
		}
//		Debug.log("QuestionInformationList.............." + questionsInformationList);

	}

