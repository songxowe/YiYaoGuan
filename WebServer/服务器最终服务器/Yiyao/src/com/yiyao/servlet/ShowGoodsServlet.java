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
import com.yiyao.pojo.Goods;
import com.yiyao.pojo.ImageUrl;
import com.yiyao.util.Empty;
import com.yiyao.util.JDBC;

/**
 * Servlet implementation class ShowGoodsServlet
 */
public class ShowGoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Gson gson;
	private Type listType;

	@Override
	public void init() throws ServletException {
		super.init();

		gson = new Gson();
		listType = new TypeToken<List<ImageUrl>>() {
		}.getType();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowGoodsServlet() {
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

		String gid = request.getParameter("g_id");
		int id = 0;
		try {
			id = Integer.parseInt(gid);
		} catch (NumberFormatException e) {
			id = 0;
		}
		// 查询商品信息并返回结果
		GoodsDao dao = new GoodsDao();
		Goods goods = dao.findById(id);
		String json = "";
		if (goods != null) {
			// 查询商品 imageurl 便将其赋予
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List<ImageUrl> images = new ArrayList<>();
			try {
				conn = JDBC.getConnection();
				String sql = " select * from y_goods_image_url where g_id=? ";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, id);
				rs = ps.executeQuery();
				while (rs.next()) {
					String image = rs.getString("g_image_url");
					ImageUrl image_url = new ImageUrl(image);
					images.add(image_url);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBC.close(conn, ps, rs);
			}
			goods.setG_image_url(gson.toJson(images, listType));

			json = gson.toJson(goods, Goods.class);
		} else {
			json = "{'g_id':0}";
		}
		out.write(json);
		out.flush();
		out.close();
	}

}
