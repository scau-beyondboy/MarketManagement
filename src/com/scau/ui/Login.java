package com.scau.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JDialog
{
	JLabel userName=new JLabel();
	JLabel status=new JLabel();
	JLabel pass=new JLabel();
	JTextField userNameField=new JTextField();
	JPasswordField passField=new JPasswordField();
	JComboBox<String> statusBox=new JComboBox<String>();
	JButton registerbButton=new JButton();
	JButton loginButton=new JButton();
	JPanel panel1=new JPanel();
	public Login(Frame frame,String title,boolean model)
	{
		super(frame,title,model);
		try
		{
			uiInit();
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			pack();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public Login()
	{
		this(new Frame(),"����ϵͳ��½����",false);
		this.setSize(450,250);
		this.setLocation(400, 250);
		this.setVisible(true);
	}
	public static void main(String[] args)
	{
		new Login();
	}
	public void uiInit() throws Exception
	{
		panel1.setLayout(null);
		userName.setText("<html>�ֿ���<br/>�򿨺�</html>");	
		userName.setBounds(10, 15, 42, 40);
		pass.setText("����:");
		pass.setBounds(10, 80, 42, 20);
		status.setText("���:");
		status.setFont(new Font(null,Font.BOLD,14));
		status.setBounds(260,20,42,20);
		statusBox.addItem("��ͨ��Ա");
		statusBox.addItem("����Ա");
		statusBox.addItem("����Ա");
		statusBox.setBounds(300, 15, 80, 30);
		userNameField.setBounds(55, 20, 190, 27);
		passField.setBounds(50, 80, 190, 27);
		registerbButton.setText("��½");
		registerbButton.setBounds(100,130,83,40);
		registerbButton.setForeground(Color.red);
		loginButton.setText("ע��");		
		loginButton.setBounds(220, 130, 83, 40);
		loginButton.setForeground(Color.red);
		panel1.add(userName);
		panel1.add(pass);
		panel1.add(status);
		panel1.add(userNameField);
		panel1.add(statusBox);
		panel1.add(passField);
		panel1.add(registerbButton);
		panel1.add(loginButton);
		getContentPane().add(panel1);
		loginButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(userNameField.getText().length()==0||String.valueOf(passField.getPassword()).length()==0)
				{
					JOptionPane.showMessageDialog(null,  "����������","��Ϣ������ʾ",JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
		});
	}	
}
