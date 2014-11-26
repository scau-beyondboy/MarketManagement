package com.scau.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import com.scau.model.GetConnection;
import com.scau.model.GetTime;
import com.scau.model.StockSql;
/**
 * 进货界面
 * @author beyondboy
 */
public class Stock
{
	DefaultTableModel tableModel;
	StockSql sql;
	JFrame jFrame=new JFrame("进货");
	JPanel panel1=new JPanel();
	//JPanel panel2=new JPanel();
	JPanel panel3=new JPanel();
	JPanel panel4=new JPanel();
	JTextField dateField=new JTextField(12);
	JTextField operaterField=new JTextField(10);
	JTextField supplierField=new JTextField(10);
	JButton addButton=new JButton("添加");
	JButton modifyButton=new JButton("修改");
	JButton insertButton=new JButton("插入");
	JButton deleteButton=new JButton("删除");
//	JButton queryButton=new JButton("查询");
	JButton summitButton=new JButton("提交");
	JButton exitButton=new JButton("退出");
	JButton clearButton=new JButton("清空");
	JButton loadFileButton=new JButton("文件导入");
	JLabel sumcountJLabel=new JLabel("入库总金额:");
	DefaultTableCellRenderer defaultTableCellRenderer=new DefaultTableCellRenderer();
	JTextField sumcountField=new JTextField(15);
	JTable jTable;
	//储存表格一行的数据
	Vector<String> tableData;
	//定义进货Jtable列名
	Vector<String> columnTitle=new Vector<String>();
	//Jtable表格的数据储存
	Vector<Vector<String>> rowindex1=new Vector<Vector<String>>();
	Vector<String> rowindex2=new Vector<String>(7);
	TableColumnModel tableColumnModel;
	public static final String INSERT_CREATE="insert into stock_sql values(?,?,?,?,?,?,?,?)";
	public Stock()
	
	{
		jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		uiInit();
		jFrame.setSize(new Dimension(850,650));	
		setTableModel();
		jFrame.setLocation(100, 50);
		jFrame.setVisible(true);
	}
	public void setTableModel()
	{
		columnTitle.add("商品编号");
		columnTitle.add("商品名称");
		columnTitle.add("生产日期");
		columnTitle.add("进货价");
		columnTitle.add("数量");
		columnTitle.add("总金额");
		columnTitle.add("种类");
		rowindex1.add(rowindex2);
		tableModel=new DefaultTableModel(rowindex1,columnTitle);
		jTable=new JTable(tableModel)
		{
			//设置单击单元格就编辑
			@Override
			public void changeSelection(int rowIndex, int columnIndex,
					boolean toggle, boolean extend)
			{				
				super.changeSelection(rowIndex, columnIndex, toggle, extend);
				super.editCellAt(rowIndex, columnIndex, null);
			}
			//实现单元格颜色
			@Override
			public Component prepareRenderer(TableCellRenderer renderer,
					int row, int column)
			{
				//获取Jtable单元格组件
				Component component=super.prepareRenderer(renderer, row, column);
				if(column==0)
				{
					try
					(
							//获取数据库
							Connection connection=GetConnection.getConnection("mysql.properties");
							CallableStatement callableStatement=connection.prepareCall("{call stock_query(?,?)}");
					)
					{
						// 设置stock_sql入参数
						callableStatement.setInt(1, Integer.parseInt((String)tableModel.getValueAt(row, column)));
						callableStatement.registerOutParameter(2, Types.INTEGER);
						callableStatement.execute();
						//判断商品编号是否在库存已存在
						if(callableStatement.getInt(2)==1)
						{
							//商品编号存在设置背景色为红色
							component.setBackground(Color.red);
						}
						else
						{
							//商品编号不存在设置背景色为白色
							component.setBackground(Color.white);							
						}
						return component;
					}
					catch (Exception e2) 
					{
						System.out.println("不成功查询");
						return super.prepareRenderer(renderer, row, column);
					}
				}
				else
				{
					component.setBackground(Color.white);	
					return super.prepareRenderer(renderer, row, column);
				}
			}
		};
		final Vector<String> vector=new Vector<String>();
		 //设置单元表格的监听
		tableModel.addTableModelListener(new TableModelListener()
		{
			@Override
			public void tableChanged(TableModelEvent e)
			{
				int row=e.getFirstRow();
				//计算商品总金额
				if(e.getColumn()==3||e.getColumn()==4)
				{
					int column1=3;
					int column2=4;
					int column3=5;
					 //3列和4列数值不能为空
					if((String)tableModel.getValueAt(row, column1)==null||(String)tableModel.getValueAt(row, column2)==null)
					{						
						tableModel.setValueAt(0, row, column3);
					}
					else if((String)tableModel.getValueAt(row, column1)!=null&&(String)tableModel.getValueAt(row, column2)!=null)
					{
						double value1;
						double value2;
						try
						{
							 //获得两列的数值
							 value1=Double.valueOf(String.valueOf(tableModel.getValueAt(row, column1)));
							 value2=Double.valueOf(String.valueOf(tableModel.getValueAt(row, column2)));
						} catch (NumberFormatException e1)
						{
							//若发生异常则设置两列的值为零
							value1=0;
							value2=0;
							System.out.println("数字异常");
						}
						DecimalFormat format=new DecimalFormat("####.00");
						String result=format.format(value1*value2);
						if(result.equals(".00"))
						{
							result="0";
						}
						tableModel.setValueAt(result, row, column3);
						return;
					}
				}
				else
					return;
			}
		});
		jFrame.add(new JScrollPane(jTable));
	}
	public void uiInit()
	{			
		panel1.setLayout(new FlowLayout(FlowLayout.CENTER,10,0));
		panel1.add(new JLabel("时间:"));
		
		dateField.setText(GetTime.getTime());
		panel1.add(dateField);
		panel1.add(new JLabel("操作员:"));
		panel1.add(operaterField);
		panel1.add(new JLabel("供货商:"));
		panel1.add(supplierField);
		panel1.add(sumcountJLabel);
		panel1.add(sumcountField);
		jFrame.add(panel1, BorderLayout.NORTH);
		//jFrame.add(panel2,BorderLayout.CENTER);
		panel3.setLayout(new GridLayout(2, 4, 20, 20));
		panel3.add(addButton);
		panel3.add(deleteButton);
		panel3.add(modifyButton);
		panel3.add(insertButton);
//		panel3.add(queryButton);		
		panel3.add(summitButton);
		panel3.add(exitButton);
		panel3.add(clearButton);
		panel3.add(loadFileButton);
		jFrame.add(panel3,BorderLayout.SOUTH);
		//添加按钮添加监听
		addButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				tableModel.addRow(new Vector<String>());
			}
		});		
		//删除按钮添加监听
		deleteButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(jTable.getSelectedRows().length>=0)
				{
					//获取所有选中的行的坐标
					int[] indexs=jTable.getSelectedRows();
					int count=0;
					for(int index:indexs)
					{
						//删除选中行的坐标
						tableModel.removeRow(index-count);
						count++;
					}
				}
			}
		});
		//修改按钮添加监听
		modifyButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(jTable.getSelectedRow()>=0)
				{
					tableData=(Vector<String>)tableModel.getDataVector().elementAt(jTable.getSelectedRow());
					//打开修改界面
					new Modify(Stock.this);
				}
			}
		});
		//清空按钮添加监听
		clearButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				tableModel.getDataVector().removeAllElements();
				tableModel.fireTableDataChanged();
				tableModel.addRow(new Vector<String>());
			}
		});
		//插入按钮监听
		insertButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(jTable.getSelectedRow()>=0)
				{
					tableModel.insertRow(jTable.getSelectedRow()+1, new Vector<String>());
				}
			}
		});
		//提交按钮监听
		summitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(jTable.isEditing())
				jTable.getCellEditor().stopCellEditing();
				int rows=tableModel.getRowCount();
				String data[][]=new String[rows][8];
				//String str[]=new String[7];
				Vector<String> vector=null;
				int k=0;
				for(int i=0;i<rows;i++)
				{
					
					//获得一行数据
					vector=(Vector<String>)tableModel.getDataVector().elementAt(i);
					int j;
					for( j=0;j<vector.size();j++)
					{
						if(vector.get(j)==null)
						{							
							break;
						}
						else
						{							
							data[k][j]=vector.get(j);
						}
					}
					//若某行数据为空，则不提交改行
					if(j==vector.size())
					{
						data[k][7]=operaterField.getText();
						k++;
					}
				}
				try
				{
					sql=new StockSql("mysql.properties");
					sql.insertTable(INSERT_CREATE, data);
				} catch (Exception e1)
				{
					sql.createTable();
					sql.insertTable(INSERT_CREATE,data);
				}
				return;
			}
		});
	}
	public static void main(String[] args)
	{
		new Stock();
	}
}
