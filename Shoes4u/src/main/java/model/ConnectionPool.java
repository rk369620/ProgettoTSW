package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool{
	
	private static final String URL="jdbc:mysql://localhost:3306/tswproject1";
	private static final String Username="root";
	private static final String Password="123456";

	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(URL,Username,Password);
		
	}

}
