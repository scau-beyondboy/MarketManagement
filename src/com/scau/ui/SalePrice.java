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
	JLabel loadJLabel=new JLabel("������Ʒ����");
	JComboBox<String> loadBox=new JComboBox<String>();
	JLabel priceJLabel=new JLabel("����۸�");
	JTextField priceField=new JTextField(10);
	JLabel discountJLabel=new JLabel("�ۿ�");
	JTextField discountField=new JTextField(10);
	JLabel queryJLabel=new JLabel("ɸѡ��Ʒ:");
	JComboBox<String> queryBox=new JComboBox<String>();
	JLabel operaterJLabel=new JLabel("������Ա");
	JTextField operaterField=new JTextField(10);
	private String[] columnNames={"��Ʒ���","��Ʒ����","����","������","���ۼ�","�ۿ�","ӯ��"};
	DefaultTableModel defaultTableModel=new DefaultTableModel(columnNames,0);
	JTable jTable=new JTable(defaultTableModel)
	{
		 //���õ�����Ԫ��ͱ༭
		public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) 
		{
			super.changeSelection(rowIndex, columnIndex, toggle, extend);
			super.editCellAt(rowIndex, columnIndex, null);
		};
		/*//ʵ�ֵ�Ԫ����ɫ
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
						CallableStatement callableStatement=connection.prepareCall("{call price_query(?,?)}");
				)
				{
					// ����stock_sql�����
					callableStatement.setInt(1, Integer.parseInt((String)defaultTableModel.getValueAt(row, column)));
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
		}*/
	};
	JButton loadButton=new JButton("������Ʒ");
	JButton modefyButton=new JButton("�޸�");
	JButton summitButton=new JButton("�ύ");
	JButton exitButton=new JButton("�˳�");
	JPanel panel2=new JPanel();
	//�����Ʒ���뷽ʽ��0��1�ֱ��ʾ�Ӳֿ⣬�ļ�����;
	int loadWay=0;
	public Vector<String> tableData;
	public SalePrice()
	{
		Init();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setLocation(200, 10);
		jFrame.setSize(650, 700);
		jFrame.setTitle("���ۼ۸��ƽ���");
		jFrame.setVisible(true);
	}
	public void Init()
	{
		queryBox.addItem("��¼��۸����Ʒ");
		queryBox.addItem("δ¼��۸����Ʒ");
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
		loadBox.addItem("�ֿ⵼��");
		loadBox.addItem("�ļ�����");
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
		 //����˳���ť����
		exitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				jFrame.setVisible(false);
			}
		});
		//��ӵ��뷽ʽ�����˵�����
		loadBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				loadWay=loadBox.getSelectedIndex();
			}
		});
		//��ӵ��밴ť����
		loadButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				 //�ֿ⵼��
				if(loadWay==0)
				{
					stockLoad();
				}
				 //�ļ�����
				else
					loadExcel();
			}
		});
		 //��Ʒ¼����������˵���Ӽ���
		queryBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//���ɾ��������
				int count=0;
				 //ɾ��δ¼����Ʒ�۸�����
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
				//ɾ����¼����Ʒ�����
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
		 //����޸İ�ť
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
					//���޸Ľ���
					new Sale_Modify(SalePrice.this);
				}
			}
		});
		 //����ύ��ť
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
					//���Jtable����
					int rows=defaultTableModel.getRowCount();
					for(int i=0;i<rows;i++)
					{
						if((String)defaultTableModel.getValueAt(i, 4)==null||((String)defaultTableModel.getValueAt(i, 4)).trim().length()==0)
							continue;
						//��jtable��������д�����ݿ�
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
					System.out.println("��������Ƿ�ɹ�");
					e2.printStackTrace();
				}
			}
		});
	}
	//���һ�����б�ǩ���ı������
	public void addPanel(JLabel contentJLabel,JComponent component)
	{
		JPanel jPanel=new JPanel();
		jPanel.add(contentJLabel);
		jPanel.add(component);
		panel.add(jPanel);
	}
	//�ֿ⵼��
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
			System.out.println("�Ƿ�ɹ�");
			e.printStackTrace();
		}
	}
	//д��EXcel�������
	public void loadExcel()
	{
		JFileChooser fileChooser=new JFileChooser(".");
		fileChooser.setFileFilter(new XLSFilter());
		if(fileChooser.showDialog(new JFrame(), "ѡ��Excel����")==JFileChooser.APPROVE_OPTION)
		{
			setXLSContent(fileChooser.getSelectedFile().getAbsolutePath());
			return;
		}
		else
		{
			return;
		}
	}
		//��ȡExcel����������ʾ��Jtable
		public void setXLSContent(String fileName)
		{
			//ɾ��Jtable���е���
			defaultTableModel.getDataVector().removeAllElements();
			try
			(
					//��Excel�ĵ�
					InputStream isStream=new FileInputStream(fileName);		
			)
			{
					Workbook wb=Workbook.getWorkbook(isStream);
					Sheet sheet=wb.getSheet(0);
					for(int i=1;i<sheet.getRows();i++)
					{
						System.out.println(i);
						//������ʱ�����洢Excel��ֵ
						Vector<String> vector=new Vector<String>(7);
						//���Excelÿ�е�����
						for(int j=0;j<sheet.getColumns();j++)
							vector.add(sheet.getCell(j,i).getContents());							
						//��Jtable��ӻ��Excel��ֵ						
						defaultTableModel.addRow(vector);
					}
					return;
			}
			catch (Exception e) 
			{
				System.out.println("��ȡExcel�ļ����ɹ�");
				return;
			}
		}
		// XLS�ļ���չ��������
		class XLSFilter extends FileFilter {
			// �ж��ļ�����
			public boolean accept(File f) {
				// �����Ŀ¼���˳�
				if (f.isDirectory()) {
					return true;
				}
				
				// �����չ��Ϊxls�򷵻���
				String[] filePostfix = { "xls" };
				for (String str : filePostfix) {
					if (getExtension(f).equals(str))
						return true;
				}
				return false;
			}

			// ����XLS�ļ�������Ϣ
			public String getDescription() {
				return "Excel�ļ���*.xls��";
			}

			// �õ��ļ���չ��
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
		//��ѯ����Ʒ�Ƿ��Ѷ���
		public int isPrice(int id)
		{
			try
			(
					//��ȡ���ݿ�
					Connection connection=GetConnection.getConnection("mysql.properties");
					CallableStatement callableStatement=connection.prepareCall("{call price_query(?,?)}");
			)
			{
				// ����stock_sql�����
				callableStatement.setInt(1, id);
				callableStatement.registerOutParameter(2, Types.INTEGER);
				callableStatement.execute();
				return callableStatement.getInt(2);
			}
			catch (Exception e) 
			{
				System.out.println("��ѯ�쳣");
				e.printStackTrace();
				return -1;
			}
		}
	public static void main(String[] args)
	{
		new SalePrice();
	}	
}

