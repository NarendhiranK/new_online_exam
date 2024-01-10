package com.vastpro.onlineexam.events;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.UtilHttp;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;

import com.vastpro.onlineexam.constants.ConstantValue;

public class UserAttemptMasterEvent {
//	public static final String MODULE = UserExamMappingMasterEvent.class.getName();
//	public static String userAttemptMasterEvent(HttpServletRequest request, HttpServletResponse response) {		
//		Map<String, Object> combinedMap = UtilHttp.getCombinedMap(request);
//		Delegator delegator = (Delegator) combinedMap.get(ConstantValue.DELEGATOR);
//		String  userLoginId =(String)request.getSession().getAttribute(ConstantValue.USER_LOGIN_ID);
//		String  examId =(String)combinedMap.get(ConstantValue.EXAM_ID);
//		LocalDispatcher dispatcher = (LocalDispatcher) combinedMap.get(ConstantValue.DISPATCHER);
//		
//		try {
//			
//			GenericValue userLoginQueryResult = EntityQuery.use(delegator).from(ConstantValue.USER_LOGIN).where(ConstantValue.USER_LOGIN_ID, userLoginId).cache()
//					.queryOne();
//			if(UtilValidate.isNotEmpty(userLoginQueryResult)) {
//				String partyId =(String) userLoginQueryResult.get(ConstantValue.PARTY_ID);
//				GenericValue userExamMappingMasterQueryResult = EntityQuery.use(delegator).from(ConstantValue.USER_EXAM_MAPPING_MASTER).where(ConstantValue.PARTY_ID, partyId,ConstantValue.EXAM_ID,examId)
//						.queryOne();
//				if(UtilValidate.isNotEmpty(userExamMappingMasterQueryResult)) {
//					String noOfAttempts =(String)userExamMappingMasterQueryResult.get(ConstantValue.NO_OF_ATTEMPTS);
//					
//				}
////				Map<String,Object> userAttemptMasterResult = dispatcher.runSync("createUserAttemptMaster",
////						UtilMisc.toMap(ConstantValue.));
//			}
//			
//			
//		}
//		catch(GenericServiceException e) {
//			e.printStackTrace();
//			String errMsg = e.toString();
//		}
//		return null;
//}
}
