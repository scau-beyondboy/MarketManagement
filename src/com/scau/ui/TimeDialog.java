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
 * ��ʱ��ʾ��Ϣ��������
 * @author beyonboy
 */
public class TimeDialog 
{
	private  int second;
	private  JDialog jDialog;
	/**
	 * ��ʱ�رյ�����Ϣ��ʾ
	 * @param father �ô��������ĸ�����
	 * @param message �ô��ڵ���ʾ��Ϣ
	 * @param time ��ʱ��ʾ���ڵ�ʱ��
	 */
	public  void showDialog(final JDialog father,String message,int time )
	{
		JLabel messageJLabel=new JLabel();
		messageJLabel.setText(message);
		messageJLabel.setBounds(90,10, 120, 120);
		messageJLabel.setFont(new Font("Serif",Font.BOLD,25));
		messageJLabel.setForeground(Color.red);
		this.second=time;
		jDialog=new JDialog(father,"��Ϣ��ʾ");
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
					//�رո�����
					father.dispose();
					father.setVisible(false);
				}
			}
		}, 0,1,TimeUnit.SECONDS);				
	}
}
