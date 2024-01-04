package com.vastpro.onlineexam.events;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.UtilHttp;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.LocalDispatcher;

import com.vastpro.onlineexam.constants.ConstantValue;

public class ExamTopicRetrieveEvent {
	
	
	/*
	 * 
	 * This event is used to retrieve the particular record  on the exam topic mapping  master entity based on the exam id and topic Id.
	 * 
	 * 
	 * */

	public static String getExamTopicDetails(HttpServletRequest request,HttpServletResponse response) {
		
		
		Delegator delegator = (Delegator) request.getAttribute(ConstantValue.DELEGATOR);
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute(ConstantValue.DISPATCHER);
		Map<String, Object> combinedMap = UtilHttp.getCombinedMap(request);
		List<Map<String,Object>> mylist=new LinkedList<>();
		String examId = (String) combinedMap.get(ConstantValue.EXAM_ID);
		String topicId=(String)combinedMap.get(ConstantValue.TOPIC_ID);
		
		try {
			GenericValue examTopicDetails=EntityQuery.use(delegator).from("ExamTopicMappingMaster").where("examId",examId,"topicId",topicId).cache().queryOne();
			if(examTopicDetails!=null) {
				request.setAttribute("resultMap",examTopicDetails);
				return "success";
			}
			else {
				request.setAttribute(ConstantValue.ERROR_MESSAGE, "No records are found");
				return "Error";
			}
		}
		catch(GenericEntityException e) {
			String errMsg = "Unable : " + e.toString();
			request.setAttribute("_ERROR_MESSAGE_", "error");
			return "error";
		}
		
		
		
	}
}
