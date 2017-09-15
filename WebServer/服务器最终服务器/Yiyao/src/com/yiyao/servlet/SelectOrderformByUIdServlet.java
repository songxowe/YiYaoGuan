package com.yiyao.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yiyao.dao.OrderformDao;
import com.yiyao.pojo.Orderform;
import com.yiyao.util.Pager;

/**
 * Servlet implementation class SelectOrderformByUIdServlet
 */
public class SelectOrderformByUIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson;
	private Type listType;

	@Override
	public void init() throws ServletException {
		super.init();

		gson = new Gson();
		listType = new TypeToken<List<Orderform>>() {
		}.getType();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SelectOrderformByUIdServlet() {
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
		// 分页参数
		String spageno = request.getParameter("pageno");
		System.out.println("spageno" + spageno);
		int pageno = 0;
		try {
			pageno = Integer.parseInt(spageno);
		} catch (NumberFormatException e) {
			pageno = 1;
		}
		// 获得排序参数
		String su_id = request.getParameter("u_id");
		int u_id = Integer.parseInt(su_id);

		// 查询并返回结果
		OrderformDao dao = new OrderformDao();
		Pager pager = new Pager();
		int totalpage = dao.findByUIdTotal(u_id);
		pager.setTotalpage(totalpage);
		// 判断分页的其他情况，确认页数
		if (pageno < 1) {
			pageno = 1;
		} else if (pageno > totalpage) {
			pageno = totalpage;
		}
		// 按页数查询，并返回结果
		String json = "";
		List list = dao.findByUId(u_id,pageno);

		if (list != null && list.size() != 0) {
			pager.setList(list);
			json += gson.toJson(list, listType);
		} else {
			json += "{'o_id':0}";
		}
		out.write(json);
		out.flush();
		out.close();
	}

}
