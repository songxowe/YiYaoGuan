package com.yiyao.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yiyao.dao.OrderformDao;
import com.yiyao.util.Empty;

/**
 * Servlet implementation class SelectOrderformByStateTotalServlet
 */
public class SelectOrderformByStateTotalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SelectOrderformByStateTotalServlet() {
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

		// 获得参数
		String su_id = request.getParameter("u_id");
		String so_state = request.getParameter("o_state");
		int u_id = Integer.parseInt(su_id);
		int o_state = Integer.parseInt(so_state);

		// 查询并返回结果
		OrderformDao dao = new OrderformDao();
		int totalpage = dao.findByStateTotal(u_id, o_state);
		String json = "{'totalpage':" + totalpage + "}";
		out.write(json);
		out.flush();
		out.close();
	}

}
