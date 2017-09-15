package com.yiyao.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yiyao.dao.OrderformDao;
import com.yiyao.dao.ShopCarDao;
import com.yiyao.pojo.Orderform;

/**
 * Servlet implementation class AddOrderformServlet
 */
public class AddOrderformServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddOrderformServlet() {
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

		Orderform orderform = new Orderform();

		String su_id = request.getParameter("u_id");
		int u_id = Integer.parseInt(su_id);
		orderform.setU_id(u_id);

		String g_ids = request.getParameter("g_ids");
		orderform.setG_ids(g_ids);

		String o_date = request.getParameter("o_date");
		orderform.setO_date(o_date);
		// state在添加时 自动转为待支付状态，因此不用传递参数过来
		// String so_state = request.getParameter("o_state");
		orderform.setO_state(1);

		String o_goods_value = request.getParameter("o_goods_value");
		orderform.setO_goods_value(Float.parseFloat(o_goods_value));

		String o_transport_value = request.getParameter("o_transport_value");
		orderform.setO_transport_value(Float.parseFloat(o_transport_value));

		String o_total_value = request.getParameter("o_total_value");
		orderform.setO_total_value(Float.parseFloat(o_total_value));

		String o_receive_people = request.getParameter("o_receive_people");
		orderform.setO_receive_people(o_receive_people);
		String o_receive_address = request.getParameter("o_receive_address");
		orderform.setO_receive_address(o_receive_address);
		String o_receive_phone = request.getParameter("o_receive_phone");
		orderform.setO_receive_phone(o_receive_phone);

		ShopCarDao shopcardao = new ShopCarDao();
		String[] og_id = g_ids.split("\\*");
		for (int i = 0; i < og_id.length; i++) {
			int g_id = Integer.parseInt(og_id[i]);
			shopcardao.deleteByGId(g_id, u_id);
		}
		
		OrderformDao dao = new OrderformDao();
		int count = dao.add(orderform);
		if (count > 0) {
			out.println("{'flag':'success'}");
		} else {
			out.println("{'flag':'error'}");
		}
		out.flush();
		out.close();
	}

}
