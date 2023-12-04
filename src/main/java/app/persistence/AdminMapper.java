package app.persistence;
import app.entities.*;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminMapper {


    public static Map<User, List<Order>> getUsersAndOrders(ConnectionPool connectionPool) {
        Map<User, List<Order>> usersAndOrders = new HashMap<>();
        try (Connection connection = connectionPool.getConnection())  {
            String sql = "SELECT \"user\".id, \"user\".forname, \"user\".aftername, \"user\".email, " +
                    "\"user\".phone, \"user\".zip, \"user\".adress, " +
                    "orders.ordernumber, orders.user_id, orders.status, orders.price " +
                    "FROM \"user\" " +
                    "LEFT JOIN orders ON \"user\".id = orders.user_id";

            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),      // Brugerens ID
                        rs.getString("email"), // Brugerens email
                        rs.getString("forname"),
                        rs.getString("aftername"),
                        rs.getString("phone"),
                        rs.getInt("zip"),
                        rs.getString("adress")

                );

                Order order = new Order(
                        rs.getInt("ordernumber"),
                        rs.getInt("user_id"),  // Ordrens nummer
                        rs.getString("status"), // Ordrens status
                        rs.getInt("price")      // Ordrens pris
                );

                if (!usersAndOrders.containsKey(user)) {
                    usersAndOrders.put(user, new ArrayList<>());
                }

                usersAndOrders.get(user).add(order);
            }

            return usersAndOrders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static List<Cupcake> getOrderDetails(int orderNr, ConnectionPool connectionPool) {
        List<Cupcake> orderDetails = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            String sql = "SELECT ctop.flavor AS top_flavor, ctop.price AS top_price, cbottom.flavor AS bottom_flavor, cbottom.price AS bottom_price, amount\n" +
                    "FROM ordersdetails od\n" +
                    "JOIN cupcake_top ctop ON od.top_id = ctop.id\n" +
                    "JOIN cupcake_bottom cbottom ON od.bottom_id = cbottom.id\n" +
                    "WHERE od.order_nr = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderNr);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String cTopFlavor = rs.getString("top_flavor");
                int cTopPrice = rs.getInt("top_price");
                String cBottomFlavor = rs.getString("bottom_flavor");
                int cBottomPrice = rs.getInt("bottom_price");
                int amount = rs.getInt("amount");
                Cupcake cupcake = new Cupcake(new CupcakeTop(cTopFlavor,cTopPrice), new CupcakeBottom(cBottomFlavor,cBottomPrice), amount);
                orderDetails.add(cupcake);
            }
            return orderDetails;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
