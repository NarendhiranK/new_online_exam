package com.vastpro.onlineexam.events;

import java.util.HashMap;
import java.util.List;
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

import com.vastpro.onlineexam.constants.ConstantValue;

public class DeleteQuestionsForTopicEvent {
	public static final String module = DeleteQuestionsForTopicEvent.class.getName();

	public static String deleteQuestionsForTopicEvent(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> combinedMap = UtilHttp.getCombinedMap(request);
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute(ConstantValue.DISPATCHER);
		Delegator delegator = (Delegator) combinedMap.get(ConstantValue.DELEGATOR);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String topicId = (String) combinedMap.get(ConstantValue.TOPIC_ID);
		String examId = (String) combinedMap.get(ConstantValue.EXAM_ID);

		Debug.logInfo("topicId :...: ", topicId);
		Debug.logInfo("ExamId  :...: ", topicId);

		try {

			List<GenericValue> listOfQuestions = EntityQuery.use(delegator).from(ConstantValue.QUESTION_MASTER)
					.where(ConstantValue.TOPIC_ID, topicId).queryList();
			if (UtilValidate.isNotEmpty(listOfQuestions)) {

				for (GenericValue oneQuestion : listOfQuestions) {
					Long questionId = (Long) oneQuestion.get(ConstantValue.QUESTION_ID);
					Map<String, Object> deleteQuestionMasterResult = dispatcher.runSync("deleteQuestionMaster",
							UtilMisc.toMap(ConstantValue.QUESTION_ID, questionId, ConstantValue.TOPIC_ID, topicId));           
				}

			} else {
				request.setAttribute(ConstantValue.ERROR_MESSAGE, "There is no questions under this topic");
			}
		} catch (GenericServiceException | GenericEntityException e) {

			e.printStackTrace();
		}
		
		
		try {
		  GenericValue examTopicMappingMasterResult = EntityQuery.use(delegator).select(ConstantValue.EXAM_ID).from(ConstantValue.EXAM_TOPIC_MAPPING_MASTER)
					.where(ConstantValue.TOPIC_ID, topicId).queryOne();
		  if(UtilValidate.isNotEmpty(examTopicMappingMasterResult)) {
			  Map<String, Object> deleteExamToppingMappingMasterResult = dispatcher.runSync("deleteExamTopicMappingMaster",
						UtilMisc.toMap(ConstantValue.EXAM_ID, examId, ConstantValue.TOPIC_ID, topicId)); 
		  }
		   
		  else {
			  request.setAttribute(ConstantValue.ERROR_MESSAGE, "no record found in examtopicmappingmaster");
		  }
	
		  
		}
		catch( GenericEntityException | GenericServiceException e) {
			e.printStackTrace();
		}
		
		try {
			 
			  GenericValue topicMasterResult = EntityQuery.use(delegator).from(ConstantValue.TOPIC_MASTER)
						.where(ConstantValue.TOPIC_ID, topicId).queryOne();
			  if(UtilValidate.isNotEmpty(topicMasterResult)) {
				  Map<String, Object> deleteTopicMasterResult = dispatcher.runSync("deleteTopicMaster",
							UtilMisc.toMap(ConstantValue.EXAM_ID, examId, ConstantValue.TOPIC_ID, topicId));
			  }
			  else {
				  request.setAttribute(ConstantValue.ERROR_MESSAGE, "no records found in topicMaster entity.!");
			  }
			  
		}
		catch(GenericEntityException | GenericServiceException e) {
			e.printStackTrace();
		}
		
		

		return "success";
	}
}
