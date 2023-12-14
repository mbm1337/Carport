package app.persistence;


import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.*;

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
}
