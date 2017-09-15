package com.yiyao.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.yiyao.dao.UserAddressDao;
import com.yiyao.pojo.UserAddress;

/**
 * Servlet implementation class SelectReceiveAddressServlet
 */
public class SelectReceiveAddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectReceiveAddressServlet() {
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

		String sa_id = request.getParameter("a_id");
		int a_id=Integer.parseInt(sa_id);
		
		UserAddressDao dao = new UserAddressDao();
		UserAddress useraddress=dao.findByAid(a_id);

		String json = "";
		if (useraddress == null) {
			json = "{'a_id':0}";
		} else {
			Gson gson = new Gson();
			json = gson.toJson(useraddress, UserAddress.class);
		}
		out.write(json);
		out.flush();
		out.close();
	}

}
