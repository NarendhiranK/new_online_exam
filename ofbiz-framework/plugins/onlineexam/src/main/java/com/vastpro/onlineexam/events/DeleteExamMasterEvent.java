package com.vastpro.onlineexam.events;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;

import com.vastpro.onlineexam.constants.ConstantValue;

public class DeleteExamMasterEvent {

	/*
	 * This event is used to delete the record in the exam master entity.
	 *
	 * */
	public static String deleteExamMaster(HttpServletRequest request,HttpServletResponse response) {
		 Delegator delegator = (Delegator) request.getAttribute(ConstantValue.DELEGATOR);
         LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute(ConstantValue.DISPATCHER);
         GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
         
         String examId=(String)request.getAttribute(ConstantValue.EXAM_ID);
         try {
        	 dispatcher.runSync("deleteExamMaster",UtilMisc.toMap("examId",examId));
     
         }
         catch(GenericServiceException e) {
        	 String errMsg = "Unable to delete  records in exam master entity: " + e.toString();
             request.setAttribute("_ERROR_MESSAGE_", errMsg);
             return "error";
         }
		
         request.setAttribute("_EVENT_MESSAGE_", "exam Master recored deleted successfully");
         return "success";
         
       
	}
}
