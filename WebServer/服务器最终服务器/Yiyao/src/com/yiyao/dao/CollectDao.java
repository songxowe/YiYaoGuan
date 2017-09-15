package com.yiyao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yiyao.pojo.Collection;
import com.yiyao.util.JDBC;

public class CollectDao {
	public int add(Collection collection) {
		Connection conn = null;
		PreparedStatement ps = null;
		int count = 0;
		try {
			conn = JDBC.getConnection();
			String sql = " insert into y_collect(c_id,u_id,g_id) ";
			sql += " values(seq_y_collect_id.nextval,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, collection.getU_id());
			ps.setInt(2, collection.getG_id());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, null);
		}
		return count;
	}

	public int selectByUidGid(int u_id, int g_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;

		try {
			conn = JDBC.getConnection();
			StringBuffer sql = new StringBuffer(" select count(*) from y_collect where u_id= ? and g_id= ? ");
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, u_id);
			ps.setInt(2, g_id);
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, null);
		}
		return count;
	}

	public int deleteByUidGid(int u_id, int g_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		int count = 0;
		try {
			conn = JDBC.getConnection();
			StringBuffer sql = new StringBuffer(" delete y_collect where u_id= ? and g_id= ? ");
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, u_id);
			ps.setInt(2, g_id);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, null);
		}
		return count;
	}

	public int delete(int c_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		int count = 0;

		try {
			conn = JDBC.getConnection();
			StringBuffer sql = new StringBuffer(" delete y_collect where c_id= ? ");
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, c_id);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, null);
		}
		return count;
	}

	public int deleteAll(int u_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		int count = 0;

		try {
			conn = JDBC.getConnection();
			StringBuffer sql = new StringBuffer(" delete y_collect where u_id= ? ");
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, u_id);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, null);
		}
		return count;
	}

	public List<Collection> findByUid(int u_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Collection> list = null;
		try {
			conn = JDBC.getConnection();
			String sql = " select y_collect.*,g_show_image_url,g_name,g_price,g_discount from y_collect,y_goods"
					+ " where y_collect.g_id = y_goods.g_id and u_id=? ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, u_id);
			rs = ps.executeQuery();
			list = new ArrayList<>();
			while (rs.next()) {
				Collection collection = new Collection();
				collection.setC_id(rs.getInt("c_id"));
				collection.setU_id(rs.getInt("u_id"));
				collection.setG_id(rs.getInt("g_id"));
				collection.setG_image_url(rs.getString("g_show_image_url"));
				collection.setG_name(rs.getString("g_name"));
				collection.setG_price(rs.getFloat("g_price"));
				collection.setG_discount(rs.getFloat("g_discount"));
				list.add(collection);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, rs);
		}
		return list;
	}
}
