package com.yiyao.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yiyao.dao.GoodsDao;
import com.yiyao.dao.OrderformDao;
import com.yiyao.pojo.Goods;
import com.yiyao.pojo.Orderform;
import com.yiyao.util.JDBC;

/**
 * Servlet implementation class ShowOrderformServlet
 */
public class ShowOrderformServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Gson gson;
	private Type listType;

	@Override
	public void init() throws ServletException {
		super.init();

		gson = new Gson();
		listType = new TypeToken<List<Goods>>() {
		}.getType();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowOrderformServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		String so_id = request.getParameter("o_id");
		int o_id = 0;
		try {
			o_id = Integer.parseInt(so_id);
		} catch (NumberFormatException e) {
			o_id = 0;
		}
		// 查询商品信息并返回结果
		OrderformDao dao = new OrderformDao();
		Orderform orderform = dao.findById(o_id);

		String json = "";
		if (orderform != null) {
			// 查询商品 imageurl 便将其赋予
			String g_ids = orderform.getG_ids();
			String[] gids = g_ids.split("\\*");
			GoodsDao goodsDao = new GoodsDao();
			List<Goods> rgoods = new ArrayList<>();
			for (int i = 0; i < gids.length; i++) {
				Goods good = goodsDao.findById(Integer.parseInt(gids[i]));
				rgoods.add(good);
			}
			orderform.setOrderfrom_good(gson.toJson(rgoods, listType));

			json = gson.toJson(orderform, Orderform.class);
		} else {
			json = "{'o_id':0}";
		}
		out.write(json);
		out.flush();
		out.close();
	}

}
