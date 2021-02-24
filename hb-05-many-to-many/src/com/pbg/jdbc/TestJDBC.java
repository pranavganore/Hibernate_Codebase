package com.pbg.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJDBC {

	public static void main(String[] args) {
		 
		// Creating a basic connection to the mySQL database and testing it
		
		String jdbcURL = "jdbc:mysql://localhost:3306/hb-04-one-to-many-uni?useSSL=false";
		String user = "hbstudent";
		String pwd = "hbstudent";
		
		try {
			System.out.println("Connecting to database : " + jdbcURL);
			
			Connection myConn = DriverManager.getConnection(jdbcURL, user, pwd);
			
			System.out.println("Connection Successful!!");
			
		}catch(Exception E) {
			
		}

	}

}

/*
 * Note 1: running the sql scripts to initialize the environment for this project
 * 
 * Run the scripts (or execute the queries inside them line by line on mysql> terminal)
 * 	'01-create-user.sql' & then '01-student-tracker.sql' (attached in this project)
 * 
 */