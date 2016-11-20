package bulletinboard.controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulletinboard.beans.Branch;
import bulletinboard.beans.Department;
import bulletinboard.beans.User;
import bulletinboard.service.BranchService;
import bulletinboard.service.DepartmentService;
import bulletinboard.service.UserService;


@WebServlet (urlPatterns = { "/setting" })

public class SettingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {




		//取得
		List<User> users = new UserService().getUser();
		List<Branch> branches = new BranchService().getBranch();
		List<Department> departments = new DepartmentService().getDepartment();

		//jspで使えるように
		request.setAttribute("users", users);
		request.setAttribute("branches", branches);
		request.setAttribute("departments", departments);

		//jspファイルをgetする
		request.getRequestDispatcher("setting.jsp").forward(request, response);
	}


	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {


		User user = new User();
		user.setId(Integer.parseInt(request.getParameter("id")));
		user.setStopped(Boolean.parseBoolean(request.getParameter("stopped")));

		new UserService().stop(user);

		response.sendRedirect("setting");


	}

}

