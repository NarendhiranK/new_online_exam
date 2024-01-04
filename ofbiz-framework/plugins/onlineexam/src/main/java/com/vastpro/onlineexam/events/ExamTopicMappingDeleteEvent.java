package com.vastpro.onlineexam.events;
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
public class ExamTopicMappingDeleteEvent {
	
	 public static final String module = ExamTopicMappingDeleteEvent.class.getName();
	 
		/*
		 * 
		 * This event is used to delete the topics on the exam topic mapping master entity based on the exam id and topic Id.
		 * 
		 * 
		 * */
    
	    public static String deleteExamTopicMappingEvent(HttpServletRequest request, HttpServletResponse response)  {
	        Delegator delegator = (Delegator) request.getAttribute(ConstantValue.DELEGATOR);
	        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute(ConstantValue.DISPATCHER);
	        Map<String, Object> combinedMap = UtilHttp.getCombinedMap(request);
	         String examId =(String) combinedMap.get(ConstantValue.EXAM_ID);
	         String topicId =(String) combinedMap.get(ConstantValue.TOPIC_ID);
	         Debug.log("topicId....! :",topicId);
	         Debug.log("examId....! :",examId);
	        try {
	        	List<GenericValue> listOfexamTopics=EntityQuery.use(delegator).from("ExamTopicMappingMaster").where("examId",examId).cache().queryList();
	        	long percentages=0;
	        	for(GenericValue oneExamTopic : listOfexamTopics) {
	        			String percentageValue=oneExamTopic.getString("percentage");
	        			long topicPercentage=Long.parseLong(percentageValue);
	        			percentages=percentages+topicPercentage;
	        			
	        	}
	        	
	        	
	        GenericValue examMappingMaster = EntityQuery.use(delegator).from("ExamTopicMappingMaster").where("examId",examId,"topicId",topicId)
						.queryOne();
	       
	        if(UtilValidate.isNotEmpty(examMappingMaster)) {
	        	Debug.logInfo("===== deleteExamTopicMapping service called...!=========", module);
	            dispatcher.runSync("deleteExamTopicMappingMaster", UtilMisc.toMap("examId",examId,
	            		"topicId",topicId));
	        }
	           
	        } catch (Exception e) {
	            String errMsg = "Unable to delete records in deleteExamTopicMappingMaster: " + e.toString();
	            request.setAttribute("_ERROR_MESSAGE_", errMsg);
	            return "error";
	        }
	        request.setAttribute("_EVENT_MESSAGE_", "ExamTopicMappingMaster deleted succesfully.");
	        return "success";
}
}
