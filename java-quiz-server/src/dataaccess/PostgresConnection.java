package dataaccess;

import org.postgresql.jdbc.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.nio.file.Paths;
import java.nio.file.Files;

public class PostgresConnection {
	private static Connection conn = null;
	public static Connection getConnection() {
		try {
			if (conn == null || conn.isClosed()) {
				renewConnection(getDbURL());
			}
		} catch (SQLException e) {
			conn = null;
		}
		return conn;
	}
	private static void renewConnection(String dbURL) {
		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
		        Properties parameters = new Properties();
		        parameters.put("user", "postgres");
		        parameters.put("password", "javapostgres");
	
	        	conn = DriverManager.getConnection(dbURL, parameters);
		} catch (SQLException e) {
	        	e.printStackTrace();
	    	}
	}
	private static String getDbURL() {
		try {
			String dbURL = new String(Files.readAllBytes(Paths.get("dbURL.txt")));
			return dbURL.trim();
		} catch (Exception e) {
			System.out.println("The file cannot be found.");
			return null;
		}
	}
	public static ResultSet executeQuery(String query) {
		ResultSet rs = null;
		getConnection();
		try {
			//conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			//st.setFetchSize(50);
			rs = st.executeQuery(query);
			st.close();
		} catch (SQLException e) {
	        	e.printStackTrace();
	    	}
		return rs;
	}
	public static boolean executeUpdate(String nonQuery) {
		getConnection();
		try {
			Statement st = conn.createStatement();
			st.executeUpdate(nonQuery);
			st.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
