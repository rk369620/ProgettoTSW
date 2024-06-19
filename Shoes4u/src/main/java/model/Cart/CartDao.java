package model.Cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionPool;

public class CartDao {

    public void addToCart(CartItem cartItem, int cartId) {
        try (Connection conn = ConnectionPool.getConnection()) {
            String sql = "INSERT INTO cart_item (cartId, productId, quantity) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cartId);
            ps.setInt(2, cartItem.getProductId());
            ps.setInt(3, cartItem.getQuantity());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<CartItem> getCartItems(int userId) {
        List<CartItem> cartItems = new ArrayList<>();
        try (Connection conn = ConnectionPool.getConnection()) {
            String sql = "SELECT ci.*, p.name, p.price, p.image FROM cart_item ci "
                       + "JOIN cart c ON ci.cartId = c.id "
                       + "JOIN product p ON ci.productId = p.id "
                       + "WHERE c.userId = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setId(rs.getInt("id"));
                cartItem.setCartId(rs.getInt("cartId"));
                cartItem.setProductId(rs.getInt("productId"));
                cartItem.setQuantity(rs.getInt("quantity"));
                cartItem.setProductName(rs.getString("name"));
                cartItem.setProductPrice(rs.getBigDecimal("price"));
                cartItem.setProductImage(rs.getString("image"));
                cartItems.add(cartItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    public int createCart(int userId) {
        int cartId = -1;
        try (Connection conn = ConnectionPool.getConnection()) {
            String sql = "INSERT INTO cart (userId) VALUES (?)";
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                cartId = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartId;
    }
}
