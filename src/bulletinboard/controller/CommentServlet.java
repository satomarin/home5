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

import bulletinboard.beans.Comment;
import bulletinboard.beans.User;
import bulletinboard.service.CommentService;

@WebServlet(urlPatterns = { "/comment" })
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {

		Comment comment = new Comment();
		request.setAttribute("comment", comment);

		request.getRequestDispatcher("/top.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();

		List<String> comments = new ArrayList<String>();

		User user = (User) session.getAttribute("loginUser");

		Comment comment = new Comment();
		comment.setUserId(user.getId());
		comment.setMessageId(Integer.parseInt(request.getParameter("messageId")));
		comment.setText(request.getParameter("text"));

		if (isValid(request, comments) == true) {

			new CommentService().register(comment);

			response.sendRedirect("./");

		} else {
			session.setAttribute("errorMessages", comments);
			request.setAttribute("comment",comment);

			response.sendRedirect("./");
		}

	}

	private boolean isValid(HttpServletRequest request, List<String> comments) {

		String text = request.getParameter("text");

		if (StringUtils.isEmpty(text) == true) {
			comments.add("コメントを入力してください");
		}
		if (500 < text.length()) {
			comments.add("コメントは500文字以下で入力してください");
		}
		if (comments.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
