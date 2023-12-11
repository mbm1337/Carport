package app.persistence;

import app.entities.Materials;
import app.entities.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialMapper {




  /*  public static void getprice(int id, ConnectionPool connectionPool) {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            String sql = "SELECT price FROM \"materials\" WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderNr = rs.getInt("ordernumber");
                int userId = rs.getInt("user_id");
                String status = rs.getString("status");
                int price = rs.getInt("price");
                Order order = new Order(userId, status, price);
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

   */
}
