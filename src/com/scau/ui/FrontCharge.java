package com.scau.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class FrontCharge
{
	private String[] columnNames={"商品编号","商品名称","生产日期","销售价","数量","原总金额","折扣","实际金额"};
	DefaultTableModel defaultTableModel=new DefaultTableModel(columnNames, 0);
	JTable jTable=new JTable(defaultTableModel);
	JPanel panel=new JPanel();
	JLabel idJLabel=new JLabel("商品编号");
	JTextField idField=new JTextField(10);
	JPanel panel2=new JPanel();
	JLabel nameJLabel=new JLabel("商品名称");
	JTextField nameField=new JTextField(10);
	JPanel panel3=new JPanel();
	JLabel dateJLabel=new JLabel("当前日期");
	JTextField dateField=new JTextField(10);
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
		jFrame.setSize(700,600);
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
		panel9.add(panel8,BorderLayout.SOUTH);
		jFrame.add(panel9,BorderLayout.SOUTH);
		defaultTableModel.addRow(new Vector(8));
		jFrame.add(new JScrollPane(jTable));
		cancelButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				jFrame.setVisible(false);
			}
		});
	}
	public static void main(String[] args)
	{
		new FrontCharge();
	}
}
