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
 * 登陆界面
 * @author beyondboy
 */
public class Login extends JDialog
{
	 //数据库对象
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
	 * 初始登陆界面
	 * @param frame 界面的父frame
	 * @param title 界面的标题
	 * @param model 该窗口的模式
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
		this(new Frame(),"超市系统登陆界面",false);
		this.setSize(450,250);
		this.setLocation(400, 250);
		this.setVisible(true);
	}
	public static void main(String[] args)
	{
		new Login();
	}
	//初始界面的配置
	public void uiInit() throws Exception
	{
		panel1.setLayout(null);
		userName.setText("<html>持卡人<br/>或卡号</html>");	
		userName.setBounds(10, 15, 42, 40);
		pass.setText("密码:");
		pass.setBounds(10, 80, 42, 20);
		status.setText("身份:");
		status.setFont(new Font(null,Font.BOLD,14));
		status.setBounds(260,20,42,20);
		//statusBox.addItem("普通会员");
		statusBox.addItem("管理员");
		statusBox.addItem("收银员");
		statusBox.setBounds(300, 15, 80, 30);
		userNameField.setBounds(55, 20, 190, 27);
		passField.setBounds(50, 80, 190, 27);
		loginButton.setText("登陆");
		loginButton.setBounds(100,130,83,40);
		loginButton.setForeground(Color.red);
		registerbButton.setText("注册");		
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
		//登陆按钮的监听事件
		loginButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//判断输入是否为空
				if(userNameField.getText().length()==0||String.valueOf(passField.getPassword()).length()==0)
				{
					JOptionPane.showMessageDialog(null,  "请输入数据","信息错误提示",JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				else
				{
					sql=new RegisterSql("mysql.properties");
					//验证是否用户存在
					if(sql.validate(userNameField.getText(),String.valueOf(passField.getPassword()),(String)statusBox.getSelectedItem()))
					{
						sql=null;
						String name=getName(userNameField.getText(),String.valueOf(passField.getPassword()));
						if(name.equals(""))
						{
							name=userNameField.getText();
						}
						setVisible(false);
						//弹出登陆成功提示
						//(new TimeDialog()).showDialog(Login.this, "成功登陆",2);	
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
						JOptionPane.showMessageDialog(null,  "请输入的用户不存在","信息错误提示",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}				
			}
		});
		//注册按钮监听
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
			System.out.println("执行异常");
			e.printStackTrace();
		}
		return name;
	}
}
