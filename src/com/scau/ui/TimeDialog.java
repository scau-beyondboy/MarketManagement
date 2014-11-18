package com.scau.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JDialog;
import javax.swing.JLabel;
/**
 * 定时提示信息弹出窗口
 * @author beyonboy
 */
public class TimeDialog 
{
	private  int second;
	private  JDialog jDialog;
	/**
	 * 定时关闭弹出信息提示
	 * @param father 该窗口依赖的父窗口
	 * @param message 该窗口的提示信息
	 * @param time 定时显示窗口的时间
	 */
	public  void showDialog(final JDialog father,String message,int time )
	{
		JLabel messageJLabel=new JLabel();
		messageJLabel.setText(message);
		messageJLabel.setBounds(90,10, 120, 120);
		messageJLabel.setFont(new Font("Serif",Font.BOLD,25));
		messageJLabel.setForeground(Color.red);
		this.second=time;
		jDialog=new JDialog(father,"信息提示");
		jDialog.setLayout(null);
		jDialog.add(messageJLabel);
		ScheduledExecutorService service=Executors.newSingleThreadScheduledExecutor();
		jDialog.pack();
		jDialog.setLocationRelativeTo(father);
		jDialog.setSize(new Dimension(350,150));
		jDialog.setVisible(true);	
		service.scheduleAtFixedRate(new Runnable()
		{
			@Override
			public void run()
			{
				TimeDialog.this.second--;
				if(TimeDialog.this.second==0)
				{
					jDialog.dispose();
					//关闭父窗口
					father.dispose();
					father.setVisible(false);
				}
			}
		}, 0,1,TimeUnit.SECONDS);				
	}
}
