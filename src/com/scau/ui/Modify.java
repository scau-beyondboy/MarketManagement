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
	//�޸�ǰ�ı��һά����
	Vector<Vector<String>> rowIndex1=new Vector<Vector<String>>(); 
	Vector<Vector<String>> rowIndex2=new Vector<Vector<String>>(); 
	JLabel preModifyJLabel=new JLabel("��Ʒ�޸�ǰ:");
	JLabel sufModifyJLabel=new JLabel("���ⵥԪ���޸���Ʒ:");
	JButton exitButton=new JButton("�˳�");
	JButton comfirmButton=new JButton("ȷ��");
	String[] columnTitle={"��Ʒ���","��Ʒ����","��������","������","����","�ܽ��","����"};
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
		this.setTitle("�޸Ľ���");
		this.setLocation(400, 250);
		this.setVisible(true);
	}	
	public Modify()
	{
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		uiInit();
		this.setSize(850,250);
		this.setTitle("�޸Ľ���");
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
