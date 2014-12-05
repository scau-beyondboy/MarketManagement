package com.scau.ui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.scau.model.GetTime;
import com.scau.model.RegisterSql;
/**
 * 注册界面
 * @author beyondboy
 */
public class Register extends JDialog
{
	String statu="管理员";
	JLabel userName=new JLabel();
	JLabel cardId=new JLabel();
	JLabel pass=new JLabel();
	JLabel comfirePass=new JLabel();
	JLabel call=new JLabel();
	JLabel date=new JLabel();
//	JLabel intigral=new JLabel();
	JLabel validate=new JLabel();
	JTextField validateField=new JTextField();
	JTextField userField=new JTextField();
	JTextField cardIdField=new JTextField();
	JPasswordField passField=new JPasswordField();
	JPasswordField comfirePassField=new JPasswordField();
	JTextField callField=new JTextField();
	JTextField dateField=new JTextField();
	//JTextField intigralField=new JTextField();
	JComboBox<String> statusBox=new JComboBox<String>();
	JButton registerButton=new JButton();
	JButton resetButton=new JButton();
	JPanel panel1=new JPanel();
	public final static String CREATE_TABLE="create table register_table ( id int auto_increment primary key,userName varchar(255) ,cardId varchar(255),pass varchar(255),user_call varchar(255),user_date varchar(255),user_status varchar(255));";
	public final static String INSERT="insert into register_table (userName,cardId,pass,user_call,user_date,user_status) values(?,?,?,?,?,?);";
	//正则表达式
	String str1="^(?:(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])|(?=.*[A-Z])(?=.*[a-z])(?=.*[^A-Za-z0-9])|(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9])|(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9])).{6,12}$";
	String str2="^(\\d+([a-z]|[A-Z])+\\d*)|(([a-z]|[A-Z])+\\d+([a-z]|[A-Z])*)|([a-z]+[A-Z]+[a-z]*)|([A-Z]+[a-z]+[A-Z]*)$";
	String str3="\\w{1,5}|\\d{6,20}|[a-z]{6,20}|[A-Z]{6,20}";
	//数据库
	RegisterSql sql=null;
	public Register()
	{
		this(new Frame(), "注册用户",false);
		setSize(400, 380);
		setLocation(450, 200);
		setVisible(true);
	}
	public Register(Frame own,String title,boolean model)
	{
		super(own, title, model);
		try
		{
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			uiInit();
			pack();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}	
	/*public static void main(String[] args)
	{
		new Register();
		new Login();
	}*/
	private void uiInit() throws Exception
	{
		//清除布局格式
		panel1.setLayout(null);
		userName.setText("持卡人");
		userName.setBounds(10, 10, 42, 15);
		cardId.setText("卡号");
		cardId.setBounds(10, 50,42,15);
		pass.setText("密码");
		pass.setBounds(10, 90, 42, 15);
		validate.setText("强度");
		validate.setBounds(260, 90, 30, 15);
		comfirePass.setText("确认密码");
		comfirePass.setBounds(10, 130, 60, 15);
		call.setText("电话");
		call.setBounds(10, 170, 42, 15);
		date.setText("开卡日期");
		date.setBounds(10, 210, 60, 15);
//		intigral.setText("积分");
//		intigral.setBounds(10, 250, 42, 15);
		userField.setBounds(60, 10, 190, 27);
		cardIdField.setBounds(60, 50, 190, 27);
		passField.setBounds(60, 90, 190, 27);
		comfirePassField.setBounds(80, 130, 190, 27);
		validateField.setBounds(300, 90, 27, 27);
		callField.setBounds(60, 170, 190, 27);
		dateField.setBounds(70, 210, 130, 27);
//		intigralField.setBounds(60, 250, 50, 27);
		registerButton.setText("注册");
		registerButton.setForeground(Color.red);
		registerButton.setBounds(75,260, 83, 40);
		resetButton.setText("重置");
		resetButton.setBounds(200,260, 83, 40);
		resetButton.setForeground(Color.red);
		//statusBox.addItem("普通会员");
		statusBox.addItem("管理员");
		statusBox.addItem("收银员");
		statusBox.setBounds(280, 10, 80, 27);		
		// 利用定时器类，设定一秒更新开卡日期的时间
		Timer timer=new Timer();
		timer.schedule(new UpdateTime(), 0, 1000);
		panel1.add(userName);
		panel1.add(cardId);
		panel1.add(pass);
		panel1.add(validate);
		panel1.add(comfirePass);
		panel1.add(call);
		panel1.add(date);
//		panel1.add(intigral);
		panel1.add(userField);
		panel1.add(cardIdField);
		panel1.add(passField);
		panel1.add(comfirePass);
		panel1.add(comfirePassField);
		panel1.add(validateField);
		panel1.add(callField);
		panel1.add(dateField);
//		panel1.add(intigralField);
		panel1.add(statusBox);
		panel1.add(registerButton);
		panel1.add(resetButton);
		getContentPane().add(panel1);
		//添加密码框的监听
		passField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				super.keyReleased(e);
				super.keyPressed(e);
				String str=checkPassword(String.valueOf(passField.getPassword()));
				validateField.setText(str);
			}
		});
		//添加下拉菜单条目改变监听
		statusBox.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				statu=(String)statusBox.getSelectedItem();
			}
		});
		//添加卡号的监听
		cardIdField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				super.keyPressed(e);
				int keyChar=e.getKeyChar();
				if(!(keyChar>=KeyEvent.VK_0&&keyChar<=KeyEvent.VK_9))
					JOptionPane.showMessageDialog(null, "输入数据不合法","信息错误提示",JOptionPane.INFORMATION_MESSAGE);
				cardIdField.setText(cardIdField.getText().replaceAll("[^0-9]",""));
			}
		});
		//添加电话的键盘监听
		callField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				super.keyPressed(e);
				int keyChar=e.getKeyChar();
				//输入若不是数字，将弹出错误提示
				if(!(keyChar>=KeyEvent.VK_0&&keyChar<=KeyEvent.VK_9))
					JOptionPane.showMessageDialog(null,  "输入数据不合法","信息错误提示",JOptionPane.INFORMATION_MESSAGE);
				callField.setText(callField.getText().replaceAll("[^0-9]",""));
			}
		});
		//添加按钮监听
		resetButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				userField.setText("");
				cardIdField.setText("");
				passField.setText("");
				comfirePassField.setText("");
				callField.setText("");
				//intigralField.setText("");
			}
		});
		//添加注册按钮监听
		registerButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(String.valueOf(passField.getPassword()).length()==0||cardIdField.getText().length()==0
				  ||callField.getText().length()==0)
				{
					JOptionPane.showMessageDialog(Register.this, "提交的数据不合法，请检查", "提示", JOptionPane.INFORMATION_MESSAGE);
					return;
				}		
				if(!(String.valueOf(passField.getPassword()).equals(String.valueOf(comfirePassField.getPassword()))))
				{
					JOptionPane.showMessageDialog(Register.this, "输入密码不一致，请重新输入", "提示", JOptionPane.INFORMATION_MESSAGE);
					passField.setText("");
					comfirePassField.setText("");
					return;
				}
				String args[]=new String[6];
				args[0]=userField.getText();
				args[1]=cardIdField.getText();
				args[2]=String.valueOf(passField.getPassword());
				args[3]=callField.getText();
				args[4]=dateField.getText();
//				args[5]=intigralField.getText();				
				args[5]=statu;	
				try
				{
					sql=new RegisterSql("mysql.properties");
					sql.insertUserPrepare(INSERT, args);
				} catch (Exception e1)
				{
					try
					{
						System.out.println("异常");
						sql.createTable(CREATE_TABLE);
						sql.insertUserPrepare(INSERT, args);
					}
					catch (Exception e2)
					{
						e2.printStackTrace();
					}										
				}
				setVisible(false);
			}		  
		});
	}
	/**
	 * 定时更新开卡日期的时间
	 * @author Yang
	 */
	class UpdateTime extends TimerTask
	{
		@Override
		public void run()
		{
			dateField.setText(GetTime.getTime());
		}		
	}
	public String checkPassword(String passWord)
	{
		if(passWord.matches(str3))
		{
			return "弱";
		}
		else if(passWord.matches(str1))
		{
			return "强";
		}
		else if(passWord.matches(str2))			
		{
			return "中";
		}
		else 
		{
			return "弱";
		}
	}	
}
