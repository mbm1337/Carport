package app.persistence;

import app.entities.City;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper {
    public static String getOrderStatusByUserId(int userId, ConnectionPool connectionPool) throws DatabaseException {
        String status = null;
        String sql = "SELECT status FROM orders WHERE user_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    status = rs.getString("status");
                }
            }
        } catch (SQLException e) {
            String message = "Couldn't retrieve order status for user " + userId;
            throw new DatabaseException(message, e);
        }

        return status;
    }

}