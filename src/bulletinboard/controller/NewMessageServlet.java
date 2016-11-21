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

import org.apache.commons.lang.StringUtils;

import bulletinboard.beans.Message;
import bulletinboard.beans.User;
import bulletinboard.service.MessageService;

@WebServlet(urlPatterns = { "/newMessage" })
public class NewMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {

		Message message = new Message();
		request.setAttribute("message", message);

		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}




	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();

		List<String> messages = new ArrayList<String>();

		User user = (User) session.getAttribute("loginUser");

		Message message = new Message();

		message.setUserId(user.getId());
		message.setTitle(request.getParameter("title"));
		message.setCategory(request.getParameter("category"));
		message.setText(request.getParameter("text"));

		if (isValid(request, messages) == true) {

			new MessageService().register(message);

			response.sendRedirect("./");

		} else {
			session.setAttribute("errorMessages", messages);

			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String title = request.getParameter("title");
		String category = request.getParameter("category");
		String text = request.getParameter("text");


		if (StringUtils.isEmpty(title) == true || StringUtils.isBlank(title) == true) {
			messages.add("件名を入力してください");
		}
		if (StringUtils.isEmpty(category) == true || StringUtils.isBlank(category) == true){
			messages.add("カテゴリーを入力してください");
		}
		if (StringUtils.isEmpty(text) == true || StringUtils.isBlank(text) == true){
			messages.add("本文を入力してください");
		}


		if (50 < title.length()){
			messages.add("件名は50文字以下で入力してください");
		}
		if (10 < category.length()){
			messages.add("カテゴリーは10文字以下で入力してください");
		}
		if (1000 < text.length()){
			messages.add("本文は1000文字以下で入力してください");
		}

		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
