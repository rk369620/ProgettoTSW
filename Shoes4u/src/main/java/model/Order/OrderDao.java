package model.Order;

import model.ConnectionPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    public int placeOrder(Order order) throws SQLException {
        int orderId = -1;
        String insertOrderQuery = "INSERT INTO `order` (userId, orderDate, totalPrice) VALUES (?, ?, ?)";
        String insertOrderItemQuery = "INSERT INTO order_item (orderId, productId, productName, productPrice, quantity) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmtOrder = connection.prepareStatement(insertOrderQuery, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtOrderItem = connection.prepareStatement(insertOrderItemQuery)) {

            // Insert into `order` table
            stmtOrder.setInt(1, order.getUserId());
            stmtOrder.setTimestamp(2, order.getOrderDate());
            stmtOrder.setDouble(3, order.getTotalPrice());
            stmtOrder.executeUpdate();

            ResultSet generatedKeys = stmtOrder.getGeneratedKeys();
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
                order.setId(orderId);  // Set the orderId in the Order object
            }

            // Insert into order_item table
            for (OrderItem orderItem : order.getOrderItems()) {
                stmtOrderItem.setInt(1, orderId);
                stmtOrderItem.setInt(2, orderItem.getProductId());
                stmtOrderItem.setString(3, orderItem.getProductName());
                stmtOrderItem.setDouble(4, orderItem.getProductPrice());
                stmtOrderItem.setInt(5, orderItem.getQuantity());
                stmtOrderItem.executeUpdate();
            }
        }
        return orderId;
    }

    public Order getOrderById(int orderId) {
        Order order = null;
        String sql = "SELECT * FROM `order` WHERE id = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setUserId(rs.getInt("userId"));
                    order.setOrderDate(rs.getTimestamp("orderDate"));
                    order.setTotalPrice(rs.getDouble("totalPrice"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM order_item WHERE orderId = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setId(rs.getInt("id"));
                    orderItem.setOrderId(rs.getInt("orderId"));
                    orderItem.setProductId(rs.getInt("productId"));
                    orderItem.setProductName(rs.getString("productName"));
                    orderItem.setProductPrice(rs.getDouble("productPrice"));
                    orderItem.setQuantity(rs.getInt("quantity"));
                    orderItems.add(orderItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }



public List<Order> getOrdersByUserId(int userId) {
    List<Order> orders = new ArrayList<>();
    String sql = "SELECT * FROM `order` WHERE userId = ?";
    String sqlItems = "SELECT * FROM order_item WHERE orderId = ?";

    try (Connection conn = ConnectionPool.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, userId);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("userId"));
                order.setOrderDate(rs.getTimestamp("orderDate"));
                order.setTotalPrice(rs.getDouble("totalPrice"));
                List<OrderItem> orderItems = new ArrayList<>();
                try (PreparedStatement stmtItems = conn.prepareStatement(sqlItems)) {
                    stmtItems.setInt(1, order.getId());
                    try (ResultSet rsItems = stmtItems.executeQuery()) {
                        while (rsItems.next()) {
                            OrderItem orderItem = new OrderItem();
                            orderItem.setId(rsItems.getInt("id"));
                            orderItem.setOrderId(order.getId());
                            orderItem.setProductId(rsItems.getInt("productId"));
                            orderItem.setProductName(rsItems.getString("productName"));
                            orderItem.setProductPrice(rsItems.getDouble("productPrice"));
                            orderItem.setQuantity(rsItems.getInt("quantity"));
                            orderItems.add(orderItem);
                        }
                    }
                }
                order.setOrderItems(orderItems);

                orders.add(order);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return orders;
}

public List<Order> getOrders(Date startDate, Date endDate, String customer) throws SQLException {
    List<Order> orders = new ArrayList<>();
    String query = "SELECT o.*, u.username AS customer_name " +
                   "FROM `order` o " +
                   "INNER JOIN user u ON o.userId = u.id " +
                   "WHERE 1=1";

    if (startDate != null && endDate != null) {
        query += " AND o.orderDate BETWEEN ? AND ?";
    } else if (startDate != null) {
        query += " AND o.orderDate >= ?";
    } else if (endDate != null) {
        query += " AND o.orderDate <= ?";
    }

    if (customer != null && !customer.isEmpty()) {
        query += " AND u.username LIKE ?";
    }

    try (Connection connection = ConnectionPool.getConnection();
         PreparedStatement stmt = connection.prepareStatement(query)) {

        int paramIndex = 1;

        if (startDate != null && endDate != null) {
            stmt.setTimestamp(paramIndex++, new java.sql.Timestamp(startDate.getTime()));
            stmt.setTimestamp(paramIndex++, new java.sql.Timestamp(endDate.getTime()));
        } else if (startDate != null) {
            stmt.setTimestamp(paramIndex++, new java.sql.Timestamp(startDate.getTime()));
        } else if (endDate != null) {
            stmt.setTimestamp(paramIndex++, new java.sql.Timestamp(endDate.getTime()));
        }

        if (customer != null && !customer.isEmpty()) {
            stmt.setString(paramIndex++, "%" + customer + "%");
        }

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setCustomer(rs.getString("customer_name"));
            order.setOrderDate(rs.getTimestamp("orderDate"));
            order.setTotalPrice(rs.getDouble("totalPrice"));
            orders.add(order);
        }
    }

    return orders;
}



}