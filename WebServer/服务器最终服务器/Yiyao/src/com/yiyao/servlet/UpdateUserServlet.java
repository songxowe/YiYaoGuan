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
 * Servlet implementation class UpdateUserServlet
 */
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
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
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		String u_image_url = request.getParameter("u_image_url");
		String u_password = request.getParameter("u_password");
		String u_backgroundimageurl = request.getParameter("u_backgroundimageurl");
		String u_nickname = request.getParameter("u_nickname");
		String u_username = request.getParameter("u_username");
		String u_sex = request.getParameter("u_sex");
		String u_age = request.getParameter("u_age");
		String u_address = request.getParameter("u_address");
		String u_mobile = request.getParameter("u_mobile");
		String u_id = request.getParameter("u_id");

		User user = new User();
		user.setU_id(Integer.parseInt(u_id));
		if (u_image_url != null && !u_image_url.equals("")) {
			user.setU_imageurl(u_image_url);
		}
		if (u_password != null && !u_password.equals("")) {
			user.setU_password(u_password);
		}
		if (u_backgroundimageurl != null && !u_backgroundimageurl.equals("")) {
			user.setU_backgroundimageurl(u_backgroundimageurl);;
		}
		if (u_nickname != null && !u_nickname.equals("")) {
			user.setU_nickname(u_nickname);
		}
		if (u_username != null && !u_username.equals("")) {
			user.setU_username(u_username);
		}
		if (u_sex != null && !u_sex.equals("")) {
			user.setU_sex(u_sex);
		}
		if (u_age != null && !u_age.equals("")) {
			user.setU_age(Integer.parseInt(u_age));
		}
		if (u_address != null && !u_address.equals("")) {
			user.setU_address(u_address);
		}
		if (u_mobile != null && !u_mobile.equals("")) {
			user.setU_mobile(u_mobile);
		}
		
		UserDao userDao = new UserDao();
		int count = userDao.update(user);
		if (count > 0) {
			out.println("{'flag':'success'}");
		} else {
			out.println("{'flag':'error'}");
		}
		out.flush();
		out.close();
	}
}
