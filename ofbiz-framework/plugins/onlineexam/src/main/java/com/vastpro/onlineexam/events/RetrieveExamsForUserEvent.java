package com.vastpro.onlineexam.events;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.LocalDispatcher;

import com.vastpro.onlineexam.constants.ConstantValue;

public class RetrieveExamsForUserEvent {

	public static final String module = RetrieveExamsForUserEvent.class.getName();
	
	public static String getExamsForUser(HttpServletRequest request,HttpServletResponse response) {
		Delegator delegator=(Delegator)request.getAttribute(ConstantValue.DELEGATOR);
		LocalDispatcher dispatcher=(LocalDispatcher)request.getAttribute(ConstantValue.DISPATCHER);
		GenericValue userLogin=(GenericValue)request.getAttribute("userLogin");
		
		List<Map<String,Object>> examsList=new LinkedList<Map<String,Object>>();
		
		String partyId=(String) request.getAttribute(ConstantValue.PARTY_ID);
		try {
			List<GenericValue>listOfExamsForUser=EntityQuery.use(delegator).from("UserExamMappingMaster").where("partyId",partyId).queryList();
			if(UtilValidate.isNotEmpty(listOfExamsForUser)) {
				for(GenericValue oneExamDetails : listOfExamsForUser) {
					Map<String,Object> oneExam=new HashMap<String,Object>();
					oneExam.put("partyId", oneExamDetails.get(ConstantValue.PARTY_ID));
					oneExam.put("examId", oneExamDetails.get(ConstantValue.EXAM_ID));
					String examId=(String)oneExamDetails.get(ConstantValue.EXAM_ID);
					GenericValue examDetails=EntityQuery.use(delegator).from("ExamMaster").where(ConstantValue.EXAM_ID,examId).cache().queryOne();
					String examName=examDetails.getString(ConstantValue.EXAM_NAME);
					Debug.log("examName...>"+examName);
					oneExam.put("examName", examName);
					oneExam.put("allowedAttempts", oneExamDetails.get(ConstantValue.ALLOWED_ATTEMPTS));
					oneExam.put("noOfAttempts", oneExamDetails.get(ConstantValue.NO_OF_ATTEMPTS));
					oneExam.put("lastPerformanceDate", oneExamDetails.get(ConstantValue.LAST_PERFORMANCE_DATE));
					oneExam.put("passwordChangesAuto", oneExamDetails.get(ConstantValue.PASSWORD_CAHNGES_AUTO));
					oneExam.put("timeoutDays", oneExamDetails.get(ConstantValue.TIME_OUT_DAYS));
					oneExam.put("canSplitExams", oneExamDetails.get(ConstantValue.CAN_SPLIT_EXAMS));
					oneExam.put("canSeeDetailedResults", oneExamDetails.get(ConstantValue.CAN_SEE_DETAILED_RESULTS));
					oneExam.put("maxSplitAttempts", oneExamDetails.get(ConstantValue.MAX_SPLIT_ATTEMPTS));
					examsList.add(oneExam);
				}
				Map<String,Object> resultMap=new HashMap<>();
				resultMap.put("examDetailsMap", examsList);
				request.setAttribute("resultMap", resultMap);
				
			}else {
				request.setAttribute(ConstantValue.ERROR_MESSAGE,"no exams for this party Id");
			}
			
		}
		catch(GenericEntityException e) {
			request.setAttribute(ConstantValue.ERROR_MESSAGE, "value should not be taken from the entity");
			e.printStackTrace();
		}
		
		return "success";	}

}
