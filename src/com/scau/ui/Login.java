package com.scau.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import com.scau.model.GetConnection;
import com.scau.model.RegisterSql;
/**
 * ��½����
 * @author beyondboy
 */
public class Login extends JDialog
{
	 //���ݿ����
	RegisterSql sql;
	JLabel userName=new JLabel();
	JLabel status=new JLabel();
	JLabel pass=new JLabel();
	JTextField userNameField=new JTextField();
	JPasswordField passField=new JPasswordField();
	JComboBox<String> statusBox=new JComboBox<String>();
	JButton registerbButton=new JButton();
	JButton loginButton=new JButton();
	JPanel panel1=new JPanel();
	/**
	 * ��ʼ��½����
	 * @param frame ����ĸ�frame
	 * @param title ����ı���
	 * @param model �ô��ڵ�ģʽ
	 */
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
	//��ʼ���������
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
		//statusBox.addItem("��ͨ��Ա");
		statusBox.addItem("����Ա");
		statusBox.addItem("����Ա");
		statusBox.setBounds(300, 15, 80, 30);
		userNameField.setBounds(55, 20, 190, 27);
		passField.setBounds(50, 80, 190, 27);
		loginButton.setText("��½");
		loginButton.setBounds(100,130,83,40);
		loginButton.setForeground(Color.red);
		registerbButton.setText("ע��");		
		registerbButton.setBounds(220, 130, 83, 40);
		registerbButton.setForeground(Color.red);
		panel1.add(userName);
		panel1.add(pass);
		panel1.add(status);
		panel1.add(userNameField);
		panel1.add(statusBox);
		panel1.add(passField);
		panel1.add(registerbButton);
		panel1.add(loginButton);
		getContentPane().add(panel1);
		//��½��ť�ļ����¼�
		loginButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//�ж������Ƿ�Ϊ��
				if(userNameField.getText().length()==0||String.valueOf(passField.getPassword()).length()==0)
				{
					JOptionPane.showMessageDialog(null,  "����������","��Ϣ������ʾ",JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				else
				{
					sql=new RegisterSql("mysql.properties");
					//��֤�Ƿ��û�����
					if(sql.validate(userNameField.getText(),String.valueOf(passField.getPassword()),(String)statusBox.getSelectedItem()))
					{
						sql=null;
						String name=getName(userNameField.getText(),String.valueOf(passField.getPassword()));
						if(name.equals(""))
						{
							name=userNameField.getText();
						}
						setVisible(false);
						//������½�ɹ���ʾ
						//(new TimeDialog()).showDialog(Login.this, "�ɹ���½",2);	
						if(statusBox.getSelectedIndex()==1)
						{
							new FrontCharge(name);
						}
						else if(statusBox.getSelectedIndex()==0)
						{
							new User(name);
						}
						return;
					}
					else
					{
						JOptionPane.showMessageDialog(null,  "��������û�������","��Ϣ������ʾ",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}				
			}
		});
		//ע�ᰴť����
		registerbButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				new Register();
				setVisible(false);
			}
		});
	}
	public String getName(String cardId,String pass)
	{
		String name="";
		try
		(
				Connection connection=GetConnection.getConnection("mysql.properties");
				PreparedStatement statement=connection.prepareStatement("select userName from register_table where cardId=? and pass=?");
		)
		{
				statement.setObject(1, cardId);
				statement.setObject(2, pass);
				ResultSet set=statement.executeQuery();
				if(set.next())
				{
					name=(String)set.getObject(1);
				}
				return  name;
		}
		catch (Exception e) 
		{
			System.out.println("ִ���쳣");
			e.printStackTrace();
		}
		return name;
	}
}
