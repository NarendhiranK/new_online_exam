package com.vastpro.onlineexam.events;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.LocalDispatcher;

import com.vastpro.onlineexam.constants.ConstantValue;

public class ExamsForUserEvent {

	public static String examsForUserEvent(HttpServletRequest request,HttpServletResponse response) {
		 	Delegator delegator = (Delegator) request.getAttribute(ConstantValue.DELEGATOR);
	        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute(ConstantValue.DISPATCHER);
	        String username=(String)request.getAttribute(ConstantValue.USERNAME);
	        String userexamid=null;
	        try {
	        	GenericValue userLogin = EntityQuery.use(delegator).from("UserLogin").where("userLoginId", username).cache()
						.queryFirst();
				if (UtilValidate.isNotEmpty(userLogin)) {
					String partyId = (String) userLogin.get(ConstantValue.PARTY_ID);
					Debug.log("partyID....." +partyId);
					List<GenericValue> examId = EntityQuery.use(delegator).from("UserExamMappingMaster").where("partyId", partyId)
							.cache().queryList();
					if(examId!=null && !examId.isEmpty()) {
						System.out.println("Exam Id's for the user's ...." + examId);
					}
					Debug.log(examId.toString());

//					System.out.println("List of exams for user.............." + examId);
					List<String> examIds=new ArrayList<>();
					for(GenericValue genericValue: examId) {
						
						
						String examIId=(String)genericValue.getString("examId");
						userexamid=examIId;
						examIds.add(examIId);
						Debug.log("List of user ExamId's..............." + examIds);
						
						GenericValue listofexamsusingexamid=EntityQuery.use(delegator).from("ExamMaster").where("examId",userexamid).cache().queryFirst();
						if(listofexamsusingexamid!=null && listofexamsusingexamid.isEmpty()) {
//							System.out.println("List of exams for the exam Id.." + listofexamsusingexamid);
							request.setAttribute("examId", userexamid);						}
						else {
//							System.out.println("exam Details..." + listofexamsusingexamid);
							Debug.log("The exam list is empty");
						}
						
					}
					request.getSession().setAttribute("examIds",examIds);
					return "success";
					
				}
				else {
					
				}
	        }
	        catch(GenericEntityException e) {
	        	e.printStackTrace();
	        	return "error";
	        }
			
			
			
//			Map<String, String> resultMap = new HashMap<String, String>();
//			if (result.equalsIgnoreCase("success")) {
//				resultMap.put("usersRoletypeId", roleTypeId);
//				resultMap.put("login_condition", result);
//				request.setAttribute("resultMap", resultMap);
//			} else {
//				resultMap.put("login_condition", result);
//				request.setAttribute("resultMap", resultMap);
//			}
			// System.out.println(userLogin.toString());
			// request.setAttribute("EVENT_MESSAGE", "username and password succesfully." );
			return "success";
	}

}
