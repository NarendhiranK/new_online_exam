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

public class TopicsForUsersExam {

	public static String topicsForUsersExam(HttpServletRequest request,HttpServletResponse response) {
		
		Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		List<String> userexamid = (List<String>) request.getSession().getAttribute("examIds");
		Map<String,Object> topicslist=new HashMap<>();
		List<Map<String,Object>> Listoftopics=new ArrayList<>();
		for(int i=0;i<userexamid.size();i++) {
			String examid=userexamid.get(i);
			try {
				Debug.log("Inside the try block............");
				List<GenericValue>genericvalues=EntityQuery.use(delegator).from("ExamTopicMappingMaster").where("examId",examid).cache().queryList();
				for(GenericValue gen : genericvalues) {
						HashMap<String,Object> listofTopics=new HashMap<>();
						listofTopics.put("topicId", gen.getString("topicId"));
						Debug.log("TOpic Id..." + gen.getString("topicId"));
						Listoftopics.add(listofTopics);
				}
				
			}
			catch(GenericEntityException e) {
				e.printStackTrace();
			}
		}
		topicslist.put("topicsdetailslist", Listoftopics);
		request.setAttribute("topicslist", topicslist);
		return "suucess";
	}
}
