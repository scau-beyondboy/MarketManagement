package com.scau.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.scau.model.GetConnection;

public class User_Add extends JDialog
{
	DefaultTableModel tableModel;
	String[] columnNames;
	String talbeName;
	JTable jTable;
	DefaultTableModel tableModel2;
	JPanel panel=new JPanel();
	JButton comfirmButton=new JButton("确定");
	JButton exitButton=new JButton("退出");
	public User_Add(DefaultTableModel tableModel, String[] columnNames,
			String talbeName)
	{
		this.tableModel = tableModel;
		this.columnNames = columnNames;
		this.talbeName = talbeName;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		init();
		this.setSize(850,120);
		this.setTitle("修改界面");
		this.setLocation(400, 250);
		this.setVisible(true);
	}
	public void init()
	{	
		setLayout(new BorderLayout());
		tableModel2=new DefaultTableModel(columnNames,1);
		jTable=new JTable(tableModel2)
		{
			//设置单击单元格就编辑
			@Override
			public void changeSelection(int rowIndex, int columnIndex,
					boolean toggle, boolean extend)
			{				
				super.changeSelection(rowIndex, columnIndex, toggle, extend);
				super.editCellAt(rowIndex, columnIndex, null);
			}
		};
		panel.setLayout(new FlowLayout(FlowLayout.CENTER,50,0));
		panel.add(comfirmButton);
		panel.add(exitButton);
		add(panel,BorderLayout.SOUTH);
		add(new JScrollPane(jTable));
		//添加退出按钮监听
		exitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}
		});
		//添加添加按钮监听
		comfirmButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(jTable.isEditing())
					jTable.getCellEditor().stopCellEditing();
				try
				(
						Connection connection=GetConnection.getConnection("mysql.properties");
						Statement statement=connection.createStatement();
				)
				{
					ResultSet resultSet=statement.executeQuery("select * from "+talbeName+" "+"where id="+tableModel2.getValueAt(0, 0));
					if(resultSet.next())
					{
						JOptionPane.showMessageDialog(null, "该信息已存在","友好提示",JOptionPane.INFORMATION_MESSAGE);
					}
					else
					{
						Vector vector=new Vector();
						String str="";
						for(int i=0;i<columnNames.length;i++)
						{
							vector.add(tableModel2.getValueAt(0, i));
							if(i!=0)
							str+="'"+(String)tableModel2.getValueAt(0, i)+"',";
							else
							str+=(String)tableModel2.getValueAt(0, i)+",";
						}
						str=str.substring(0, str.length()-1);
						tableModel.addRow(vector);
						statement.execute("insert into "+talbeName+" values ("+str+")");
						setVisible(false);
					}
				}
				catch (Exception e2) 
				{
					System.out.println("执行异常");
					e2.printStackTrace();
				}
			}
		});		
	}
}
