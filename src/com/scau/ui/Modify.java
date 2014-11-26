package com.scau.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Modify extends JDialog
{
	Stock stock;
	//修改前的表格一维索引
	Vector<Vector<String>> rowIndex1=new Vector<Vector<String>>(); 
	Vector<Vector<String>> rowIndex2=new Vector<Vector<String>>(); 
	JLabel preModifyJLabel=new JLabel("商品修改前:");
	JLabel sufModifyJLabel=new JLabel("任意单元格修改商品:");
	JButton exitButton=new JButton("退出");
	JButton comfirmButton=new JButton("确认");
	String[] columnTitle={"商品编号","商品名称","生产日期","进货价","数量","总金额","种类"};
	DefaultTableModel tableModel1;
	DefaultTableModel tableModel2;
	JTable preModifyJTable;
	JTable sufModifyJTable;
	public Modify(Stock stock)
	{
		this.stock=stock;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		uiInit();
		this.setSize(850,250);
		this.setTitle("修改界面");
		this.setLocation(400, 250);
		this.setVisible(true);
	}	
	public Modify()
	{
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		uiInit();
		this.setSize(850,250);
		this.setTitle("修改界面");
		this.setLocation(400, 250);
		this.setVisible(true);
	}	
	public void uiInit()
	{	
		rowIndex1.add(stock.tableData);
		rowIndex2.add(stock.tableData);
		tableModel1=new DefaultTableModel(rowIndex1,new Vector<String>(Arrays.asList(columnTitle)));
		tableModel2=new DefaultTableModel(rowIndex2,new Vector<String>(Arrays.asList(columnTitle)));
		preModifyJTable=new JTable(tableModel1);
		sufModifyJTable=new JTable(tableModel2);
		preModifyJTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		sufModifyJTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		this.setLayout(null);
		preModifyJLabel.setBounds(20, 20, 120, 30);
		JScrollPane jScrollPane=new JScrollPane(preModifyJTable);
		jScrollPane.setBounds(20, 50,800,43);
		add(preModifyJLabel);
		add(jScrollPane);
		sufModifyJLabel.setBounds(20, 100, 120, 30);
		jScrollPane=new JScrollPane(sufModifyJTable);
		jScrollPane.setBounds(20, 125, 800, 43);
		add(sufModifyJLabel);
		add(jScrollPane);
		exitButton.setBounds(600, 180,80, 20);
		comfirmButton.setBounds(700, 180, 80, 20);
		add(exitButton);
		add(comfirmButton);
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
				int index=stock.jTable.getSelectedRow();
				Vector<String> tableData=(Vector<String>)tableModel2.getDataVector().elementAt(0);
				stock.tableModel.removeRow(index);
				stock.tableModel.insertRow(index, tableData);
				setVisible(false);
			}
		});
	}
	public static void main(String[] args)
	{
		new Modify();
	}
}
