package com.scau.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import com.scau.model.GetConnection;

public class StockQuery
{
	JFrame jFrame=new JFrame("����ѯ");
	JLabel queryJLabel=new JLabel("��ѯ��ʽ:");
	JComboBox<String> queryBox=new JComboBox<String>();
	JLabel enterJLabel=new JLabel("�����ѯ��Ϣ:");
	JLabel filterJLabel=new JLabel("���˲�ѯ:");
	JTextField filterField=new JTextField(15);	
	JTextField enterField=new JTextField(15);
	JPanel pane1=new JPanel();
	JPanel pane2=new JPanel();
	ResultSet resultSet;
	//��ѯ��ʽ��־��0,1,2,3,4,5�ֱ��ʾ����Ʒ��ţ���Ʒ���ƣ�����Ա�����࣬ȱ��,���з�ʽ��ѯ
	int statuway=0;
	JButton queryButton=new JButton("��ѯ");
	JButton exitButton=new JButton("�˳�");
	JButton exportButton=new JButton("�����ļ�");
	private String[] columnNames={"��Ʒ���","��Ʒ����","��������","������","����","�ܽ��","����","����Ա"};
	DefaultTableModel defaultTableModel=new DefaultTableModel(columnNames,0);	
	JTable jTable=new JTable(defaultTableModel);
	TableRowSorter<TableModel> sorter=new TableRowSorter<TableModel>(defaultTableModel);
	public StockQuery()
	{
		Init();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setLocation(200, 20);
		jFrame.setSize(700, 700);
		jFrame.setVisible(true);
	}
	public void Init()
	{
		jTable.setRowSorter(sorter);
		BoxLayout boxLayout=new BoxLayout(pane1, BoxLayout.X_AXIS);
		pane1.setLayout(boxLayout);
		pane1.add(Box.createHorizontalStrut(30));
		pane1.add(queryJLabel);
		queryBox.addItem("��Ʒ���");
		queryBox.addItem("��Ʒ����");
		queryBox.addItem("����Ա");
		queryBox.addItem("����");
		queryBox.addItem("ȱ��");
		queryBox.addItem("����");
		pane1.add(queryBox);
		pane1.add(Box.createHorizontalStrut(60));
		pane1.add(enterJLabel);
		pane1.add(enterField);
		pane1.add(Box.createHorizontalStrut(110));
		pane1.add(filterJLabel);
		pane1.add(filterField);
		defaultTableModel.addRow(new Vector<String>(8));
		pane2.setLayout(new FlowLayout(FlowLayout.RIGHT,30,0));
		pane2.add(queryButton);
		pane2.add(exportButton);
		pane2.add(exitButton);
		jFrame.add(new JScrollPane(jTable));
		jFrame.add(pane1,BorderLayout.NORTH);
		jFrame.add(pane2,BorderLayout.SOUTH);
		//����˳���ť
		exitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				jFrame.setVisible(false);
			}
		});
		//��ѯ��ʽ��Ӽ�����ť
		queryBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//��ò�ѯ��ʽ
				statuway=queryBox.getSelectedIndex();
			}
		});
		//��ѯ��ť��Ӽ���
		queryButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//�ж������Ƿ�Ϊ��
				if(!(filterField.getText().trim().length()==0))
				{
					String text=filterField.getText();		
					sorter.setRowFilter(RowFilter.regexFilter(text));
					filterField.setText("");
					return;
				}
				else
				{
					sorter.setRowFilter(null);
				}
				//�жϲ�ѯ���벻Ϊ�գ����ѯΪ���к�ȱ��ʱ
				if(!(enterField.getText().equals(""))||statuway==4||statuway==5)
				{
					defaultTableModel.getDataVector().removeAllElements();
					try
					(
							Connection connection=GetConnection.getConnection("mysql.properties");
							CallableStatement callableStatement=connection.prepareCall("{call stock_query1(?,?,?)}")
					)
					{
						callableStatement.setObject(1, statuway);
						if(statuway==0)
							callableStatement.setObject(2, enterField.getText());
						else
							callableStatement.setObject(2,null);
						callableStatement.setObject(3, enterField.getText());
						resultSet=callableStatement.executeQuery();
						while(resultSet.next())
						{
							Vector vector=new Vector(8);
							vector.add(resultSet.getObject(1));
							vector.add(resultSet.getObject(2));
							vector.add(resultSet.getObject(3));
							vector.add(resultSet.getObject(4));
							vector.add(resultSet.getObject(5));
							vector.add(resultSet.getObject(6));
							vector.add(resultSet.getObject(7));
							vector.add(resultSet.getObject(8));
							defaultTableModel.addRow(vector);
						}
						enterField.setText("");
						return;
					
					}
					catch (Exception e2) 
					{
						Vector vector=new Vector(8);
						defaultTableModel.addRow(vector);
						enterField.setText("");
						System.out.println("ִ���쳣");
						return;
					}
				}	
				//����ѯ��Ϣ������Ϊ�գ���ֱ�ӽ���
				if(enterField.getText().equals("")&&filterField.getText().equals(""))
					return;				
			}
		});
		//�����ļ���ť����
	   exportButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//����û�������ļ���
				String fileName=JOptionPane.showInputDialog("�����������ļ���");
				fileName="C:/Users/Public/Desktop/"+fileName;
				createXLSFile(fileName);
			}
		});
	}
	public void createXLSFile(String fileName)
	{
		try
		(
				OutputStream outputStream=new FileOutputStream(fileName);
		)
		{
			WritableWorkbook workbook=Workbook.createWorkbook(outputStream);
			WritableSheet sheet=workbook.createSheet("��Ʒ���",0);
			for(int i=0;i<defaultTableModel.getColumnCount();i++)
			{
				jxl.write.Label label=new jxl.write.Label(i, 0, defaultTableModel.getColumnName(i));
				sheet.addCell(label);
			}
			for(int i=0;i<defaultTableModel.getRowCount();i++)
			{
				for(int j=0;j<defaultTableModel.getColumnCount();j++)
				{
					jxl.write.Label label=new jxl.write.Label(j, i+1, String.valueOf(defaultTableModel.getValueAt(i, j)));
					sheet.addCell(label);
				}
			}
			workbook.write();
			workbook.close();
		}
		catch (Exception e)
		{
			System.out.println("�ɹ������ļ�");
		}
	}
	public static void main(String[] args)
	{
		new StockQuery();
	}
}
/*class SortableTableModel extends AbstractTableModel
{
	private TableModel model;
	private int sortColumn;
	//����ԭTableModel��������
	private Row[] rows;
	public SortableTableModel(TableModel m)
	{
		model=m;
		rows=new Row[model.getRowCount()];
		for(int i=0;i<rows.length;i++)
			rows[i]=new Row(i);
	}
	private  class Row implements Comparable<Row>
	{
		public int index;			
		public Row(int index)
		{
			super();
			this.index = index;
		}
		@Override
		public int compareTo(Row o)
		{
			Object a=model.getValueAt(index, sortColumn);
			Object b=model.getValueAt(o.index, sortColumn);		
			if(a instanceof Comparable)
			{
				return -(((Comparable)a).compareTo(b));
			}
			else
			{
				return -(a.toString().compareTo(b.toString()));
			}
		}
		
	}
	public void sort(int c)
	{
		sortColumn=c;
		Arrays.sort(rows);
		fireTableDataChanged();
	}
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	{
		super.setValueAt(aValue, rowIndex, columnIndex);
	}
	@Override
	public int getRowCount()
	{
		return model.getRowCount();
	}
	@Override
	public int getColumnCount()
	{
		return model.getColumnCount();
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		return model.getValueAt(rows[rowIndex].index,columnIndex);
	}
	@Override
	public String getColumnName(int column)
	{		
		return model.getColumnName(column);
	}
	@Override
	public Class<?> getColumnClass(int columnIndex)
	{		
		return model.getColumnClass(columnIndex);
	}
}*/
