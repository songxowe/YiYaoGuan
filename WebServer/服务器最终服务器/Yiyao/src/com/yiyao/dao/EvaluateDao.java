package com.yiyao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yiyao.pojo.Evaluate;
import com.yiyao.util.Empty;
import com.yiyao.util.JDBC;

public class EvaluateDao {
	public List<Evaluate> findByGid(int g_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Evaluate> list = null;
		try {
			conn = JDBC.getConnection();
			String sql = " select y_evaluate.*,u_nickname,u_imageurl from y_evaluate "
					+ " join y_user on(y_evaluate.u_id=y_user.u_id) where g_id= ? order by e_id ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, g_id);
			rs = ps.executeQuery();
			list = new ArrayList<>();
			while (rs.next()) {
				Evaluate evaluate = new Evaluate();
				evaluate.setE_id(rs.getInt("e_id"));
				evaluate.setG_id(g_id);
				evaluate.setU_id(rs.getInt("u_id"));
				evaluate.setU_nickname(Empty.emptyToString(rs.getString("u_nickname")));
				evaluate.setU_imageurl(Empty.emptyToString(rs.getString("u_imageurl")));
				evaluate.setEvaluate_content(Empty.emptyToString(rs.getString("evaluate_content")));
				evaluate.setEvaluate_date(Empty.emptyToString(rs.getString("evaluate_date")));
				evaluate.setEvaluate_level(rs.getInt("evaluate_level"));
				list.add(evaluate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, rs);
		}
		return list;
	}
	
	public List<Evaluate> findByEvaluateLevel(int g_id,int evaluate_level) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Evaluate> list = null;
		try {
			conn = JDBC.getConnection();
			String sql = " select y_evaluate.*,u_nickname,u_imageurl from y_evaluate "
					+ " join y_user on(y_evaluate.u_id=y_user.u_id) where evaluate_level= ? "
					+ " and g_id =? order by e_id ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1,evaluate_level);
			ps.setInt(2,g_id);
			rs = ps.executeQuery();
			list = new ArrayList<>();
			while (rs.next()) {
				Evaluate evaluate = new Evaluate();
				evaluate.setE_id(rs.getInt("e_id"));
				evaluate.setG_id(rs.getInt("g_id"));
				evaluate.setU_id(rs.getInt("u_id"));
				evaluate.setU_nickname(Empty.emptyToString(rs.getString("u_nickname")));
				evaluate.setU_imageurl(Empty.emptyToString(rs.getString("u_imageurl")));
				evaluate.setEvaluate_content(Empty.emptyToString(rs.getString("evaluate_content")));
				evaluate.setEvaluate_date(Empty.emptyToString(rs.getString("evaluate_date")));
				evaluate.setEvaluate_level(rs.getInt("evaluate_level"));
				list.add(evaluate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, rs);
		}
		return list;
	}
	
	public int add(Evaluate evaluate) {
		Connection conn = null;
		PreparedStatement ps = null;
		int count = 0;
		try {
			conn = JDBC.getConnection();
			String sql = " insert into y_evaluate(e_id,g_id,u_id,evaluate_content,evaluate_date,evaluate_level) ";
			sql += " values(seq_y_evaluate_id.nextval,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, evaluate.getG_id());
			ps.setInt(2,evaluate.getU_id());
			ps.setString(3,evaluate.getEvaluate_content());
			ps.setString(4,evaluate.getEvaluate_date());
			ps.setInt(5,evaluate.getEvaluate_level());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, null);
		}
		return count;
	}
}
