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
import com.yiyao.dao.CollectDao;
import com.yiyao.pojo.Collection;


/**
 * Servlet implementation class SelectAllCollectionServlet
 */
public class SelectAllCollectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson;
	private Type listType;

	@Override
	public void init() throws ServletException {
		super.init();

		gson = new Gson();
		listType = new TypeToken<List<Collection>>() {
		}.getType();
	}   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectAllCollectionServlet() {
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
		
		String su_id = request.getParameter("u_id");
		int u_id=Integer.parseInt(su_id);
		
		CollectDao dao=new CollectDao();
		String json = "";
		List list = null;
		if ((list = dao.findByUid(u_id)) != null) {
			json += gson.toJson(list, listType);
		} else {
			json += "{'c_id':0}";
		}
		out.write(json);
		out.flush();
		out.close();
	}

}
