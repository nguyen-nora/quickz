package dataaccess;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import dataaccess.PostgresConnection;

public class Score {
	public static boolean add(domain.Score score) {
		String nonQuery = String.format("INSERT INTO SCORE (USERNAME, ID_QUIZ, SCORE) VALUES('%s','%s',%d) ON CONFLICT (USERNAME, ID_QUIZ) DO UPDATE SET SCORE = %d;", score.getUsername(), score.getQuizId(), score.getScore(), score.getScore());
		return PostgresConnection.executeUpdate(nonQuery);
	}
	public static boolean delete(domain.Score score) {
		String nonQuery = String.format("DELETE FROM SCORE WHERE USERNAME LIKE '%s' AND ID_QUIZ = %s;", score.getUsername(), score.getQuizId());
		return PostgresConnection.executeUpdate(nonQuery);
	}
	public static domain.Score fetch(String username, String quizId) {
		String query = String.format("SELECT USERNAME, ID_QUIZ, SCORE FROM SCORE WHERE USERNAME LIKE '%s' AND ID_QUIZ = %s;", username, quizId);
		domain.Score score = null;
		try {
			Statement st = PostgresConnection.getConnection().createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				score = new domain.Score(rs.getString(1), rs.getString(2), rs.getInt(3));
			}
			rs.close();
			st.close();
			return score;
		} catch (SQLException e) {
	        	e.printStackTrace();
			return null;
		}
	}
}
