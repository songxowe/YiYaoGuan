package com.yiyao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yiyao.pojo.UserAddress;
import com.yiyao.util.JDBC;


public class UserAddressDao {
	public List<UserAddress> findByUid(int u_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<UserAddress> list = null;
		try {
			conn = JDBC.getConnection();
			String sql = " select * from y_user_address where u_id= ? ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, u_id);
			rs = ps.executeQuery();
			list = new ArrayList<>();
			while (rs.next()) {
				UserAddress userAddress = new UserAddress();
				userAddress.setA_id(rs.getInt("a_id"));
				userAddress.setU_id(u_id);
				userAddress.setA_receive_user_name(rs.getString("a_receive_user_name"));
				userAddress.setA_receive_user_address(rs.getString("a_receive_user_address"));
				userAddress.setA_receive_user_phone(rs.getString("a_receive_user_phone"));
				list.add(userAddress);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, rs);
		}
		return list;
	}
	
	public UserAddress findByAid(int a_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserAddress userAddress =null;
		try {
			conn = JDBC.getConnection();
			String sql = " select * from y_user_address where a_id= ? ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, a_id);
			rs = ps.executeQuery();
			if(rs.next()) {
				userAddress = new UserAddress();
				userAddress.setA_id(a_id);
				userAddress.setU_id(rs.getInt("u_id"));
				userAddress.setA_receive_user_name(rs.getString("a_receive_user_name"));
				userAddress.setA_receive_user_address(rs.getString("a_receive_user_address"));
				userAddress.setA_receive_user_phone(rs.getString("a_receive_user_phone"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, rs);
		}
		return userAddress;
	}
	
	public int update(UserAddress userAddress) {
		Connection conn = null;
		PreparedStatement ps = null;
		int count = 0;

		try {
			conn = JDBC.getConnection();
			StringBuffer sql = new StringBuffer(" update y_user_address set u_id= ?, "
					+ " a_receive_user_name=?,a_receive_user_address=?,a_receive_user_phone=? "
					+ " where a_id= ? ");
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, userAddress.getU_id());
			ps.setString(2,userAddress.getA_receive_user_name());
			ps.setString(3,userAddress.getA_receive_user_address());
			ps.setString(4,userAddress.getA_receive_user_phone());
			ps.setInt(5,userAddress.getA_id());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, null);
		}
		return count;
	}
	
	public int delete(int a_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		int count = 0;

		try {
			conn = JDBC.getConnection();
			StringBuffer sql = new StringBuffer(" delete y_user_address where a_id= ? ");
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1,a_id);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, null);
		}
		return count;
	}
	
	public int add(UserAddress userAddress) {
		Connection conn = null;
		PreparedStatement ps = null;
		int count = 0;
		try {
			conn = JDBC.getConnection();
			String sql = " insert into y_user_address(a_id,u_id,a_receive_user_name,a_receive_user_address,a_receive_user_phone) ";
			sql += " values(seq_y_user_address_id.nextval,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userAddress.getU_id());
			ps.setString(2,userAddress.getA_receive_user_name());
			ps.setString(3,userAddress.getA_receive_user_address());
			ps.setString(4,userAddress.getA_receive_user_phone());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, null);
		}
		return count;
	}
}
