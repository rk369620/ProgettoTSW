package model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionPool;

public class ProductDao {

    // Metodo per ottenere tutti i prodotti
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
                product.setPrice(resultSet.getDouble("price"));
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

    // Metodo per ottenere i prodotti in base alla categoria
    public List<Product> getProductsByCategory(String category) {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM product WHERE category = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, category);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setBrand(resultSet.getString("brand"));
                product.setPrice(resultSet.getDouble("price"));
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


    public Product getProductById(int productId) {
        Product product = null;
        String query = "SELECT * FROM product WHERE id = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setBrand(resultSet.getString("brand"));
                product.setPrice(resultSet.getDouble("price"));
                product.setAvailability(resultSet.getString("availability"));
                product.setCategory(resultSet.getString("category"));
                product.setImage(resultSet.getString("image"));
                product.setQuantity(resultSet.getInt("quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    // Metodo per aggiungere un nuovo prodotto
    public boolean addProduct(Product product) {
        String query = "INSERT INTO product(name, brand, price, availability, category, image, quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setString(2, product.getBrand());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setString(4, product.getAvailability());
            preparedStatement.setString(5, product.getCategory());
            preparedStatement.setString(6, product.getImage());
            preparedStatement.setInt(7, product.getQuantity());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Metodo per aggiornare un prodotto esistente
    public boolean updateProduct(Product product) {
        String query = "UPDATE product SET name = ?, brand = ?, price = ?, availability = ?, category = ?, image = ?, quantity = ? WHERE id = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setString(2, product.getBrand());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setString(4, product.getAvailability());
            preparedStatement.setString(5, product.getCategory());
            preparedStatement.setString(6, product.getImage());
            preparedStatement.setInt(7, product.getQuantity());
            preparedStatement.setInt(8, product.getProductId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Metodo per eliminare un prodotto
    public boolean deleteProduct(int productId) {
        String query = "DELETE FROM product WHERE id = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, productId);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
