package com.scau.model;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * 添加数据库类
 * @author beyondboy
 * 返回数据库对象
 */
public class Sql
{
	private String driver;
	private String url;
	private String user;
	private String password;
	/**
	 * 返回数据库连接对象
	 * @param paramFile 数据库参数配置文件
	 * @return 数据库连接对象
	 * @throws Exception
	 */
	public Connection getConnection(String paramFile) throws Exception
	{
		//使用Properties类来加载属性文件
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
