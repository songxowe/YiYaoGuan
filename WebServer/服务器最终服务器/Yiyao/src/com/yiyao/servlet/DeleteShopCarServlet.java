package com.yiyao.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yiyao.dao.ShopCarDao;

/**
 * Servlet implementation class DeleteShopCarServlet
 */
public class DeleteShopCarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteShopCarServlet() {
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
		
		String ss_id = request.getParameter("s_id");
		String[] ss_id_list=ss_id.split("\\*");
		
		ShopCarDao dao=new ShopCarDao();
		int count =0;
		for(int i=0;i<ss_id_list.length;i++){
			int s_id=Integer.parseInt(ss_id_list[i]);
			count+=dao.delete(s_id);
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
