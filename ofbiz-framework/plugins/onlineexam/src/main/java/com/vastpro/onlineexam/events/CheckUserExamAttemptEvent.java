package com.vastpro.onlineexam.events;

import java.util.ArrayList;


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
import org.apache.ofbiz.service.LocalDispatcher;

import com.vastpro.onlineexam.constants.ConstantValue;

public class CheckUserExamAttemptEvent {
	public static final String MODULE = CheckUserExamAttemptEvent.class.getName();

	public static String checkUserExamAttemptEvent(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> combinedMap = UtilHttp.getCombinedMap(request);
		Delegator delegator = (Delegator) combinedMap.get(ConstantValue.DELEGATOR);
		String examId = (String) combinedMap.get(ConstantValue.EXAM_ID);
		String userLoginId = (String) request.getSession().getAttribute(ConstantValue.USER_LOGIN_ID);
		LocalDispatcher dispatcher = (LocalDispatcher) combinedMap.get(ConstantValue.DISPATCHER);
		List<Map<String, Object>> attemptUserRecord = new ArrayList<>();
		List<Map<String, Object>> noAttemptUserRecord = new ArrayList<>();
		try {

			List<GenericValue> userExamMappingResult = EntityQuery.use(delegator)
					.from(ConstantValue.USER_EXAM_MAPPING_MASTER).where(ConstantValue.EXAM_ID, examId).queryList();
			if (UtilValidate.isNotEmpty(userExamMappingResult)) {
				for (GenericValue userExamMappingList : userExamMappingResult) {
					if (userExamMappingList != null) {
						String partyId = userExamMappingList.getString(ConstantValue.PARTY_ID);
						System.out.println("partyId....! :" + partyId);
						if (partyId != null) {
							GenericValue userAttemptMasterResult = EntityQuery.use(delegator)
									.from(ConstantValue.USER_ATTEMPT_MASTER)
									.where(ConstantValue.PARTY_ID, partyId, ConstantValue.EXAM_ID, examId).queryOne();
							if (UtilValidate.isNotEmpty(userAttemptMasterResult)) {

								GenericValue personEntityResult = EntityQuery.use(delegator).from(ConstantValue.PERSON)
										.where(ConstantValue.PARTY_ID, partyId).queryOne();
								String firstName = personEntityResult.getString(ConstantValue.FIRST_NAME);
								long attemptNumber = (long) userAttemptMasterResult.get(ConstantValue.ATTEMPT_NUMBER);
								String userPassed =userAttemptMasterResult.getString(ConstantValue.USER_PASSED);
                                 Debug.logInfo(ConstantValue.USER_PASSED , userPassed);
									
                                 if (attemptNumber<= 0) {
									Map<String, Object> userExamNoAttemptRecord = new HashMap<>();
									userExamNoAttemptRecord.put(ConstantValue.FIRST_NAME, firstName);
									attemptUserRecord.add(userExamNoAttemptRecord);

								}

								if (attemptNumber >= 1) {
									Map<String, Object> userExamAttemptRecord = new HashMap<>();
									userExamAttemptRecord.put(ConstantValue.FIRST_NAME, firstName);
									userExamAttemptRecord.put(ConstantValue.USER_PASSED ,userPassed);
									System.out.println(" user passed....> ! : " +userPassed);
									attemptUserRecord.add(userExamAttemptRecord);									
								}
								
							}
						}
					}

				}
			}
			request.setAttribute("AttemptUserRecords", attemptUserRecord);

		request.setAttribute("NoAttemptUserRecords", noAttemptUserRecord);
		return ConstantValue.SUCCESS_MESSAGE;
		} 
		
		catch (GenericEntityException e) {
			e.printStackTrace();
			String errMsg = e.toString();
		}
		return ConstantValue.ERROR_MESSAGE;
	}
}
