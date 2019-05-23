package controller;

import java.sql.*;
import java.util.TimeZone;

/*
 * Author: Anqi Li
 * Data: 3/6/2019
 * Title: Assignment2 Multithreaded Client/Server - Student Database
 * 
 * Description: JDBC connects and disconnects to MySQL database
 */

public class JdbcConnection {
	// The name of the MySQL account to use
	private final String userName = "root";

	// The password for the MySQL account 
	private final String password = "";

	// The name of the computer running MySQL 
	private final String serverName = "localhost";

	// The port of the MySQL server (default is 3306) 
	private final int portNumber = 3306;

	// The name of database
	private final String dbName = "assign2";

	// Test to connect Database 
/**
	public static void main(String[] args) {
		JdbcConnection j = new JdbcConnection();
		j.connectDB();
	}
*/

	// Connect to the MySQL Database 
	public Connection connectDB() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://" + this.serverName + ":" + this.portNumber + "/"
					+ this.dbName
					+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone="
					+ TimeZone.getDefault().getID(), this.userName, this.password);
			System.out.println("Connect to the database successfully");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			System.out.println(e);
		}
		return conn;
	}

	// Close all resources 
	public static void closeAll(Statement stmt, ResultSet rs, Connection conn) {
		closeResultSet(rs);
		closepst(stmt);
		closeCoon(conn);
	}

	// Close statement 
	public static void closepst(Statement stmt) {
		try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Close result set 
	public static void closeResultSet(ResultSet res) {
		if (res != null) {
			try {
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// Close connection of database 
	public static void closeCoon(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}
}
