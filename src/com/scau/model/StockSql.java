package com.scau.model;

import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.Properties;
/**
 * 进货的数据库
 * @author beyondboy
 */
public class StockSql
{
	private  String driver;
	private String url;
	private String password;
	private String user;
	public final static String CREATE_TABLE="create table if not exists stock_sql(id int auto_increment ,name varchar(255),good_date varchar(255),price varchar(255),count varchar(255),sum varchar(255),type varchar(255),operater varchar(255),primary key (id),index(name(50)));"; 
	/**
	 * 返回数据库连接对象
	 * @param paramFile 数据库参数配置文件
	 * @return 数据库连接对象
	 * @throws Exception
	 */
	public StockSql(String paramFile)
	{
		try
		{
			initParam(paramFile);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
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
	//创建表格
	public  void createTable()
	{
		try
		(
				Connection connection=DriverManager.getConnection(url, user, password);
				Statement statement=connection.createStatement();
		)
		{
			statement.executeUpdate(CREATE_TABLE);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	//将数据提交到数据库中
	public  void insertTable(String sql,String[][] args)
	{
		try
		(
			Connection connection=DriverManager.getConnection(url, user, password);
			//调用写入数据库的创建存储过程
			CallableStatement callableStatement=connection.prepareCall("{call stock_add(?,?,?,?)}");
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
		)
		{
			for(int i=0;i<args.length;i++)
			{
				//插入参数
				callableStatement.setObject(1, args[i][0]);
				callableStatement.setObject(2, args[i][4]);
				callableStatement.setObject(3, args[i][5]);
				callableStatement.registerOutParameter(4, Types.INTEGER);
				callableStatement.execute();
				//判断数据库是否已存在该商品编号
				if(callableStatement.getInt(4)==1)
					continue;
				//preparedStatement.setInt(1, Integer.parseInt(args[i][0]));
				for(int j=0;j<args[i].length;j++)
				{
					preparedStatement.setObject(j+1, args[i][j]);
				}
				preparedStatement.executeUpdate();
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
