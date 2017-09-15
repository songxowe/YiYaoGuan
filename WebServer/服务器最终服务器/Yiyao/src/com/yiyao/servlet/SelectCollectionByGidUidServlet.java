package com.yiyao.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yiyao.dao.CollectDao;

/**
 * Servlet implementation class SelectCollectionByGidUidServlet
 */
public class SelectCollectionByGidUidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SelectCollectionByGidUidServlet() {
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
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		String su_id = request.getParameter("u_id");
		String sg_id = request.getParameter("g_id");
		int u_id = Integer.parseInt(su_id);
		int g_id = Integer.parseInt(sg_id);

		CollectDao dao = new CollectDao();
		int count = dao.selectByUidGid(u_id, g_id);

		String json = "{'count':" + count + "}";
		out.write(json);
		out.flush();
		out.close();
	}

}
