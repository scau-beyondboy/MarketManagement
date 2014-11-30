package com.scau.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import jxl.Sheet;
import jxl.Workbook;
import com.scau.model.GetConnection;

public class SalePrice
{
	Connection connection;
	JPanel panel=new JPanel();
	JFrame jFrame=new JFrame();
	JLabel loadJLabel=new JLabel("导入商品渠道");
	JComboBox<String> loadBox=new JComboBox<String>();
	JLabel priceJLabel=new JLabel("输入价格");
	JTextField priceField=new JTextField(10);
	JLabel discountJLabel=new JLabel("折扣");
	JTextField discountField=new JTextField(10);
	JLabel queryJLabel=new JLabel("筛选商品:");
	JComboBox<String> queryBox=new JComboBox<String>();
	JLabel operaterJLabel=new JLabel("定制人员");
	JTextField operaterField=new JTextField(10);
	private String[] columnNames={"商品编号","商品名称","种类","进货价","销售价","折扣","盈利"};
	DefaultTableModel defaultTableModel=new DefaultTableModel(columnNames,0);
	JTable jTable=new JTable(defaultTableModel)
	{
		 //设置单击单元格就编辑
		public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) 
		{
			super.changeSelection(rowIndex, columnIndex, toggle, extend);
			super.editCellAt(rowIndex, columnIndex, null);
		};
		/*//实现单元格颜色
		@Override
		public Component prepareRenderer(TableCellRenderer renderer,
				int row, int column)
		{
			//获取Jtable单元格组件
			Component component=super.prepareRenderer(renderer, row, column);
			if(column==0)
			{
				try
				(
						//获取数据库
						Connection connection=GetConnection.getConnection("mysql.properties");
						CallableStatement callableStatement=connection.prepareCall("{call price_query(?,?)}");
				)
				{
					// 设置stock_sql入参数
					callableStatement.setInt(1, Integer.parseInt((String)defaultTableModel.getValueAt(row, column)));
					callableStatement.registerOutParameter(2, Types.INTEGER);
					callableStatement.execute();
					//判断商品编号是否在库存已存在
					if(callableStatement.getInt(2)==1)
					{
						//商品编号存在设置背景色为红色
						component.setBackground(Color.red);
					}
					else
					{
						//商品编号不存在设置背景色为白色
						component.setBackground(Color.white);							
					}
					return component;
				}
				catch (Exception e2) 
				{
					System.out.println("不成功查询");
					return super.prepareRenderer(renderer, row, column);
				}
			}
			else
			{
				component.setBackground(Color.white);	
				return super.prepareRenderer(renderer, row, column);
			}
		}*/
	};
	JButton loadButton=new JButton("导入商品");
	JButton modefyButton=new JButton("修改");
	JButton summitButton=new JButton("提交");
	JButton exitButton=new JButton("退出");
	JPanel panel2=new JPanel();
	//标记商品导入方式，0，1分别表示从仓库，文件导入;
	int loadWay=0;
	public Vector<String> tableData;
	public SalePrice()
	{
		Init();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setLocation(200, 10);
		jFrame.setSize(650, 700);
		jFrame.setTitle("销售价格定制界面");
		jFrame.setVisible(true);
	}
	public void Init()
	{
		queryBox.addItem("已录入价格的商品");
		queryBox.addItem("未录入价格的商品");
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
		loadBox.addItem("仓库导入");
		loadBox.addItem("文件导入");
		addPanel(loadJLabel,loadBox);
		//addPanel(priceJLabel, priceField);
		//addPanel(discountJLabel, discountField);
		addPanel(operaterJLabel, operaterField);
		addPanel(queryJLabel, queryBox);
		defaultTableModel.addRow(new Vector(7));
		panel2.setLayout(new FlowLayout(FlowLayout.CENTER,20,0));
		panel2.add(loadButton);
		panel2.add(modefyButton);
		panel2.add(summitButton);
		panel2.add(exitButton);
		jFrame.add(panel,BorderLayout.NORTH);
		jFrame.add(new JScrollPane(jTable));
		jFrame.add(panel2,BorderLayout.SOUTH);
		 //添加退出按钮监听
		exitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				jFrame.setVisible(false);
			}
		});
		//添加导入方式下拉菜单监听
		loadBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				loadWay=loadBox.getSelectedIndex();
			}
		});
		//添加导入按钮监听
		loadButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				 //仓库导入
				if(loadWay==0)
				{
					stockLoad();
				}
				 //文件导入
				else
					loadExcel();
			}
		});
		 //商品录入情况下拉菜单添加监听
		queryBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//标记删除的行数
				int count=0;
				 //删除未录入商品价格的情况
				if(queryBox.getSelectedIndex()==0)
				{
					int rows=jTable.getRowCount();
					for(int i=0;i<rows;i++)
					{
						int index=Integer.parseInt(String.valueOf(defaultTableModel.getValueAt(i-count, 0)));
						if(isPrice(index)==0)
						{
							defaultTableModel.removeRow(i-count);
							count++;
						}
					}
				}
				//删除已录入商品的情况
				else
				{
					int rows=jTable.getRowCount();
					for(int i=0;i<rows;i++)
					{
						int index=Integer.parseInt(String.valueOf(defaultTableModel.getValueAt(i-count, 0)));
						if(isPrice(index)==1)
						{
							defaultTableModel.removeRow(i-count);
							count++;
						}
					}
				}
			}
		});
		 //添加修改按钮
		modefyButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(jTable.isEditing())
					jTable.getCellEditor().stopCellEditing();
				if(jTable.getSelectedRow()>=0)
				{
					tableData=(Vector<String>)defaultTableModel.getDataVector().elementAt(jTable.getSelectedRow());
					//打开修改界面
					new Sale_Modify(SalePrice.this);
				}
			}
		});
		 //添加提交按钮
		summitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(jTable.isEditing())
					jTable.getCellEditor().stopCellEditing();
				try
				(
						Connection connection=GetConnection.getConnection("mysql.properties");
						CallableStatement statement=connection.prepareCall("{call price_insert(?,?,?,?,?,?,?,?)}");
				)
				{
					//获得Jtable行数
					int rows=defaultTableModel.getRowCount();
					for(int i=0;i<rows;i++)
					{
						if((String)defaultTableModel.getValueAt(i, 4)==null||((String)defaultTableModel.getValueAt(i, 4)).trim().length()==0)
							continue;
						//将jtable毎行数据写入数据库
						for(int j=0;j<defaultTableModel.getColumnCount();j++)
						{
							statement.setObject(j+1, defaultTableModel.getValueAt(i, j));
						}
						statement.setObject(8, operaterField.getText());
						statement.executeUpdate();
					}
				}
				catch (Exception e2)
				{
					System.out.println("插入语句是否成功");
					e2.printStackTrace();
				}
			}
		});
	}
	//添加一个带有标签的文本框面板
	public void addPanel(JLabel contentJLabel,JComponent component)
	{
		JPanel jPanel=new JPanel();
		jPanel.add(contentJLabel);
		jPanel.add(component);
		panel.add(jPanel);
	}
	//仓库导入
	public void stockLoad()
	{
		defaultTableModel.getDataVector().removeAllElements();
		try
		(
				Connection connection=GetConnection.getConnection("mysql.properties");
				Statement statement=connection.createStatement();
				Statement statement2=connection.createStatement();
				ResultSet resultSet=statement.executeQuery("select id,name,type,price from stock_sql");
		)
		{
			while(resultSet.next())
			{
				Vector<Object> vObjects=new Vector<Object>(7);
				int index=Integer.parseInt(String.valueOf(resultSet.getObject(1)));
				if(isPrice(index)==1)
				{
					ResultSet resultSet2=statement2.executeQuery("select * from price_sql where id="+index);
					if(resultSet2.next())
					{
						vObjects.add(resultSet2.getObject(1));
						vObjects.add(resultSet2.getObject(2));
						vObjects.add(resultSet2.getObject(3));
						vObjects.add(resultSet2.getObject(4));
						vObjects.add(resultSet2.getObject(5));
						vObjects.add(resultSet2.getObject(6));
						vObjects.add(resultSet2.getObject(7));
						defaultTableModel.addRow(vObjects);
					}
					resultSet2.close();
				}
				else
				{
					vObjects.add(resultSet.getObject(1));
					vObjects.add(resultSet.getObject(2));
					vObjects.add(resultSet.getObject(3));
					vObjects.add(resultSet.getObject(4));
					defaultTableModel.addRow(vObjects);
				}
			}
			return;
		}
		catch (Exception e) 
		{
			System.out.println("是否成功");
			e.printStackTrace();
		}
	}
	//写入EXcel表格数据
	public void loadExcel()
	{
		JFileChooser fileChooser=new JFileChooser(".");
		fileChooser.setFileFilter(new XLSFilter());
		if(fileChooser.showDialog(new JFrame(), "选择Excel数据")==JFileChooser.APPROVE_OPTION)
		{
			setXLSContent(fileChooser.getSelectedFile().getAbsolutePath());
			return;
		}
		else
		{
			return;
		}
	}
		//读取Excel表格的内容显示到Jtable
		public void setXLSContent(String fileName)
		{
			//删除Jtable所有的行
			defaultTableModel.getDataVector().removeAllElements();
			try
			(
					//打开Excel文档
					InputStream isStream=new FileInputStream(fileName);		
			)
			{
					Workbook wb=Workbook.getWorkbook(isStream);
					Sheet sheet=wb.getSheet(0);
					for(int i=1;i<sheet.getRows();i++)
					{
						System.out.println(i);
						//创建临时变量存储Excel的值
						Vector<String> vector=new Vector<String>(7);
						//获得Excel每行的数据
						for(int j=0;j<sheet.getColumns();j++)
							vector.add(sheet.getCell(j,i).getContents());							
						//在Jtable添加获得Excel的值						
						defaultTableModel.addRow(vector);
					}
					return;
			}
			catch (Exception e) 
			{
				System.out.println("读取Excel文件不成功");
				return;
			}
		}
		// XLS文件扩展名过滤器
		class XLSFilter extends FileFilter {
			// 判断文件类型
			public boolean accept(File f) {
				// 如果是目录则退出
				if (f.isDirectory()) {
					return true;
				}
				
				// 如果扩展名为xls则返回真
				String[] filePostfix = { "xls" };
				for (String str : filePostfix) {
					if (getExtension(f).equals(str))
						return true;
				}
				return false;
			}

			// 返回XLS文件描述信息
			public String getDescription() {
				return "Excel文件（*.xls）";
			}

			// 得到文件扩展名
			public String getExtension(File f) {
				String ext = "";
				String s = f.getName();
				int i = s.lastIndexOf('.');

				if (i > 0 && i < s.length() - 1) {
					ext = s.substring(i + 1).toLowerCase();
				}
				return ext;
			}			
		}
		//查询该商品是否已定价
		public int isPrice(int id)
		{
			try
			(
					//获取数据库
					Connection connection=GetConnection.getConnection("mysql.properties");
					CallableStatement callableStatement=connection.prepareCall("{call price_query(?,?)}");
			)
			{
				// 设置stock_sql入参数
				callableStatement.setInt(1, id);
				callableStatement.registerOutParameter(2, Types.INTEGER);
				callableStatement.execute();
				return callableStatement.getInt(2);
			}
			catch (Exception e) 
			{
				System.out.println("查询异常");
				e.printStackTrace();
				return -1;
			}
		}
	public static void main(String[] args)
	{
		new SalePrice();
	}	
}

