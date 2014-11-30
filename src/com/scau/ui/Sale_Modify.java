package com.scau.ui;

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

public class Sale_Modify extends JDialog
{
	SalePrice salePrice;
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
	String[] columnTitle={"商品编号","商品名称","种类","进货价","销售价","折扣","盈利"};
	DefaultTableModel tableModel1;
	DefaultTableModel tableModel2;
	JTable preModifyJTable;
	JTable sufModifyJTable;
	public Sale_Modify(SalePrice salePrice)
	{
		this.salePrice=salePrice;
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
		modifyWayBox.addItem("销售价");
		modifyWayBox.addItem("折扣");
		rowIndex1.add(salePrice.tableData);
		rowIndex2.add(salePrice.tableData);
		tableModel1=new DefaultTableModel(rowIndex1,new Vector<String>(Arrays.asList(columnTitle)));
		tableModel2=new DefaultTableModel(rowIndex2,new Vector<String>(Arrays.asList(columnTitle)));
		preModifyJTable=new JTable(tableModel1);
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
	}
	/*public static void main(String[] args)
	{
		
	}*/
}
