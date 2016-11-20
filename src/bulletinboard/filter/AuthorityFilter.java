package bulletinboard.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bulletinboard.beans.User;

@WebFilter("/*")
public class AuthorityFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


		HttpSession session = ((HttpServletRequest) request).getSession();
		User user = (User) session.getAttribute("loginUser");


		if( user != null ){
			String branchId = user.getBranchID();
			String departmentId = user.getDepartmentID();

			StringBuffer requestUrl = ((HttpServletRequest) request).getRequestURL();
			String requestUrlStr = requestUrl.toString();


			System.out.println("管理者フィルターを開始");

			if( !branchId.equals("1") || !departmentId.equals("1") ){
					if( requestUrlStr.matches(".*editing.*") || requestUrlStr.matches(".*setting.*") || requestUrlStr.matches(".*signup.*")  ){
						((HttpServletResponse)response).sendRedirect("./");
						return ;
					}
			}

		}

		System.out.println("管理者フィルターを終了");

		chain.doFilter(request, response); // サーブレットを実行



	}

	@Override
	public void init(FilterConfig config) {

	}

	@Override
	public void destroy() {
	}

}
