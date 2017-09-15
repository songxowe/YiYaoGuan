package com.yiyao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yiyao.pojo.Goods;
import com.yiyao.util.Empty;
import com.yiyao.util.JDBC;
import com.yiyao.util.Pager;


public class GoodsDao {
	public List<Goods> findOrderPrice(Goods goods, int pageno) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Goods> list = null;
		try {
			conn = JDBC.getConnection();
			StringBuffer sql = new StringBuffer(" select * from "
					+ " (select row_number() over (order by g_price) rn , y.*  from y_goods y where 1=1 ");

			if (!Empty.isEmpty(goods.getCondition())) {
				sql.append(" and "
						+ " ( upper(g_name) like ? "
						+ " or upper(g_cure_symptom) like ? "
						+ " or upper(g_classify_one) like ? "
						+ " or upper(g_classify_two) like ? "
						+ " or upper(g_suit_people) like ? "
						+ " or upper(g_trademark) like ? "
						+ " or upper(g_product_company) like ? "
						+ " ) ");
			}

			if (!Empty.isEmpty(goods.getG_cure_symptom())) {
				sql.append(" and upper(g_cure_symptom) like ? ");
			}
			if (!Empty.isEmpty(goods.getG_classify_one())) {
				sql.append(" and upper(g_classify_one) like ? ");
			}
			if (!Empty.isEmpty(goods.getG_classify_two())) {
				sql.append(" and upper(g_classify_two) like ? ");
			}
			if (!Empty.isEmpty(goods.getG_suit_people())) {
				sql.append(" and upper(g_suit_people) like ? ");
			}
			if (!Empty.isEmpty(goods.getG_trademark())) {
				sql.append(" and upper(g_trademark) like ? ");
			}
			sql.append(" ) where rn > ? and rn <= ? ");

			ps = conn.prepareStatement(sql.toString());

			int i = 1;
			if (!Empty.isEmpty(goods.getCondition())) {
				ps.setString(i++, "%" + goods.getCondition().toUpperCase() + "%");
				ps.setString(i++, "%" + goods.getCondition().toUpperCase() + "%");
				ps.setString(i++, "%" + goods.getCondition().toUpperCase() + "%");
				ps.setString(i++, "%" + goods.getCondition().toUpperCase() + "%");
				ps.setString(i++, "%" + goods.getCondition().toUpperCase() + "%");
				ps.setString(i++, "%" + goods.getCondition().toUpperCase() + "%");
				ps.setString(i++, "%" + goods.getCondition().toUpperCase() + "%");
			}
			if (!Empty.isEmpty(goods.getG_cure_symptom())) {
				ps.setString(i++, "%" + goods.getG_cure_symptom().toUpperCase() + "%");
			}
			if (!Empty.isEmpty(goods.getG_classify_one())) {
				ps.setString(i++, "%" + goods.getG_classify_one().toUpperCase() + "%");
			}
			if (!Empty.isEmpty(goods.getG_classify_two())) {
				ps.setString(i++, "%" + goods.getG_classify_two().toUpperCase() + "%");
			}
			if (!Empty.isEmpty(goods.getG_suit_people())) {
				ps.setString(i++, "%" + goods.getG_suit_people().toUpperCase() + "%");
			}
			if (!Empty.isEmpty(goods.getG_trademark())) {
				ps.setString(i++, "%" + goods.getG_trademark().toUpperCase() + "%");
			}

			ps.setInt(i++, pageno * Pager.getSize() - Pager.getSize());
			ps.setInt(i++, pageno * Pager.getSize());

			rs = ps.executeQuery();
			list = new ArrayList<>();
			Goods returngoods = null;
			while (rs.next()) {
				returngoods = new Goods();
				returngoods.setG_id(rs.getInt("g_id"));
				returngoods.setG_show_image_url(Empty.emptyToString(rs.getString("g_show_image_url")));
				returngoods.setG_name(Empty.emptyToString(rs.getString("g_name")));
				returngoods.setG_price(rs.getFloat("g_price"));
				returngoods.setG_discount(rs.getFloat("g_discount"));
				returngoods.setG_imagetxt_url(Empty.emptyToString(rs.getString("g_imagetxt_url")));
				returngoods.setG_cure_symptom(Empty.emptyToString(rs.getString("g_cure_symptom")));
				returngoods.setG_classify_one(Empty.emptyToString(rs.getString("g_classify_one")));
				returngoods.setG_classify_two(Empty.emptyToString(rs.getString("g_classify_two")));
				returngoods.setG_suit_people(Empty.emptyToString(rs.getString("g_suit_people")));
				returngoods.setG_trademark(Empty.emptyToString(rs.getString("g_trademark")));
				returngoods.setG_product_company(Empty.emptyToString(rs.getString("g_product_company")));
				returngoods.setG_special_sell_day(Empty.emptyToString(rs.getString("g_special_sell_day")));
				returngoods.setG_good_evaluate(rs.getInt("g_good_evaluate"));
				returngoods.setG_normal_evaluate(rs.getInt("g_normal_evaluate"));
				returngoods.setG_bad_evaluate(rs.getInt("g_bad_evaluate"));
				list.add(returngoods);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, rs);
		}
		return list;
	}
	
	public List<Goods> findOrderDiscount(Goods goods, int pageno) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Goods> list = null;
		try {
			conn = JDBC.getConnection();
			StringBuffer sql = new StringBuffer(" select * from "
					+ " (select row_number() over (order by g_discount) rn , y.*  from y_goods y where 1=1 ");

			if (!Empty.isEmpty(goods.getCondition())) {
				sql.append(" and "
						+ " ( upper(g_name) like ? "
						+ " or upper(g_cure_symptom) like ? "
						+ " or upper(g_classify_one) like ? "
						+ " or upper(g_classify_two) like ? "
						+ " or upper(g_suit_people) like ? "
						+ " or upper(g_trademark) like ? "
						+ " or upper(g_product_company) like ? "
						+ " ) ");
			}

			if (!Empty.isEmpty(goods.getG_cure_symptom())) {
				sql.append(" and upper(g_cure_symptom) like ? ");
			}
			if (!Empty.isEmpty(goods.getG_classify_one())) {
				sql.append(" and upper(g_classify_one) like ? ");
			}
			if (!Empty.isEmpty(goods.getG_classify_two())) {
				sql.append(" and upper(g_classify_two) like ? ");
			}
			if (!Empty.isEmpty(goods.getG_suit_people())) {
				sql.append(" and upper(g_suit_people) like ? ");
			}
			if (!Empty.isEmpty(goods.getG_trademark())) {
				sql.append(" and upper(g_trademark) like ? ");
			}
			sql.append(" ) where rn > ? and rn <= ? ");

			ps = conn.prepareStatement(sql.toString());

			int i = 1;
			if (!Empty.isEmpty(goods.getCondition())) {
				ps.setString(i++, "%" + goods.getCondition().toUpperCase() + "%");
				ps.setString(i++, "%" + goods.getCondition().toUpperCase() + "%");
				ps.setString(i++, "%" + goods.getCondition().toUpperCase() + "%");
				ps.setString(i++, "%" + goods.getCondition().toUpperCase() + "%");
				ps.setString(i++, "%" + goods.getCondition().toUpperCase() + "%");
				ps.setString(i++, "%" + goods.getCondition().toUpperCase() + "%");
				ps.setString(i++, "%" + goods.getCondition().toUpperCase() + "%");
			}
			if (!Empty.isEmpty(goods.getG_cure_symptom())) {
				ps.setString(i++, "%" + goods.getG_cure_symptom().toUpperCase() + "%");
			}
			if (!Empty.isEmpty(goods.getG_classify_one())) {
				ps.setString(i++, "%" + goods.getG_classify_one().toUpperCase() + "%");
			}
			if (!Empty.isEmpty(goods.getG_classify_two())) {
				ps.setString(i++, "%" + goods.getG_classify_two().toUpperCase() + "%");
			}
			if (!Empty.isEmpty(goods.getG_suit_people())) {
				ps.setString(i++, "%" + goods.getG_suit_people().toUpperCase() + "%");
			}
			if (!Empty.isEmpty(goods.getG_trademark())) {
				ps.setString(i++, "%" + goods.getG_trademark().toUpperCase() + "%");
			}

			ps.setInt(i++, pageno * Pager.getSize() - Pager.getSize());
			ps.setInt(i++, pageno * Pager.getSize());

			rs = ps.executeQuery();
			list = new ArrayList<>();
			Goods returngoods = null;
			while (rs.next()) {
				returngoods = new Goods();
				returngoods.setG_id(rs.getInt("g_id"));
				returngoods.setG_show_image_url(Empty.emptyToString(rs.getString("g_show_image_url")));
				returngoods.setG_name(Empty.emptyToString(rs.getString("g_name")));
				returngoods.setG_price(rs.getFloat("g_price"));
				returngoods.setG_discount(rs.getFloat("g_discount"));
				returngoods.setG_imagetxt_url(Empty.emptyToString(rs.getString("g_imagetxt_url")));
				returngoods.setG_cure_symptom(Empty.emptyToString(rs.getString("g_cure_symptom")));
				returngoods.setG_classify_one(Empty.emptyToString(rs.getString("g_classify_one")));
				returngoods.setG_classify_two(Empty.emptyToString(rs.getString("g_classify_two")));
				returngoods.setG_suit_people(Empty.emptyToString(rs.getString("g_suit_people")));
				returngoods.setG_trademark(Empty.emptyToString(rs.getString("g_trademark")));
				returngoods.setG_product_company(Empty.emptyToString(rs.getString("g_product_company")));
				returngoods.setG_special_sell_day(Empty.emptyToString(rs.getString("g_special_sell_day")));
				returngoods.setG_good_evaluate(rs.getInt("g_good_evaluate"));
				returngoods.setG_normal_evaluate(rs.getInt("g_normal_evaluate"));
				returngoods.setG_bad_evaluate(rs.getInt("g_bad_evaluate"));
				list.add(returngoods);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, rs);
		}
		return list;
	}
	
	public int getTotal(Goods goods) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int total = 0;

		try {
			conn = JDBC.getConnection();
			StringBuffer sql = new StringBuffer(" select count(*) from y_goods where 1=1 ");
			if (!Empty.isEmpty(goods.getCondition())) {
				sql.append(" and "
						+ " ( upper(g_name) like ? "
						+ " or upper(g_cure_symptom) like ? "
						+ " or upper(g_classify_one) like ? "
						+ " or upper(g_classify_two) like ? "
						+ " or upper(g_suit_people) like ? "
						+ " or upper(g_trademark) like ? "
						+ " or upper(g_product_company) like ? "
						+ " ) ");
			}

			if (!Empty.isEmpty(goods.getG_cure_symptom())) {
				sql.append(" and upper(g_cure_symptom) like ? ");
			}
			if (!Empty.isEmpty(goods.getG_classify_one())) {
				sql.append(" and upper(g_classify_one) like ? ");
			}
			if (!Empty.isEmpty(goods.getG_classify_two())) {
				sql.append(" and upper(g_classify_two) like ? ");
			}
			if (!Empty.isEmpty(goods.getG_suit_people())) {
				sql.append(" and upper(g_suit_people) like ? ");
			}
			if (!Empty.isEmpty(goods.getG_trademark())) {
				sql.append(" and upper(g_trademark) like ? ");
			}
			ps = conn.prepareStatement(sql.toString());

			int i = 1;
			if (!Empty.isEmpty(goods.getCondition())) {
				ps.setString(i++, "%" + goods.getCondition().toUpperCase() + "%");
				ps.setString(i++, "%" + goods.getCondition().toUpperCase() + "%");
				ps.setString(i++, "%" + goods.getCondition().toUpperCase() + "%");
				ps.setString(i++, "%" + goods.getCondition().toUpperCase() + "%");
				ps.setString(i++, "%" + goods.getCondition().toUpperCase() + "%");
				ps.setString(i++, "%" + goods.getCondition().toUpperCase() + "%");
				ps.setString(i++, "%" + goods.getCondition().toUpperCase() + "%");
			}
			if (!Empty.isEmpty(goods.getG_cure_symptom())) {
				ps.setString(i++, "%" + goods.getG_cure_symptom().toUpperCase() + "%");
			}
			if (!Empty.isEmpty(goods.getG_classify_one())) {
				ps.setString(i++, "%" + goods.getG_classify_one().toUpperCase() + "%");
			}
			if (!Empty.isEmpty(goods.getG_classify_two())) {
				ps.setString(i++, "%" + goods.getG_classify_two().toUpperCase() + "%");
			}
			if (!Empty.isEmpty(goods.getG_suit_people())) {
				ps.setString(i++, "%" + goods.getG_suit_people().toUpperCase() + "%");
			}
			if (!Empty.isEmpty(goods.getG_trademark())) {
				ps.setString(i++, "%" + goods.getG_trademark().toUpperCase() + "%");
			}
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
	
	
	public Goods findById(int g_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Goods goods = null;
		try {
			conn = JDBC.getConnection();
			String sql = " select * from y_goods where g_id=? ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, g_id);
			rs = ps.executeQuery();
			if (rs.next()) {
				goods = new Goods();
				goods.setG_id(rs.getInt("g_id"));
				goods.setG_show_image_url(Empty.emptyToString(rs.getString("g_show_image_url")));
				goods.setG_name(Empty.emptyToString(rs.getString("g_name")));
				goods.setG_price(rs.getFloat("g_price"));
				goods.setG_discount(rs.getFloat("g_discount"));
				goods.setG_imagetxt_url(Empty.emptyToString(rs.getString("g_imagetxt_url")));
				goods.setG_cure_symptom(Empty.emptyToString(rs.getString("g_cure_symptom")));
				goods.setG_classify_one(Empty.emptyToString(rs.getString("g_classify_one")));
				goods.setG_classify_two(Empty.emptyToString(rs.getString("g_classify_two")));
				goods.setG_suit_people(Empty.emptyToString(rs.getString("g_suit_people")));
				goods.setG_trademark(Empty.emptyToString(rs.getString("g_trademark")));
				goods.setG_product_company(Empty.emptyToString(rs.getString("g_product_company")));
				goods.setG_special_sell_day(Empty.emptyToString(rs.getString("g_special_sell_day")));
				goods.setG_good_evaluate(rs.getInt("g_good_evaluate"));
				goods.setG_normal_evaluate(rs.getInt("g_normal_evaluate"));
				goods.setG_bad_evaluate(rs.getInt("g_bad_evaluate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, rs);
		}
		return goods;
	}
	
	public int SelectGoodsEvaluate(int g_id,int evaluate_level) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int evaluate_count = 0;
		try {
			conn = JDBC.getConnection();
			String sql = " select * from y_goods where g_id=? ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, g_id);
			rs = ps.executeQuery();
			if (rs.next()) {
				if(evaluate_level==1){
					evaluate_count=rs.getInt("g_good_evaluate");
				}else if(evaluate_level==2){
					evaluate_count=rs.getInt("g_normal_evaluate");
				}else if(evaluate_level==3){
					evaluate_count=rs.getInt("g_bad_evaluate");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, null);
		}
		return evaluate_count;
	}
	
	public int UpdateGoodsEvaluate(int g_id,int evaluate_level) {
		Connection conn = null;
		PreparedStatement ps = null;
		//评价一次，获取该评价数量，并在原基础上加一
		int evaluate_count=SelectGoodsEvaluate(g_id,evaluate_level)+1;
		int count = 0;
		try {
			conn = JDBC.getConnection();
			String sql="";
			if(evaluate_level==1){
				sql+=" update y_goods set g_good_evaluate=? where g_id=? ";
			}else if(evaluate_level==2){
				sql+=" update y_goods set g_normal_evaluate=? where g_id=? ";
			}else if(evaluate_level==3){
				sql+=" update y_goods set g_bad_evaluate=? where g_id=? ";
			}
			ps = conn.prepareStatement(sql);
			ps.setInt(1, evaluate_count);
			ps.setInt(2, g_id);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, ps, null);
		}
		return count;
	}
}
