package Database;

import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import com.mysql.cj.jdbc.CallableStatement;

public class Database {
       //单例模式
	private static Database Instance;
	private Database() {};
	public static Database getInstance() {
		if(Instance==null) {
			Instance=new Database();
		}
		return Instance;
	}
	   //查找教师用户是否存在
	public boolean teacher_exist(int ID,Connection conn) {
		PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT COUNT(*) FROM 教师 WHERE 职工号 = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, ID);//设置参数
	        rs = stmt.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            if (count > 0) {
	                return true;
	            }
	        }
	        return false;
	    } catch (SQLException e) {
	        // handle exception
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException e) {
	            // handle exception
	        }
	    }
	    return false;
	}
		//检查密码是否正确
	public boolean isright(int ID,String password,Connection conn) {
		PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT COUNT(*) FROM user WHERE id = ? AND password =? ";
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, ID);//设置参数
	        stmt.setString(2, password);
	        rs = stmt.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            if (count > 0) {
	                return true;
	            }
	        }
	        return false;
	    } catch (SQLException e) {
	        // handle exception
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException e) {
	            // handle exception
	        }
	    }
	    return false;
	}
		//设置教师界面
	public void setTeaPanel(int ID ,Connection conn) {
		PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT 姓名,职称 FROM 教师 WHERE 职工号= ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, ID);//设置参数
	        rs = stmt.executeQuery();
	        if (rs.next()) {
	            TeaPanel.getInstance().setlabeltext(rs.getString(1), ID, rs.getString(2));
	        }
	        else {
	        	return;
	        }
	    } catch (SQLException e) {
	        // handle exception
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException e) {
	            // handle exception
	        }
	    }
	    return ;
	}
	//格式化字符串
	private  String flushLeftC(long l, String string){
	
	String c="    ";
	String str = "";

	String temp = "";

	if (string.length() > l)

	str = string;

	else

	for (int i = 0; i < l - string.length(); i++) {
		temp = temp + c;
	}

	str = string+temp;

	return str;

	}
	//格式化字符串
	private  String flushLeftE(long l, String string){
	
	String c=" ";
	String str = "";

	String temp = "";

	if (string.length() > l)

	str = string;

	else

	for (int i = 0; i < l - string.length(); i++) {
		temp = temp + c;
	}

	str = string+temp;

	return str;

	}
	//显示课程
	public void showcourses(Connection connection) {
		
        	PreparedStatement stmt = null;
    	    ResultSet rs = null;
    	    try {
    	    	// 执行SQL查询语句
    	    	String sql = "SELECT 课程名称, 课程代号 FROM 课程";
    	        stmt = connection.prepareStatement(sql);
    	        rs = stmt.executeQuery();
    	        // 处理查询结果
                StringBuilder sb = new StringBuilder();
                sb.append(flushLeftC(10, "课程名称")).append(flushLeftC(10, "课程代号"));
                sb.append("\n");
                while (rs.next()) {
                    String name = rs.getString("课程名称");
                    int id = rs.getInt("课程代号");
                    sb.append(flushLeftC(10, name)).append(flushLeftE(10, String.valueOf(id)));
                    sb.append("\n");
                }
                // 将结果设置到JTextArea中
                ManagerPanel.getInstance().textArea.setText(sb.toString());
    	      
    	    } catch (SQLException e) {
    	        // handle exception
    	    } finally {
    	        try {
    	            if (rs != null) {
    	                rs.close();
    	            }
    	            if (stmt != null) {
    	                stmt.close();
    	            }
    	        } catch (SQLException e) {
    	            // handle exception
    	        }
    	    }
	}
	//输入数量来删除社团
	public int delete_club(int count, Connection connection) {
		
		int rows=0;
		PreparedStatement stmt = null;  
	     try {
	    	    String sql = "DELETE FROM 社团 WHERE 社团名称 IN (SELECT 参加.参加的社团名称 FROM 参加 GROUP BY 参加.参加的社团名称 HAVING COUNT(*) < ?)";
	            stmt= connection.prepareStatement(sql);
	            stmt.setInt(1, count); // 设置参数值
	            rows = stmt.executeUpdate();
	            System.out.println("Deleted " + rows + " rows.");
	            return rows;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	                try {
	                	if (stmt != null) {
	                		stmt.close();
	                	}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        }
	     return rows;
	}
	//删除没人参加的社团
	public int delete_club0(Connection connection) {
		int rows=0;
		PreparedStatement stmt = null;  
	     try {
	    	    String sql = "DELETE FROM 社团 WHERE NOT EXISTS (SELECT * FROM 参加 WHERE 参加.参加的社团名称 = 社团.社团名称)";
	            stmt= connection.prepareStatement(sql);
	            rows = stmt.executeUpdate();
	            System.out.println("Deleted " + rows + " rows.");
	            JOptionPane.showMessageDialog(null, null, "删除成功！", JOptionPane.INFORMATION_MESSAGE);
	            return rows;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	                try {
	                	if (stmt != null) {
	                		stmt.close();
	                	}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        }
	     return rows;
	}
    //增加课程
	public void add_course(int id,int tid, String name, String time, Connection conn) {
		
		// 创建 PreparedStatement 对象
		PreparedStatement pstmt = null;
		try {
		// 为 SQL 语句中的参数赋值
		String sql = "INSERT INTO 课程 (课程名称, 课程代号, 开课老师, 上课时间) VALUES (?, ?, ?, ?)";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name); // 课程名称
        pstmt.setInt(2, id); // 课程代号
        pstmt.setInt(3, tid); // 开课老师
        pstmt.setString(4, time); // 上课时间

        // 执行 SQL 语句，并获得受影响的行数
        int rowsAffected = pstmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "开设成功", "提示信息", JOptionPane.INFORMATION_MESSAGE); // 弹出对应的弹窗
        // 输出受影响的行数
        System.out.println(rowsAffected + " row(s) affected");
    	} catch (SQLException e) {
    		String errorMsg = e.getMessage();  // 获取异常信息
    		JOptionPane.showMessageDialog(null, errorMsg, "错误信息", JOptionPane.ERROR_MESSAGE); // 弹出对应的弹窗
        } finally {
                try {
                	if (pstmt != null) {
                		pstmt.close();
                	}
				} catch (SQLException e) {
					e.printStackTrace();
				}
        }

    			
	}
	public void apprais_student(int id, Connection conn) {
		
		java.sql.CallableStatement stmt=null;
		try {
		// 创建CallableStatement对象
	      stmt = conn.prepareCall("{call 评优(?)}");
	      // 设置输入参数
	      stmt.setInt(1, id);  // 学生学号
	      
	      // 执行Stored Procedure
	      stmt.execute();
	      JOptionPane.showMessageDialog(null, "评优成功！", "提示信息", JOptionPane.INFORMATION_MESSAGE); // 弹出对应的弹窗
	    } catch (Exception e) {
	    	String errorMsg = e.getMessage();  // 获取异常信息
	    	JOptionPane.showMessageDialog(null, errorMsg, "错误信息", JOptionPane.ERROR_MESSAGE); // 弹出对应的弹窗
	    }finally {
	        try {
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException e) {
	            // handle exception
	        }
	    }
	}
	//显示视图
	public void showview(String clubname, Connection conn) {
		java.sql.CallableStatement stmt=null;
		ResultSet rs=null;
		try {
			// 创建CallableStatement对象
		      stmt = conn.prepareCall("{call 传参数(?)}");
		      // 设置输入参数
		      stmt.setString(1, clubname);  // 社团名称
		      
		      // 执行Stored Procedure
		      rs=stmt.executeQuery();
		      // 处理查询结果
              StringBuilder sb = new StringBuilder();
              sb.append(flushLeftC(8, "学号")).append(flushLeftC(7, "姓名")).append(flushLeftC(7, "性别")).append(flushLeftC(20, "所学专业")).append(flushLeftC(20, "入学时间")).append(flushLeftC(7, "称谓"));
              sb.append("\n");
              while (rs.next()) {
                  int id = rs.getInt("学号");
                  String name=rs.getString("姓名");
                  String sex=rs.getString("性别");
                  String major=rs.getString("所学专业");
                  String time=rs.getString("入学时间");
                  String title=rs.getString("称谓");
                  sb.append(flushLeftE(18, String.valueOf(id))).append(flushLeftC(7, name)).append(flushLeftC(7, sex)).append(flushLeftC(20, major)).append(flushLeftC(20,time)).append(flushLeftC(7, title));
                  sb.append("\n");
              }
              // 将结果设置到JTextArea中
              ManagerPanel.getInstance().textArea.setText(sb.toString());
		      
		    } catch (Exception e) {
		      e.printStackTrace();
		    }finally {
		        try {
		            if (rs != null) {
		                rs.close();
		            }
		            if (stmt != null) {
		                stmt.close();
		            }
		        } catch (SQLException e) {
		            // handle exception
		        }
		    }
		}
	public boolean student_exist(int id, Connection conn) {
		
		PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT COUNT(*) FROM 本科生 WHERE 学号 = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, id);//设置参数
	        rs = stmt.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            if (count > 0) {
	                return true;
	            }
	        }
	        return false;
	    } catch (SQLException e) {
	        // handle exception
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException e) {
	            // handle exception
	        }
	    }
	    return false;
	}
	public void setStuPanel(int id, Connection conn) {
		
		PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT 姓名,所学专业,称谓 FROM 本科生 WHERE 学号= ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, id);//设置参数
	        rs = stmt.executeQuery();
	        if (rs.next()) {
	            StuPanel.getInstance().setlabeltext(rs.getString(1), id, rs.getString(2),rs.getString(3));
	        }
	        else {
	        	return;
	        }
	    } catch (SQLException e) {
	        // handle exception
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException e) {
	            // handle exception
	        }
	    }
	    return ;
		
	}
	public void showteacourse(int id, Connection connection) {
		
    	PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	    	// 执行SQL查询语句
	    	String sql = "SELECT 课程名称, 课程代号,上课时间 FROM 课程 WHERE 开课老师=? ";
	        stmt = connection.prepareStatement(sql);
	        stmt.setInt(1, id);//设置参数
	        rs = stmt.executeQuery();
	        // 处理查询结果
            StringBuilder sb = new StringBuilder();
            sb.append(flushLeftC(7,"课程名称")).append(flushLeftC(8,"课程代号")).append(flushLeftC(8,"上课时间"));
            sb.append("\n");
            while (rs.next()) {
                String name = rs.getString("课程名称");
                int courseid = rs.getInt("课程代号");
                String time=rs.getString("上课时间");
                sb.append(flushLeftC(8,name)).append(flushLeftE(8,String.valueOf(courseid))).append(flushLeftC(8,time));
                sb.append("\n");
            }
            // 将结果设置到JTextArea中
            TeaPanel.getInstance().infoTextArea.setText(sb.toString());
	      
	    } catch (SQLException e) {
	        // handle exception
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException e) {
	            // handle exception
	        }
	    }
	}
	public void showGrade(int id, Connection connection) {
		
		PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	    	// 执行SQL查询语句
	    	String sql = "SELECT 学生学号, 成绩 FROM 成绩 WHERE 课号=? ";
	        stmt = connection.prepareStatement(sql);
	        stmt.setInt(1, id);//设置参数
	        rs = stmt.executeQuery();
	        // 处理查询结果
            StringBuilder sb = new StringBuilder();
            sb.append(flushLeftC(5,"学生学号")).append(flushLeftC(5,"成绩"));
            sb.append("\n");
            while (rs.next()) {
                int stuid = rs.getInt("学生学号");
                float grade=rs.getFloat("成绩");
                sb.append(flushLeftE(8,String.valueOf(stuid))).append(flushLeftE(8,String.valueOf(grade)));
                sb.append("\n");
            }
            // 将结果设置到JTextArea中
            TeaPanel.getInstance().infoTextArea.setText(sb.toString());
	      
	    } catch (SQLException e) {
	        // handle exception
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException e) {
	            // handle exception
	        }
	    }
	}
	public void select_course(int courseid, Connection conn) {
		
		        // 创建 PreparedStatement 对象
				PreparedStatement pstmt = null;
				try {
					
				// 为 SQL 语句中的参数赋值
				String sql = "INSERT INTO 成绩 (课号, 学生学号) VALUES (?, ?)";
		        pstmt = conn.prepareStatement(sql);
		        pstmt.setInt(1, courseid); // 课号
		        pstmt.setInt(2, LoginPanel.id); // 学生学号
		        // 执行 SQL 语句，并获得受影响的行数
		        int rowsAffected = pstmt.executeUpdate();

		        // 输出受影响的行数
		        System.out.println(rowsAffected + " row(s) affected");
		    	} catch (SQLException e) {
		    		String errorMsg = e.getMessage();  // 获取异常信息
		    		JOptionPane.showMessageDialog(null, errorMsg, "错误信息", JOptionPane.ERROR_MESSAGE); // 弹出对应的弹窗
		        } finally {
		                try {
		                	if (pstmt != null) {
		                		pstmt.close();
		                	}
						} catch (SQLException e) {
							e.printStackTrace();
						}
		        }
	}
	//学生界面查询成绩
	public void showmyGrade(int id, Connection connection) {
		
		PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	    	// 执行SQL查询语句
	    	String sql = "SELECT DISTINCT 课程名称,成绩 FROM 课程,成绩 WHERE 成绩.学生学号=? AND 成绩.课号=课程.课程代号 ";
	        stmt = connection.prepareStatement(sql);
	        stmt.setInt(1, id);//设置参数
	        rs = stmt.executeQuery();
	        // 处理查询结果
            StringBuilder sb = new StringBuilder();
            sb.append(flushLeftC(20,"课程名称")).append(flushLeftC(6,"成绩"));
            sb.append("\n");
            while (rs.next()) {
                String name = rs.getString("课程名称");
                float grade=rs.getFloat("成绩");
                sb.append(flushLeftC(20,name)).append(flushLeftE(6,String.valueOf(grade)));
                sb.append("\n");
            }
            // 将结果设置到JTextArea中
            StuPanel.getInstance().infoTextArea.setText(sb.toString());
	      
	    } catch (SQLException e) {
	        // handle exception
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException e) {
	            // handle exception
	        }
	    }
	}
	//删除指定名称的社团
	public void delete_nameclub(String name, Connection conn) {
		
		 PreparedStatement stmt = null;
		 ResultSet rs = null;
		 try {
			 //开始事务
			 conn.setAutoCommit(false);
			 	// 执行SQL查询语句
		    	 String sql = "SELECT COUNT(*) FROM 参加 WHERE 参加的社团名称=?";
		    	 stmt = conn.prepareStatement(sql);
			     stmt.setString(1, name);//设置参数
			     rs = stmt.executeQuery();
			     int count=0;
			     if(rs.next()) {
			    	 count=rs.getInt(1);
			     }
			     //判断学生数量是否大于2
		            if (count > 2) {
		            	JOptionPane.showMessageDialog(null,"删除不成功，该社团的学生数量大于2", "错误信息", JOptionPane.ERROR_MESSAGE); // 弹出对应的弹窗
		                // 事务回滚
		                conn.rollback();
		            } else {
		                //执行删除操作
		                String deleteClubQuery = "DELETE FROM 社团 WHERE 社团名称 = '" + name + "'";
		                stmt.executeUpdate(deleteClubQuery);
		                //提交事务
		                conn.commit();
		                JOptionPane.showMessageDialog(null,"删除成功", "提示信息", JOptionPane.INFORMATION_MESSAGE); // 弹出对应的弹窗
		            }
		            stmt.close();
		 }catch (Exception e) {
			 	//处理异常
	            e.printStackTrace();
	            try {
	                // 事务回滚
	                if (conn != null) {
	                    conn.rollback();
	                }
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
		}finally {
			try {
				if(stmt!=null) {
					stmt.close();
				}
				if(rs!=null) {
					rs.close();
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	//学生退课
	public void drop_course(int courseid, int id,Connection connection) {
		ResultSet rs = null;
		PreparedStatement stmt1 = null;  
		PreparedStatement stmt2 = null;  
	     try {
	    	 	String sql = "SELECT COUNT(*) FROM 成绩 WHERE 课号=? AND 学生学号=?";
	    	 	stmt1 = connection.prepareStatement(sql);
	    	 	stmt1.setInt(1, courseid);//设置参数
	    	 	stmt1.setInt(2, id);//设置参数
	    	 	rs = stmt1.executeQuery();
	    	 	int count=0;
	    	 	if(rs.next()) {
	    	 		count=rs.getInt(1);
	    	 	}
	    	 	if(count<=0) {
	    	 		 JOptionPane.showMessageDialog(null,"该学生未选这门课！", "错误！", JOptionPane.ERROR_MESSAGE);
	    	 	}
	    	 	else {
	    	 		sql = "DELETE FROM 成绩 WHERE 课号=? AND 学生学号=?";	    	   
		            stmt2= connection.prepareStatement(sql);
		            stmt2.setInt(1, courseid);
		            stmt2.setInt(2, id);
		            stmt2.executeUpdate();
		            JOptionPane.showMessageDialog(null,  "退课成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
	    	 	}
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	                try {
	                	if (stmt1 != null) {
	                		stmt1.close();
	                	}
	                	if (stmt2 != null) {
	                		stmt2.close();
	                	}
	                	if(rs!=null) {
	                		rs.close();
	                	}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        }
	}
	//参加社团
	public void take_club(String clubname, int id, Connection conn) {
		
			// 创建 PreparedStatement 对象
			PreparedStatement pstmt = null;
			try {
				
			// 为 SQL 语句中的参数赋值
			String sql = "INSERT INTO 参加 (参加的社团名称, 学生学号) VALUES (?, ?)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, clubname); // 社团名称
	        pstmt.setInt(2, id); // 学生学号
	        pstmt.executeUpdate();
	        JOptionPane.showMessageDialog(null,"参加社团成功", "提示", JOptionPane.INFORMATION_MESSAGE); // 弹出对应的弹窗
	    	} catch (SQLException e) {
	    		JOptionPane.showMessageDialog(null,"没有该社团！", "错误信息", JOptionPane.ERROR_MESSAGE); // 弹出对应的弹窗
	        } finally {
	                try {
	                	if (pstmt != null) {
	                		pstmt.close();
	                	}
					} catch (SQLException e) {
						e.printStackTrace();
					}
	        }
	}
	public void add_club(String name, String time, Connection conn) {
		
					// 创建 PreparedStatement 对象
					PreparedStatement pstmt = null;
					try {
						
					// 为 SQL 语句中的参数赋值
					String sql = "INSERT INTO 社团 (社团名称, 成立时间) VALUES (?, ?)";
			        pstmt = conn.prepareStatement(sql);
			        pstmt.setString(1, name); // 社团名称
			        pstmt.setString(2, time); // 成立时间
			        pstmt.executeUpdate();
			        JOptionPane.showMessageDialog(null,"创建社团成功", "提示", JOptionPane.INFORMATION_MESSAGE); // 弹出对应的弹窗
			    	} catch (SQLException e) {
			    		e.printStackTrace();
			        } finally {
			                try {
			                	if (pstmt != null) {
			                		pstmt.close();
			                	}
							} catch (SQLException e) {
								e.printStackTrace();
							}
			        }
	}
	//查看学院
	public void showcollege(Connection connection) {
		PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	    	// 执行SQL查询语句
	    	String sql = "SELECT 学院名称,建立时间 FROM 学院";
	        stmt = connection.prepareStatement(sql);
	        rs = stmt.executeQuery();
	        // 处理查询结果
            StringBuilder sb = new StringBuilder();
            sb.append(flushLeftC(20,"学院名称")).append(flushLeftC(10,"建立时间"));
            sb.append("\n");
            while (rs.next()) {
                String name = rs.getString("学院名称");
                String time=rs.getString("建立时间");
                sb.append(flushLeftC(20,name)).append(flushLeftE(10,time));
                sb.append("\n");
            }
            // 将结果设置到JTextArea中
            ManagerPanel.getInstance().textArea.setText(sb.toString());
	      
	    } catch (SQLException e) {
	        // handle exception
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException e) {
	            // handle exception
	        }
	    }
		
	}
	//查看学生
	public void showstudents(Connection connection) {
		PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	    	// 执行SQL查询语句
	    	String sql = "SELECT * FROM 本科生";
	        stmt = connection.prepareStatement(sql);
	        rs = stmt.executeQuery();
	        // 处理查询结果
            StringBuilder sb = new StringBuilder();
            sb.append(flushLeftC(7,"姓名")).append(flushLeftC(8,"学号")).append(flushLeftC(7,"入学时间")).append(flushLeftC(5,"性别")).append(flushLeftC(10,"所学专业")).append(flushLeftC(10,"称谓"));
            sb.append("\n");
            while (rs.next()) {
                String name = rs.getString("姓名");
                int id=rs.getInt(2);
                String time=rs.getString("入学时间");
                String sex=rs.getString("性别");
                String major=rs.getString("所学专业");
                String title=rs.getString("称谓");
                sb.append(flushLeftC(7,name)).append(flushLeftC(11,String.valueOf(id))).append(flushLeftE(19,time)).append(flushLeftC(5,sex)).append(flushLeftC(10,major)).append(flushLeftC(20,title));
                sb.append("\n");
            }
            // 将结果设置到JTextArea中
            ManagerPanel.getInstance().textArea.setText(sb.toString());
	      
	    } catch (SQLException e) {
	        // handle exception
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException e) {
	            // handle exception
	        }
	    }
		
	}
	//查看老师
	public void showteachers(Connection connection) {
		PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	    	// 执行SQL查询语句
	    	String sql = "SELECT * FROM 教师";
	        stmt = connection.prepareStatement(sql);
	        rs = stmt.executeQuery();
	        // 处理查询结果
            StringBuilder sb = new StringBuilder();
            sb.append(flushLeftC(5,"姓名")).append(flushLeftC(5,"职工号")).append(flushLeftC(5,"职称")).append(flushLeftC(10,"所属学院")).append(flushLeftC(10,"入职时间"));
            sb.append("\n");
            while (rs.next()) {
                String name = rs.getString("姓名");
                int id=rs.getInt(2);
                String time=rs.getString("入职时间");
                String college=rs.getString("所属学院");
                String title=rs.getString("职称");
                sb.append(flushLeftC(5,name)).append(flushLeftC(8,String.valueOf(id))).append(flushLeftE(11,title)).append(flushLeftC(12,college)).append(flushLeftC(20,time));
                sb.append("\n");
            }
            // 将结果设置到JTextArea中
            ManagerPanel.getInstance().textArea.setText(sb.toString());
	      
	    } catch (SQLException e) {
	        // handle exception
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException e) {
	            // handle exception
	        }
	    }
		
	}
	//查看社团
	public void showclubs(Connection connection) {
		
		PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	    	// 执行SQL查询语句
	    	String sql = "SELECT * FROM 社团";
	        stmt = connection.prepareStatement(sql);
	        rs = stmt.executeQuery();
	        // 处理查询结果
            StringBuilder sb = new StringBuilder();
            sb.append(flushLeftC(20,"社团名称")).append(flushLeftC(10,"成立时间"));
            sb.append("\n");
            while (rs.next()) {
                String name = rs.getString("社团名称");
                String time=rs.getString("成立时间");
                sb.append(flushLeftC(20,name)).append(flushLeftC(20,time));
                sb.append("\n");
            }
            // 将结果设置到JTextArea中
            ManagerPanel.getInstance().textArea.setText(sb.toString());
	      
	    } catch (SQLException e) {
	        // handle exception
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException e) {
	            // handle exception
	        }
	    }
	}
	public boolean manager_exist(int id, Connection conn) {
		
		PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT COUNT(*) FROM 管理者 WHERE 账号 = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, id);//设置参数
	        rs = stmt.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            if (count > 0) {
	                return true;
	            }
	        }
	        return false;
	    } catch (SQLException e) {
	        // handle exception
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException e) {
	            // handle exception
	        }
	    }
	    return false;
	}
}
