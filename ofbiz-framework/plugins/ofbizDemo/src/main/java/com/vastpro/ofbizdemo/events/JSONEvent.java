package com.vastpro.ofbizdemo.events;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.entity.Delegator;

public class JSONEvent {
	public static String customJSONEvent(HttpServletRequest request, HttpServletResponse response) {
		
		HashMap<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("Username", "Prashanth");
		resultMap.put("Password", "Prashu10");
		resultMap.put("DOB", "27-04-99");
		resultMap.put("Gender", "Male");
		request.setAttribute("myDetails", resultMap);
		return "success";

	}
}
