package com.vastpro.onlineexam.events;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.Debug;

public class MyEvent {
public String myEvent(HttpServletRequest request, HttpServletResponse response) {

	Debug.logInfo("statevalue .........................: ", "hi debug log");
	System.out.println("hello world da mfmmm...................");
	
	request.setAttribute("state", "response sent from event...!");
	return "success";
}
}
