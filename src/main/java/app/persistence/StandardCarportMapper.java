package app.persistence;

import app.entities.StandardCarport;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StandardCarportMapper {

    public static StandardCarport getCarportById(int id, ConnectionPool connectionPool) throws DatabaseException {
        StandardCarport carport = null;

        String sql = "SELECT merchandiser, productname, price, description " +
                "FROM carports " +
                "WHERE id = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String merchandiser = rs.getString("merchandiser");
                    String productName = rs.getString("productname");
                    int price = rs.getInt("price");
                    String description = rs.getString("description");

                    carport = new StandardCarport(id, merchandiser, productName, price, description);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't fetch the carport from the database by ID: " + e);
        }

        return carport;
    }
}

