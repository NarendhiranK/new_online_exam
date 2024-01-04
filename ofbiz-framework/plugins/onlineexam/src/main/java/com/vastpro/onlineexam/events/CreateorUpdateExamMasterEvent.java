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

public class CreateorUpdateExamMasterEvent{

	public static final String MODULE = CreateorUpdateExamMasterEvent.class.getName();
	
	/*
	 * 
	 * This event is used to either create or update the questions on the exam master entity.
	 * 
	 * 
	 * */

	public static String createOrUpdateExamMasterEvent(HttpServletRequest request, HttpServletResponse response)  {
        Delegator delegator = (Delegator) request.getAttribute(ConstantValue.DELEGATOR);
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute(ConstantValue.DISPATCHER);
        String examId = (String)request.getAttribute(ConstantValue.EXAM_ID);
        String examName = (String)request.getAttribute(ConstantValue.EXAM_NAME);
        Object description = (String)request.getAttribute(ConstantValue.DESCRIPTION);
        String creationDate = (String)request.getAttribute(ConstantValue.CREATION_DATE);
        String expirationDate = (String)request.getAttribute(ConstantValue.EXPIRATION_DATE);
        String noOfQuestions = (String)request.getAttribute(ConstantValue.NO_OF_QUESTIONS);
        String durationMinutes = (String)request.getAttribute(ConstantValue.DURATION_MINUTES);
        String passPercentage =(String) request.getAttribute(ConstantValue.PASS_PERCENTAGE);
        String questionsRandomized = (String)request.getAttribute(ConstantValue.QUESTIONS_RANDOMIZED);
        String answersMust =(String) request.getAttribute(ConstantValue.ANSWERS_MUST);
        String enableNegativeMark =(String)request.getAttribute(ConstantValue.ENABLE_NEGATIVE_MARK);
        String negativeMarkValue =(String)request.getAttribute(ConstantValue.NEGATIVE_MARK_VALUE);
        GenericValue userLogin=(GenericValue)request.getSession().getAttribute("userLogin");
        try {
        	GenericValue genericvalue = EntityQuery.use(delegator).from("ExamMaster").where("examId", examId).cache()
					.queryOne();
        	
        	if(genericvalue!=null) {
        		String myexamId=delegator.getNextSeqId(examId);
        		 Debug.logInfo("=====update service called...... =========", MODULE);
 	            dispatcher.runSync("updateExamMaster", UtilMisc.toMap("examId",examId,
 	            		"examName",examName,"description",description,
 	            		"creationDate",creationDate,"expirationDate",expirationDate,"noOfQuestions",noOfQuestions,"durationMinutes",durationMinutes,"passPercentage",passPercentage,
 	            		"questionsRandomized",questionsRandomized,
 	            		"answersMust",answersMust,"enableNegativeMark",enableNegativeMark,"negativeMarkValue",negativeMarkValue,"userLogin",userLogin));
        	}
        	else {
<<<<<<< HEAD
        		 Debug.logInfo("=====create service called.....! =========", module);
 	            dispatcher.runSync("createExamMaster", UtilMisc.toMap(
=======
        		 Debug.logInfo("=====create service called.....! =========", MODULE);
 	            dispatcher.runSync("createExamMaster", UtilMisc.toMap("examId",examId,
>>>>>>> 05a7c4a4995c3c6817e870a23185bed312cc11fb
 	            		"examName",examName,"description",description,
 	            		"creationDate",creationDate,"expirationDate",expirationDate,"noOfQuestions",noOfQuestions,"durationMinutes",durationMinutes,"passPercentage",passPercentage,
 	            		"questionsRandomized",questionsRandomized,
 	            		"answersMust",answersMust,"enableNegativeMark",enableNegativeMark,"negativeMarkValue",negativeMarkValue,"userLogin",userLogin));
        	}
        	
        } catch (GenericServiceException | GenericEntityException e) {
            String errMsg = "Unable to create or update records in ExamMasterentity: " + e.toString();
            request.setAttribute("_ERROR_MESSAGE_", errMsg);
            return "error";
        }
        request.setAttribute("_EVENT_MESSAGE_", "ExamMaster created or updated succesfully.");
        return "success";
	}

}