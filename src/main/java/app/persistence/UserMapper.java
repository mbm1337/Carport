package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper
{

    public static User login(String email, String password, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "select * from \"user\" where email=? and password=?";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setString(1, email);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                {
                    int id = rs.getInt("id");
                    boolean status = rs.getBoolean("admin");
                    return new User(id, email, password,status);
                } else
                {
                    throw new DatabaseException("Fejl i login. Prøv igen.");
                }
            }

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

    }

    public static void createuser(String firstName, String lastName, int phoneNumber, String email, int zip, String address, boolean admin, String password, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "insert into \"user\" (firstname, lastname, tlf, email, zip, address, admin, password) values (?,?,?,?,?,?,?,?)";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setInt(3, phoneNumber);
                ps.setString(4, email);
                ps.setInt(5, zip);
                ps.setString(6, address);
                ps.setBoolean(7, admin);
                ps.setString(8, password);

                int rowsAffected =  ps.executeUpdate();
                if (rowsAffected != 1)
                {
                    throw new DatabaseException("Fejl ved oprettelse af ny bruger");
                }
            }
        }
        catch (SQLException e)
        {
            String msg = "Der er sket en fejl. Prøv igen";
            if (e.getMessage().startsWith("ERROR: duplicate key value "))
            {
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

                    user = new User(id,email); // Create a User object with the retrieved email
                }
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl under søgning efter brugeren.";
            throw new DatabaseException(msg);
        }

        return user;
    }

}