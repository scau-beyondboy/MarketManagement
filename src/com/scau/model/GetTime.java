package com.scau.model;

import java.util.Calendar;
import java.util.Date;


/**
 * 获得去时间
 * @author  Yang
 */
public class GetTime
{
	
	public GetTime()
	{
		
	}
	public static String getTime()
	{
		// 获取完整当前的时间
		Date date=new Date();
		Calendar calendar=Calendar.getInstance();
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH);
		int day=calendar.get(Calendar.DATE);
		//月份需加一，因为该月份值是从零开始的
		month=month+1;
		Integer month1=new Integer(month);
		String monthString;
		//如果月份小于十，前面就加零
		if(month<10)
		{
			monthString="0"+month1.toString();
		}
		else
		{
			monthString=month1.toString();
		}
		Integer day1=new Integer(day);
		String daysString;
		//如果日小于十，前面就加零
		if(day<10)
		{
			daysString="0"+day1.toString();
		}
		else
		{
			daysString=day1.toString();
		}
		//获取当前时间的小时，分，秒
		String time=date.toString().substring(11, 19);
		//返回xxxx-xx-xx xx:xx:xx 格式的时间
		return year+"-"+monthString+"-"+daysString+" "+time;
	}
}
