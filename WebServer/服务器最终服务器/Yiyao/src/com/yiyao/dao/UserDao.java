package com.yiyao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yiyao.pojo.User;
import com.yiyao.util.JDBC;

public class UserDao {
	
	public int update(User user) {
		Connection conn = null;
		PreparedStatement ps = null;

		int count = 0;

		try {
			conn = JDBC.getConnection();
			StringBuffer sql = new StringBuffer(" update y_user ");
			boolean isfirst=true;

			if (user.getU_imageurl() != null && !user.getU_imageurl().equals("")) {
				if(isfirst){
					sql.append(" set u_imageurl= ? ");
				}else{
					sql.append(" ,u_imageurl= ? ");
				}
				isfirst=false;
			}
			if (user.getU_password() != null && !user.getU_password().equals("")) {
				if(isfirst){
					sql.append(" set u_password= ? ");
				}else{
					sql.append(" ,u_password= ? ");
				}
				isfirst=false;
			}	
			if (user.getU_backgroundimageurl() != null && !user.getU_backgroundimageurl().equals("")) {
				if(isfirst){
					sql.append(" set u_backgroundimageurl= ? ");
				}else{
					sql.append(" ,u_backgroundimageurl= ? ");
				}
				isfirst=false;
			}
			if (user.getU_nickname() != null && !user.getU_nickname().equals("")) {
				if(isfirst){
					sql.append(" set u_nickname= ? ");
				}else{
					sql.append(" ,u_nickname= ? ");
				}
				isfirst=false;
			}
			if (user.getU_username() != null && !user.getU_username().equals("")) {
				if(isfirst){
					sql.append(" set u_username= ? ");
				}else{
					sql.append(" ,u_username= ? ");
				}
				isfirst=false;
			}
			if (user.getU_sex() != null && !user.getU_sex().equals("")) {
				if(isfirst){
					sql.append(" set u_sex= ? ");
				}else{
					sql.append(" ,u_sex= ? ");
				}
				isfirst=false;
			}
			if (user.getU_age()!= 0) {
				if(isfirst){
					sql.append(" set u_age= ? ");
				}else{
					sql.append(" ,u_age= ? ");
				}
				isfirst=false;
			}
			if (user.getU_address() != null && !user.getU_address().equals("")) {
				if(isfirst){
					sql.append(" set u_address= ? ");
				}else{
					sql.append(" ,u_address= ? ");
				}
				isfirst=false;
			}
			if (user.getU_mobile() != null && !user.getU_mobile().equals("")) {
				if(isfirst){
					sql.append(" set u_mobile= ? ");
				}else{
					sql.append(" ,u_mobile= ? ");
				}
				isfirst=false;
			}
			sql.append(" where u_id= ?");
			ps = conn.prepareStatement(sql.toString());

			int i = 1;
			if (user.getU_imageurl() != null && !user.getU_imageurl().equals("")) {
				ps.setString(i++, user.getU_imageurl());
			}
			if (user.getU_password() != null && !user.getU_password().equals("")) {
				ps.setString(i++, user.getU_password());
			}	
			if (user.getU_backgroundimageurl() != null && !user.getU_backgroundimageurl().equals("")) {
				ps.setString(i++, user.getU_backgroundimageurl());
			}
			if (user.getU_nickname() != null && !user.getU_nickname().equals("")) {
				ps.setString(i++, user.getU_nickname());
			}
			if (user.getU_username() != null && !user.getU_username().equals("")) {
				ps.setString(i++, user.getU_username());
			}
			if (user.getU_sex() != null && !user.getU_sex().equals("")) {
				ps.setString(i++, user.getU_sex());
			}
			if (user.getU_age()!= 0) {
				ps.setInt(i++, user.getU_age());
			}
			if (user.getU_address() != null && !user.getU_address().equals("")) {
				ps.setString(i++, user.getU_address());
			}
			if (user.getU_mobile() != null && !user.getU_mobile().equals("")) {
				ps.setString(i++, user.getU_mobile());
			}
			ps.setInt(i++, user.getU_id());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, null);
		}
		return count;
	}

	
	
	public int add(User user) {
		Connection conn = null;
		PreparedStatement ps = null;
		int count = 0;

		try {
			conn = JDBC.getConnection();
			String sql = " insert into y_user(u_id,u_mobile,u_password) ";
			sql += " values(seq_y_user_id.nextval,?,?)";

			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getU_mobile());
			ps.setString(2, user.getU_password());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, null);
		}
		return count;
	}

	public User find(String mobile, String password) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;

		try {
			conn = JDBC.getConnection();
			String sql = "select * from y_user ";
			sql += " where u_mobile = ? ";
			sql += " and u_password = ? ";

			ps = conn.prepareStatement(sql);
			ps.setString(1, mobile);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setU_id(rs.getInt("u_id"));
				user.setU_imageurl(rs.getString("u_imageurl")==null?"":rs.getString("u_imageurl"));
				user.setU_backgroundimageurl(rs.getString("u_backgroundimageurl")==null?"":rs.getString("u_backgroundimageurl"));
				user.setU_nickname(rs.getString("u_nickname")==null?"":rs.getString("u_nickname"));
				user.setU_sex(rs.getString("u_sex")==null?"":rs.getString("u_sex"));
				user.setU_age(rs.getInt("u_age"));
				user.setU_username(rs.getString("u_username")==null?"":rs.getString("u_username"));
				user.setU_address(rs.getString("u_address")==null?"":rs.getString("u_address"));
				user.setU_mobile(rs.getString("u_mobile")==null?"":rs.getString("u_mobile"));
				user.setU_password(rs.getString("u_password")==null?"":rs.getString("u_password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, rs);
		}
		return user;
	}

	public User find(String mobile) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;

		try {
			conn = JDBC.getConnection();
			String sql = "select * from y_user ";
			sql += " where u_mobile = ? ";

			ps = conn.prepareStatement(sql);
			ps.setString(1, mobile);
			rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setU_id(rs.getInt("u_id"));
				user.setU_imageurl(rs.getString("u_imageurl")==null?"":rs.getString("u_imageurl"));
				user.setU_backgroundimageurl(rs.getString("u_backgroundimageurl")==null?"":rs.getString("u_backgroundimageurl"));
				user.setU_nickname(rs.getString("u_nickname")==null?"":rs.getString("u_nickname"));
				user.setU_sex(rs.getString("u_sex")==null?"":rs.getString("u_sex"));
				user.setU_age(rs.getInt("u_age"));
				user.setU_username(rs.getString("u_username")==null?"":rs.getString("u_username"));
				user.setU_address(rs.getString("u_address")==null?"":rs.getString("u_address"));
				user.setU_mobile(rs.getString("u_mobile")==null?"":rs.getString("u_mobile"));
				user.setU_password(rs.getString("u_password")==null?"":rs.getString("u_password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, rs);
		}
		return user;
	}
}
