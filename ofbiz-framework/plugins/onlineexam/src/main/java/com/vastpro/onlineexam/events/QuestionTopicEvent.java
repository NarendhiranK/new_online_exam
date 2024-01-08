package com.vastpro.onlineexam.events;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilHttp;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;

import com.vastpro.onlineexam.constants.ConstantValue;

public class QuestionTopicEvent {

	public static final String module = QuestionTopicEvent.class.getName();
	public static String viewQuestions(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> combinedMap = UtilHttp.getCombinedMap(request);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String result = null;
		String topicId = (String)combinedMap.get(ConstantValue.TOPIC_ID);
		
		Debug.logInfo("topicId",topicId);
		Delegator delegator = (Delegator) combinedMap.get(ConstantValue.DELEGATOR);
		try {
			GenericValue getTopicMaster = EntityQuery.use(delegator).from(ConstantValue.TOPIC_MASTER).where(ConstantValue.TOPIC_ID, topicId)
					.cache().queryOne();

			String topicName = getTopicMaster.getString(ConstantValue.TOPIC_NAME);
			

			if (UtilValidate.isNotEmpty(getTopicMaster)) {
				Debug.log("topicmaster..."+getTopicMaster.toString());
				List<GenericValue> listQuestions = EntityQuery.use(delegator).from(ConstantValue.QUESTION_MASTER)
						.where(ConstantValue.TOPIC_ID, topicId).cache().queryList();
				Debug.log("list of questions...."+listQuestions.toString());
				if (UtilValidate.isNotEmpty(listQuestions)) {
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
					resultMap.put("querycondition", result);
					request.setAttribute("resultMap", resultMap);
					request.setAttribute(" Error_Message", "There are no such values in the entity");
				}
			}
			else {
				resultMap.put("querycondition", result);
			}
			request.setAttribute("topicName", topicName);
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
