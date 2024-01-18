package com.vastpro.onlineexam.events;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import com.vastpro.onlineexam.constants.ConstantValue;

public class QuestionInformationForUser {

	
	
	public static String getQuestionsForUser(HttpServletRequest request,HttpServletResponse response) {
		
		Delegator delegator=(Delegator)request.getAttribute(ConstantValue.DELEGATOR);
		LocalDispatcher dispatcher=(LocalDispatcher)request.getAttribute(ConstantValue.DISPATCHER);
		GenericValue userLogin=(GenericValue)request.getAttribute(ConstantValue.USERLOGIN);
		
		String examId=(String)request.getAttribute(ConstantValue.EXAM_ID);
		String partyId=(String)userLogin.get(ConstantValue.PARTY_ID);
		String performanceId=null;
		
		LocalDateTime now = LocalDateTime.now();  
		System.out.println(now);
		if(UtilValidate.isNotEmpty(examId)) {
			
		}
		
		try {
			GenericValue examDetails=EntityQuery.use(delegator).from(ConstantValue.EXAM_MASTER).where(ConstantValue.EXAM_ID,examId).queryOne();
			String noOFQuestions=(String)examDetails.get(ConstantValue.NO_OF_QUESTIONS);
			GenericValue userExamDetails=EntityQuery.use(delegator).from(ConstantValue.USER_EXAM_MAPPING_MASTER).where(ConstantValue.EXAM_ID,examId,ConstantValue.PARTY_ID,partyId).queryFirst();
			String allowedAttempts=(String)userExamDetails.get(ConstantValue.ALLOWED_ATTEMPTS);
			String noOfattemptsbyUser=(String)userExamDetails.get(ConstantValue.NO_OF_ATTEMPTS);
			Long noOfattempts=Long.parseLong(noOfattemptsbyUser);
			noOfattempts=noOfattempts+1;
			Map<String,Object>resultOfUserAttemptMaster=dispatcher.runSync("createUserAttemptMaster",UtilMisc.toMap(ConstantValue.EXAM_ID,examId,ConstantValue.PARTY_ID,partyId,ConstantValue.NO_OF_QUESTIONS,noOFQuestions,ConstantValue.ALLOWED_ATTEMPTS,noOfattempts));
			performanceId=(String)resultOfUserAttemptMaster.get(ConstantValue.PERFORMANCE_ID);
			
			List<GenericValue> topicsListforExam=EntityQuery.use(delegator).from(ConstantValue.EXAM_TOPIC_MAPPING_MASTER).where(ConstantValue.EXAM_ID,examId).queryList();
			if(UtilValidate.isNotEmpty(topicsListforExam)) {
				for(GenericValue oneTopic : topicsListforExam) {
					
					String topicId=(String)oneTopic.get(ConstantValue.TOPIC_ID);
					String topicPassPercentage=(String)oneTopic.get(ConstantValue.TOPIC_PASS_PERCENTAGE);
					String totalQuestionsInThisTopic=(String)oneTopic.get(ConstantValue.QUESTION_PER_EXAM);
					
					Map<String,Object>resultOfUserTopicMaster=dispatcher.runSync("createUserAttemptTopicMaster",UtilMisc.toMap(ConstantValue.PERFORMANCE_ID,performanceId,ConstantValue.TOPIC_ID,topicId,ConstantValue.TOPIC_PASS_PERCENTAGE,topicPassPercentage,ConstantValue.QUESTION_PER_EXAM,totalQuestionsInThisTopic));
					if(!ServiceUtil.isSuccess(resultOfUserAttemptMaster)) {
						request.setAttribute(ConstantValue.ERROR_MESSAGE, "Error in the creating the service recordds");
					}
					else {
						request.setAttribute(ConstantValue.SUCCESS_MESSAGE,"Record is created successfully in the usertopic attempt master");
					}
					
				}
			}
			else {
				request.setAttribute(ConstantValue.ERROR_MESSAGE, "There are no topics under this exam");
			}
			
			
			Map<String,Object>callQuestionsService=dispatcher.runSync("listOfQuestionsForTheExam",UtilMisc.toMap("request",request,ConstantValue.EXAM_ID,examId,ConstantValue.USERLOGIN,userLogin));
			
			}
		catch(Exception e) {
			e.printStackTrace();
		}
	
		
		
		
		
		return "success";
	}
}
