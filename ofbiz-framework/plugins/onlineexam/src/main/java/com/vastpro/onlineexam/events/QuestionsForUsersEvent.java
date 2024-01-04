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

import com.vastpro.onlineexam.constants.ConstantValue;

public class QuestionsForUsersEvent {

	public static String questionsForUsersEvent(HttpServletRequest request, HttpServletResponse response) {

		Delegator delegator = (Delegator) request.getAttribute(ConstantValue.DELEGATOR);
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute(ConstantValue.DISPATCHER);
		List<String> userexamid = (List<String>) request.getSession().getAttribute("examIds");
		HashMap<String, Object> listofexams = new HashMap<>();
		List<Map<String,Object>> list = new ArrayList<>();
		Debug.log("Lisst of exam id's........." + userexamid);
		for(int i=0;i<userexamid.size();i++) {
			String examid = userexamid.get(i);
			Debug.log("examId.........." + examid);
			try {
				
				List<GenericValue> genericvalue=EntityQuery.use(delegator).from(ConstantValue.EXAM_MASTER).where(ConstantValue.EXAM_ID,examid).cache().queryList();
				for(GenericValue genvalue : genericvalue) {
					HashMap<String,Object> listofexamdetails=new HashMap<>();
					listofexamdetails.put("examId",genvalue.getString(ConstantValue.EXAM_ID));
					listofexamdetails.put("examName", genvalue.getString(ConstantValue.EXAM_NAME));
					listofexamdetails.put("description", genvalue.getString(ConstantValue.DESCRIPTION));
					listofexamdetails.put("creationDate", genvalue.getString(ConstantValue.CREATION_DATE));
					listofexamdetails.put("expirationDate", genvalue.getString(ConstantValue.EXPIRATION_DATE));
					listofexamdetails.put("noOfQuestions", genvalue.getString(ConstantValue.NO_OF_QUESTIONS));
					listofexamdetails.put("durationMinutes", genvalue.getString(ConstantValue.DURATION_MINUTES));
					listofexamdetails.put("passPercentage", genvalue.getString(ConstantValue.PASS_PERCENTAGE));
					listofexamdetails.put("questionsRandomized", genvalue.getString(ConstantValue.QUESTIONS_RANDOMIZED));
					listofexamdetails.put("answersMust", genvalue.getString(ConstantValue.ANSWERS_MUST));
					listofexamdetails.put("enableNegativeMark", genvalue.getString(ConstantValue.ENABLE_NEGATIVE_MARK));
					listofexamdetails.put("negativeMarkValue", genvalue.getString(ConstantValue.NEGATIVE_MARK_VALUE));
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
