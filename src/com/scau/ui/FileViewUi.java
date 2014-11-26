package com.scau.ui;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;
import javax.swing.table.DefaultTableModel;
import jxl.Sheet;
import jxl.Workbook;

public class FileViewUi
{
	//��������
	Stock stock;
	//��ѡ����ļ�·��
	String path;
	JFileChooser chooser=new JFileChooser(".");
	ExtensionFileFilter  filter=new ExtensionFileFilter();
	public FileViewUi(Stock stock)
	{
		this.stock=stock;
		init();
	}
	public FileViewUi()
	{
		init();
	}
	public void init()
	{
		//filter.addExtension("doc");
		filter.addExtension("txt");
		filter.addExtension("xls");
		filter.setDescription("�����ļ�(*.txt,*.xls)");
		chooser.addChoosableFileFilter(filter);
		//��ֹ"�ļ�����"�����б�����ʾ�����ļ�ѡ��
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileView(new FileIconView(filter));
		chooser.addPropertyChangeListener(new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				if(evt.getPropertyName()==JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)
				{
					File f=(File) evt.getNewValue();
					if(f==null)
						return;
					 //����ļ�·��
					path=f.getPath();
				}
			}
		});
		chooser.setPreferredSize(new Dimension(750,700));
		int result=chooser.showDialog(new JFrame(), "�����ļ�");
		if(result==JFileChooser.APPROVE_OPTION)
		{
			if(path.endsWith(".xls"))
			setXLSContent(path);
			else
			{
				setTxtContent(path);
			}
		}
	}
	//Jtable��ʾtxt����
	public void setTxtContent(String fileName)
	{
		//ɾ��Jtable���е���
		stock.tableModel.getDataVector().removeAllElements();
		String str1="";
		String[] strs;
		try
		(
			RandomAccessFile accessFile=new RandomAccessFile(fileName, "r");	
		)		
		{
			accessFile.readLine();
			//���һ�е�����
			while((str1=accessFile.readLine())!=null)
			{
				//һ���зָ����� 
				strs=str1.split(" +");
				Vector<String> vector=new Vector<String>();
				for(int i=0;i<strs.length;i++)
				{
					System.out.println(strs[i]);
					vector.add(strs[i]);
				}
				//���һ������
				stock.tableModel.addRow(vector);
			}
			return;
		} catch (Exception e)
		{
			System.out.println("����word��txt�ɹ�");
			return;
		}
	}
	//��ȡExcel����������ʾ��Jtable
	public void setXLSContent(String fileName)
	{
		//ɾ��Jtable���е���
		stock.tableModel.getDataVector().removeAllElements();
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
					for(int j=0;j<sheet.getColumns();j++)
					{
						//���Excelÿ�е�����
						vector.add(sheet.getCell(j,i).getContents());
					}
					//��Jtable��ӻ��Excel��ֵ
					
					stock.tableModel.addRow(vector);
				}
				return;
		}
		catch (Exception e) 
		{
			System.out.println("��ȡ�ļ����ɹ�");
			return;
		}
	}
	 //�ļ�������
	class ExtensionFileFilter extends FileFilter
	{

		private String description;
		//�洢�ļ�����չ��
		private ArrayList<String> extensions=new ArrayList<String>();
		public void addExtension(String extension)
		{
			if(!extension.startsWith("."))
			{
				extension="."+extension;
				extensions.add(extension.toLowerCase());
			}
		}
		public void setDescription(String description)
		{
			this.description = description;
		}
		@Override
		public boolean accept(File f)
		{
			//���f���ļ����򷵻�TRUE
			if(f.isDirectory()) return true;
			else
			{
				String name=f.getName().toLowerCase();
				//����ļ���ƥ�䣬�򷵻�true
				for(String extension:extensions)
				{
					if(name.endsWith(extension))
						return true;
				}
				return false;
			}
		}
		@Override
		public String getDescription()
		{
			return description;
		}		
	}
	//�Զ�һ��FileView�࣬����Ϊָ�����͵��ļ����ļ�������ͼ��
	class FileIconView extends FileView
	{
		private FileFilter fileFilter;

		public FileIconView(FileFilter fileFilter)
		{
			super();
			this.fileFilter = fileFilter;
		}
		//��д�÷�����Ϊ�ļ��У��ļ�����ͼ��
		@Override
		public Icon getIcon(File f)
		{
			if(!f.isDirectory()&&fileFilter.accept(f))
			{
				return super.getIcon(f);
			}
			else if(f.isDirectory())
			{
				//��ȡ���и�·��
				File[] files=File.listRoots();
				for(File file:files)
				{
					//�����·���Ǹ�·��
					if(file.equals(f))
					{
						return new ImageIcon("ico/dsk.png");
					}
				}
				return new ImageIcon("ico/folder.png");
			}
			else
			{
				return null;
			}
		}
	}
	/*public static void main(String[] args)
	{
		new FileViewUi();
	}*/
}
