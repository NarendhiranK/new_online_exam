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
				
			}catch (GenericEntityException e) {
				e.printStackTrace();
			}

		}
		
		listofexams.put("listofexams", list);
		Debug.log(listofexams.toString());
		return "success";
	}
}
