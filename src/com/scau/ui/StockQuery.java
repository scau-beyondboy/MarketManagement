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
	JFrame jFrame=new JFrame("库存查询");
	JLabel queryJLabel=new JLabel("查询方式:");
	JComboBox<String> queryBox=new JComboBox<String>();
	JLabel enterJLabel=new JLabel("输入查询信息:");
	JLabel filterJLabel=new JLabel("过滤查询:");
	JTextField filterField=new JTextField(15);	
	JTextField enterField=new JTextField(15);
	JPanel pane1=new JPanel();
	JPanel pane2=new JPanel();
	ResultSet resultSet;
	//查询方式标志，0,1,2,3,4,5分别表示按商品编号，商品名称，操作员，种类，缺货,所有方式查询
	int statuway=0;
	JButton queryButton=new JButton("查询");
	JButton exitButton=new JButton("退出");
	JButton exportButton=new JButton("生成文件");
	private String[] columnNames={"商品编号","商品名称","生产日期","进货价","数量","总金额","种类","操作员"};
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
		queryBox.addItem("商品编号");
		queryBox.addItem("商品名称");
		queryBox.addItem("操作员");
		queryBox.addItem("种类");
		queryBox.addItem("缺货");
		queryBox.addItem("所有");
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
		//添加退出按钮
		exitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				jFrame.setVisible(false);
			}
		});
		//查询方式添加监听按钮
		queryBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//获得查询方式
				statuway=queryBox.getSelectedIndex();
			}
		});
		//查询按钮添加监听
		queryButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//判断输入是否为空
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
				//判断查询输入不为空，或查询为所有和缺货时
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
						System.out.println("执行异常");
						return;
					}
				}	
				//若查询信息都输入为空，就直接借宿
				if(enterField.getText().equals("")&&filterField.getText().equals(""))
					return;				
			}
		});
		//生成文件按钮监听
	   exportButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//获得用户输入的文件名
				String fileName=JOptionPane.showInputDialog("请输入生成文件名");
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
			WritableSheet sheet=workbook.createSheet("商品库存",0);
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
			System.out.println("成功生成文件");
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
	//储存原TableModel的行索引
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
