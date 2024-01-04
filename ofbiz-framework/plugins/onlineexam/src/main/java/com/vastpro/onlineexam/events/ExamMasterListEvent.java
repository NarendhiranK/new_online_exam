package com.vastpro.onlineexam.events;

import java.util.ArrayList;
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

import com.vastpro.onlineexam.constants.ConstantValue;


public class ExamMasterListEvent {

	
	/*
	 * This event either it is used to retrieve the all the exams or retrieve the particular exam details 
	 * based on the exam id on the exam  master entity.
	 * 
	 * */
	public static String listOfExams(HttpServletRequest request, HttpServletResponse response) {

		String examId = (String) request.getAttribute(ConstantValue.EXAM_ID);
		Debug.log("ExamID:" + examId);
		Delegator delegator = (Delegator) request.getAttribute(ConstantValue.DELEGATOR);
		Map<String, Object> listOfExamMaster = new HashMap<String, Object>();
		List<Map<String, Object>> tempData = new ArrayList<Map<String, Object>>();
		try {
			if (UtilValidate.isNotEmpty(examId)) {
				GenericValue listExambyId = EntityQuery.use(delegator).from("ExamMaster").where("examId", examId)
						.queryOne();
				if (UtilValidate.isNotEmpty(listExambyId)) {
					listOfExamMaster.put(ConstantValue.EXAM_ID, listExambyId.get(ConstantValue.EXAM_ID));
					listOfExamMaster.put(ConstantValue.EXAM_NAME, listExambyId.get(ConstantValue.EXAM_NAME));
					listOfExamMaster.put(ConstantValue.DESCRIPTION, listExambyId.get(ConstantValue.DESCRIPTION));
					listOfExamMaster.put(ConstantValue.CREATION_DATE, listExambyId.get(ConstantValue.CREATION_DATE));
					listOfExamMaster.put(ConstantValue.EXPIRATION_DATE, listExambyId.get(ConstantValue.EXPIRATION_DATE));
					listOfExamMaster.put(ConstantValue.NO_OF_QUESTIONS, listExambyId.get(ConstantValue.NO_OF_QUESTIONS));
					listOfExamMaster.put(ConstantValue.DURATION_MINUTES, listExambyId.get(ConstantValue.DURATION_MINUTES));
					listOfExamMaster.put(ConstantValue.PASS_PERCENTAGE, listExambyId.get(ConstantValue.PASS_PERCENTAGE));
					listOfExamMaster.put(ConstantValue.QUESTIONS_RANDOMIZED, listExambyId.get(ConstantValue.QUESTIONS_RANDOMIZED));
					listOfExamMaster.put(ConstantValue.ANSWERS_MUST, listExambyId.get(ConstantValue.ANSWERS_MUST));
					listOfExamMaster.put(ConstantValue.ENABLE_NEGATIVE_MARK, listExambyId.get(ConstantValue.ENABLE_NEGATIVE_MARK));
					listOfExamMaster.put(ConstantValue.NEGATIVE_MARK_VALUE, listExambyId.get(ConstantValue.NEGATIVE_MARK_VALUE));
					tempData.add(listOfExamMaster);
					request.setAttribute("ExamData", tempData);
				} else {
					request.setAttribute("ENTITY ERROR", "No records found");
				}
			} else {
				List<GenericValue> listOfExams = EntityQuery.use(delegator).from("ExamMaster").queryList();
				Debug.log("listofExams  : " + listOfExams);
				if (UtilValidate.isNotEmpty(listOfExams)) {
					for (GenericValue listvalue : listOfExams) {
						HashMap<String,Object>listOfExamMasterEntity=new HashMap<>();
						listOfExamMasterEntity.put(ConstantValue.EXAM_ID, listvalue.get(ConstantValue.EXAM_ID));
						listOfExamMasterEntity.put(ConstantValue.EXAM_NAME, listvalue.get(ConstantValue.EXAM_NAME));
						listOfExamMasterEntity.put(ConstantValue.DESCRIPTION, listvalue.get(ConstantValue.DESCRIPTION));
						listOfExamMasterEntity.put(ConstantValue.CREATION_DATE, listvalue.get(ConstantValue.CREATION_DATE));
						listOfExamMasterEntity.put(ConstantValue.EXPIRATION_DATE, listvalue.get(ConstantValue.EXPIRATION_DATE));
						listOfExamMasterEntity.put(ConstantValue.NO_OF_QUESTIONS, listvalue.get(ConstantValue.NO_OF_QUESTIONS));
						listOfExamMasterEntity.put(ConstantValue.DURATION_MINUTES, listvalue.get(ConstantValue.DURATION_MINUTES));
						listOfExamMasterEntity.put(ConstantValue.PASS_PERCENTAGE, listvalue.get(ConstantValue.PASS_PERCENTAGE));
						listOfExamMasterEntity.put(ConstantValue.QUESTIONS_RANDOMIZED, listvalue.get(ConstantValue.QUESTIONS_RANDOMIZED));
						listOfExamMasterEntity.put(ConstantValue.ANSWERS_MUST, listvalue.get(ConstantValue.ANSWERS_MUST));
						listOfExamMasterEntity.put(ConstantValue.ENABLE_NEGATIVE_MARK, listvalue.get(ConstantValue.ENABLE_NEGATIVE_MARK));
						listOfExamMasterEntity.put(ConstantValue.NEGATIVE_MARK_VALUE, listvalue.get(ConstantValue.NEGATIVE_MARK_VALUE));
						tempData.add(listOfExamMasterEntity);
					}
					Debug.log("tempdata :" + tempData);
					Debug.log("listofexam  : " + listOfExamMaster);
					request.setAttribute("ExamData", tempData);
				
				} else {
					request.setAttribute(ConstantValue.ERROR_MESSAGE, "No records found");
				}
			}
		} catch (GenericEntityException e) {
			e.printStackTrace();
			request.setAttribute(ConstantValue.ERROR_MESSAGE, "No records found");
			return "error";
		}
		return "success";
	}
}