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


@WebServlet (urlPatterns = { "/signup" })

public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {

		//取得
		List<Branch> branches = new BranchService().getBranch();
		List<Department> departments = new DepartmentService().getDepartment();
		//jspで使えるように
		request.setAttribute("branches", branches);
		request.setAttribute("departments", departments);


		User user = new User();
		request.setAttribute("user", user);

		//jspファイルをgetする
		request.getRequestDispatcher("signup.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();

		User user = new User();
		user.setAccount(request.getParameter("account"));
		user.setName(request.getParameter("name"));
		user.setPassword(request.getParameter("password"));
		user.setBranchID(request.getParameter("branch_id"));
		user.setDepartmentID(request.getParameter("department_id"));
		user.setStopped(false);


		if (isValid(request, messages) == true) {

			new UserService().register(user);

			response.sendRedirect("setting");

		} else {
			session.setAttribute("errorMessages", messages);

			request.setAttribute("user", user);

			//取得
			List<Branch> branches = new BranchService().getBranch();
			List<Department> departments = new DepartmentService().getDepartment();
			//jspで使えるように
			request.setAttribute("branches", branches);
			request.setAttribute("departments", departments);


			request.getRequestDispatcher("signup.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String account = request.getParameter("account");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String branchId = request.getParameter("branch_id");
		String departmentId = request.getParameter("department_id");
		String confirmationPassword = request.getParameter("confirmationPassword");

		//取得
		User users = new UserService().overlap(account);



		if (StringUtils.isEmpty(account) == true) {
			messages.add("ログインIDを入力してください");
		}
		if ( users != null ) {
			messages.add("アカウントが重複しています");
		}

		if (StringUtils.isEmpty(name) == true || StringUtils.isBlank(name) == true) {
			messages.add("名前を入力してください");
		}
		if (StringUtils.isEmpty(password) == true) {
			messages.add("パスワードを入力してください");
		}
		if (StringUtils.isEmpty(confirmationPassword) == true) {
			messages.add("確認用パスワードを入力してください");
		}
		if (!password.equals(confirmationPassword)){
			messages.add("確認用パスワードが間違っています");
		}

		if (StringUtils.isEmpty(branchId) == true ){
			messages.add("支店を選択してください");
		}

		if (StringUtils.isEmpty(departmentId) == true){
			messages.add("部署・役職を選択してください");
		}

		if (!account.matches("^[a-zA-Z0-9]{6,20}$") && (StringUtils.isEmpty(account) == false)){
			messages.add("ログインIDは[a-zA-Z0-9]の6文字以上20文字以下で入力してください");
		}

		if (!password.matches("^[a-zA-Z0-9!-/:-@¥\\[-`{-~]+$") && (StringUtils.isEmpty(password) == false)){
			if (password.length() < 6 && password.length() > 255){
				messages.add("パスワードは記号を含む半角文字の6文字以上255文字以下で入力してください");
			}
			messages.add("パスワードは記号を含む半角文字で入力してください");
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

