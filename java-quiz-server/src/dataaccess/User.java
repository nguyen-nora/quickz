package dataaccess;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import dataaccess.PostgresConnection;

public class User {
	public static boolean add(domain.User user) {
		String nonQuery = String.format("INSERT INTO USERS (USERNAME, PWD) VALUES('%s','%s');", user.getUsername(), user.getPwd());
		return PostgresConnection.executeUpdate(nonQuery);
	}
	public static boolean delete(domain.User user) {
		String nonQuery = String.format("DELETE FROM USERS WHERE USERNAME LIKE '%s';", user.getUsername());
		return PostgresConnection.executeUpdate(nonQuery);
	}
	public static domain.User fetch(String username) {
		String query = String.format("SELECT USERNAME, PWD FROM USERS WHERE USERNAME LIKE '%s';", username);
		domain.User user = null;
		try {
			Statement st = PostgresConnection.getConnection().createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				user = new domain.User(rs.getString(1), rs.getString(2));
			}
			rs.close();
			st.close();
			System.out.println(user.getPwd());
			return user;
		} catch (SQLException e) {
	        	e.printStackTrace();
			return null;
		}
	}
}
