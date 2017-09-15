package com.yiyao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yiyao.pojo.ShopCar;
import com.yiyao.util.JDBC;

public class ShopCarDao {
	public int add(ShopCar shopcar) {
		Connection conn = null;
		PreparedStatement ps = null;
		int count = 0;
		try {
			conn = JDBC.getConnection();
			String sql = " insert into y_shopcar(s_id,u_id,g_id) ";
			sql += " values(seq_y_shopcar_id.nextval,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, shopcar.getU_id());
			ps.setInt(2, shopcar.getG_id());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, null);
		}
		return count;
	}

	public int delete(int s_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		int count = 0;

		try {
			conn = JDBC.getConnection();
			StringBuffer sql = new StringBuffer(" delete y_shopcar where s_id= ? ");
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, s_id);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, null);
		}
		return count;
	}

	public int findByGIdUid(int g_id, int u_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;

		try {
			conn = JDBC.getConnection();
			StringBuffer sql = new StringBuffer(" select count(*) from  y_shopcar where g_id= ? and u_id=? ");
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, g_id);
			ps.setInt(2, u_id);
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

	public int deleteByGId(int g_id, int u_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		int count = 0;

		try {
			conn = JDBC.getConnection();
			StringBuffer sql = new StringBuffer(" delete y_shopcar where g_id= ? and u_id=? ");
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, g_id);
			ps.setInt(2, u_id);
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
			StringBuffer sql = new StringBuffer(" delete y_shopcar where u_id= ? ");
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

	public List<ShopCar> findByUid(int u_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ShopCar> list = null;
		try {
			conn = JDBC.getConnection();
			String sql = " select y_shopcar.*,g_show_image_url,g_name,g_price,g_discount from y_shopcar,y_goods "
					+ " where y_shopcar.g_id = y_goods.g_id and u_id=? " + " order by s_id ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, u_id);
			rs = ps.executeQuery();
			list = new ArrayList<>();
			while (rs.next()) {
				ShopCar shopcar = new ShopCar();
				shopcar.setS_id(rs.getInt("s_id"));
				shopcar.setU_id(rs.getInt("u_id"));
				shopcar.setG_id(rs.getInt("g_id"));
				shopcar.setG_image_url(rs.getString("g_show_image_url"));
				shopcar.setG_name(rs.getString("g_name"));
				shopcar.setG_price(rs.getFloat("g_price"));
				shopcar.setG_discount(rs.getFloat("g_discount"));
				list.add(shopcar);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, rs);
		}
		return list;
	}
}
