package com.scau.model;

import java.util.Calendar;
import java.util.Date;


/**
 * ���ȥʱ��
 * @author  Yang
 */
public class GetTime
{
	
	public GetTime()
	{
		
	}
	public static String getTime()
	{
		// ��ȡ������ǰ��ʱ��
		Date date=new Date();
		Calendar calendar=Calendar.getInstance();
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH);
		int day=calendar.get(Calendar.DATE);
		//�·����һ����Ϊ���·�ֵ�Ǵ��㿪ʼ��
		month=month+1;
		Integer month1=new Integer(month);
		String monthString;
		//����·�С��ʮ��ǰ��ͼ���
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
		//�����С��ʮ��ǰ��ͼ���
		if(day<10)
		{
			daysString="0"+day1.toString();
		}
		else
		{
			daysString=day1.toString();
		}
		//��ȡ��ǰʱ���Сʱ���֣���
		String time=date.toString().substring(11, 19);
		//����xxxx-xx-xx xx:xx:xx ��ʽ��ʱ��
		return year+"-"+monthString+"-"+daysString+" "+time;
	}
}
