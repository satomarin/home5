package bulletinboard.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulletinboard.service.MessageService;


@WebServlet (urlPatterns = { "/deleteMessage" })
public class DeleteMessageServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {

		int messageId = (Integer.parseInt(request.getParameter("id")));
		new MessageService().delete(messageId);

		response.sendRedirect("./");

	}


}
