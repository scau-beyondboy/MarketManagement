package com.scau.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import com.scau.model.GetConnection;
import com.scau.model.GetTime;

public class FrontCharge 
{
	private String[] columnNames={"商品编号","商品名称","销售价","数量","原总金额","折扣","实际金额"};
	JLabel chargerJLabel=new JLabel("收银员:");
	JTextField chargerField=new JTextField(10);
	JPanel panel10=new JPanel();
	DefaultTableModel defaultTableModel=new DefaultTableModel(columnNames, 0);
	JTable jTable=new JTable(defaultTableModel)
	{
		 //设置单击单元格就编辑
		public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) 
		{
			super.changeSelection(rowIndex, columnIndex, toggle, extend);
			super.editCellAt(rowIndex, columnIndex, null);
		};
	};
	JPanel panel=new JPanel();
	JLabel idJLabel=new JLabel("商品编号");
	JTextField idField=new JTextField(10);
	JPanel panel2=new JPanel();
	JLabel nameJLabel=new JLabel("商品名称");
	JTextField nameField=new JTextField(10);
	JPanel panel3=new JPanel();
	JLabel dateJLabel=new JLabel("当前日期");
	JTextField dateField=new JTextField(12);
	JPanel panel4=new JPanel();
	JLabel dealJLabel=new JLabel("应付金额");
	JTextField dealField=new JTextField(10);
	JPanel panel5=new JPanel();
	JLabel prepayJLabel=new JLabel("预付金额");
	JTextField prepayField=new JTextField(10);
	JPanel panel6=new JPanel();
	JLabel changeJLabel=new JLabel("找零金额");
	JTextField changeField=new JTextField(10);
	JPanel panel7=new JPanel();
	JButton addButton=new JButton("添加商品"); 
	JButton updoButton=new JButton("撤销商品");
	JButton cancelButton=new JButton("取消购买");
	JButton comfirmButton=new JButton("确定购买");
	JPanel panel8=new JPanel();
	JPanel panel9=new JPanel();
	JFrame jFrame=new JFrame();
	public FrontCharge()
	{
		Init();
		jFrame.setTitle("前台收银");
		jFrame.setLocation(200, 20);
		jFrame.setSize(750,600);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true);
	}
	public void Init()
	{
		panel7.setLayout(new GridLayout(2, 3,20, 10));
		panel9.setLayout(new BorderLayout());
		panel.add(idJLabel);
		panel.add(idField);
		panel2.add(nameJLabel);
		panel2.add(nameField);
		panel3.add(dateJLabel);
		panel3.add(dateField);
		panel4.add(dealJLabel);
		panel4.add(dealField);
		panel5.add(prepayJLabel);
		panel5.add(prepayField);
		panel6.add(changeJLabel);
		panel6.add(changeField);
		panel7.add(panel);
		panel7.add(panel2);
		panel7.add(panel3);
		panel7.add(panel4);
		panel7.add(panel5);
		panel7.add(panel6);
		panel8.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		panel8.add(addButton);
		panel8.add(updoButton);
		panel8.add(comfirmButton);
		panel8.add(cancelButton);
		panel9.add(panel7);
		panel10.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));
		panel10.add(chargerJLabel);
		panel10.add(chargerField);
		jFrame.add(panel10,BorderLayout.NORTH);
		panel9.add(panel8,BorderLayout.SOUTH);
		jFrame.add(panel9,BorderLayout.SOUTH);
		//defaultTableModel.addRow(new Vector(7));
		jFrame.add(new JScrollPane(jTable));
		dateField.setText(GetTime.getTime());
		 //取消按钮监听
		cancelButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				jFrame.setVisible(false);
			}
		});
		//添加商品编号监听
		idField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				if(idField.getText().trim().length()!=0)
				if(e.getKeyCode()==Event.ENTER)
				{
					Vector vector=new Vector(7);
					try
					(
							Connection connection=GetConnection.getConnection("mysql.properties");
							Statement statement=connection.createStatement();
							ResultSet resultSet=statement.executeQuery("select * from price_sql where id="+idField.getText());
					)
					{
							if(resultSet.next())
							{
								vector.add(resultSet.getObject(1));
								vector.add(resultSet.getObject(2));
								vector.add(resultSet.getObject(5));
								vector.add(null);
								vector.add(null);
								vector.add(resultSet.getObject(6));
								//nameField.setText((String)resultSet.getObject(2));
								idField.setText("");
								defaultTableModel.addRow(vector);								
							}
							
					}
					catch (Exception e2) 
					{
						System.out.println("异常");
						e2.printStackTrace();
					}
				}
			}
		});
		//添加商品名称监听
		nameField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				if(nameField.getText().trim().length()!=0)
					if(e.getKeyCode()==Event.ENTER)
					{
						Vector vector=new Vector(7);
						try
						(
								Connection connection=GetConnection.getConnection("mysql.properties");
								Statement statement=connection.createStatement();
								ResultSet resultSet=statement.executeQuery("select * from price_sql where name="+nameField.getText());
						)
						{
							if(resultSet.next())
							{
								vector.add(resultSet.getObject(1));
								vector.add(resultSet.getObject(2));
								vector.add(resultSet.getObject(5));
								vector.add(null);
								vector.add(null);
								vector.add(resultSet.getObject(6));
								//idField.setText(String.valueOf(resultSet.getObject(1)));
								nameField.setText("");
								defaultTableModel.addRow(vector);								
							}
							
						}
						catch (Exception e2) 
						{
							System.out.println("异常");
							e2.printStackTrace();
						}
					}
			}
		});
		addButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(idField.getText().trim().length()!=0)
				{
					Vector vector=new Vector(7);
					try
					(
							Connection connection=GetConnection.getConnection("mysql.properties");
							Statement statement=connection.createStatement();
							ResultSet resultSet=statement.executeQuery("select * from price_sql where id="+idField.getText());
					)
					{
							if(resultSet.next())
							{
								vector.add(resultSet.getObject(1));
								vector.add(resultSet.getObject(2));
								vector.add(resultSet.getObject(5));
								vector.add(null);
								vector.add(null);
								vector.add(resultSet.getObject(6));
								//nameField.setText((String)resultSet.getObject(2));
								idField.setText("");
								defaultTableModel.addRow(vector);								
							}
							
					}
					catch (Exception e2) 
					{
						System.out.println("异常");
						e2.printStackTrace();
					}
				}
				else if(nameField.getText().trim().length()!=0)
				{
					Vector vector=new Vector(7);
					try
					(
							Connection connection=GetConnection.getConnection("mysql.properties");
							Statement statement=connection.createStatement();
							ResultSet resultSet=statement.executeQuery("select * from price_sql where name="+nameField.getText());
					)
					{
						if(resultSet.next())
						{
							vector.add(resultSet.getObject(1));
							vector.add(resultSet.getObject(2));
							vector.add(resultSet.getObject(5));
							vector.add(null);
							vector.add(null);
							vector.add(resultSet.getObject(6));
							//idField.setText(String.valueOf(resultSet.getObject(1)));
							defaultTableModel.addRow(vector);	
							nameField.setText("");
						}						
					}
					catch (Exception e2) 
					{
						System.out.println("异常");
						e2.printStackTrace();
					}
				}				
			}
		});
		defaultTableModel.addTableModelListener(new TableModelListener()
		{
			@Override
			public void tableChanged(TableModelEvent e)
			{
				int row=e.getFirstRow();
				if(e.getColumn()==3)
				{
					if(defaultTableModel.getValueAt(row, e.getColumn())!=null&&((String)defaultTableModel.getValueAt(row, e.getColumn())).trim().length()!=0)
					{
						double price=Double.parseDouble(String.valueOf(defaultTableModel.getValueAt(row,2)));
						double count=Double.parseDouble(String.valueOf(defaultTableModel.getValueAt(row,3)));
						double discount=Double.parseDouble(String.valueOf(defaultTableModel.getValueAt(row,5)));
						DecimalFormat format=new DecimalFormat("0.00");
						double sum=Double.parseDouble(format.format(price*count));
						defaultTableModel.setValueAt(format.format(price*count), row,4);
						defaultTableModel.setValueAt(format.format(sum*discount), row,6);						
					}
				}
			}
		});
		 //Jtable增加键盘按钮监听
		jTable.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				if(e.getKeyCode()==Event.ENTER)
				{
					if(jTable.isEditing())
						jTable.getCellEditor().stopCellEditing();
					double money=Double.parseDouble(String.valueOf(jTable.getValueAt(jTable.getRowCount()-1, 6)));
					double sum=0;
					if(!(dealField.getText().trim().equals("")))
					{
						sum=Double.parseDouble((String)dealField.getText());
					}
					dealField.setText(String.valueOf(money+sum));
				}
			}
		});
		//添加撤销按钮监听
		updoButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				defaultTableModel.removeRow(jTable.getSelectedRow());
			}
		});
		//添加预付监听
		prepayField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==Event.ENTER)
				{
					if(prepayField.getText().trim().length()!=0)
					{
						double money=Double.parseDouble((String)dealField.getText());
						double giveMoney=Double.parseDouble((String)prepayField.getText());
						changeField.setText(String.valueOf(giveMoney-money));
					}
				}
			}
		});
		comfirmButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				(
						//获取数据库
						Connection connection=GetConnection.getConnection("mysql.properties");
						CallableStatement callableStatement=connection.prepareCall("{call sale_add(?,?,?,?,?,?)}");
						CallableStatement callableStatement2=connection.prepareCall("{call stock_out(?,?)}");
				)
				{
					int rows=jTable.getRowCount();
					for(int i=0;i<rows;i++)
					{
						for(int j=0;j<4;j++)
							callableStatement.setObject(j+1, defaultTableModel.getValueAt(i, j));
						callableStatement.setObject(5,defaultTableModel.getValueAt(i, 6));
						callableStatement.setObject(6, chargerField.getText());
						callableStatement2.setObject(1, defaultTableModel.getValueAt(i, 0));
						callableStatement2.setObject(2, defaultTableModel.getValueAt(i, 3));
						callableStatement2.execute();
						callableStatement.execute();
					}
				}
				catch (Exception e2) 
				{
					System.out.println("查询异常");
					e2.printStackTrace();
				}
			}
		});
	}
	public static void main(String[] args)
	{
		new FrontCharge();
	}
}
