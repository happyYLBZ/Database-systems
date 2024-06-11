package Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.*;

//登录面板
 class LoginPanel extends JPanel {
	 
	 //页码
	 int page=0;
	 //账号
	 static int id;
	 //密码
	 static String password;
    public LoginPanel() {
        // 设置布局为null，方便自由定位组件
        setLayout(null);
        // 添加一个标签，显示"用户名："
        JLabel label_user = new JLabel("账  号：");
        label_user.setFont(new Font("宋体", Font.PLAIN, 14));
        label_user.setForeground(Color.WHITE);
        label_user.setBounds(270, 120, 80, 30);
        add(label_user);

        // 添加一个文本框，用于输入用户名
        JTextField text_user = new JTextField();
        text_user.setBounds(330, 120, 120, 30);
        add(text_user);

        // 添加一个标签，显示"密码："
        JLabel label_pwd = new JLabel("密  码：");
        label_pwd.setFont(new Font("宋体", Font.PLAIN, 14));
        label_pwd.setForeground(Color.WHITE);
        label_pwd.setBounds(270, 160, 80, 30);
        add(label_pwd);

        // 添加一个密码框，用于输入密码
        JPasswordField text_pwd = new JPasswordField();
        text_pwd.setBounds(330, 160, 120, 30);
        add(text_pwd);

        // 添加一个按钮，用于退出
        JButton btn_exit = new JButton("退出");
        btn_exit.setFont(new Font("宋体", Font.PLAIN, 14));
        btn_exit.setBounds(370, 220, 80, 30);
        add(btn_exit);
        btn_exit.setBackground(Color.pink); // 设置背景颜色为粉色
        
        //添加一个按钮，用于登录并设置监听器
        JButton loginButton=new JButton("登录");
        loginButton.setFont(new Font("宋体", Font.PLAIN, 14));
        loginButton.setBounds(270, 220, 80, 30);
        loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch(page) {
				case 1:
					 // 当点击登录按钮时，翻转到教师页面
					id=Integer.parseInt(text_user.getText());
					password=new String(text_pwd.getPassword());
					if(Database.getInstance().teacher_exist(id, ConnectDatabase.conn)) {
						Database.getInstance().setTeaPanel(id, ConnectDatabase.conn);
						if(Database.getInstance().isright(id,password,ConnectDatabase.conn)) {
							Card_Panel.getinstance().cardLayout.show(Card_Panel.getinstance(), "teacher");
						}
						else {
							JOptionPane.showMessageDialog(null, null, "密码不对！", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					else {
						JOptionPane.showMessageDialog(null, null, "不存在该用户！", JOptionPane.INFORMATION_MESSAGE);
					}
					break;
				case 2:
					 // 当点击登录按钮时，翻转到本科生页面
					id=Integer.parseInt(text_user.getText());
					password=new String(text_pwd.getPassword());
					if(Database.getInstance().student_exist(id, ConnectDatabase.conn)) {
						Database.getInstance().setStuPanel(id, ConnectDatabase.conn);
						if(Database.getInstance().isright(id,password,ConnectDatabase.conn)) {
							Card_Panel.getinstance().cardLayout.show(Card_Panel.getinstance(), "undergraduate");
						}
						else {
							JOptionPane.showMessageDialog(null, null, "密码不对！", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					else {
						JOptionPane.showMessageDialog(null, null, "不存在该用户！", JOptionPane.INFORMATION_MESSAGE);
					}
					break;
				case 3:
					 // 当点击登录按钮时，翻转到本科生页面
					id=Integer.parseInt(text_user.getText());
					password=new String(text_pwd.getPassword());
					if(Database.getInstance().manager_exist(id, ConnectDatabase.conn)) {
						if(Database.getInstance().isright(id,password,ConnectDatabase.conn)) {
							Card_Panel.getinstance().cardLayout.show(Card_Panel.getinstance(), "Manager");
						}
						else {
							JOptionPane.showMessageDialog(null, null, "密码不对！", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					else {
						JOptionPane.showMessageDialog(null, null, "不存在该用户！", JOptionPane.INFORMATION_MESSAGE);
					}
					break;
				default:
					JOptionPane.showMessageDialog(null, null, "你没有选择身份！", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
        loginButton.setBackground(Color.pink); // 设置背景颜色为粉色
        add(loginButton);
        // 添加三个个按钮，用于选择教师、本科生、管理者并设置监听器
        JButton btn1=new JButton("教师");
        JButton btn2=new JButton("本科生");
        JButton btn3=new JButton("管理者");
        setButton(btn1,10, 50, 80, 30,1);
        setButton(btn2,10, 150, 80, 30,2);
        setButton(btn3,10, 250, 80, 30,3);
        add(btn1);
        add(btn2);
        add(btn3);
        //选择身份
        JLabel select=new JLabel("请选择你的身份");
        select.setFont(new Font("宋体", Font.PLAIN, 14));
        select.setForeground(Color.WHITE);
        select.setBounds(10, 0, 120, 30);
        add(select);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // 调用父类方法
        super.paintComponent(g);

        // 加载图片
        ImageIcon icon = new ImageIcon("src/imagines/登录界面.jpg");
        // 绘制图片
        g.drawImage(icon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }
    private void setButton(JButton btn,int x,int y,int width,int height,int Page) {
    	btn.setFont(new Font("楷体", Font.PLAIN, 14));
    	btn.setForeground(Color.WHITE);
    	btn.setBounds(x, y, width, height);
    	btn.setContentAreaFilled(false);//透明
		btn.setFocusable(false);//去焦点
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 page=Page;
			}
		});
    }
}
//教师用户界面
class TeaPanel extends JPanel {
	//姓名、职工号、职称
   private JLabel nameLabel, idLabel, titleLable;
   private JLabel nameTextLabel, idTextLabel, titleTextLabel;
    //课程名称、课程号、开课时间
   private JLabel courseidLabel,coursenameLabel,coursetimeLable;
   private JTextField  courseNumTextField, courseNameTextField, courseTimeTextField;
   private JButton openCourseButton, showCourseButton, queryGradeButton;
   public JTextArea infoTextArea;
   //单例模式
   private static TeaPanel Instance;
   public static TeaPanel getInstance() {
	   if(Instance==null) {
		   Instance=new TeaPanel();
	   }
	   return Instance;
   }
   private TeaPanel() {
       //设置布局为BorderLayout
       setLayout(new BorderLayout());

       //上部分
       JPanel topPanel = new JPanel(new GridLayout(3, 5));
       
       nameLabel = new JLabel("姓名：");
       idLabel = new JLabel("职工号：");
       titleLable = new JLabel("职称：");
       nameTextLabel = new JLabel("");
       idTextLabel = new JLabel("");
       titleTextLabel = new JLabel("");
       
       courseidLabel = new JLabel("课程号：");
       coursenameLabel=new JLabel("课程名称");
       coursetimeLable=new JLabel("上课时间");
       courseNumTextField = new JTextField();
       courseNameTextField = new JTextField();
       courseTimeTextField = new JTextField();
       
       //按钮
       openCourseButton = new JButton("开设课程");
       openCourseButton.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int id=Integer.parseInt(courseNumTextField.getText());
			String name=courseNameTextField.getText();
			String time=courseTimeTextField.getText();
			Database.getInstance().add_course(id,LoginPanel.id,name,time,ConnectDatabase.conn);
		}
	});
       showCourseButton = new JButton("查询已开设课程");
       showCourseButton.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			 //查询已开设课程并将结果显示在infoTextArea中
            Database.getInstance().showteacourse(LoginPanel.id,ConnectDatabase.conn);
			
		}
	});
       queryGradeButton = new JButton("查询学生成绩");
       //给查询学生成绩按钮添加监听器
       queryGradeButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               //查询学生成绩并将结果显示在infoTextArea中
        	   int id=Integer.parseInt(courseNumTextField.getText());
               Database.getInstance().showGrade(id,ConnectDatabase.conn);
           }
       });
       //第一行
       topPanel.add(nameLabel);
       topPanel.add(nameTextLabel);
       topPanel.add(coursenameLabel);
       topPanel.add(courseNameTextField);
       topPanel.add(openCourseButton);
       //第二行
       topPanel.add(idLabel);
       topPanel.add(idTextLabel);
       topPanel.add(courseidLabel);
       topPanel.add(courseNumTextField);
       topPanel.add(showCourseButton);
       //第三行
       topPanel.add(titleLable);
       topPanel.add(titleTextLabel);
       topPanel.add(coursetimeLable);
       topPanel.add(courseTimeTextField);
       topPanel.add(queryGradeButton);
       add(topPanel, BorderLayout.NORTH);

       //下部分
       infoTextArea = new JTextArea();
       infoTextArea.setEditable(false);
       JScrollPane scrollPane = new JScrollPane(infoTextArea);
       add(scrollPane, BorderLayout.CENTER);

   }
   public void setlabeltext(String name,int id,String title){
	      nameTextLabel.setText(name);
	      idTextLabel.setText(String.valueOf(id));
	      titleTextLabel.setText(title);
   }
}
//本科生用户界面
class StuPanel extends JPanel {
    public JTextArea infoTextArea;
    //按钮
    private JButton select_courseButton;
    private JButton drop_courseButton;
    private JButton check_courseButton;
    private JButton take_clubButton;
    private JLabel stuname,stuid,stumajor,stutitle;
    //单例模式
    private static StuPanel Instance;
    public static StuPanel getInstance() {
    	if(Instance==null) {
    		Instance=new StuPanel();
    	}
    	return Instance;
    }
    private StuPanel() {
        // 设置布局为BorderLayout
        setLayout(new BorderLayout());

        // 上部分面板
        JPanel topPanel = new JPanel(new GridLayout(4, 4));
        
        // 选课、退课、查询成绩、参加社团按钮
        select_courseButton = new JButton("选课");
        drop_courseButton = new JButton("退课");
        check_courseButton = new JButton("查询成绩");
        take_clubButton=new JButton("参加社团");
        
        // 姓名、学号、专业等信息的标签
        stuname=new JLabel("");
        stuid=new JLabel("");
        stumajor=new JLabel("");
        stutitle= new JLabel("");
        //添加部件
        topPanel.add(new JLabel("姓名"));
        topPanel.add(stuname);
        //选课
        select_courseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
		        JLabel label = new JLabel("请输入课号");
		        JTextField textField = new JTextField(10);
		        JButton button = new JButton("选课");
		        button.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						int courseid=Integer.parseInt(textField.getText());
						Database.getInstance().select_course(courseid,ConnectDatabase.conn);
						
					}
				});


		        JPanel spanel = new JPanel();
		        spanel.add(label);
		        spanel.add(textField);
		        spanel.add(button);


		        JPanel panel = new JPanel();
		        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		        panel.add(spanel);

		        JOptionPane.showMessageDialog(null, panel, "选课", JOptionPane.INFORMATION_MESSAGE);
				
			}
		});
        topPanel.add(select_courseButton);
        topPanel.add(new JLabel());
        topPanel.add(new JLabel("学号"));
        topPanel.add(stuid);
        //退课
        drop_courseButton.addActionListener(new ActionListener() {
			
    		@Override
			public void actionPerformed(ActionEvent e) {
		        JLabel label = new JLabel("请输入课号");
		        JTextField textField = new JTextField(10);
		        JButton button = new JButton("退课");
		        button.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						int courseid=Integer.parseInt(textField.getText());
						Database.getInstance().drop_course(courseid,LoginPanel.id,ConnectDatabase.conn);
						
					}
				});


		        JPanel spanel = new JPanel();
		        spanel.add(label);
		        spanel.add(textField);
		        spanel.add(button);


		        JPanel panel = new JPanel();
		        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		        panel.add(spanel);

		        JOptionPane.showMessageDialog(null, panel, "退课", JOptionPane.INFORMATION_MESSAGE);
				
			}
		});
        topPanel.add(drop_courseButton);
        topPanel.add(new JLabel());
        topPanel.add(new JLabel("专业"));
        topPanel.add(stumajor);
        check_courseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Database.getInstance().showmyGrade(LoginPanel.id,ConnectDatabase.conn);
			}
		});
        topPanel.add(check_courseButton);
        topPanel.add(new JLabel());
        topPanel.add(new JLabel("称谓"));
        topPanel.add(stutitle);
        //参加社团
        take_clubButton.addActionListener(new ActionListener() {
			
       		@Override
    			public void actionPerformed(ActionEvent e) {
    		        JLabel label = new JLabel("请输入社团名称");
    		        JTextField textField = new JTextField(10);
    		        JButton button = new JButton("参加");
    		        button.addActionListener(new ActionListener() {
    					
    					@Override
    					public void actionPerformed(ActionEvent e) {
    						// TODO Auto-generated method stub
    						String clubname=textField.getText();
    						Database.getInstance().take_club(clubname,LoginPanel.id,ConnectDatabase.conn);
    						
    					}
    				});


    		        JPanel spanel = new JPanel();
    		        spanel.add(label);
    		        spanel.add(textField);
    		        spanel.add(button);


    		        JPanel panel = new JPanel();
    		        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    		        panel.add(spanel);

    		        JOptionPane.showMessageDialog(null, panel, "参加社团", JOptionPane.INFORMATION_MESSAGE);
    				
    			}
		});
        topPanel.add(take_clubButton);


        // 添加上部分面板到StuPanel中
        add(topPanel, BorderLayout.NORTH);

        // 下部分面板
        infoTextArea = new JTextArea();
        infoTextArea.setEditable(false);
        add(infoTextArea, BorderLayout.CENTER);
    }
    public void setlabeltext(String name,int id,String major,String title){
	      stuname.setText(name);
	      stuid.setText(String.valueOf(id));
	      stutitle.setText(title);
	      stumajor.setText(major);
 }
}
//管理者界面
class ManagerPanel extends JPanel {
    
    public JTextArea textArea;
    private JButton btnAddClub, btnDeleteClub, btnViewCourses, btnViewStudents, btnViewTeachers, btnViewColleges, btnViewClubs,apprais,btnclubstu;
    //单例模式
    private static ManagerPanel Instance;
    public static ManagerPanel getInstance() {
    	if(Instance==null) {
    		Instance=new ManagerPanel();
    	}
    	return Instance;
    }
    private ManagerPanel() {
        setLayout(new BorderLayout());

        // 左部分工具栏
        JToolBar toolBar = new JToolBar();
        toolBar.setOrientation(SwingConstants.VERTICAL);
        toolBar.setFloatable(false); // 不可拖动
        add(toolBar, BorderLayout.WEST);

        // 添加按钮
        btnAddClub = new JButton("增加社团");
        btnAddClub.addActionListener(new ActionListener() {
			
        	@Override
			public void actionPerformed(ActionEvent e) {
		        JLabel label1 = new JLabel("请输入社团名称:");
		        JTextField textField1 = new JTextField(10);
		        JLabel label2 = new JLabel("成立时间:");
		        JTextField textField2 = new JTextField(11);
		        JButton button = new JButton("增加");
		        button.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String name=textField1.getText();
						String time=textField2.getText();
						Database.getInstance().add_club(name,time,ConnectDatabase.conn);
					}
				});
		        JPanel panel1 = new JPanel();
		        panel1.add(label1);
		        panel1.add(textField1);

		        JPanel panel2 = new JPanel();
		        panel2.add(label2);
		        panel2.add(textField2);
		        panel2.add(button);

		        JPanel panel = new JPanel();
		        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		        panel.add(panel1);
		        panel.add(panel2);

		        JOptionPane.showMessageDialog(null, panel, "Information", JOptionPane.INFORMATION_MESSAGE);
		    }
		});
        toolBar.add(btnAddClub);

        btnDeleteClub = new JButton("删除社团");
        btnDeleteClub.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
		        JLabel label1 = new JLabel("人数小于:");
		        JTextField textField1 = new JTextField(10);
		        JButton button1 = new JButton("删除");
		        button1.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						int count=Integer.parseInt(textField1.getText());
						int row1=Database.getInstance().delete_club(count,ConnectDatabase.conn);
						int row2=Database.getInstance().delete_club0(ConnectDatabase.conn);
						System.out.println(row1+row2);
					}
				});

		        JLabel label2 = new JLabel("删除名称:");
		        JTextField textField2 = new JTextField(10);
		        JButton button2 = new JButton("删除");
		        button2.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String name=textField2.getText();
						Database.getInstance().delete_nameclub(name,ConnectDatabase.conn);
					}
				});
		        JPanel panel1 = new JPanel();
		        panel1.add(label1);
		        panel1.add(textField1);
		        panel1.add(button1);

		        JPanel panel2 = new JPanel();
		        panel2.add(label2);
		        panel2.add(textField2);
		        panel2.add(button2);

		        JPanel panel = new JPanel();
		        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		        panel.add(panel1);
		        panel.add(panel2);

		        JOptionPane.showMessageDialog(null, panel, "Information", JOptionPane.INFORMATION_MESSAGE);
		    }
		});
        toolBar.add(btnDeleteClub);

        btnViewColleges = new JButton("查看学院");
        btnViewColleges.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Database.getInstance().showcollege(ConnectDatabase.conn);
				
			}
		});
        toolBar.add(btnViewColleges);
        
        apprais=new JButton("评优学生");
        apprais.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
		        JLabel label = new JLabel("请输入学号");
		        JTextField textField = new JTextField(10);
		        JButton button = new JButton("评优");
		        button.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						int id=Integer.parseInt(textField.getText());
						Database.getInstance().apprais_student(id,ConnectDatabase.conn);
						
					}
				});


		        JPanel spanel = new JPanel();
		        spanel.add(label);
		        spanel.add(textField);
		        spanel.add(button);


		        JPanel panel = new JPanel();
		        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		        panel.add(spanel);

		        JOptionPane.showMessageDialog(null, panel, "评优学生", JOptionPane.INFORMATION_MESSAGE);
				
			}
		});
        toolBar.add(apprais);
        toolBar.addSeparator();

        btnViewCourses = new JButton("查看所有课程");
        btnViewCourses.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Database.getInstance().showcourses(ConnectDatabase.conn);
			}
		});
        toolBar.add(btnViewCourses);
        
        btnViewStudents = new JButton("查看所有学生");
        btnViewStudents.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Database.getInstance().showstudents(ConnectDatabase.conn);
				
			}
		});
        toolBar.add(btnViewStudents);

        btnViewTeachers = new JButton("查看所有老师");
        btnViewTeachers.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Database.getInstance().showteachers(ConnectDatabase.conn);
				
			}
		});
        toolBar.add(btnViewTeachers);

        btnViewClubs = new JButton("查看所有社团");
        btnViewClubs.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Database.getInstance().showclubs(ConnectDatabase.conn);
				
			}
		});
        toolBar.add(btnViewClubs);
        
        btnclubstu=new JButton("查看社团学生");
        btnclubstu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
		        JLabel label = new JLabel("请输入社团名称");
		        JTextField textField = new JTextField(10);
		        JButton button = new JButton("查看");
		        button.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String clubname=textField.getText();
						Database.getInstance().showview(clubname,ConnectDatabase.conn);
						
					}
				});


		        JPanel spanel = new JPanel();
		        spanel.add(label);
		        spanel.add(textField);
		        spanel.add(button);


		        JPanel panel = new JPanel();
		        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		        panel.add(spanel);

		        JOptionPane.showMessageDialog(null, panel, "评优学生", JOptionPane.INFORMATION_MESSAGE);
				
			}
		});
        toolBar.add(btnclubstu);
        // 右部分文本域
        textArea = new JTextArea();
        textArea.setEditable(false); // 不可编辑
        add(textArea, BorderLayout.CENTER);
    }
}
//卡片面板
class Card_Panel extends JPanel{
	//卡片布局
	CardLayout cardLayout;
	//各层面板
  LoginPanel loginPanel;//登录界面
  StuPanel uatePanel;//本科生界面
	//单例模式
	private static Card_Panel instance;
	private Card_Panel() {
		  cardLayout=new CardLayout();
        setLayout(cardLayout);
        //添加登录面板
        loginPanel=new LoginPanel();
        add(loginPanel, "login");
        //添加教师用户界面
        add(TeaPanel.getInstance(), "teacher");
        //添加本科生用户界面
        add(StuPanel.getInstance(),"undergraduate");
        //添加管理者界面
        add(ManagerPanel.getInstance(),"Manager");
	};
	public static Card_Panel getinstance() {
		if(instance==null) {
			instance=new Card_Panel();
		}
		return instance;
	}
}
//可视化
public class Interface extends JFrame {
	 
    public Interface() {
        // 设置窗口大小和关闭按钮操作
    	int windowWidth = 700;
    	int windowHeight = 500;
        setSize(windowWidth, windowHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 计算窗口在屏幕上的位置
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int x = (screenWidth - windowWidth) / 2;
        int y = (screenHeight - windowHeight) / 2;
        setLocation(x, y);
        // 设置窗口标题
        setTitle("学校管理系统");
        // 设置窗口图标
        ImageIcon icon = new ImageIcon("src/imagines/图标.png");
        setIconImage(icon.getImage());
        // 将卡片布局添加到窗口并显示
        add(Card_Panel.getinstance());
        setVisible(true);
    }
}

