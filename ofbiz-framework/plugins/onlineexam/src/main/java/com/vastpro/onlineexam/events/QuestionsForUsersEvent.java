package com.vastpro.onlineexam.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.LocalDispatcher;

public class QuestionsForUsersEvent {

	public static String questionsForUsersEvent(HttpServletRequest request, HttpServletResponse response) {

		Delegator delegator = (Delegator) request.getAttribute("delegator");
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		List<String> userexamid = (List<String>) request.getSession().getAttribute("examIds");
		HashMap<String, Object> listofexams = new HashMap<>();
		List<Map<String,Object>> list = new ArrayList<>();
		Debug.log("Lisst of exam id's........." + userexamid);
		for(int i=0;i<userexamid.size();i++) {
			String examid = userexamid.get(i);
			Debug.log("examId.........." + examid);
			try {
				
				List<GenericValue> genericvalue=EntityQuery.use(delegator).from("ExamMaster").where("examId",examid).cache().queryList();
				for(GenericValue genvalue : genericvalue) {
					HashMap<String,Object> listofexamdetails=new HashMap<>();
					listofexamdetails.put("examId",genvalue.getString("examId"));
					listofexamdetails.put("examName", genvalue.getString("examName"));
					listofexamdetails.put("description", genvalue.getString("description"));
					listofexamdetails.put("creationDate", genvalue.getString("creationDate"));
					listofexamdetails.put("expirationDate", genvalue.getString("expirationDate"));
					listofexamdetails.put("noOfQuestions", genvalue.getString("noOfQuestions"));
					listofexamdetails.put("durationMinutes", genvalue.getString("durationMinutes"));
					listofexamdetails.put("passPercentage", genvalue.getString("passPercentage"));
					listofexamdetails.put("questionsRandomized", genvalue.getString("questionsRandomized"));
					listofexamdetails.put("answersMust", genvalue.getString("answersMust"));
					listofexamdetails.put("enableNegativeMark", genvalue.getString("enableNegativeMark"));
					listofexamdetails.put("negativeMarkValue", genvalue.getString("negativeMarkValue"));
					list.add(listofexams);
					listofexamdetails=new HashMap<>();
				}

			}catch (GenericEntityException e) {
				e.printStackTrace();
			}

		}
		
		listofexams.put("listofexams", list);
		Debug.log(listofexams.toString());
		return "success";
	}
}
