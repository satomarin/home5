package bulletinboard.dao;

import static bulletinboard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import bulletinboard.beans.User;
import bulletinboard.exception.SQLRuntimeException;

public class UserDao {

	//ログイン用
	public User getUser(Connection connection, String account, String password) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE account = ? AND password = ? ";

			ps = connection.prepareStatement(sql);
			ps.setString(1, account);
			ps.setString(2, password);


			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	//
	private List<User> toUserList(ResultSet rs) throws SQLException {

		List<User> ret = new ArrayList<User>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String account = rs.getString("account");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String branchID = rs.getString("branch_id");
				String departmentID = rs.getString("department_id");
				Boolean stopped = rs.getBoolean("stopped");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				Timestamp updateDate = rs.getTimestamp("update_date");

				User user = new User();
				user.setId(id);
				user.setAccount(account);
				user.setPassword(password);
				user.setName(name);
				user.setBranchID(branchID);
				user.setDepartmentID(departmentID);
				user.setStopped(stopped);
				user.setInsertDate(insertDate);
				user.setUpdateDate(updateDate);

				ret.add(user);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	//新規登録で打ち込んだデータをdbへ
	public void insert (Connection connection, User user) {

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO users ( ");
			sql.append(" account");
			sql.append(", password");
			sql.append(", name");
			sql.append(", branch_id");
			sql.append(", department_id");
			sql.append(", stopped");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(") VALUES (");
			sql.append(" ?"); // account
			sql.append(", ?"); // password
			sql.append(", ?"); // name
			sql.append(", ?"); // branch_id
			sql.append(", ?"); // department_id
			sql.append(", ?"); // stopped
			sql.append(", CURRENT_TIMESTAMP"); // insert_date
			sql.append(", CURRENT_TIMESTAMP"); // update_date
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getAccount());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setString(4, user.getBranchID());
			ps.setString(5, user.getDepartmentID());
			ps.setBoolean(6, user.getStopped());

			ps.executeUpdate();


		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	//全てのユーザーテーブルのデータをもらう
	public List<User> getUser(Connection connection) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			//SQL文を用意
			sql.append("SELECT * FROM users ");
			//生成
			ps = connection.prepareStatement(sql.toString());

			//実行
			ResultSet rs = ps.executeQuery();
			//listの対象の型に落としこみ
			List<User> ret = toUserList(rs);

			return ret;

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);

		} finally {
			close(ps);
		}
	}

	//編集時に選択されたユーザーIDのデータをもらう
	public User getUser(Connection connection, String editId) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE id = ?";

			ps = connection.prepareStatement(sql);
			ps.setString(1, editId);


			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);

			if (userList.isEmpty() == true) {
				return null;
			}else {
				return userList.get(0);
			}

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}

	//ユーザー情報更新
	public void update (Connection connection, User user) {

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();

			sql.append("UPDATE users SET ");
			sql.append(" account");
			sql.append(" = ? "); // account
			sql.append(", name");
			sql.append(" = ? "); // name

			if(!StringUtils.isEmpty(user.getPassword())){
				sql.append(", password");
				sql.append(" = ? "); // password
			}

			sql.append(", branch_id");
			sql.append(" = ? "); // branch_id
			sql.append(", department_id");
			sql.append(" = ? "); // department_id

			sql.append(", update_date");
			sql.append(" = CURRENT_TIMESTAMP "); // update_date

			sql.append("WHERE id = ?");

			ps = connection.prepareStatement(sql.toString());


			ps.setString(1, user.getAccount());
			ps.setString(2, user.getName());

			if(!StringUtils.isEmpty(user.getPassword())){
				ps.setString(3, user.getPassword());
				ps.setString(4, user.getBranchID());
				ps.setString(5, user.getDepartmentID());
				ps.setInt(6, user.getId());

			}else{
				ps.setString(3, user.getBranchID());
				ps.setString(4, user.getDepartmentID());
				ps.setInt(5, user.getId());

			}
			System.out.println(ps);
			ps.executeUpdate();


		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}



	//ユーザー停止情報更新
	public void stop (Connection connection, User user) {

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET ");
			sql.append(" stopped");
			sql.append(" = ? "); // stopped

			sql.append("WHERE id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setBoolean(1, user.getStopped());
			ps.setInt(2, user.getId());


			ps.executeUpdate();


		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}




	//重複
	public User overlap(Connection connection, String account) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users where account = ?";

			ps = connection.prepareStatement(sql);
			ps.setString(1, account);

			ResultSet rs = ps.executeQuery();

			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			}else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}



//	//ユーザー削除
//	public void delete(Connection connection , User user) {
//
//		PreparedStatement ps = null;
//		try {
//			StringBuilder sql = new StringBuilder();
//			//SQL文を用意
//			sql.append("DELETE FROM users WHERE id = ? ");
//			//生成
//			ps = connection.prepareStatement(sql.toString());
//			ps.setInt(1, user.getId());
//
//
//			ps.executeUpdate();
//
//		} catch (SQLException e) {
//			throw new SQLRuntimeException(e);
//
//		} finally {
//			close(ps);
//		}
//	}
}
