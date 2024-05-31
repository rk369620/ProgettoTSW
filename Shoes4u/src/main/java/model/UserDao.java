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
	
	
	public boolean addUser(User user) {
	    String query = "INSERT INTO user(firstName, lastName, username, email, password) VALUES (?, ?, ?, ?, ?)";
	    try (Connection connection = ConnectionPool.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

	        preparedStatement.setString(1, user.getFirstName());
	        preparedStatement.setString(2, user.getLastName());
	        preparedStatement.setString(3, user.getUsername());
	        preparedStatement.setString(4, user.getEmail());
	        preparedStatement.setString(5, user.getPassword());

	        int rows = preparedStatement.executeUpdate();

	        return rows > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
		

}

