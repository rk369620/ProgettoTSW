package model.Payment;

import model.ConnectionPool;
import model.Order.Order;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PaymentDao {
    public void createPayment(Order order, double amount, String status) throws SQLException {
        String query = "INSERT INTO payment (orderId, paymentDate, amount, status) VALUES (?, ?, ?, ?)";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, order.getId());
            stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            stmt.setDouble(3, amount);
            stmt.setString(4, status);
            stmt.executeUpdate();
        }
    }
}
