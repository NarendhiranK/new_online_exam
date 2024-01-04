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

public class QuestionMasterListEvent {

	public static String questionMasterListEvent(HttpServletRequest request,HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute(ConstantValue.DELEGATOR);
		Map<String, Object> combinedMap = UtilHttp.getCombinedMap(request);
		 String questionId =(String)combinedMap.get(ConstantValue.QUESTION_ID);
		 Long qId =Long.parseLong(questionId);
		  Map<String, Object> listOfExamMaster = new HashMap<String,Object>();
		 
		  if(UtilValidate.isNotEmpty(questionId)) {
			  GenericValue getRecord;
			try {
				getRecord = EntityQuery.use(delegator).from("QuestionMaster").where("questionId",qId).cache().queryOne();
				request.setAttribute("getRecord", getRecord);
			} catch (GenericEntityException e) {
				
				e.printStackTrace();
			}
		       
		  }
		  else {
			  try {
					List<GenericValue> listOfQuestions = EntityQuery.use(delegator).from("QuestionMaster").cache().queryList();
					
					Debug.log("listOfQuestions",listOfQuestions);
					if(UtilValidate.isNotEmpty(listOfQuestions)) {
						request.setAttribute("listOfQuestions",listOfQuestions);
					}
					else {
						request.setAttribute("noRecordFound","no record found in the entity!");
						return "error";
					}
			  }
			  catch(GenericEntityException e) {
				  e.printStackTrace();
			  }
		  }
		 	
		 		  return "success";
	}

}
