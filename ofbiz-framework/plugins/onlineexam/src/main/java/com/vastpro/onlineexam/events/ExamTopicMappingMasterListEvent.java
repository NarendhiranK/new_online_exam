package com.vastpro.onlineexam.events;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilHttp;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.LocalDispatcher;

import com.vastpro.onlineexam.constants.ConstantValue;

public class ExamTopicMappingMasterListEvent {
	public static final String module = ExamTopicMappingMasterListEvent.class.getName();
	
	/*
	 * 
	 * This event is used to retrieve all the records on the exam topic mapping master entity.
	 * 
	 * 
	 * */
    
    public static String getExamTopicEvent(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute(ConstantValue.DELEGATOR);
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute(ConstantValue.DISPATCHER);
		Map<String, Object> combinedMap = UtilHttp.getCombinedMap(request);
		List<Map<String,Object>> mylist=new LinkedList<>();
		String examId = (String) combinedMap.get(ConstantValue.EXAM_ID);
		System.out.println("ExamIdd......" + examId);
		String topicId=null;
		Map<String, Object> ExamTopicMapping = new HashMap<>();
		try {
			GenericValue genericvalue = EntityQuery.use(delegator).from("ExamMaster").where("examId", examId).cache()
					.queryOne();
			String ExamName = genericvalue.getString(ConstantValue.EXAM_NAME);
			Debug.log("examName is.." + ExamName);
			List<GenericValue> examMappingMaster = EntityQuery.use(delegator).from("ExamTopicMappingMaster")
					.where("examId", examId).cache().queryList();
			if (examMappingMaster == null) {
				request.setAttribute("noRecordsFound", "no records found in entity!");
				return "error";
			}
			Map <String,Object> hash=new HashMap<>();
			for (GenericValue examList : examMappingMaster) {
				hash.put("examName", ExamName);
				hash.put("examId", examList.get(ConstantValue.EXAM_ID));
				hash.put("topicId", examList.get(ConstantValue.TOPIC_ID));
				topicId=(String)examList.get(ConstantValue.TOPIC_ID);
				Debug.log(topicId);
				hash.put("percentage", examList.get(ConstantValue.PERCENTAGE));
				hash.put("topicPassPercentage", examList.get(ConstantValue.TOPIC_PASS_PERCENTAGE));
				hash.put("questionsPerExam", examList.get(ConstantValue.QUESTION_PER_EXAM));
				GenericValue topicMasterEntity = EntityQuery.use(delegator).from("TopicMaster").where("topicId", topicId).cache()
						.queryOne();
				String topicName=(String)topicMasterEntity.get("topicName");
				Debug.log("TopicName......" + topicName);
				hash.put("topicName", topicName);
				mylist.add(hash);
				hash=new HashMap<>();
			}
		
			ExamTopicMapping.put("ExamTopicMapping", mylist);
			ExamTopicMapping.put("genericvalue", genericvalue);
			Debug.log("ExamTopicMapping" + ExamTopicMapping);
			Debug.logInfo("===== =========", module);
			request.setAttribute("ExamTopicNameMapping", ExamTopicMapping);
			request.setAttribute("_EVENT_MESSAGE_", examMappingMaster);
			request.setAttribute("genericvalue", genericvalue);
		} catch (GenericEntityException e) {
			String errMsg = "Unable : " + e.toString();
			request.setAttribute("_ERROR_MESSAGE_", "error");
			return "error";
		}
		return "success";
	}



}
