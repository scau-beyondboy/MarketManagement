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
 * ��������
 * @author beyondboy
 */
public class Stock
{
	DefaultTableModel tableModel;
	StockSql sql;
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
//	JButton queryButton=new JButton("��ѯ");
	JButton summitButton=new JButton("�ύ");
	JButton exitButton=new JButton("�˳�");
	JButton clearButton=new JButton("���");
	JButton loadFileButton=new JButton("�ļ�����");
	JLabel sumcountJLabel=new JLabel("����ܽ��:");
	DefaultTableCellRenderer defaultTableCellRenderer=new DefaultTableCellRenderer();
	JTextField sumcountField=new JTextField(15);
	JTable jTable;
	//������һ�е�����
	Vector<String> tableData;
	//�������Jtable����
	Vector<String> columnTitle=new Vector<String>();
	//Jtable�������ݴ���
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
		columnTitle.add("��Ʒ���");
		columnTitle.add("��Ʒ����");
		columnTitle.add("��������");
		columnTitle.add("������");
		columnTitle.add("����");
		columnTitle.add("�ܽ��");
		columnTitle.add("����");
		rowindex1.add(rowindex2);
		tableModel=new DefaultTableModel(rowindex1,columnTitle);
		jTable=new JTable(tableModel)
		{
			//���õ�����Ԫ��ͱ༭
			@Override
			public void changeSelection(int rowIndex, int columnIndex,
					boolean toggle, boolean extend)
			{				
				super.changeSelection(rowIndex, columnIndex, toggle, extend);
				super.editCellAt(rowIndex, columnIndex, null);
			}
			//ʵ�ֵ�Ԫ����ɫ
			@Override
			public Component prepareRenderer(TableCellRenderer renderer,
					int row, int column)
			{
				//��ȡJtable��Ԫ�����
				Component component=super.prepareRenderer(renderer, row, column);
				if(column==0)
				{
					try
					(
							//��ȡ���ݿ�
							Connection connection=GetConnection.getConnection("mysql.properties");
							CallableStatement callableStatement=connection.prepareCall("{call stock_query(?,?)}");
					)
					{
						// ����stock_sql�����
						callableStatement.setInt(1, Integer.parseInt((String)tableModel.getValueAt(row, column)));
						callableStatement.registerOutParameter(2, Types.INTEGER);
						callableStatement.execute();
						//�ж���Ʒ����Ƿ��ڿ���Ѵ���
						if(callableStatement.getInt(2)==1)
						{
							//��Ʒ��Ŵ������ñ���ɫΪ��ɫ
							component.setBackground(Color.red);
						}
						else
						{
							//��Ʒ��Ų��������ñ���ɫΪ��ɫ
							component.setBackground(Color.white);							
						}
						return component;
					}
					catch (Exception e2) 
					{
						System.out.println("���ɹ���ѯ");
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
		 //���õ�Ԫ���ļ���
		tableModel.addTableModelListener(new TableModelListener()
		{
			@Override
			public void tableChanged(TableModelEvent e)
			{
				int row=e.getFirstRow();
				//������Ʒ�ܽ��
				if(e.getColumn()==3||e.getColumn()==4)
				{
					int column1=3;
					int column2=4;
					int column3=5;
					 //3�к�4����ֵ����Ϊ��
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
							 //������е���ֵ
							 value1=Double.valueOf(String.valueOf(tableModel.getValueAt(row, column1)));
							 value2=Double.valueOf(String.valueOf(tableModel.getValueAt(row, column2)));
						} catch (NumberFormatException e1)
						{
							//�������쳣���������е�ֵΪ��
							value1=0;
							value2=0;
							System.out.println("�����쳣");
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
		//��Ӱ�ť��Ӽ���
		addButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				tableModel.addRow(new Vector<String>());
			}
		});		
		//ɾ����ť��Ӽ���
		deleteButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(jTable.getSelectedRows().length>=0)
				{
					//��ȡ����ѡ�е��е�����
					int[] indexs=jTable.getSelectedRows();
					int count=0;
					for(int index:indexs)
					{
						//ɾ��ѡ���е�����
						tableModel.removeRow(index-count);
						count++;
					}
				}
			}
		});
		//�޸İ�ť��Ӽ���
		modifyButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(jTable.getSelectedRow()>=0)
				{
					tableData=(Vector<String>)tableModel.getDataVector().elementAt(jTable.getSelectedRow());
					//���޸Ľ���
					new Modify(Stock.this);
				}
			}
		});
		//��հ�ť��Ӽ���
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
		//���밴ť����
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
		//�ύ��ť����
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
					
					//���һ������
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
					//��ĳ������Ϊ�գ����ύ����
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
