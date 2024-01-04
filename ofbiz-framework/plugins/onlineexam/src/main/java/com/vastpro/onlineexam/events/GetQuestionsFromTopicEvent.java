package com.vastpro.onlineexam.events;

import java.util.HashMap;
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

public class GetQuestionsFromTopicEvent {
	
	
	/*
	 * 
	 * This event is used to retrieve the questions  on the particular topic based on the topic Id on the question master entity.
	 * 
	 * 
	 * */
	public static final String module = GetQuestionsFromTopicEvent.class.getName();
	public static String viewQuestions(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String result = null;
		String topicId = request.getParameter("topicId");
		Debug.log(topicId);
		if (topicId == null) {
			topicId = (String) request.getAttribute("topicId");
		}
		Debug.log(topicId);
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		try {
			GenericValue getTopicMaster = EntityQuery.use(delegator).from("TopicMaster").where("topicId", topicId)
					.cache().queryOne();
			String topicName = getTopicMaster.getString("topicName");
			if (UtilValidate.isNotEmpty(getTopicMaster + "------------------------------------")) {
			
				List<GenericValue> listQuestions = EntityQuery.use(delegator).from("QuestionMaster")
						.where("topicId", topicId).cache().queryList();
				
				if (UtilValidate.isNotEmpty("-------------------------------------" + listQuestions)) {
					result = "success";
				} else {
					result = "error";
				}
				if (result.equalsIgnoreCase("success")) {
					resultMap.put("query_condition", result);
					resultMap.put("topicName", topicName);
					resultMap.put("listQuestions", listQuestions);
					request.setAttribute("resultMap", resultMap);
				} else {
					resultMap.put("query_condi" + "tion", result);
					request.setAttribute("resultMap", resultMap);
					request.setAttribute(" Error_Message", "There are no such values in the entity");
				}
			}
			// request.setAttribute("listOfQuestions", listOfQuestions);
		} catch (GenericEntityException e) {
			e.printStackTrace();
			request.setAttribute(" Error_Message", "There are no such values in the entity");
			result = "error";
			return result;
		}
		return result;
	}
}
