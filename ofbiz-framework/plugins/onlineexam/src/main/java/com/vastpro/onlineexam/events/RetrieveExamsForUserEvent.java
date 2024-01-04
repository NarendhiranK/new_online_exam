package com.vastpro.onlineexam.events;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.entity.Delegator;

import com.vastpro.onlineexam.constants.ConstantValue;

public class RetrieveExamsForUserEvent {

	public static final String module = RetrieveExamsForUserEvent.class.getName();
	
	public static String getExamsForUser(HttpServletRequest request,HttpServletResponse response) {
		Delegator delegator=(Delegator)request.getAttribute(ConstantValue.DELEGATOR);
		return "success";)
	}

}
