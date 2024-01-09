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

public class TopicsForUsersExam {

	public static String topicsForUsersExam(HttpServletRequest request,HttpServletResponse response) {
		
		Delegator delegator = (Delegator) request.getAttribute(ConstantValue.DELEGATOR);
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute(ConstantValue.DISPATCHER);
		List<String> userexamid = (List<String>) request.getSession().getAttribute("examIds");
		Map<String,Object> topicslist=new HashMap<>();
		List<Map<String,Object>> Listoftopics=new ArrayList<>();
		for(int i=0;i<userexamid.size();i++) {
			String examid=userexamid.get(i);
			try {
				Debug.log("Inside the try block............");
				List<GenericValue>genericvalues=EntityQuery.use(delegator).from(ConstantValue.EXAM_TOPIC_MAPPING_MASTER).where(ConstantValue.EXAM_ID,examid).cache().queryList();
				for(GenericValue gen : genericvalues) {
						HashMap<String,Object> listofTopics=new HashMap<>();
						listofTopics.put("topicId", gen.getString(ConstantValue.TOPIC_ID));
						Debug.logInfo("TOpic Id..." , gen.getString(ConstantValue.TOPIC_ID).toString());
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
