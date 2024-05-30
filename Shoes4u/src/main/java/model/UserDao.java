package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
	

	public boolean isValidUser(String username, String password) {
		String query="SELECT * FROM user WHERE username=? AND password=?";
			try(Connection connection =ConnectionPool.getConnection();
				PreparedStatement preparedStatement=connection.prepareStatement(query)){
				
				preparedStatement.setString(1,username);
				preparedStatement.setString(2,password);
				
				ResultSet resultSet=preparedStatement.executeQuery();
				
				return resultSet.next();
				
		
			}catch(SQLException e) {
				e.printStackTrace();
				return false;
			}
	}
	
	
		

}