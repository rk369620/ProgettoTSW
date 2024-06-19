package model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionPool;

public class ProductDao {

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM product";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setBrand(resultSet.getString("brand"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setAvailability(resultSet.getString("availability"));
                product.setCategory(resultSet.getString("category"));
                product.setImage(resultSet.getString("image"));
                product.setQuantity(resultSet.getInt("quantity"));
                productList.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<Product> getProductsByCategory(String category) {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM product WHERE category=?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, category);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setBrand(resultSet.getString("brand"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setAvailability(resultSet.getString("availability"));
                product.setCategory(resultSet.getString("category"));
                product.setImage(resultSet.getString("image"));
                product.setQuantity(resultSet.getInt("quantity"));
                productList.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
}
