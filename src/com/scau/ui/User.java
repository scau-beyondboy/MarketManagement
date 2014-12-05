package com.scau.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.AttributedCharacterIterator;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import com.scau.model.GetConnection;
public class User
{
	JFrame jFrame=new JFrame();
	JPanel panel=new JPanel();
	JPanel panel2=new JPanel();
	JLabel queryJLabel=new JLabel("��ѯ��Ϣ:");
	JComboBox<String> queryBox=new JComboBox<String>();
	JButton comfireButton=new JButton("ȷ��");
	JLabel userJLabel=new JLabel("����Ա:");
	JTextField userField=new JTextField(12);
	DefaultTableModel tableModel=new DefaultTableModel();
	JTable jTable =new JTable(tableModel);	
	JButton addButton=new JButton("���");
	JButton deleteButton=new JButton("ɾ��");
	JButton modifyButton=new JButton("�޸�");
	JButton saleButton=new JButton("����");
	JButton stockButton=new JButton("���");
	JButton frontButton=new JButton("ǰ̨����");
	JButton exitButton=new JButton("�˳�");
	int queryway=0;
	String name="";
	public User(String name)
	{
		this.name=name;
		Init();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setTitle("����Ա");
		jFrame.setLocation(200, 20);
		jFrame.setSize(740,700);
		jFrame.setVisible(true);
	}
	//���һ�����б�ǩ���ı������
	public void addPanel(JLabel contentJLabel,JComponent component)
	{
		JPanel jPanel=new JPanel();
		jPanel.add(contentJLabel);
		jPanel.add(component);
		panel.add(jPanel);
	}
	public void Init()
	{
		userField.setText(name);
		queryBox.addItem("�۸���Ϣ");
		queryBox.addItem("�û���Ϣ");
		queryBox.addItem("������Ϣ");
		queryBox.addItem("�����Ϣ");
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 0));
		addPanel(queryJLabel, queryBox);
		panel.add(comfireButton);
		addPanel(userJLabel,userField);
		jFrame.add(panel, BorderLayout.NORTH);
		panel2.setLayout(new GridLayout(2, 3, 40, 20));
		panel2.add(addButton);
		panel2.add(deleteButton);
		panel2.add(modifyButton);
		panel2.add(saleButton);
		panel2.add(stockButton);
		panel2.add(frontButton);
		panel2.add(exitButton);
		jFrame.add(panel2,BorderLayout.SOUTH);
		jFrame.add(new JScrollPane(jTable));
		//�˳���ť��Ӽ���
		exitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				jFrame.setVisible(false);
			}
		});
		//��ѯ��ʽ��Ӽ���
		queryBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				queryway=queryBox.getSelectedIndex();				
			}
		});
		//ȷ�ϰ�ť��Ӽ���
		comfireButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//���߳�ˢ�½���
				new UpdateUi().start();
				/*try
				(
					Connection connection=GetConnection.getConnection("mysql.properties");
					Statement statement=connection.createStatement();
				)
				{
					DatabaseMetaData data=connection.getMetaData();
					String  str="price_sql";
					switch (queryway)
					{
						case 0:
							str="price_sql";
							break;
						case 1:
							str="register_table";
							break;
						case 2:
							str="sale_sql";
							break;
						case 3:
							str="stock_sql";
							break;
					}
					ResultSet resultSet=data.getTables(null, null,str,new String[]{"TABLE"});
					String table_name="price_sql";
					if(resultSet.next())
					{
						table_name=(String)resultSet.getObject(3);
					}
					ResultSet resultSet2=statement.executeQuery("select * from "+table_name);
					ResultSetMetaData metaData=resultSet2.getMetaData();
					String[] columnNames=getColumnNames();
					tableModel=new DefaultTableModel(columnNames, 0);
					while(resultSet2.next())
					{
						Vector vector=new Vector ();
						for(int i=0;i<metaData.getColumnCount();i++)
						{
							vector.add(resultSet2.getObject(i+1));
						}
						tableModel.addRow(vector);
					}
					jTable=new JTable(tableModel);
					JScrollPane jPane=new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					jPane.setViewportView(jTable);
					jFrame.add(jPane);
					System.out.println(jFrame.getComponentCount());
					jFrame.validate();
				}
				catch (Exception e2) 
				{
					System.out.println("�����ʾ�쳣");
					e2.printStackTrace();
				}*/
			}
		});
		addButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				(
					Connection connection=GetConnection.getConnection("mysql.properties");
					Statement statement=connection.createStatement();
				)
				{
					String[] columnNames1=getColumnNames();
					DatabaseMetaData data=connection.getMetaData();
					String  str="price_sql";
					switch (queryway)
					{
						case 0:
							str="price_sql";
							break;
						case 1:
							str="register_table";
							break;
						case 2:
							str="sale_sql";
							break;
						case 3:
							str="stock_sql";
							break;
					}
					ResultSet resultSet=data.getTables(null, null,str,new String[]{"TABLE"});
					String table_name="price_sql";
					if(resultSet.next())
					{
						table_name=(String)resultSet.getObject(3);
					}	
					new User_Add(User.this.tableModel, columnNames1, table_name);
				}
				catch (Exception e2)
				{
					System.out.println("ִ���쳣");
					e2.printStackTrace();
				}
			}
		});
		 //ɾ����ť����
		deleteButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("�ɹ�");
				int index=jTable.getSelectedRow();
				if(index>=0)
				{
					try
					(
						Connection connection=GetConnection.getConnection("mysql.properties");
						Statement statement=connection.createStatement();
					)
					{
						String[] columnNames1=getColumnNames();
						DatabaseMetaData data=connection.getMetaData();
						String  str="price_sql";
						switch (queryway)
						{
							case 0:
								str="price_sql";
								break;
							case 1:
								str="register_table";
								break;
							case 2:
								str="sale_sql";
								break;
							case 3:
								str="stock_sql";
								break;
						}
						ResultSet resultSet=data.getTables(null, null,str,new String[]{"TABLE"});
						String table_name="price_sql";
						if(resultSet.next())
						{
							table_name=(String)resultSet.getObject(3);
						}	
						statement.execute("delete from "+table_name+" where id="+tableModel.getValueAt(index, 0));
						tableModel.removeRow(index);						
					}
					catch (Exception e2) 
					{
						System.out.println("ִ���쳣");
					}
				}
			}
		});
		//���۰�ť����
		saleButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				new SalePrice(name);
			}
		});
		//��ⰴť��Ӽ���
		stockButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				new Stock(name);
			}
		});
		 //ǰ̨��ť����
		frontButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				new FrontCharge(name);
			}
		});
		//�޸İ�ť����
		modifyButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int index=jTable.getSelectedRow();
				if(index>=0)
				{
					System.out.println("��������");
					try		
					(
						Connection connection=GetConnection.getConnection("mysql.properties");
						Statement statement=connection.createStatement();
					)
					{
						String[] columnNames1=getColumnNames();
						DatabaseMetaData data=connection.getMetaData();
						String  str="price_sql";
						switch (queryway)
						{
							case 0:
								str="price_sql";
								break;
							case 1:
								str="register_table";
								break;
							case 2:
								str="sale_sql";
								break;
							case 3:
								str="stock_sql";
								break;
						}
						ResultSet resultSet=data.getTables(null, null,str,new String[]{"TABLE"});
						String table_name="price_sql";
						if(resultSet.next())
						{
							table_name=(String)resultSet.getObject(3);
						}	
						Vector vector=(Vector)tableModel.getDataVector().elementAt(index);
						new User_Modify(index,tableModel,vector,columnNames1,table_name);
					}
					catch (Exception e2) 
					{
						System.out.println("�޸��쳣");
						e2.printStackTrace();
					}
				}
			}
		});
	}
	//int mark=0;
	 //���½�������߳�
	private class UpdateUi extends Thread
	{
		@Override
		public void run()
		{	
			try
			(
				Connection connection=GetConnection.getConnection("mysql.properties");
				Statement statement=connection.createStatement();
			)
			{
				DatabaseMetaData data=connection.getMetaData();
				String  str="price_sql";
				switch (queryway)
				{
					case 0:
						str="price_sql";
						break;
					case 1:
						str="register_table";
						break;
					case 2:
						str="sale_sql";
						break;
					case 3:
						str="stock_sql";
						break;
				}
				ResultSet resultSet=data.getTables(null, null,str,new String[]{"TABLE"});
				String table_name="price_sql";
				if(resultSet.next())
				{
					table_name=(String)resultSet.getObject(3);
				}
				ResultSet resultSet2=statement.executeQuery("select * from "+table_name);
				ResultSetMetaData metaData=resultSet2.getMetaData();
				String[] columnNames=getColumnNames();
				tableModel=new DefaultTableModel(columnNames, 0);
				while(resultSet2.next())
				{
					Vector vector=new Vector (columnNames.length);
					if(queryway!=1)
					for(int i=0;i<metaData.getColumnCount();i++)
					{
						vector.add(resultSet2.getObject(i+1));
					}
					else if(queryway==1)
						for(int i=1;i<metaData.getColumnCount();i++)
						{
							vector.add(resultSet2.getObject(i+1));
						}
					tableModel.addRow(vector);
				}	
				tableModel.fireTableDataChanged();
				jTable.setModel(tableModel);				
			}
			catch (Exception e2) 
			{
				System.out.println("�����ʾ�쳣");
			}
		}
	}
	public String[] getColumnNames()
	{
		String[]  columnNames=null;
		switch (queryway)
		{
			case 0:
			 columnNames=new String[]{"��Ʒ���","��Ʒ����","����","������","���ۼ�","�ۿ�","ӯ��","������Ա"};
				break;		
			case 1:
				columnNames=new String[]{"����","�ֿ���","����","�绰","��������","���"};
				break;		
			case 2:
				columnNames=new String[]{"��Ʒ���","��Ʒ����","���ۼ�","��������","�����ܽ��","����Ա"};
				break;		
			case 3:
				columnNames=new String[]{"��Ʒ���","��Ʒ����","��������","������","��������","�ܽ��","����","����Ա"};
				break;				
		}
		return  columnNames;
	}
	/*public static void main(String[] args)
	{
		new User();
	}*/
}
