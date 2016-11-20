package bulletinboard.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
import bulletinboard.beans.UserComment;
import bulletinboard.beans.UserMessage;
import bulletinboard.service.CommentService;
import bulletinboard.service.MessageService;
import bulletinboard.service.UserService;



@WebServlet (urlPatterns = { "/index.jsp" })
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {


		//人が選択
		String category =request.getParameter("category");
		request.setAttribute("category", category);

		String firstTime =request.getParameter("firstTime");

		String lastTime =request.getParameter("lastTime");




		//date→String
		//nullだったら最古・最新の日数を格納
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (StringUtils.isEmpty(firstTime)){
			firstTime = sdf1.format(new MessageService().getOldestDate());
		} else {
			request.setAttribute("firstTime", firstTime);
		}
		if (StringUtils.isEmpty(lastTime)){
			lastTime = sdf1.format(new MessageService().getLatestDate());
		} else {
			request.setAttribute("lastTime", lastTime);
		}


		//ユーザー
		List<User> users = new UserService().getUser();
		request.setAttribute("users", users);

		HttpSession session = request.getSession();
        User editUsers = (User) session.getAttribute("loginUser");
        request.setAttribute("editUsers", editUsers);


		//絞込み
		List<UserMessage> messages = new MessageService().getMessage(category,firstTime,lastTime);
		request.setAttribute("messages", messages);

		//カテゴリーごとのメッセージ
		List<Message> messageCatalogs = new MessageService().getMessageCatalog();
		request.setAttribute("messageCatalogs", messageCatalogs);


		//コメント取得
		List<UserComment> comments = new CommentService().getComment();
		request.setAttribute("comments", comments);


		request.getRequestDispatcher("/top.jsp").forward(request, response);

	}

}