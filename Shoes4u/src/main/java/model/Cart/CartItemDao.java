package model.Cart;

import model.ConnectionPool;
import model.Cart.CartItem;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartItemDao {
    
    // Method to retrieve a cart item by its ID
    public CartItem getCartItemById(int cartItemId) throws SQLException {
        CartItem cartItem = null;
        String query = "SELECT * FROM cart_item WHERE id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cartItemId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cartItem = new CartItem();
                cartItem.setId(rs.getInt("id"));
                cartItem.setCartId(rs.getInt("cartId"));
                cartItem.setProductId(rs.getInt("productId"));
                cartItem.setQuantity(rs.getInt("quantity"));
            }
        }
        return cartItem;
    }

    // Method to update the quantity of a cart item
    public void updateCartItemQuantity(int cartItemId, int quantity) throws SQLException {
        String query = "UPDATE cart_item SET quantity = ? WHERE id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, cartItemId);
            stmt.executeUpdate();
        }
    }

    // Method to delete a cart item by its ID
    public void deleteCartItem(int cartItemId) throws SQLException {
        String query = "DELETE FROM cart_item WHERE id = ?";
        try (Connection connection =ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cartItemId);
            stmt.executeUpdate();
        }
    }
}
