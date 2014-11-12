package com.scau.model;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;

/**
 * ������ݿ���
 * @author beyondboy
 * �������ݿ����
 */
public class RegisterSql
{
	private String driver;
	private String url;
	private String user;
	private String password;
	public RegisterSql()
	{
		
	}
	/**
	 * �������ݿ����Ӷ���
	 * @param paramFile ���ݿ���������ļ�
	 * @return ���ݿ����Ӷ���
	 * @throws Exception
	 */
	public void initParam(String paramFile) throws Exception
	{
		//ʹ��Properties�������������ļ�
		Properties properties=new Properties();
		properties.load(new FileInputStream(paramFile));
		driver=properties.getProperty("driver");
		url=properties.getProperty("url");
		user=properties.getProperty("user");
		password=properties.getProperty("pass");		
	}
	/**
	 * �������ݱ�
	 * @param sql �������ݱ����
	 * @throws Exception
	 */
	public void createTable(String sql)throws Exception
	{
		Class.forName(driver);
		Connection connection=DriverManager.getConnection(url, user, password);
		Statement stmt=connection.createStatement();
		stmt.executeUpdate(sql);
		connection.close();
		System.out.println("����");
	}
	/**
	 * �������
	 * @param sql �������
	 * @param arg �����б��ֵ
	 * @throws Exception
	 */
	public void insertUserPrepare(String sql,String... arg)throws Exception
	{
		Class.forName(driver);
		Connection connection=DriverManager.getConnection(url,user,password);
		PreparedStatement pstmt=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);		
		for(int i=0;i<arg.length;i++)
		{
			pstmt.setString(i+1, arg[i]);
		}
		pstmt.executeUpdate();
		connection.close();
	}
}
