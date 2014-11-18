package com.scau.model;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 添加数据库类
 * @author beyondboy
 * 返回数据库对象
 */
public class RegisterSql
{
	private String driver;
	private String url;
	private String user;
	private String password;
	public static final String QUERY="select * from register_table where ? in(userName,cardId) and pass=? and user_status=?";
	public RegisterSql(String paramFile)
	{
		try
		{
			initParam(paramFile);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * 返回数据库连接对象
	 * @param paramFile 数据库参数配置文件
	 * @return 数据库连接对象
	 * @throws Exception
	 */
	public void initParam(String paramFile) throws Exception
	{
		//使用Properties类来加载属性文件
		Properties properties=new Properties();
		properties.load(new FileInputStream(paramFile));
		driver=properties.getProperty("driver");
		url=properties.getProperty("url");
		user=properties.getProperty("user");
		password=properties.getProperty("pass");
		Class.forName(driver);
	}
	/**
	 * 建立数据表
	 * @param sql 建立数据表语句
	 * @throws Exception
	 * 
	 */
	public void createTable(String sql)throws Exception
	{		
		Connection connection=DriverManager.getConnection(url, user, password);
		Statement stmt=connection.createStatement();
		stmt.executeUpdate(sql);
		connection.close();
	}
	/**
	 * 插入语句
	 * @param sql 插入语句
	 * @param arg 设置列表的值
	 * @throws Exception
	 */
	public void insertUserPrepare(String sql,String... arg)throws Exception
	{
		Connection connection=DriverManager.getConnection(url,user,password);
		PreparedStatement pstmt=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);		
		for(int i=0;i<arg.length;i++)
		{
			pstmt.setString(i+1, arg[i]);
		}
		pstmt.executeUpdate();
		connection.close();
	}
	public boolean validate(String userName,String userPass,String statu)
	{
		try
		
		( 
			Connection connection=DriverManager.getConnection(url, user, password);
			PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
		)
		{
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, userPass);
			preparedStatement.setString(3, statu);
			try
			(
					ResultSet rs=preparedStatement.executeQuery();					
			)
			{
				if(rs.next())
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		 catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
