package app.persistence;


import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static User login(String email, String password, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM \"user\" WHERE email=? AND password=?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, email);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int id = rs.getInt("id");
                    String firstName = rs.getString("forname");
                    String lastName = rs.getString("aftername");
                    int phoneNumber = rs.getInt("phone");
                    int zip = rs.getInt("zip");
                    String address = rs.getString("address");
                    boolean admin = rs.getBoolean("admin");

                    return new User(id, firstName, lastName, phoneNumber, email, zip, address, admin, password);
                } else {
                    throw new DatabaseException("Fejl i login. Prøv igen.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void createuser(String forname, String aftername, String email, int zip, String address, boolean admin, String password, int phone, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "insert into \"user\" (forname, aftername,email,zip,address,admin,password,phone) values (?, ?, ?, ?, ?, ?, ?, ?)";
        int newPassword = 0;

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, forname);
                ps.setString(2, aftername);
                ps.setString(3, email);
                ps.setInt(4, zip);
                ps.setString(5, address);
                ps.setBoolean(6, admin);
                ps.setString(7, password);
                ps.setInt(8, phone);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected != 1) {
                    throw new DatabaseException("Fejl ved oprettelse af ny bruger");
                }
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            if (e.getMessage().startsWith("ERROR: duplicate key value ")) {
                msg = "Brugernavnet findes allerede. Vælg et andet";
            }

            throw new DatabaseException(msg);
        }
    }


    public static User searchUser(String email, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM \"user\" WHERE email = ?";
        User user = null;

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, email);
                ResultSet resultSet = ps.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");


                    user = new User(id, email);
                }
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl under søgning efter brugeren.";
            throw new DatabaseException(msg);
        }

        return user;

    }

    public static int createUserGenerated(User user, ConnectionPool connectionPool) throws DatabaseException {
          int newUserId = 0;

        try (Connection connection = connectionPool.getConnection()) {
            String sql = "INSERT INTO \"user\" (forname, aftername, email, zip, address, admin, password, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setInt(4, user.getZip());
            ps.setString(5, user.getAddress());
            ps.setBoolean(6, user.isAdmin());
            ps.setInt(7, user.getPhoneNumber());
            ps.setInt(8, user.getPhoneNumber());

            int rs = ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                newUserId = generatedKeys.getInt(1);
            }

            return newUserId;
        } catch (SQLException e) {
            String message = "Couldn't create a new user in the database: " + e.getMessage();
            throw new DatabaseException(message);
        }
    }
    // UserMapper.java


        public static void updateUser(int userId, String firstName , String lastName, String address, int zip,int phone,String password ,boolean isAdmin,ConnectionPool connectionPool) throws DatabaseException {
            try (Connection connection = connectionPool.getConnection()) {
                String sql = "UPDATE \"user\"  SET forname = ?, aftername = ?, address = ?, zip = ?, phone = ?, password = ?, admin = ?  WHERE id = ?";
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setString(1, firstName);
                    ps.setString(2, lastName);
                    ps.setString(3, address);
                    ps.setInt(4, zip);
                    ps.setInt(5, phone);
                    ps.setString(6, password);
                    ps.setBoolean(7, isAdmin);
                    ps.setInt(8, userId);

                    int rowsaffected =ps.executeUpdate();

                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DatabaseException("Error updating user information");
            }
return;
        }

    public static Object getUserById(int id, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM \"user\" WHERE id = ?";
        User user = null;

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet resultSet = ps.executeQuery();

                if (resultSet.next()) {
                    String firstName = resultSet.getString("forname");
                    String lastName = resultSet.getString("aftername");
                    String email = resultSet.getString("email");
                    int zip = resultSet.getInt("zip");
                    String address = resultSet.getString("address");
                    boolean admin = resultSet.getBoolean("admin");
                    String password = resultSet.getString("password");
                    int phone = resultSet.getInt("phone");

                    user = new User(id, firstName, lastName, phone, email, zip, address, admin, password);
                }
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl under søgning efter brugeren.";
            throw new DatabaseException(msg);
        }

        return user;
    }
    public static List<Integer> getOrdersForUser(int userId, Connection connection) throws SQLException {
        List<Integer> orderIds = new ArrayList<>();

        String selectOrdersSQL = "SELECT ordernumber FROM orders WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(selectOrdersSQL)) {
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                orderIds.add(resultSet.getInt("ordernumber"));
            }
        }

        return orderIds;
    }
    public static void deleteOrder(Connection connection, int orderId) throws SQLException {
        String deleteOrdersSQL = "DELETE FROM orders WHERE ordernumber = ?";
        try (PreparedStatement ps = connection.prepareStatement(deleteOrdersSQL)) {
            ps.setInt(1, orderId);
            ps.executeUpdate();
        }
    }

    public static void deleteHasShed(Connection connection, int orderId) throws SQLException {
        String deleteShedSQL = "DELETE FROM has_shed WHERE order_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(deleteShedSQL)) {
            ps.setInt(1, orderId);
            ps.executeUpdate();
        }
    }

    public static void deleteOrderDetails(Connection connection, int orderId) throws SQLException {
        String deleteDetailsSQL = "DELETE FROM orderdetails WHERE ordernumber = ?";
        try (PreparedStatement ps = connection.prepareStatement(deleteDetailsSQL)) {
            ps.setInt(1, orderId);
            ps.executeUpdate();
        }
    }

    public static void deleteUser(int userId, Connection connection) throws SQLException {
        String deleteUserSQL = "DELETE FROM \"user\" WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(deleteUserSQL)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }

}

