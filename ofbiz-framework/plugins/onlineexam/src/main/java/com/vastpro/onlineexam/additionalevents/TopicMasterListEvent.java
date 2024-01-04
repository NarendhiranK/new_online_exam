package com.vastpro.onlineexam.additionalevents;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
public class TopicMasterListEvent {
	 public static String listOfTopic(HttpServletRequest request, HttpServletResponse response) {
		 Delegator delegator = (Delegator) request.getAttribute("delegator");
		  Map<String, Object> listOfExamMaster = new HashMap<String,Object>();
			System.out.println("before try block .......");
			try {
				List<GenericValue> listOfTopic = EntityQuery.use(delegator).from("TopicMaster").queryList();
				System.out.println("listofTopic to display   : " + listOfTopic);
				request.setAttribute("ListOfTopic", listOfTopic);
				
			}
			catch (GenericEntityException e) {
				e.printStackTrace();
			}
			return " topics displayed successfully";
	 }
}
