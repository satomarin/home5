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

import bulletinboard.beans.Branch;
import bulletinboard.beans.Department;
import bulletinboard.beans.User;
import bulletinboard.service.BranchService;
import bulletinboard.service.DepartmentService;
import bulletinboard.service.UserService;


@WebServlet (urlPatterns = { "/editing" })

public class EditingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {

		//取得
		List<Branch> branches = new BranchService().getBranch();
		List<Department> departments = new DepartmentService().getDepartment();

		//jspで使えるように
		request.setAttribute("branches", branches);
		request.setAttribute("departments", departments);


		//ユーザー情報編集にてどこの編集が押されたのか、ユーザーIDをもらっている
		String editId =request.getParameter("id");


		//editID画数字かどうか、nullかどうか
		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();
		if( editId == null || (!editId.matches("[0-9]+$")) ){
			messages.add("無効なURLが入力されました");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("setting");

			return;
		}
		UserService userService = new UserService();
		User editUser = userService.editing(editId);

		//editUserが空かnullじゃなかったらok
		if(editUser == null){
			messages.add("無効なURLが入力されました");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("setting");

			return;
		}


		request.setAttribute("editUser", editUser);

		//jspファイルをgetする
		request.getRequestDispatcher("editing.jsp").forward(request, response);


	}

	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();


        User user = new User();
		user.setId(Integer.parseInt(request.getParameter("id")));
		user.setAccount(request.getParameter("account"));
		user.setName(request.getParameter("name"));
		user.setPassword(request.getParameter("password"));
		user.setBranchID(request.getParameter("branch_id"));
		user.setDepartmentID(request.getParameter("department_id"));



		if (isValid(request, messages) == true) {

			new UserService().update(user);

			response.sendRedirect("setting");

		} else {

			session.setAttribute("errorMessages", messages);


			request.setAttribute("editUser", user);

			//取得
			List<Branch> branches = new BranchService().getBranch();
			List<Department> departments = new DepartmentService().getDepartment();

			//jspで使えるように
			request.setAttribute("branches", branches);
			request.setAttribute("departments", departments);

			//jspファイルをgetする
			request.getRequestDispatcher("editing.jsp").forward(request, response);
		}
	}


	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String id = request.getParameter("id");
		String account = request.getParameter("account");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String confirmationPassword = request.getParameter("confirmationPassword");

		System.out.println(name);


		//取得
		User users = new UserService().overlap(account);


		if (StringUtils.isEmpty(account) == true) {
			messages.add("ログインIDを入力してください");
		}
		if (StringUtils.isEmpty(name) == true || StringUtils.isBlank(name) == true) {
			messages.add("名前を入力してください");
		}

		//&& users.getId() != loginUser.getId()
		if ( users != null && users.getId() != Integer.parseInt(id)) {
			messages.add("アカウントが重複しています");
		}


		if (!password.matches("^[a-zA-Z0-9!-/:-@¥\\[-`{-~]+$") && (StringUtils.isEmpty(password) == false)){
			if (password.length() < 6 && password.length() > 255){
				messages.add("パスワードは記号を含む半角文字の6文字以上255文字以下で入力してください");
			}
			messages.add("パスワードは記号を含む半角文字で入力してください");
		}
		if (!password.equals(confirmationPassword)){
			messages.add("確認用パスワードが間違っています");
		}
		if (!account.matches("^[a-zA-Z0-9]{6,20}$") && (StringUtils.isEmpty(account) == false)){
			messages.add("ログインIDは[a-zA-Z0-9]の6文字以上20文字以下で入力してください");
		}

		if (10 < name.length() && (StringUtils.isEmpty(name) == false)){
			messages.add("名前は10文字以下で入力してください");
		}


		// TODO アカウントが既に利用されていないか、メールアドレスが既に登録されていないかなどの確認も必要
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}

