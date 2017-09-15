package com.yiyao.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yiyao.dao.UserAddressDao;
import com.yiyao.pojo.UserAddress;

/**
 * Servlet implementation class UpdateReceiveAddressServlet
 */
public class UpdateReceiveAddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateReceiveAddressServlet() {
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
		String su_id = request.getParameter("u_id");
		String a_receive_user_name = request.getParameter("a_receive_user_name");
		String a_receive_user_address = request.getParameter("a_receive_user_address");
		String a_receive_user_phone = request.getParameter("a_receive_user_phone");
		
		UserAddress useraddress=new UserAddress();
		int a_id=Integer.parseInt(sa_id);
		int u_id=Integer.parseInt(su_id);
		
		useraddress.setA_id(a_id);
		useraddress.setU_id(u_id);
		useraddress.setA_receive_user_name(a_receive_user_name);
		useraddress.setA_receive_user_address(a_receive_user_address);
		useraddress.setA_receive_user_phone(a_receive_user_phone);
		
		UserAddressDao dao=new UserAddressDao();
		int count = dao.update(useraddress);
		if (count > 0) {
			out.println("{'flag':'success'}");
		} else {
			out.println("{'flag':'error'}");
		}
		out.flush();
		out.close();
	}

}
