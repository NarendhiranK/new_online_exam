package com.vastpro.onlineexam.additionalevents;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.Debug;

public class DateTimeClass {

	public static String currentDateAndTime(HttpServletRequest request,HttpServletResponse response) {
		Calendar calendar=Calendar.getInstance();
		Date currentDate=calendar.getTime();
		String currentDateTime=currentDate.toString();
		
		Date currentdate=new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDatetime=dateFormat.format(currentdate);
		Debug.log("current date time.................>" + currentDatetime);
		
		return "success";
	}
}
