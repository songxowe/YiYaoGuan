package com.yiyao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yiyao.pojo.Goods;
import com.yiyao.pojo.Orderform;
import com.yiyao.util.Empty;
import com.yiyao.util.JDBC;
import com.yiyao.util.Pager;

public class OrderformDao {
	public int add(Orderform orderform) {
		Connection conn = null;
		PreparedStatement ps = null;
		int count = 0;
		try {
			conn = JDBC.getConnection();
			String sql = " insert into y_order_form(o_id,u_id,g_ids,o_date,o_state,o_goods_value, "
					+ " o_transport_value,o_total_value,o_receive_people, " + " o_receive_address,o_receive_phone) ";
			sql += " values(seq_y_order_form_id.nextval,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, orderform.getU_id());
			ps.setString(2, orderform.getG_ids());
			ps.setString(3, orderform.getO_date());
			ps.setInt(4, orderform.getO_state());
			ps.setFloat(5, orderform.getO_goods_value());
			ps.setFloat(6, orderform.getO_transport_value());
			ps.setFloat(7, orderform.getO_total_value());
			ps.setString(8, orderform.getO_receive_people());
			ps.setString(9, orderform.getO_receive_address());
			ps.setString(10, orderform.getO_receive_phone());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, null);
		}
		return count;
	}

	public List<Orderform> findByState(int u_id, int state, int pageno) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Orderform> list = null;
		try {
			conn = JDBC.getConnection();
			String sql = " select * from "
					+ " (select row_number() over (order by o_id desc) rn , y.*  from y_order_form y "
					+ " where u_id= ? and o_state=? ) where rn > ? and rn <= ? ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, u_id);
			ps.setInt(2, state);
			ps.setInt(3, pageno * Pager.getSize() - Pager.getSize());
			ps.setInt(4, pageno * Pager.getSize());

			rs = ps.executeQuery();
			list = new ArrayList<>();
			while (rs.next()) {
				Orderform orderform = new Orderform();
				orderform.setO_id(rs.getInt("o_id"));
				orderform.setU_id(rs.getInt("u_id"));

				// 获取第一张图片的头像url
				GoodsDao goodsdao = new GoodsDao();
				String g_ids = rs.getString("g_ids");
				orderform.setG_ids(g_ids);
				String[] g_id = g_ids.split("\\*");
				Goods goods = goodsdao.findById(Integer.parseInt(g_id[0]));
				orderform.setShow_image_url(goods.getG_show_image_url());

				orderform.setO_date(rs.getString("o_date"));
				orderform.setO_state(rs.getInt("o_state"));
				orderform.setO_goods_value(rs.getFloat("o_goods_value"));
				orderform.setO_transport_value(rs.getFloat("o_transport_value"));
				orderform.setO_total_value(rs.getFloat("o_total_value"));
				orderform.setO_receive_people(rs.getString("o_receive_people"));
				orderform.setO_receive_address(rs.getString("o_receive_address"));
				orderform.setO_receive_phone(rs.getString("o_receive_phone"));

				list.add(orderform);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, rs);
		}
		return list;
	}

	public List<Orderform> findByUId(int u_id, int pageno) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Orderform> list = null;
		try {
			conn = JDBC.getConnection();
			String sql = " select * from "
					+ " (select row_number() over (order by o_id desc) rn , y.*  from y_order_form y "
					+ " where u_id= ?) where rn > ? and rn <= ? ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, u_id);
			ps.setInt(2, pageno * Pager.getSize() - Pager.getSize());
			ps.setInt(3, pageno * Pager.getSize());

			rs = ps.executeQuery();
			list = new ArrayList<>();
			while (rs.next()) {
				Orderform orderform = new Orderform();
				orderform.setO_id(rs.getInt("o_id"));
				orderform.setU_id(rs.getInt("u_id"));

				// 获取第一张图片的头像url
				GoodsDao goodsdao = new GoodsDao();
				String g_ids = rs.getString("g_ids");
				orderform.setG_ids(g_ids);
				String[] g_id = g_ids.split("\\*");
				Goods goods = goodsdao.findById(Integer.parseInt(g_id[0]));
				orderform.setShow_image_url(goods.getG_show_image_url());

				orderform.setO_date(rs.getString("o_date"));
				orderform.setO_state(rs.getInt("o_state"));
				orderform.setO_goods_value(rs.getFloat("o_goods_value"));
				orderform.setO_transport_value(rs.getFloat("o_transport_value"));
				orderform.setO_total_value(rs.getFloat("o_total_value"));
				orderform.setO_receive_people(rs.getString("o_receive_people"));
				orderform.setO_receive_address(rs.getString("o_receive_address"));
				orderform.setO_receive_phone(rs.getString("o_receive_phone"));
				list.add(orderform);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, rs);
		}
		return list;
	}

	public int findByStateTotal(int u_id, int state) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int total = 0;

		try {
			conn = JDBC.getConnection();
			StringBuffer sql = new StringBuffer(" select count(*) from y_order_form where u_id= ? and o_state=? ");

			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, u_id);
			ps.setInt(2, state);
			rs = ps.executeQuery();

			if (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, rs);
		}
		return total % Pager.getSize() == 0 ? total / Pager.getSize() : (total / Pager.getSize() + 1);
	}

	public int findByUIdTotal(int u_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int total = 0;

		try {
			conn = JDBC.getConnection();
			StringBuffer sql = new StringBuffer(" select count(*) from y_order_form where u_id= ? ");

			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, u_id);

			rs = ps.executeQuery();

			if (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, rs);
		}
		return total % Pager.getSize() == 0 ? total / Pager.getSize() : (total / Pager.getSize() + 1);
	}

	public Orderform findById(int o_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Orderform orderform = null;
		try {
			conn = JDBC.getConnection();
			String sql = " select * from y_order_form where o_id=? ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, o_id);
			rs = ps.executeQuery();
			if (rs.next()) {
				orderform = new Orderform();
				orderform.setO_id(rs.getInt("o_id"));
				orderform.setU_id(rs.getInt("u_id"));
				String g_ids = rs.getString("g_ids");
				orderform.setG_ids(g_ids);
				orderform.setO_date(rs.getString("o_date"));
				orderform.setO_state(rs.getInt("o_state"));
				orderform.setO_goods_value(rs.getFloat("o_goods_value"));
				orderform.setO_transport_value(rs.getFloat("o_transport_value"));
				orderform.setO_total_value(rs.getFloat("o_total_value"));
				orderform.setO_receive_people(rs.getString("o_receive_people"));
				orderform.setO_receive_address(rs.getString("o_receive_address"));
				orderform.setO_receive_phone(rs.getString("o_receive_phone"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, rs);
		}
		return orderform;
	}

	public int delete(int o_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		int count = 0;
		try {
			conn = JDBC.getConnection();
			StringBuffer sql = new StringBuffer(" delete y_order_form where o_id= ? ");
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, o_id);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, null);
		}
		return count;
	}

	public int Update(int o_id, int o_state) {
		Connection conn = null;
		PreparedStatement ps = null;
		int count = 0;
		try {
			conn = JDBC.getConnection();
			StringBuffer sql = new StringBuffer(" update y_order_form set o_state= ? where o_id= ? ");
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, o_state);
			ps.setInt(2, o_id);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, null);
		}
		return count;
	}
}
