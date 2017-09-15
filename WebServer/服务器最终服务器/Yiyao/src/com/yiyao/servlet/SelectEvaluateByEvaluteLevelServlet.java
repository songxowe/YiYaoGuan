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
import com.yiyao.dao.EvaluateDao;
import com.yiyao.pojo.Evaluate;

/**
 * Servlet implementation class SelectEvaluateByEvaluteLevelServlet
 */
public class SelectEvaluateByEvaluteLevelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Gson gson;
	private Type listType;

	@Override
	public void init() throws ServletException {
		super.init();

		gson = new Gson();
		listType = new TypeToken<List<Evaluate>>() {
		}.getType();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectEvaluateByEvaluteLevelServlet() {
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
		
		String sevaluate_level = request.getParameter("evaluate_level");
		String sg_id = request.getParameter("g_id");
		int g_id=Integer.parseInt(sg_id);
		int evaluate_level=Integer.parseInt(sevaluate_level);
		
		EvaluateDao dao=new EvaluateDao();
		String json = "";
		List list = dao.findByEvaluateLevel(g_id, evaluate_level);
		if (list!= null&&list.size()!=0) {
			json += gson.toJson(list, listType);
		} else {
			json += "{'e_id':0}";
		}
		out.write(json);
		out.flush();
		out.close();
	}

}
