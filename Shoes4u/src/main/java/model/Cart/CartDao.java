package model.Cart;

import model.ConnectionPool;
import model.Product.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDao {

    public Cart getCartByUserId(int userId) throws SQLException {
        Cart cart = null;
        String query = "SELECT * FROM cart WHERE userId = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cart = new Cart();
                cart.setId(rs.getInt("id"));
                cart.setUserId(rs.getInt("userId"));
            }
        }
        if (cart != null) {
            List<CartItem> cartItems = getCartItemsByCartId(cart.getId());
            cart.setCartItems(cartItems);
        }
        return cart;
    }

    public void createCart(int userId) throws SQLException {
        String query = "INSERT INTO cart (userId) VALUES (?)";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    public void addToCart(int cartId, int productId,int quantity) throws SQLException {
        String query = "INSERT INTO cart_item (cartId, productId,quantity) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cartId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);

          
            stmt.executeUpdate();
        }
    }

    public void updateCartItemQuantity(int cartItemId, int quantity) throws SQLException {
        String query = "UPDATE cart_item SET quantity = ? WHERE id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, cartItemId);
            stmt.executeUpdate();
        }
    }

    public void removeFromCart(int cartItemId) throws SQLException {
        String query = "DELETE FROM cart_item WHERE id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cartItemId);
            stmt.executeUpdate();
        }
    }

    public List<CartItem> getCartItemsByCartId(int cartId) throws SQLException {
        List<CartItem> cartItems = new ArrayList<>();
        String query = "SELECT * FROM cart_item WHERE cartId = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cartId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setId(rs.getInt("id"));
                cartItem.setCartId(rs.getInt("cartId"));
                cartItem.setProductId(rs.getInt("productId"));
                cartItem.setQuantity(rs.getInt("quantity"));
                cartItems.add(cartItem);
            }
        }
        return cartItems;
    }

    public double getTotalPrice(int userId) throws SQLException {
        double totalPrice = 0.0;
        String query = "SELECT SUM(ci.quantity * p.price) AS total " +
                       "FROM cart_item ci " +
                       "JOIN product p ON ci.productId = p.id " +  // Corretto da p.productId a p.id, se l'id del prodotto è 'id'
                       "JOIN cart c ON ci.cartId = c.id " +
                       "WHERE c.userId = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    totalPrice = rs.getDouble("total");  // Ottieni il valore double direttamente
                }
            }
        }
        return totalPrice;
    }

    public void clearCart(int userId) throws SQLException {
        String query = "DELETE FROM cart_item WHERE cartId IN (SELECT id FROM cart WHERE userId = ?)";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    public List<CartItem> getAllCartItems(int userId) {
        List<CartItem> cartItems = new ArrayList<>();
        String query = "SELECT ci.id, ci.cartId, ci.productId, ci.quantity, p.productName, p.price " +
                       "FROM cart_item ci " +
                       "JOIN product p ON ci.productId = p.productId " +
                       "WHERE ci.cartId = (SELECT id FROM cart WHERE userId = ?)";
        
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setId(rs.getInt("id"));
                cartItem.setCartId(rs.getInt("cartId"));
                cartItem.setProductId(rs.getInt("productId"));
                cartItem.setQuantity(rs.getInt("quantity"));

                Product product = new Product();
                product.setProductId(rs.getInt("productId"));
                product.setProductName(rs.getString("productName"));
                product.setPrice(rs.getDouble("price"));

                cartItem.setProduct(product);
                cartItems.add(cartItem);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
        return cartItems;
    }

    public void removeItem(int userId, int cartItemId) throws SQLException {
        String sql = "DELETE FROM cart WHERE userId = ? AND id = ?";

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, cartItemId);
            stmt.executeUpdate();
        }
    }

	
}