package dataaccess;

import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import dataaccess.PostgresConnection;

public class QuizItem {
	public static boolean add(domain.QuizItem quizItem) {
		String nonQuery = String.format("INSERT INTO QUIZITEM (ID_QUIZITEM, ID_QUIZ, QUIZITEM) VALUES('%s','%s','%s');", quizItem.getId(), quizItem.getQuizId(), quizItem.getQuizItem().toString());
		return PostgresConnection.executeUpdate(nonQuery);
	}
	public static boolean delete(domain.QuizItem quizItem) {
		String nonQuery = String.format("DELETE FROM QUIZITEM WHERE ID_QUIZITEM LIKE '%s';", quizItem.getId());
		return PostgresConnection.executeUpdate(nonQuery);
	}
	public static domain.QuizItem fetch(String id) {
		String query = String.format("SELECT ID_QUIZITEM, ID_QUIZ, QUIZITEM FROM QUIZITEM WHERE ID_QUIZITEM = %s;", id);
		domain.QuizItem quizItem = null;
		try {
			Statement st = PostgresConnection.getConnection().createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				quizItem = new domain.QuizItem(rs.getString(1), rs.getString(2), rs.getString(3));
			}
			rs.close();
			st.close();
			return quizItem;
		} catch (SQLException e) {
	        	e.printStackTrace();
			return null;
		}
	}
	public static List<domain.QuizItem> fetchAll(String quizId) {
		String query = String.format("SELECT ID_QUIZITEM, ID_QUIZ, QUIZITEM FROM QUIZITEM WHERE ID_QUIZ = %s;", quizId);
		List<domain.QuizItem> collection = new ArrayList<domain.QuizItem>();
		try {
			Statement st = PostgresConnection.getConnection().createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				collection.add(new domain.QuizItem(rs.getString(1), rs.getString(2), rs.getString(3)));
			}
			rs.close();
			st.close();
			return collection;
		} catch (SQLException e) {
	        	e.printStackTrace();
			return null;
		}

	}
}
