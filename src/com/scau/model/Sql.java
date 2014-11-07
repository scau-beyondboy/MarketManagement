package com.scau.model;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * ������ݿ���
 * @author beyondboy
 * �������ݿ����
 */
public class Sql
{
	private String driver;
	private String url;
	private String user;
	private String password;
	/**
	 * �������ݿ����Ӷ���
	 * @param paramFile ���ݿ���������ļ�
	 * @return ���ݿ����Ӷ���
	 * @throws Exception
	 */
	public Connection getConnection(String paramFile) throws Exception
	{
		//ʹ��Properties�������������ļ�
		Properties properties=new Properties();
		properties.load(new FileInputStream(paramFile));
		driver=properties.getProperty("driver");
		url=properties.getProperty("url");
		user=properties.getProperty("user");
		password=properties.getProperty("pass");
		Class.forName("driver");
		Connection connection=DriverManager.getConnection(url, user, password);
		return connection;
	}
}
