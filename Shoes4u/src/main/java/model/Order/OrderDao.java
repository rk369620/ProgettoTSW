package model.Order;

import model.ConnectionPool;
import model.Cart.CartItem;
import model.Product.Product;
import model.User.User;
import model.User.UserDao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    public int saveOrder(int userId, List<CartItem> cartItems) {
        int orderId = -1;
        BigDecimal totalPrice = BigDecimal.ZERO;
        try (Connection conn = ConnectionPool.getConnection()) {
            conn.setAutoCommit(false);

            // Calculate total price
            for (CartItem cartItem : cartItems) {
                BigDecimal productPrice = cartItem.getProductPrice();
                BigDecimal quantity = BigDecimal.valueOf(cartItem.getQuantity());
                totalPrice = totalPrice.add(productPrice.multiply(quantity));
            }

            // Insert order
            String sqlOrder = "INSERT INTO `order` (userId, totalPrice) VALUES (?, ?)";
            PreparedStatement psOrder = conn.prepareStatement(sqlOrder, PreparedStatement.RETURN_GENERATED_KEYS);
            psOrder.setInt(1, userId);
            psOrder.setBigDecimal(2, totalPrice);
            psOrder.executeUpdate();

            ResultSet rsOrder = psOrder.getGeneratedKeys();
            if (rsOrder.next()) {
                orderId = rsOrder.getInt(1);
            }

            // Insert order items
            String sqlOrderItem = "INSERT INTO order_item (orderId, productId, productName, productPrice, quantity) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement psOrderItem = conn.prepareStatement(sqlOrderItem);
            for (CartItem cartItem : cartItems) {
                psOrderItem.setInt(1, orderId);
                psOrderItem.setInt(2, cartItem.getProductId());
                psOrderItem.setString(3, cartItem.getProductName());
                psOrderItem.setBigDecimal(4, cartItem.getProductPrice());
                psOrderItem.setInt(5, cartItem.getQuantity());
                psOrderItem.addBatch();
            }
            psOrderItem.executeBatch();

            // Commit transaction
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderId;
    }
}
