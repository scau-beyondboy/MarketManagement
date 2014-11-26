package com.scau.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;

public class FileView
{
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
	public static void main(String[] args)
	{
		JFrame jFrame=new JFrame();
		JLabel jLabel=new JLabel();
		File file=new File(".");
		String path=file.getParent();
		System.out.println(path);
//		jLabel.setIcon(new ImageIcon("D:/AndroidProject/MarketManagement/src/com/scau/ui/dsk.png"));
		jLabel.setIcon(new ImageIcon("/src/com/scau/ui/dsk.png"));
		jFrame.setLayout(new BorderLayout());
		//jFrame.setSize(new Dimension(90,80));
		jFrame.add(new JScrollPane(jLabel),BorderLayout.CENTER);
		jFrame.pack();
		jFrame.setVisible(true);
	}
}
