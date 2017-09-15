package com.yiyao.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yiyao.dao.EvaluateDao;
import com.yiyao.dao.GoodsDao;
import com.yiyao.pojo.Evaluate;
import com.yiyao.util.Empty;

/**
 * Servlet implementation class AddEvaluateServlet
 */
public class AddEvaluateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddEvaluateServlet() {
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
		
		String sg_id = request.getParameter("g_id");
		String su_id=request.getParameter("u_id");
		String evaluate_content = request.getParameter("evaluate_content");
		String evaluate_date=request.getParameter("evaluate_date");
		String sevaluate_level = request.getParameter("evaluate_level");
		
		int g_id=Integer.parseInt(sg_id);
		int u_id=Integer.parseInt(su_id);
		int evaluate_level=Integer.parseInt(sevaluate_level);
		
		Evaluate evaluate=new Evaluate();
		EvaluateDao edao=new EvaluateDao();
		GoodsDao gdao=new GoodsDao();
		int count =0;
		
		if(Empty.isEmpty(evaluate_content)){
			count=gdao.UpdateGoodsEvaluate(g_id, evaluate_level);
		}else{
			evaluate.setG_id(g_id);
			evaluate.setU_id(u_id);
			evaluate.setEvaluate_content(evaluate_content);
			evaluate.setEvaluate_date(evaluate_date);
			evaluate.setEvaluate_level(evaluate_level);
			
			edao.add(evaluate);
			count=gdao.UpdateGoodsEvaluate(g_id, evaluate_level);
		}
		if (count > 0) {
			out.println("{'flag':'success'}");
		} else {
			out.println("{'flag':'error'}");
		}
		out.flush();
		out.close();
	}

}
