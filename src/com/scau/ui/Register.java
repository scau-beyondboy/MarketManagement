package com.scau.ui;

import java.awt.Color;
import java.awt.Frame;
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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import com.scau.model.GetTime;
/**
 * ע�����
 * @author beyondboy
 */
public class Register extends JDialog
{
	JLabel userName=new JLabel();
	JLabel cardId=new JLabel();
	JLabel pass=new JLabel();
	JLabel comfirePass=new JLabel();
	JLabel call=new JLabel();
	JLabel date=new JLabel();
	JLabel intigral=new JLabel();
	JLabel validate=new JLabel();
	JTextField validateField=new JTextField();
	JTextField userField=new JTextField();
	JTextField cardIdField=new JTextField();
	JPasswordField passField=new JPasswordField();
	JPasswordField comfirePassField=new JPasswordField();
	JTextField callField=new JTextField();
	JTextField dateField=new JTextField();
	JTextField intigralField=new JTextField();
	JComboBox<String> statusBox=new JComboBox<String>();
	JButton registerButton=new JButton();
	JButton resetButton=new JButton();
	JPanel panel1=new JPanel();
	//������ʽ
	String str1="^(?:(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])|(?=.*[A-Z])(?=.*[a-z])(?=.*[^A-Za-z0-9])|(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9])|(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9])).{6,12}$";
	String str2="^(\\d+([a-z]|[A-Z])+\\d*)|(([a-z]|[A-Z])+\\d+([a-z]|[A-Z])*)$";
	String str3="\\w{1,5}|\\d{1,20}|[a-z]{1,20}|[A-Z]{1,20}";
	public Register()
	{
		this(new Frame(), "ע���û�",false);
		setSize(400, 420);
		setLocation(450, 200);
		setVisible(true);
	}
	public Register(Frame own,String title,boolean model)
	{
		super(own, title, model);
		try
		{
			uiInit();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}	
	private void uiInit() throws Exception
	{
		//������ָ�ʽ
		panel1.setLayout(null);
		userName.setText("�ֿ���");
		userName.setBounds(10, 10, 42, 15);
		cardId.setText("����");
		cardId.setBounds(10, 50,42,15);
		pass.setText("����");
		pass.setBounds(10, 90, 42, 15);
		validate.setText("ǿ��");
		validate.setBounds(260, 90, 30, 15);
		comfirePass.setText("ȷ������");
		comfirePass.setBounds(10, 130, 60, 15);
		call.setText("�绰");
		call.setBounds(10, 170, 42, 15);
		date.setText("��������");
		date.setBounds(10, 210, 60, 15);
		intigral.setText("����");
		intigral.setBounds(10, 250, 42, 15);
		userField.setBounds(60, 10, 190, 27);
		cardIdField.setBounds(60, 50, 190, 27);
		passField.setBounds(60, 90, 190, 27);
		comfirePassField.setBounds(80, 130, 190, 27);
		validateField.setBounds(300, 90, 27, 27);
		callField.setBounds(60, 170, 190, 27);
		dateField.setBounds(70, 210, 130, 27);
		intigralField.setBounds(60, 250, 50, 27);
		registerButton.setText("ע��");
		registerButton.setForeground(Color.red);
		registerButton.setBounds(75, 300, 83, 40);
		resetButton.setText("����");
		resetButton.setBounds(200,300, 83, 40);
		resetButton.setForeground(Color.red);
		statusBox.addItem("��ͨ��Ա");
		statusBox.addItem("����Ա");
		statusBox.addItem("����Ա");
		statusBox.setBounds(280, 10, 80, 27);		
		// ���ö�ʱ���࣬�趨һ����¿������ڵ�ʱ��
		Timer timer=new Timer();
		timer.schedule(new UpdateTime(), 0, 1000);
		panel1.add(userName);
		panel1.add(cardId);
		panel1.add(pass);
		panel1.add(validate);
		panel1.add(comfirePass);
		panel1.add(call);
		panel1.add(date);
		panel1.add(intigral);
		panel1.add(userField);
		panel1.add(cardIdField);
		panel1.add(passField);
		panel1.add(comfirePass);
		panel1.add(comfirePassField);
		panel1.add(validateField);
		panel1.add(callField);
		panel1.add(dateField);
		panel1.add(intigralField);
		panel1.add(statusBox);
		panel1.add(registerButton);
		panel1.add(resetButton);
		getContentPane().add(panel1);
		passField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				super.keyPressed(e);
				validateField.setText(checkPassword(passField.getText()));
			}
		});
	}
	/**
	 * ��ʱ���¿������ڵ�ʱ��
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
			return "��";
		}
		else if(passWord.matches(str1))
		{
			return "ǿ";
		}
		else if(passWord.matches(str2))
			
			return "��";
		else 
			return "��";
	}
	public static void main(String[] args)
	{
		new Register();
		/*String string="12222222";
		String string2="dddddd";
		String string3="aadbcccdSSS123";
		String string4="BBBBBBBBB";
		System.out.println(string.matches(str2));
		System.out.println(string2.matches(str2));
		System.out.println(string3.matches(str2));
		System.out.println(string4.matches(str2));*/
	}
}
