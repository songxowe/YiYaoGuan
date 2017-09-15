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
import com.yiyao.dao.GoodsDao;
import com.yiyao.pojo.Goods;
import com.yiyao.util.Empty;
import com.yiyao.util.Pager;

/**
 * Servlet implementation class SelectGoodsServlet
 */
public class SelectGoodsServlet extends HttpServlet {
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
    public SelectGoodsServlet() {
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
		// 分页参数
		String spageno = request.getParameter("pageno");
		System.out.println("spageno"+spageno);
		int pageno = 0;
		try {
			pageno = Integer.parseInt(spageno);
		} catch (NumberFormatException e) {
			pageno = 1;
		}
		//获得排序参数
		String order=request.getParameter("order");
		System.out.println("order"+order);
		
		// 其他查询需求参数(轉換為RecruitMessage對象)
		String condition = request.getParameter("condition");
		String g_cure_symptom = request.getParameter("g_cure_symptom");
		String g_classify_one = request.getParameter("g_classify_one");
		String g_classify_two = request.getParameter("g_classify_two");
		
		String g_suit_people = request.getParameter("g_suit_people");
		String g_trademark = request.getParameter("g_trademark");
		System.out.println("g_suit_people"+g_suit_people);
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
		System.out.println("goods"+goods.toString());
		// 查询并返回结果
		GoodsDao dao = new GoodsDao();
		Pager pager = new Pager();
		int totalpage = dao.getTotal(goods);
		pager.setTotalpage(totalpage);
		// 判断分页的其他情况，确认页数
		if (pageno < 1) {
			pageno = 1;
		} else if (pageno > totalpage) {
			pageno = totalpage;
		}
		// 按页数查询，并返回结果
		String json = "";
		List list = null;
		
		if(order.equals("price")){
			System.out.println("one");
			list=dao.findOrderPrice(goods, pageno);
		}else if(order.equals("discount")){
			System.out.println("one");
			list=dao.findOrderDiscount(goods, pageno);
		}
		if (list!= null) {
			pager.setList(list);
			json += gson.toJson(list,listType);
		} else {
			json += "{'g_id':0}";
		}
		out.write(json);
		out.flush();
		out.close();
	}

}
