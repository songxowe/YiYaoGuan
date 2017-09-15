package com.yiyao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JDBC {
	private static String url="jdbc:oracle:thin:@localhost:1521:orcl";
	private static String username="scott";
	private static String password="tiger";
//构造方法
//静态块：注册驱动!	
	static{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
//建立公共获取连接的方法！
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url,username,password);
	}
//静态释放资源的方法
	public static void close(Connection conn,Statement st,ResultSet rs){
		try {
			if(conn!=null){
				conn.close();
			}
			if(st!=null){
				st.close();
			}
			if(rs!=null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//重载释放资源的方法
	public static void close(Connection conn,PreparedStatement pst,ResultSet rs){
		try {
			if(conn!=null){
				conn.close();
			}
			if(pst!=null){
				pst.close();
			}
			if(rs!=null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
//set、get方法
	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		JDBC.url = url;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		JDBC.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		JDBC.password = password;
	}
	
}
