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

		User user = (User) request.getSession().getAttribute("loginUser");

		boolean isShowMessageForm;
		if (user != null) {
			isShowMessageForm = true;
		} else {
			isShowMessageForm = false;
		}


		request.setAttribute("isShowMessageForm", isShowMessageForm);
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}




	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();

		List<String> messages = new ArrayList<String>();

		String title = request.getParameter("title");
		String category = request.getParameter("category");
		String newmessage = request.getParameter("message");

		if (isValid(request, messages) == true) {

			User user = (User) session.getAttribute("loginUser");

			Message message = new Message();

			message.setUserId(user.getId());
			message.setText(request.getParameter("message"));
			message.setTitle(request.getParameter("title"));
			message.setCategory(request.getParameter("category"));

			new MessageService().register(message);

			response.sendRedirect("./");
		} else {
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("newMessage");

			session.setAttribute("category", category);
			session.setAttribute("title", title);
			session.setAttribute("message", newmessage);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String title = request.getParameter("title");
		String category = request.getParameter("category");
		String message = request.getParameter("message");


		if (StringUtils.isEmpty(title) == true) {
			messages.add("タイトルを入力してください");
		}
		if (StringUtils.isEmpty(category) == true){
			messages.add("カテゴリーを入力してください");
		}
		if (StringUtils.isEmpty(message)){
			messages.add("メッセージを入力してください");
		}


		if (50 < title.length()){
			messages.add("タイトルは50文字以下で入力してください");
		}
		if (10 < category.length()){
			messages.add("カテゴリーは10文字以下で入力してください");
		}
		if (1000 < message.length()){
			messages.add("本文は1000文字以下で入力してください");
		}

		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
