package com.vastpro.onlineexam.additionalevents;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;

public class AddTopicMasterEvent {

	public static String add_topic_master_events(HttpServletRequest request,HttpServletResponse response) {
		
		 Delegator delegator = (Delegator) request.getAttribute("delegator");
         LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
         GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
         
         String topicId=(String)request.getAttribute("topicId");
         String topicName=(String)request.getAttribute("topicName");
         
         try {
        	 dispatcher.runSync("createTopicMaster",UtilMisc.toMap("topicId",topicId,"topicName",topicName));
         }
         catch(GenericServiceException e) {
        	 e.printStackTrace();
        	 return "Error";
         }
         
         request.setAttribute("_EVENT_MESSAGE_", "Topic Master recored created successfully");
         return "success";
         

		
	}
}
