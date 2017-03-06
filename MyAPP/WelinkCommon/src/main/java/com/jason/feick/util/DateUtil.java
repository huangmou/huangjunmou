package com.jason.feick.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class DateUtil {
	private static String mYear;
	private static String mMonth;
	private static String mDay;
	private static String mWay;

	public static String StringData() {
		final Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
		mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
		mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
		mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
		if ("1".equals(mWay)) {
			mWay = "天";
		} else if ("2".equals(mWay)) {
			mWay = "一";
		} else if ("3".equals(mWay)) {
			mWay = "二";
		} else if ("4".equals(mWay)) {
			mWay = "三";
		} else if ("5".equals(mWay)) {
			mWay = "四";
		} else if ("6".equals(mWay)) {
			mWay = "五";
		} else if ("7".equals(mWay)) {
			mWay = "六";
		}
		return  mMonth + "月" + mDay + "日" + "   星期" + mWay;
	}

	public static Map<String,String> getInOutDate(){
		Date nowDate=new Date();
		long inTime=nowDate.getTime();
		long outTime=nowDate.getTime()+24*60*60*1000;
		DateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");

		final Calendar inCalender = Calendar.getInstance();
		final Calendar outCalender = Calendar.getInstance();
		inCalender.setTimeInMillis(inTime);
		outCalender.setTimeInMillis(outTime);

		String inDate=formatter.format(inCalender.getTime());
		String outDate=formatter.format(outCalender.getTime());

		Map<String,String> map=new HashMap<String,String>();
		map.put("InDate",inDate);
		map.put("OutDate",outDate);

		Log.e("TAGCALENDAR",outDate);
		return map;
	}

}
