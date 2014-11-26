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
 * ���������ݿ�
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
	 * �������ݿ����Ӷ���
	 * @param paramFile ���ݿ���������ļ�
	 * @return ���ݿ����Ӷ���
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
		//ʹ��Properties�������������ļ�
		Properties properties=new Properties();
		properties.load(new FileInputStream(paramFile));
		driver=properties.getProperty("driver");
		url=properties.getProperty("url");
		user=properties.getProperty("user");
		password=properties.getProperty("pass");
		Class.forName(driver);		
	}
	//�������
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
	//�������ύ�����ݿ���
	public  void insertTable(String sql,String[][] args)
	{
		try
		(
			Connection connection=DriverManager.getConnection(url, user, password);
			//����д�����ݿ�Ĵ����洢����
			CallableStatement callableStatement=connection.prepareCall("{call stock_add(?,?,?,?)}");
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
		)
		{
			for(int i=0;i<args.length;i++)
			{
				//�������
				callableStatement.setObject(1, args[i][0]);
				callableStatement.setObject(2, args[i][4]);
				callableStatement.setObject(3, args[i][5]);
				callableStatement.registerOutParameter(4, Types.INTEGER);
				callableStatement.execute();
				//�ж����ݿ��Ƿ��Ѵ��ڸ���Ʒ���
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
