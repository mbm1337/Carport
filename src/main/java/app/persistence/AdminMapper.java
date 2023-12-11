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
                    "\"user\".phone, \"user\".zip, \"user\".address, " +
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
                        rs.getString("address")

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




    public static List<Admin> getOrderDetails(int ordernumber, ConnectionPool connectionPool) {
        List<Admin> orderList = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection()) {
            String sql = "SELECT " +
                    "u.id AS user_id, u.forname, u.aftername, u.email, u.zip, u.address, u.admin, u.password, u.phone, " +
                    "o.ordernumber, o.orderdate, o.status, o.comments, o.customernumber, o.user_id AS order_user_id, " +
                    "o.price AS order_price, od.materials_id, od.quantityordered, od.price AS detail_price " +
                    "FROM \"user\" u " +
                    "JOIN orders o ON u.id = o.user_id " +
                    "JOIN orderdetails od ON o.ordernumber = od.ordernumber " +
                    "WHERE o.ordernumber = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, ordernumber);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Admin admin = new Admin();
                        admin.setOrderId(resultSet.getInt("ordernumber"));
                        admin.setOrderDate(resultSet.getString("orderdate"));
                        admin.setStatus(resultSet.getString("status"));
                        admin.setComments(resultSet.getString("comments"));
                        admin.setCustomerNumber(resultSet.getInt("customernumber"));
                        admin.setUserId(resultSet.getInt("user_id"));
                        admin.setOrderPrice(resultSet.getDouble("order_price"));
                        admin.setMaterialsId(resultSet.getInt("materials_id"));
                        admin.setQuantityOrdered(resultSet.getInt("quantityordered"));
                        admin.setDetailPrice(resultSet.getDouble("detail_price"));

                        orderList.add(admin);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Håndter fejl i forbindelse med databasekald
        }

        return orderList;
    }

    public static void updatePrice(int ordernumber, double price, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE orders SET price = ? WHERE ordernumber = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, price);
            ps.setInt(2, ordernumber);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i opdatering af top");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i opdatering af top");
        }
    }

    public static List<Material> getMaterials( ConnectionPool connectionPool) {
        List<Material> materials = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            String sql = "SELECT * FROM \"materials\"";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String productName = rs.getString("productname");
                String productType = rs.getString("producttype");
                String productSize = rs.getString("productsize");
                String unit = rs.getString("unit");
                short quantityInStock = rs.getShort("quantityinstock");
                double sellPrice = rs.getDouble("sellprice");
                double purchasePrice = rs.getDouble("purchaseprice");

                Material material = new Material(id, productName, productType, productSize,
                        unit, quantityInStock, sellPrice, purchasePrice);
                materials.add(material);
            }
            return materials;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}