package com.scau.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 * ������ݿ����Ӷ���
 * @author beyondboy
 */
public class GetConnection
{
	private static String driver;
	private static String url;
	private static String user;
	private static String password;
	public static Connection getConnection (String paramFile)
	{
		//ʹ��Properties�������������ļ�
		Connection connection;
		try
		{
			Properties properties=new Properties();
			properties.load(new FileInputStream(paramFile));
			driver=properties.getProperty("driver");
			url=properties.getProperty("url");
			user=properties.getProperty("user");
			password=properties.getProperty("pass");
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
			return connection;
		} catch (Exception e)
		{
			e.printStackTrace();
		} 		
		return null;
	}
}
