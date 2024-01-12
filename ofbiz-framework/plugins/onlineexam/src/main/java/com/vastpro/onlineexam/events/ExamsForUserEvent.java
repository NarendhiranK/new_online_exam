package com.vastpro.onlineexam.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilHttp;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.LocalDispatcher;

import com.vastpro.onlineexam.constants.ConstantValue;

public class ExamsForUserEvent {

	public static String examsForUserEvent(HttpServletRequest request, HttpServletResponse response) {
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute(ConstantValue.USERLOGIN);
		Delegator delegator = (Delegator) request.getAttribute(ConstantValue.DELEGATOR);
		String partyId = (String) userLogin.get(ConstantValue.PARTY_ID);
		Debug.logInfo("userLogin", ":" + userLogin);
		List<Map<String, Object>> examList = new LinkedList<Map<String, Object>>();

		try {
			List<GenericValue> listOfExamsForUser = EntityQuery.use(delegator)
					.from(ConstantValue.USER_EXAM_MAPPING_MASTER).where(ConstantValue.PARTY_ID, partyId).cache()
					.queryList();
			if (UtilValidate.isNotEmpty(listOfExamsForUser)) {
				Debug.logInfo("List of exams for this user", "" + listOfExamsForUser);
				request.setAttribute("listOfExamsForUser", listOfExamsForUser);
			} else {
				request.setAttribute(ConstantValue.ERROR_MESSAGE, "There are no exams alloted for this user");
			}

 			List<String> examIds = new ArrayList<>();
			for (GenericValue perExamFromList : listOfExamsForUser) {
				String examId = (String) perExamFromList.get(ConstantValue.EXAM_ID);
				examIds.add(examId);
				GenericValue perExamDetails = EntityQuery.use(delegator).from(ConstantValue.EXAM_MASTER)
						.where(ConstantValue.EXAM_ID, examId).cache().queryFirst();
				if (UtilValidate.isNotEmpty(perExamDetails)) {
					Map<String, Object> examDetailsResultMap = new HashMap<String, Object>();
					examDetailsResultMap.put("perExamDetails", perExamDetails);
					System.out.println("Exam details ===============================   :::" + perExamDetails);
					examList.add(examDetailsResultMap);
				}
			}
			request.setAttribute("examDetailsResultList", examList);
			Debug.logInfo("List of  ExamId's for this user...............", "" + examIds);
			request.getSession().setAttribute("examIds", examIds);
			return ConstantValue.SUCCESS;

		} catch (GenericEntityException e) {
			e.printStackTrace();
			return ConstantValue.ERROR;
		}

	}

}
