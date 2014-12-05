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
 * ע�����
 * @author beyondboy
 */
public class Register extends JDialog
{
	String statu="����Ա";
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
	//������ʽ
	String str1="^(?:(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])|(?=.*[A-Z])(?=.*[a-z])(?=.*[^A-Za-z0-9])|(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9])|(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9])).{6,12}$";
	String str2="^(\\d+([a-z]|[A-Z])+\\d*)|(([a-z]|[A-Z])+\\d+([a-z]|[A-Z])*)|([a-z]+[A-Z]+[a-z]*)|([A-Z]+[a-z]+[A-Z]*)$";
	String str3="\\w{1,5}|\\d{6,20}|[a-z]{6,20}|[A-Z]{6,20}";
	//���ݿ�
	RegisterSql sql=null;
	public Register()
	{
		this(new Frame(), "ע���û�",false);
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
//		intigral.setText("����");
//		intigral.setBounds(10, 250, 42, 15);
		userField.setBounds(60, 10, 190, 27);
		cardIdField.setBounds(60, 50, 190, 27);
		passField.setBounds(60, 90, 190, 27);
		comfirePassField.setBounds(80, 130, 190, 27);
		validateField.setBounds(300, 90, 27, 27);
		callField.setBounds(60, 170, 190, 27);
		dateField.setBounds(70, 210, 130, 27);
//		intigralField.setBounds(60, 250, 50, 27);
		registerButton.setText("ע��");
		registerButton.setForeground(Color.red);
		registerButton.setBounds(75,260, 83, 40);
		resetButton.setText("����");
		resetButton.setBounds(200,260, 83, 40);
		resetButton.setForeground(Color.red);
		//statusBox.addItem("��ͨ��Ա");
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
		//��������ļ���
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
		//��������˵���Ŀ�ı����
		statusBox.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				statu=(String)statusBox.getSelectedItem();
			}
		});
		//��ӿ��ŵļ���
		cardIdField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				super.keyPressed(e);
				int keyChar=e.getKeyChar();
				if(!(keyChar>=KeyEvent.VK_0&&keyChar<=KeyEvent.VK_9))
					JOptionPane.showMessageDialog(null, "�������ݲ��Ϸ�","��Ϣ������ʾ",JOptionPane.INFORMATION_MESSAGE);
				cardIdField.setText(cardIdField.getText().replaceAll("[^0-9]",""));
			}
		});
		//��ӵ绰�ļ��̼���
		callField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				super.keyPressed(e);
				int keyChar=e.getKeyChar();
				//�������������֣�������������ʾ
				if(!(keyChar>=KeyEvent.VK_0&&keyChar<=KeyEvent.VK_9))
					JOptionPane.showMessageDialog(null,  "�������ݲ��Ϸ�","��Ϣ������ʾ",JOptionPane.INFORMATION_MESSAGE);
				callField.setText(callField.getText().replaceAll("[^0-9]",""));
			}
		});
		//��Ӱ�ť����
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
		//���ע�ᰴť����
		registerButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(String.valueOf(passField.getPassword()).length()==0||cardIdField.getText().length()==0
				  ||callField.getText().length()==0)
				{
					JOptionPane.showMessageDialog(Register.this, "�ύ�����ݲ��Ϸ�������", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					return;
				}		
				if(!(String.valueOf(passField.getPassword()).equals(String.valueOf(comfirePassField.getPassword()))))
				{
					JOptionPane.showMessageDialog(Register.this, "�������벻һ�£�����������", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
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
						System.out.println("�쳣");
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
		{
			return "��";
		}
		else 
		{
			return "��";
		}
	}	
}
