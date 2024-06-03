package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM products";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("productId"));
                product.setProductName(resultSet.getString("productName"));
                product.setBrand(resultSet.getString("brand"));
                product.setPrice(resultSet.getDouble("price"));
                product.setAvailability(resultSet.getString("availability"));
                product.setCategory(resultSet.getString("category"));
                product.setUserType(resultSet.getString("userType"));
                product.setImage(resultSet.getString("image")); // Assuming the column name for image path is "image"
                productList.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving products: " + e.getMessage());
        }
        return productList;
    }
    
    
    public List<Product> getProductsByCategory(String category) {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM products WHERE category = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, category);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Product product = new Product();
                    product.setProductId(resultSet.getInt("productId"));
                    product.setProductName(resultSet.getString("productName"));
                    product.setBrand(resultSet.getString("brand"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setAvailability(resultSet.getString("availability"));
                    product.setCategory(resultSet.getString("category"));
                    product.setUserType(resultSet.getString("userType"));
                    productList.add(product);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving products: " + e.getMessage());
        }
        return productList;
    }


    public Product getProductById(int id) {
        Product product = null;
        String query = "SELECT * FROM products WHERE productId = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    product = new Product();
                    product.setProductId(resultSet.getInt("productId"));
                    product.setProductName(resultSet.getString("productName"));
                    product.setBrand(resultSet.getString("brand"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setAvailability(resultSet.getString("availability"));
                    product.setCategory(resultSet.getString("category"));
                    product.setUserType(resultSet.getString("userType"));
                    product.setImage(resultSet.getString("image")); // Assuming the column name for image path is "image"
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public boolean addProduct(Product product) {
        String query = "INSERT INTO products (productName, brand, price, availability, category, userType, image) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setString(2, product.getBrand());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setString(4, product.getAvailability());
            preparedStatement.setString(5, product.getCategory());
            preparedStatement.setString(6, product.getUserType());
            preparedStatement.setString(7, product.getImage());

            int rows = preparedStatement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProduct(Product product) {
        String query = "UPDATE products SET productName = ?, brand = ?, price = ?, availability = ?, category = ?, userType = ?, image = ? WHERE productId = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setString(2, product.getBrand());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setString(4, product.getAvailability());
            preparedStatement.setString(5, product.getCategory());
            preparedStatement.setString(6, product.getUserType());
            preparedStatement.setString(7, product.getImage());
            preparedStatement.setInt(8, product.getProductId());

            int rows = preparedStatement.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProduct(int id) {
        String query = "DELETE FROM products WHERE productId = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            int rows = preparedStatement.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
