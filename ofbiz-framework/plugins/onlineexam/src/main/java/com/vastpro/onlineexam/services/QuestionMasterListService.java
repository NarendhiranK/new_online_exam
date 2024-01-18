package com.vastpro.onlineexam.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	
	public static Map<String,Object>questionsForExam(DispatchContext dctx, Map<String, ? extends Object> context){
		
		Map<String,Object>mapOfQuestions=new HashMap<>();
		Map<String,Object>mapOfQuestionsForTheTopic=new HashMap<>();
		String examId=(String)context.get(ConstantValue.EXAM_ID);
		Delegator delegator=dctx.getDelegator();
		HttpServletRequest request=(HttpServletRequest) context.get("request");
			
		try {
			List<GenericValue>listOfTopics=EntityQuery.use(delegator).from(ConstantValue.EXAM_TOPIC_MAPPING_MASTER).where(ConstantValue.EXAM_ID,examId).queryList();
			
			if(UtilValidate.isNotEmpty(listOfTopics)) {
					
				for(GenericValue oneTopicDetail : listOfTopics) {
					
					String topicId=(String)oneTopicDetail.get(ConstantValue.TOPIC_ID);
					
					Integer questionsPerExam=Integer.parseInt(oneTopicDetail.get(ConstantValue.QUESTION_PER_EXAM).toString());
					
					if(topicId!=null && questionsPerExam!=null) {
						
						List<GenericValue>listOfQuestions=EntityQuery.use(delegator).from(ConstantValue.QUESTION_MASTER).where(ConstantValue.TOPIC_ID,topicId).queryList();
						if(UtilValidate.isNotEmpty(listOfQuestions)) {
							Random random=new Random();
							List<GenericValue> listOfQuestionsForThisTopic=new ArrayList();
							
							
							for(int i=0;i<questionsPerExam;i++) {
								
								int rand=random.nextInt(listOfQuestions.size());
								listOfQuestionsForThisTopic.add(listOfQuestions.get(rand));
								listOfQuestions.remove(rand);
								
							}
							
							mapOfQuestions.put(topicId, listOfQuestionsForThisTopic);
							
						}

						
					}
					
					
					
					
				}
				Debug.log("Map Of Questions..........................>" + mapOfQuestions);
			}
			else {
				request.setAttribute(ConstantValue.ERROR_MESSAGE, "No topics under this topic");
			}
			
		
		
		}catch(Exception e) {
			
		}
		
		
		
		
		return mapOfQuestions;
	}
}
