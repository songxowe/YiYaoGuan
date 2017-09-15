package com.yiyao.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yiyao.dao.ShopCarDao;
import com.yiyao.pojo.ShopCar;

/**
 * Servlet implementation class AddShopCarServlet
 */
public class AddShopCarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddShopCarServlet() {
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
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		String su_id = request.getParameter("u_id");
		String sg_id = request.getParameter("g_id");

		ShopCar shopcar = new ShopCar();
		int u_id = Integer.parseInt(su_id);
		int g_id = Integer.parseInt(sg_id);

		shopcar.setU_id(u_id);
		shopcar.setG_id(g_id);

		ShopCarDao dao = new ShopCarDao();
		int selectcount = dao.findByGIdUid(g_id, u_id);
		
		if (selectcount == 0) {
			int count = dao.add(shopcar);
			if (count > 0) {
				out.println("{'flag':'success'}");
			} else {
				out.println("{'flag':'error'}");
			}
		} else {
			out.println("{'flag':'exist'}");
		}
		out.flush();
		out.close();
	}

}
