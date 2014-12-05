package com.scau.ui;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import com.scau.model.GetConnection;

public class User_Modify extends JDialog
{

	//修改前的表格一维索引
	Vector<Vector<String>> rowIndex1=new Vector<Vector<String>>(); 
	Vector<Vector<String>> rowIndex2=new Vector<Vector<String>>(); 
	JLabel modifyWayJLabel=new JLabel("修改内容:");
	JComboBox<String> modifyWayBox=new JComboBox<String>();
	JLabel contentJLabel=new JLabel("填写数据:");
	JTextField contentField=new JTextField(15);
	JLabel preModifyJLabel=new JLabel("商品修改前:");
	JLabel sufModifyJLabel=new JLabel("任意单元格修改商品:");
	JButton exitButton=new JButton("退出");
	JButton comfirmButton=new JButton("确认");
	DefaultTableModel tableModel1;
	DefaultTableModel tableModel2;
	DefaultTableModel tableModel3;
	String[] columnNames;
	String tableName;
	JTable preModifyJTable;
	JTable sufModifyJTable;
	Vector vector;
	int index;
	public User_Modify(int index,DefaultTableModel tableModel,Vector vector,String[]columnNames,String tableName)
	{
		this.index=index;
		this.tableModel1=tableModel;
		this.vector=vector;
		this.columnNames=columnNames;
		this.tableName=tableName;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		uiInit();
		this.setSize(850,300);
		this.setTitle("修改界面");
		this.setLocation(400, 250);
		this.setVisible(true);
	}	
	/*public Sale_Modify()
	{
		//this.salePrice=salePrice;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		uiInit();
		this.setSize(850,300);
		this.setTitle("修改界面");
		this.setLocation(400, 250);
		this.setVisible(true);
	}	*/
	public void uiInit()
	{	
		for(int i=1;i<columnNames.length;i++)
		modifyWayBox.addItem(columnNames[i]);	
		//rowIndex1.add(salePrice.tableData);
		//rowIndex2.add(salePrice.tableData);
		tableModel3=new DefaultTableModel(columnNames,0);
		tableModel2=new DefaultTableModel(columnNames,0);
		tableModel3.addRow(vector);
		tableModel2.addRow(vector);
		preModifyJTable=new JTable(tableModel3);
		sufModifyJTable=new JTable(tableModel2);
		preModifyJTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		sufModifyJTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		this.setLayout(null);
		modifyWayJLabel.setBounds(108, 20, 65, 30);
		modifyWayBox.setBounds(175, 20, 80, 25);
		contentJLabel.setBounds(310, 20, 65, 30);
		contentField.setBounds(375, 20, 120, 25);
		add(modifyWayJLabel);
		add(modifyWayBox);
		add(contentJLabel);
		add(contentField);
		preModifyJLabel.setBounds(20, 60, 120, 30);
		JScrollPane jScrollPane=new JScrollPane(preModifyJTable);
		jScrollPane.setBounds(20, 90,800,43);
		add(preModifyJLabel);
		add(jScrollPane);
		sufModifyJLabel.setBounds(20, 140, 120, 30);
		jScrollPane=new JScrollPane(sufModifyJTable);
		jScrollPane.setBounds(20, 165, 800, 43);
		add(sufModifyJLabel);
		add(jScrollPane);
		exitButton.setBounds(700, 220,80, 20);
		comfirmButton.setBounds(600, 220, 80, 20);
		add(exitButton);
		add(comfirmButton);
		contentField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				if(e.getKeyCode()==Event.ENTER)
				{
					for(int i=1;i<columnNames.length;i++)
					{
						if(modifyWayBox.getSelectedItem().equals(columnNames[i]))
						{
							tableModel2.setValueAt(contentField.getText(), 0, i);
							contentField.setText("");
						}
					}
				}
			}
		});
		exitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{			
				setVisible(false);
			}
		});
		comfirmButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				(
						Connection connection=GetConnection.getConnection("mysql.properties");
						//CallableStatement statement=connection.prepareCall("{call price_insert(}")
						Statement statement=connection.createStatement();
				)
				{
						if(sufModifyJTable.isEditing())
							sufModifyJTable.getCellEditor().stopCellEditing();
						Vector vector=(Vector)tableModel2.getDataVector().elementAt(0);
						tableModel1.removeRow(index);
						tableModel1.insertRow(index,vector);
						String sql=getSql(vector);
						statement.execute(sql);
						//statement.execute("update "+tableName+" set price="+tableModel2.getValueAt(0, 4)+",discount="+tableModel2.getValueAt(0, 5)+" where id="+tableModel2.getValueAt(0, 0)+";");
						setVisible(false);
				}
				catch (Exception e2) 
				{
					
					System.out.println("修改不成功");
					e2.printStackTrace();
				}
			}
		});
	}
	public String getSql(Vector vector)
	{
		String str="";
		switch (tableName)
		{
			case "price_sql":
			str="update price_sql set name='"+vector.get(1)+"',type='"+vector.get(2)+"',cost='"+vector.get(3)+"',price='"+vector.get(4)+"',discount='"+vector.get(5)+"',profit='"+vector.get(6)+"',counter='"+vector.get(7)+"' where id="+vector.get(0);
				break;
			case "stock_sql":
				str="update stock_sql set name='"+vector.get(1)+"',good_date='"+vector.get(2)+"',price='"+vector.get(3)+"',count='"+vector.get(4)+"',sum='"+vector.get(5)+"',type='"+vector.get(6)+"',operater='"+vector.get(7)+"' where id="+vector.get(0);
				break;
			case "register_table":
//				str="update register_table set userName="+vector.get(1)+",cardId="+vector.get(2)+",pass="+vector.get(3)+",user_call="+vector.get(4)+",user_date="+vector.get(5)+",user_status="+vector.get(6)+" where id="+vector.get(0);
				str="update register_table set userName='"+vector.get(1)+"',pass='"+vector.get(2)+"',user_call='"+vector.get(3)+"',user_date='"+vector.get(4)+"',user_status='"+vector.get(5)+"' where cardId='"+vector.get(0)+"';";
				break;
			case "sale_sql":
				str="update sale_sql set name='"+vector.get(1)+"',price='"+vector.get(2)+"',count='"+vector.get(3)+"',money='"+vector.get(4)+"',cashier='"+vector.get(5)+"' where id="+vector.get(0);
				break;
		}
		return str;
	}
}
