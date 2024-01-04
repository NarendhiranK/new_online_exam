package com.vastpro.onlineexam.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.DispatchContext;

public class ListExamMasterService {

	public static Map<String, Object> listall(DispatchContext dctx, Map<String, ? extends Object> context) {


		Map<String, Object> listOfExamMaster = null;
		Delegator delegator = dctx.getDelegator();

		try {
			List<GenericValue> listOfExams = EntityQuery.use(delegator).from("ExamMaster").cache().queryList();

			listOfExamMaster.put("listOfExams", listOfExams);

		} catch (GenericEntityException e) {
			e.printStackTrace();
		}

		return listOfExamMaster;

	}
}
