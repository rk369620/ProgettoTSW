package model.Payment;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.ConnectionPool;

public class PaymentDao {

    public boolean processPayment(int orderId, BigDecimal amount) {
        try (Connection conn = ConnectionPool.getConnection()) {
            String sql = "INSERT INTO payment (orderId, amount, status) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.setBigDecimal(2, amount);
            ps.setString(3, "Completed");
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
