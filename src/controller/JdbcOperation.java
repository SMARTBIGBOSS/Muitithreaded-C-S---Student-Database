package controller;

import java.sql.*;

import model.Student;

/*
 * Author: Anqi Li
 * Data: 3/6/2019
 * Title: Assignment2 Multithreaded Client/Server - Student Database
 * 
 * Description: Retrieve students from the database
 */

public class JdbcOperation {
	// Test the search function 
/**
	public static void main(String[] args) {
		JdbcOperation j = new JdbcOperation();
		Student s = j.searchStudent("123");
		System.out.println(s.equals(null));
		System.out.println(s.getSname());
	}
 */

	// Search a student from database 
	public Student searchStudent(String stud_id) {
		Connection conn = null;
		Statement stmt = null;
		String query = "SELECT * FROM myStudents WHERE STUD_ID = '" + stud_id + "'";
		Student student = new Student();
		JdbcConnection db = new JdbcConnection();

		try {
			conn = db.connectDB();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			if (rs.next()) {
				student.setSid(rs.getString("SID"));
				student.setStud_id(rs.getString("STUD_ID"));
				student.setFname(rs.getString("FNAME"));
				student.setSname(rs.getString("SNAME"));
				System.out.println("Find a student");
			} else {
				System.out.println("Student not found");
			}
			// Close all resource 
			JdbcConnection.closeAll(stmt, rs, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return student;
	}
}
