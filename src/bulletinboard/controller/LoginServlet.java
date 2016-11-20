package bulletinboard.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bulletinboard.beans.User;
import bulletinboard.service.LoginService;


@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(javax.servlet.http.HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


		request.getRequestDispatcher("login.jsp").forward(request, response);
	}



	@Override
	protected void doPost(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException, ServletException {
		String account = request.getParameter("account");
		String password = request.getParameter("password");

		LoginService loginService = new LoginService();
		User user = loginService.login(account, password);

		HttpSession session = request.getSession();


		if ((user != null) && (user.getStopped() == false)){

			session.setAttribute("loginUser", user);
			response.sendRedirect("./");

		}else{

			List<String> messages = new ArrayList<String>();
			messages.add("ログインに失敗しました。");
			session.setAttribute("errorMessages", messages);

			//保持
			session.setAttribute("account", account);

			response.sendRedirect("login");

		}

	}

}
