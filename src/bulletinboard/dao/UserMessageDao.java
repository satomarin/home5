package bulletinboard.dao;

import static bulletinboard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import bulletinboard.beans.UserMessage;
import bulletinboard.exception.SQLRuntimeException;

public class UserMessageDao {

	public List<UserMessage> getUserMessageCategories(Connection connection, int num, String category, String firstTime, String lastTime) {

		PreparedStatement ps = null;

		try {
			StringBuilder sql = new StringBuilder();

			sql.append("SELECT * FROM user_message WHERE insert_date BETWEEN ? AND ? ");

			if(!StringUtils.isEmpty(category)){
				sql.append("AND category = ? ");
			}

			sql.append("ORDER BY insert_date DESC limit " + num );

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, firstTime + " 00:00:00");
			ps.setString(2, lastTime + " 23:59:59");

			if(!StringUtils.isEmpty(category)){
				ps.setString(3, category);
			}


			ResultSet rs = ps.executeQuery();
			List<UserMessage> ret = toUserMessageList(rs);
			return ret;

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	//最古の日付
	public Date getOldestDate(Connection connection) {

		PreparedStatement ps = null;

		try {
			StringBuilder sql = new StringBuilder();

			sql.append("SELECT * FROM user_message order by insert_date limit 1;");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			List<UserMessage> userMessage = toUserMessageList(rs);

			if (userMessage.isEmpty() == true) {
				return null;
			}else {
				return userMessage.get(0).getInsertDate();
			}

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	//最新の日付
	public Date getLatestDate(Connection connection) {

		PreparedStatement ps = null;

		try {
			StringBuilder sql = new StringBuilder();

			sql.append("SELECT * FROM user_message order by insert_date DESC LIMIT 1;");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			List<UserMessage> userMessage = toUserMessageList(rs);

			if (userMessage.isEmpty() == true) {
				return null;
			}else {
				return userMessage.get(0).getInsertDate();
			}

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}


	private List<UserMessage> toUserMessageList(ResultSet rs)throws SQLException {

		List<UserMessage> ret = new ArrayList<UserMessage>();
		try {
			while (rs.next()) {

				String account = rs.getString("account");
				String name = rs.getString("name");
				int messageId = rs.getInt("message_id");
				int userId = rs.getInt("user_id");
				int userBranchId = rs.getInt("user_branchId");
				String title = rs.getString("title");
				String category = rs.getString("category");
				String text = rs.getString("text");
				Timestamp insertDate = rs.getTimestamp("insert_date");

				UserMessage message = new UserMessage();
				message.setAccount(account);
				message.setName(name);
				message.setMessageId(messageId);
				message.setUserId(userId);
				message.setUserBranchId(userBranchId);
				message.setTitle(title);
				message.setCategory(category);
				message.setText(text);
				message.setInsertDate(insertDate);

				ret.add(message);

			}
			return ret;
		} finally {
			close(rs);
		}
	}


}
