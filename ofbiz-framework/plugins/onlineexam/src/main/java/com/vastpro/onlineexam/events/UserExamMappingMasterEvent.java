//package com.vastpro.onlineexam.events;
//
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.ofbiz.base.util.Debug;
//import org.apache.ofbiz.base.util.UtilHttp;
//import org.apache.ofbiz.base.util.UtilMisc;
//import org.apache.ofbiz.entity.Delegator;
//import org.apache.ofbiz.entity.GenericEntityException;
//import org.apache.ofbiz.entity.GenericValue;
//import org.apache.ofbiz.entity.util.EntityQuery;
//import org.apache.ofbiz.service.GenericServiceException;
//import org.apache.ofbiz.service.LocalDispatcher;
//
//import com.vastpro.onlineexam.constants.ConstantValue;
//
//public class UserExamMappingMasterEvent {
//	public static final String module = UserExamMappingMasterEvent.class.getName();
//	public static String createOrUpdateExamMasterEvent(HttpServletRequest request, HttpServletResponse response)  {
//		Map<String, Object> combinedMap = UtilHttp.getCombinedMap(request);
//        Delegator delegator = (Delegator) combinedMap.get(ConstantValue.DELEGATOR);
//        LocalDispatcher dispatcher = (LocalDispatcher) combinedMap.get(ConstantValue.DISPATCHER);
//        String partyId = (String)combinedMap.get(ConstantValue.PARTY_ID);
//        String examId = (String)combinedMap.get(ConstantValue.EXAM_ID);
//        Object allowedAttempts = (String)combinedMap.get(ConstantValue.ALLOWED_ATTEMPTS);
//        String noOfAttempts = (String)combinedMap.get(ConstantValue.NO_OF_ATTEMPTS);
//        String lastPerformanceDate = (String)request.getAttribute(ConstantValue.LAST_PERFORMANCE_DATE);
//        String timeoutDays = (String)request.getAttribute(ConstantValue.TIME_OUT_DAYS);
//        String durationMinutes = (String)request.getAttribute(ConstantValue.DURATION_MINUTES);
//        String passPercentage =(String) request.getAttribute(ConstantValue.PASS_PERCENTAGE);
//        String questionsRandomized = (String)request.getAttribute(ConstantValue.QUESTIONS_RANDOMIZED);
//        String answersMust =(String) request.getAttribute(ConstantValue.ANSWERS_MUST);
//        String enableNegativeMark =(String)request.getAttribute(ConstantValue.ENABLE_NEGATIVE_MARK);
//        String negativeMarkValue =(String)request.getAttribute(ConstantValue.NEGATIVE_MARK_VALUE);
//        GenericValue userLogin=(GenericValue)request.getSession().getAttribute("userLogin");
//        try {
//        	GenericValue genericvalue = EntityQuery.use(delegator).from("ExamMaster").where("examId", examId).cache()
//					.queryOne();
//        	
//        	if(genericvalue!=null) {
//        		String myexamId=delegator.getNextSeqId(examId);
//        		 Debug.logInfo("=====update service called...... =========", module);
// 	            dispatcher.runSync("updateExamMaster", UtilMisc.toMap("examId",examId,
// 	            		"examName",examName,"description",description,
// 	            		"creationDate",creationDate,"expirationDate",expirationDate,"noOfQuestions",noOfQuestions,"durationMinutes",durationMinutes,"passPercentage",passPercentage,
// 	            		"questionsRandomized",questionsRandomized,
// 	            		"answersMust",answersMust,"enableNegativeMark",enableNegativeMark,"negativeMarkValue",negativeMarkValue,"userLogin",userLogin));
//        	}
//        	else {
//        		 Debug.logInfo("=====create service called.....! =========", module);
// 	            dispatcher.runSync("createExamMaster", UtilMisc.toMap(
// 	            		"examName",examName,"description",description,
// 	            		"creationDate",creationDate,"expirationDate",expirationDate,"noOfQuestions",noOfQuestions,"durationMinutes",durationMinutes,"passPercentage",passPercentage,
// 	            		"questionsRandomized",questionsRandomized,
// 	            		"answersMust",answersMust,"enableNegativeMark",enableNegativeMark,"negativeMarkValue",negativeMarkValue,"userLogin",userLogin));
//        	}
//        	
//        } catch (GenericServiceException | GenericEntityException e) {
//            String errMsg = "Unable to create or update records in ExamMasterentity: " + e.toString();
//            request.setAttribute("_ERROR_MESSAGE_", errMsg);
//            return "error";
//        }
//        request.setAttribute("_EVENT_MESSAGE_", "ExamMaster created or updated succesfully.");
//        return "success";
//	}
//
//}
