package com.scau.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import com.scau.model.GetTime;
/**
 * ��������
 * @author beyondboy
 */
public class Stock
{
	DefaultTableModel tableModel;
	JFrame jFrame=new JFrame("����");
	JPanel panel1=new JPanel();
	//JPanel panel2=new JPanel();
	JPanel panel3=new JPanel();
	JPanel panel4=new JPanel();
	JTextField dateField=new JTextField(12);
	JTextField operaterField=new JTextField(10);
	JTextField supplierField=new JTextField(10);
	JButton addButton=new JButton("���");
	JButton modifyButton=new JButton("�޸�");
	JButton insertButton=new JButton("����");
	JButton deleteButton=new JButton("ɾ��");
	JButton queryButton=new JButton("��ѯ");
	JButton summitButton=new JButton("�ύ");
	JButton exitButton=new JButton("�˳�");
	JButton resetButton=new JButton("����");
	JButton loadFileButton=new JButton("�ļ�����");
	JLabel sumcountJLabel=new JLabel("����ܽ��:");
	JTextField sumcountField=new JTextField(10);
	JTable jTable;
	//�������Jtable����
	Vector<String> columnTitle=new Vector<String>();
	//Jtable�������ݴ���
	Vector<Vector<String>> rowindex1=new Vector<Vector<String>>();
	Vector<String> rowindex2=new Vector<String>();
	public Stock()
	{
		jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		uiInit();
		jFrame.setSize(new Dimension(800,650));	
		setTableModel();
		jFrame.setLocation(100, 50);
		jFrame.setVisible(true);
	}
	public void setTableModel()
	{
		columnTitle.add("��Ʒ���");
		columnTitle.add("��Ʒ����");
		columnTitle.add("��������");
		columnTitle.add("������");
		columnTitle.add("����");
		columnTitle.add("�ܽ��");
		columnTitle.add("����");
		rowindex1.add(rowindex2);
		tableModel=new DefaultTableModel(rowindex1,columnTitle);
		jTable=new JTable(tableModel);
		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		jFrame.add(new JScrollPane(jTable));
	}
	public void uiInit()
	{		
		panel1.setLayout(new FlowLayout(FlowLayout.CENTER,10,0));
		panel1.add(new JLabel("ʱ��:"));
		dateField.setText(GetTime.getTime());
		panel1.add(dateField);
		panel1.add(new JLabel("����Ա:"));
		panel1.add(operaterField);
		panel1.add(new JLabel("������:"));
		panel1.add(supplierField);
		panel1.add(sumcountJLabel);
		panel1.add(sumcountField);
		jFrame.add(panel1, BorderLayout.NORTH);
		//jFrame.add(panel2,BorderLayout.CENTER);
		panel3.setLayout(new GridLayout(2, 5, 20, 20));
		panel3.add(addButton);
		panel3.add(deleteButton);
		panel3.add(modifyButton);
		panel3.add(insertButton);
		panel3.add(queryButton);		
		panel3.add(summitButton);
		panel3.add(exitButton);
		panel3.add(resetButton);
		panel3.add(loadFileButton);
		jFrame.add(panel3,BorderLayout.SOUTH);
		addButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				tableModel.addRow(new Vector<String>());
			}
		});
		deleteButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(jTable.getSelectedRow()>=0)
				{
					tableModel.removeRow(jTable.getSelectedRow());
				}
			}
		});
	}
	public static void main(String[] args)
	{
		new Stock();
	}
}
