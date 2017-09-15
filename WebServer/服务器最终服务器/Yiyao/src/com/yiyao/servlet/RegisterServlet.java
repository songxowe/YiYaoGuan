package com.yiyao.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yiyao.dao.UserDao;
import com.yiyao.pojo.User;
/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
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
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		String u_mobile = request.getParameter("mobile");
		String password = request.getParameter("password");

		User user = new User();
		user.setU_mobile(u_mobile);
		user.setU_password(password);

		UserDao userDao = new UserDao();

		User checkUser = userDao.find(user.getU_mobile());
		if (checkUser == null) {
			int count = userDao.add(user);
			if (count > 0) {
				out.println("{'flag':'success'}");
			} else {
				out.println("{'flag':'error'}");
			}
		} else {
			out.println("{'flag':'exist'}");
		}
		out.flush();
		out.close();
	}
}
