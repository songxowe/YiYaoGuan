package com.yiyao.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yiyao.dao.GoodsDao;
import com.yiyao.pojo.Goods;
import com.yiyao.util.Empty;
import com.yiyao.util.Pager;

/**
 * Servlet implementation class SelectGoodsTotalServlet
 */
public class SelectGoodsTotalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectGoodsTotalServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		// 其他查询需求参数(轉換為RecruitMessage對象)
		String condition = request.getParameter("condition");
		String g_cure_symptom = request.getParameter("g_cure_symptom");
		String g_classify_one = request.getParameter("g_classify_one");
		String g_classify_two = request.getParameter("g_classify_two");
		String g_suit_people = request.getParameter("g_suit_people");
		String g_trademark = request.getParameter("g_trademark");

		Goods goods = new Goods();
	
		if (!Empty.isEmpty(condition)) {
			goods.setCondition(condition.trim());
		}
		if (Empty.isEmpty(condition)&&!Empty.isEmpty(g_cure_symptom)) {
			goods.setG_cure_symptom(g_cure_symptom.trim());
		}
		if (Empty.isEmpty(condition)&&!Empty.isEmpty(g_classify_one)) {
			goods.setG_classify_one(g_classify_one.trim());
		}
		if (Empty.isEmpty(condition)&&!Empty.isEmpty(g_classify_two)) {
			goods.setG_classify_two(g_classify_two.trim());
		}
		if (Empty.isEmpty(condition)&&!Empty.isEmpty(g_suit_people)) {
			goods.setG_suit_people(g_suit_people.trim());
		}
		if (Empty.isEmpty(condition)&&!Empty.isEmpty(g_trademark)) {
			goods.setG_trademark(g_trademark.trim());
		}
		// 查询并返回结果
		GoodsDao dao = new GoodsDao();
		int totalpage = dao.getTotal(goods);
		String json="{'totalpage':"+totalpage+"}";
		out.write(json);
		out.flush();
		out.close();
	}

}
