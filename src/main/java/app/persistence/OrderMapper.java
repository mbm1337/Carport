package app.persistence;

import app.entities.Shed;
import app.entities.Order;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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
    public static int insertOrder(Order order, double totalPrice, ConnectionPool connectionPool) throws DatabaseException {
        int newOrderId =0;
        try (Connection connection = connectionPool.getConnection()) {
            String sql = "INSERT INTO \"orders\" (user_id, orderdate,status,comments,price,length,width) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getUserId());
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setString(3, order.getStatus());
            ps.setString(4, order.getComment());
            ps.setDouble(5, totalPrice);
            ps.setInt(6,order.getLength());
            ps.setInt(7, order.getWidth());

            int rs = ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                newOrderId = generatedKeys.getInt(1);
            }

            return newOrderId;


        } catch (SQLException e) {
            e.printStackTrace();

            String msg = "Der skete en fejl. Kan ikke oprette en ordre";
            throw new DatabaseException(msg);
        }

    }
    public static void createOrderDetailsDatabase(int newOrderId, Order order, int id, int quantityordered, ConnectionPool connectionPool) throws DatabaseException {
        try (Connection connection = connectionPool.getConnection()) {
            String sql = "INSERT INTO \"orderdetails\" (ordernumber, quantityordered, materials_id) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, newOrderId);
            ps.setInt(2, quantityordered);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            throw new DatabaseException(msg);
        }
    }

    public static List<Order> getOrders(int id, ConnectionPool connectionPool) {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            String sql = "SELECT * FROM \"orders\" WHERE user_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderNr = rs.getInt("ordernumber");
                int userId = rs.getInt("user_id");
                String status = rs.getString("status");
                int price = rs.getInt("price");
                Order order = new Order(orderNr, userId, status, price);
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void createOrdershedDatabase(int newOrderId, Shed shed, ConnectionPool connectionPool) throws DatabaseException {
        try (Connection connection = connectionPool.getConnection()) {
            String sql = "INSERT INTO \"has_shed\" (order_id, length, width,side) VALUES (?, ?, ?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, newOrderId);
            ps.setInt(2, shed.getLength());
            ps.setInt(3, shed.getWidth());
            ps.setBoolean(4, shed.isShedside());
            ps.executeUpdate();
        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            throw new DatabaseException(msg);
        }
    }




    public static void deleteOrderDatabase(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        try (Connection connection = connectionPool.getConnection()) {
            // Slet fra "orderdetails" tabel først
            String deleteDetailsSQL = "DELETE FROM \"orderdetails\" WHERE ordernumber = ?";
            try (PreparedStatement psDetails = connection.prepareStatement(deleteDetailsSQL)) {
                psDetails.setInt(1, orderId);
                psDetails.executeUpdate();
            }

            // Slet fra "has_shed" tabel
            String deleteShedSQL = "DELETE FROM \"has_shed\" WHERE order_id = ?";
            try (PreparedStatement psShed = connection.prepareStatement(deleteShedSQL)) {
                psShed.setInt(1, orderId);
                psShed.executeUpdate();
            }

            // Slet fra "orders" tabel
            String deleteOrdersSQL = "DELETE FROM \"orders\" WHERE ordernumber = ?";
            try (PreparedStatement psOrders = connection.prepareStatement(deleteOrdersSQL)) {
                psOrders.setInt(1, orderId);
                psOrders.executeUpdate();
            }

        } catch (SQLException e) {
            String msg = "Der er sket en fejl under sletning af ordre. Prøv igen";
            throw new DatabaseException(msg);
        }
    }

}
