package com.vastpro.onlineexam.events;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;

import com.vastpro.onlineexam.constants.ConstantValue;

public class RetrieveEnumerationListEvent {
	public static final String module = RetrieveEnumerationListEvent.class.getName();

	public static String retrieveEnumerationListEvent(HttpServletRequest request, HttpServletResponse response) {
		String enumTypeId ="QT";
		Delegator delegator = (Delegator) request.getAttribute(ConstantValue.DELEGATOR);
		List<Map<String,Object>> listOfEnumerationRecords=new LinkedList<>();
		Map<String,Object>enumerationRecordMap=new HashMap<>();
		try {
			List<GenericValue> enumerationTypeList = EntityQuery.use(delegator).from(ConstantValue.ENUMERATION)
					.where(ConstantValue.ENUMERATION_TYPE_ID, enumTypeId).queryList();
			if(UtilValidate.isNotEmpty(enumerationTypeList)) {
				int index =0;
				for(GenericValue oneEnumeration:enumerationTypeList) {
					Map<String,Object>oneEnumerationRecord=new HashMap<>();
					oneEnumerationRecord.put("enumId", oneEnumeration.get(ConstantValue.ENUM_ID));
					oneEnumerationRecord.put("description", oneEnumeration.get(ConstantValue.DESCRIPTION));
					listOfEnumerationRecords.add(index , oneEnumerationRecord );
					index++;
				}
			}

				request.setAttribute("resultMap", listOfEnumerationRecords);
		} catch (GenericEntityException e) {
			
			e.printStackTrace();
		}
		return "Success";
	}
}

