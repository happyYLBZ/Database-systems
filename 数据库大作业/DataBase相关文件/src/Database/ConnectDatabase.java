package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
//资源绑定获取 文件中的内容
public class ConnectDatabase {
	    public static Connection conn=null;
		public static void main(String []arge) {
			//定义下面需要的对象
			Statement stmt=null;
			//因为下面要关闭 所以创建变量不能在try中  
			try {
				//第一步 注册驱动
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				//第二步 获取连接
				String url="jdbc:mysql://localhost:3306/school management system";//数据库
				String user="root";   //用户名
				String password="ycj-18045977718q"; //密码
				conn= DriverManager.getConnection(url, user, password);
				System.out.println("数据库连接对象"+conn);
				//第三步获取数据库操作对象
				 stmt =conn.createStatement();
				 new Interface();
				 
				 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*
			//关闭资源
			if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	if(conn!=null)
				try {
						conn.close();
				} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
			*/
		}

}
