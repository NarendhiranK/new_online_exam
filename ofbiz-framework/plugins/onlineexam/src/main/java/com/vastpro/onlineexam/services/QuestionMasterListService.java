package com.vastpro.onlineexam.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.DispatchContext;

import com.vastpro.onlineexam.constants.ConstantValue;

public class QuestionMasterListService {

//	public static Map<String, Object> questionsForExam(DispatchContext dctx, Map<String, ? extends Object> context) {
//
//		Debug.log("QuestionMasterListService is called............");	
//		
//		String examId = (String) context.get(ConstantValue.EXAM_ID);
//		Debug.log("ExamId................" + examId);
//		Delegator delegator = dctx.getDelegator();
//		HttpServletRequest request = (HttpServletRequest) context.get("request");
//
//
//		return mapOfQuestions;
//	}
	
	
	
	
//	h kumar
//	  9:57 AM
//	if (UtilValidate.isEmpty(questionsPerExam)) {
//						String errMsg = "questionsPerExam"
//								+ UtilProperties.getMessage(RES_ERR, "EmptyVariableMessage", UtilHttp.getLocale(request));
//						Debug.log(errMsg);
//						return emptyMap; // If no topics found, return emptyMap
//					}
//					// Process the topic only if both topicId and questionsPerExam are not null
//					if (topicId != null && questionsPerExam != null) {
//						// Retrieve all questions for the current topic
//						List<GenericValue> topicQuestions = EntityQuery.use(delegator).from("QuestionMaster")
//								.where(ConstantValues.TOPIC_ID, topicId).queryList();
//						// Log if no questions available for the topic
//						if (UtilValidate.isEmpty(topicQuestions)) {
//							String errMsg = "topicQuestions" + UtilProperties.getMessage(RES_ERR, "EmptyVariableMessage",
//									UtilHttp.getLocale(request));
//							Debug.log(errMsg);
//							return emptyMap; // If no topics found, return emptyMap
//						}
//						Random rd = new Random();
//						List<GenericValue> questionList = new ArrayList<>();
//						// Select random questions based on questionsPerExam
//						for (int i = 0; i < questionsPerExam; i++) {
//							int rand = rd.nextInt(topicQuestions.size());
//							questionList.add(topicQuestions.get(rand));
//							topicQuestions.remove(rand);
//						}
//						// Add the selected questions to the map if not empty
//						if (UtilValidate.isNotEmpty(questionList)) {
//							question.put(topicId, questionList);
//						}
//					}
//				}
//				// Prepare a list of lists containing selected questions
//				List<List<GenericValue>> questionTopicList = new ArrayList<>();
//				for (Entry<String, Object> entry : question.entrySet()) {
//					questionTopicList.add((List<GenericValue>) entry.getValue());
//				}
//				// Add the list of selected questions to the result map and session attribute
//				if (UtilValidate.isNotEmpty(questionTopicList)) {
//					result.put("examquestion", questionTopicList);
//					request.getSession().setAttribute("selectedQuestions", questionTopicList);
//				}
//				return result; // Return the result map
//			} catch (Exception e) {
//				System.out.println("Exception: " + e.getMessage()); // Log the exception message
//			}
}
