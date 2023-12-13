package app.persistence;

import app.entities.City;
import app.entities.Order;
import app.exceptions.DatabaseException;


import java.sql.*;


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
            throw new DatabaseException(message);
        }

        return status;
    }
    public static void insertOrder(Order order, ConnectionPool connectionPool) throws DatabaseException {
        try (Connection connection = connectionPool.getConnection()) {
            String sql = "INSERT INTO \"orders\" (user_id, orderdate,status,comments,price,length,width) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getUserId());
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setString(3, order.getStatus());
            ps.setString(4, order.getComment());
            ps.setDouble(5, order.getPrice());
            ps.setInt(6,order.getLength());
            ps.setInt(7, order.getWidth());

            int rs = ps.executeUpdate();
        } catch (SQLException e) {
            String msg = "Der skete en fejl. Kan ikke oprette en ordre";
            throw new DatabaseException(msg);
        }

    }
    public static void createOrderDetailsDatabase(int ordernumber, int quanittyordered, int price, int materials_id, ConnectionPool connectionPool) throws DatabaseException {
        try (Connection connection = connectionPool.getConnection()) {
            String sql = "INSERT INTO \"ordersdetails\" (ordernumber, quantityordered, price, materials_id) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, ordernumber);
            ps.setInt(2, quanittyordered);
            ps.setInt(3, price);
            ps.setInt(4, materials_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            throw new DatabaseException(msg);
        }
    }



}
