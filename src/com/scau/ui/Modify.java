package com.scau.ui;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class Modify extends JDialog
{
	Stock stock;	
	JLabel preModifyJLabel=new JLabel("商品修改前:");
	JLabel sufModifyJLabel=new JLabel("商品修改后:");
	JButton exitButton=new JButton("退出");
	JButton comfirmButton=new JButton("确认");
	String[] columnTitle={"商品编号","商品名称","生产日期","进货价","数量","总金额","种类"};
	DefaultTableModel tableModel1=new DefaultTableModel(new String[1][7],columnTitle);
	DefaultTableModel tableModel2=new DefaultTableModel(new String[1][7],columnTitle);
	JTable preModifyJTable=new JTable(tableModel1);
	JTable sufModifyJTable=new JTable(tableModel2);
	public Modify(Stock stock)
	{
		this.stock=stock;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		uiInit();
		this.setSize(600,250);
		this.setLocation(400, 250);
		this.setVisible(true);
	}	
	public Modify()
	{
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		uiInit();
		this.setSize(850,250);
		this.setLocation(400, 250);
		this.setVisible(true);
	}	
	public void uiInit()
	{	
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
	}
	public static void main(String[] args)
	{
		new Modify();
	}
}
