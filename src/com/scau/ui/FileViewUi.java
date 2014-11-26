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
	//进货对象
	Stock stock;
	//被选择的文件路径
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
		filter.setDescription("导入文件(*.txt,*.xls)");
		chooser.addChoosableFileFilter(filter);
		//禁止"文件类型"下拉列表中显示所有文件选项
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
					 //获得文件路径
					path=f.getPath();
				}
			}
		});
		chooser.setPreferredSize(new Dimension(750,700));
		int result=chooser.showDialog(new JFrame(), "导入文件");
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
	//Jtable显示txt数据
	public void setTxtContent(String fileName)
	{
		//删除Jtable所有的行
		stock.tableModel.getDataVector().removeAllElements();
		String str1="";
		String[] strs;
		try
		(
			RandomAccessFile accessFile=new RandomAccessFile(fileName, "r");	
		)		
		{
			accessFile.readLine();
			//获得一行的数据
			while((str1=accessFile.readLine())!=null)
			{
				//一空行分割数据 
				strs=str1.split(" +");
				Vector<String> vector=new Vector<String>();
				for(int i=0;i<strs.length;i++)
				{
					System.out.println(strs[i]);
					vector.add(strs[i]);
				}
				//添加一行数据
				stock.tableModel.addRow(vector);
			}
			return;
		} catch (Exception e)
		{
			System.out.println("导入word和txt成功");
			return;
		}
	}
	//读取Excel表格的内容显示到Jtable
	public void setXLSContent(String fileName)
	{
		//删除Jtable所有的行
		stock.tableModel.getDataVector().removeAllElements();
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
					for(int j=0;j<sheet.getColumns();j++)
					{
						//获得Excel每行的数据
						vector.add(sheet.getCell(j,i).getContents());
					}
					//在Jtable添加获得Excel的值
					
					stock.tableModel.addRow(vector);
				}
				return;
		}
		catch (Exception e) 
		{
			System.out.println("读取文件不成功");
			return;
		}
	}
	 //文件过滤器
	class ExtensionFileFilter extends FileFilter
	{

		private String description;
		//存储文件名扩展名
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
			//如果f是文件，则返回TRUE
			if(f.isDirectory()) return true;
			else
			{
				String name=f.getName().toLowerCase();
				//如果文件名匹配，则返回true
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
	//自定一个FileView类，用于为指定类型的文件或文件夹设置图标
	class FileIconView extends FileView
	{
		private FileFilter fileFilter;

		public FileIconView(FileFilter fileFilter)
		{
			super();
			this.fileFilter = fileFilter;
		}
		//重写该方法，为文件夹，文件设置图标
		@Override
		public Icon getIcon(File f)
		{
			if(!f.isDirectory()&&fileFilter.accept(f))
			{
				return super.getIcon(f);
			}
			else if(f.isDirectory())
			{
				//获取所有根路径
				File[] files=File.listRoots();
				for(File file:files)
				{
					//如果该路径是根路径
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
