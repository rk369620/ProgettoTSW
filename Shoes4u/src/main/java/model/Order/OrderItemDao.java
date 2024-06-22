package model.Order;

import model.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDao {

    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM order_item WHERE orderId = ?";
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setId(rs.getInt("id"));
                    item.setOrderId(rs.getInt("orderId"));
                    item.setProductId(rs.getInt("productId"));
                    item.setProductName(rs.getString("productName"));
                    item.setProductPrice(rs.getDouble("productPrice"));
                    item.setQuantity(rs.getInt("quantity"));
                    orderItems.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }

    public void createOrderItem(OrderItem orderItem) throws SQLException {
        String query = "INSERT INTO order_item (orderId, productId, productName, productPrice, quantity) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getProductId());
            stmt.setString(3, orderItem.getProductName());
            stmt.setDouble(4, orderItem.getProductPrice());
            stmt.setInt(5, orderItem.getQuantity());

            stmt.executeUpdate();
        }
    }
}
