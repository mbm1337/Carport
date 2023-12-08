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

    public static List<StandardCarport> getAllStandardCarports(ConnectionPool connectionPool) throws DatabaseException {
        List<StandardCarport> carports = new ArrayList<>();

        String sql = "SELECT id, merchandiser, productname, price, description " +
                "FROM carports";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int carportId = rs.getInt("id");
                    String merchandiser = rs.getString("merchandiser");
                    String productName = rs.getString("productname");
                    int price = rs.getInt("price");
                    String description = rs.getString("description");

                    StandardCarport carport = new StandardCarport(carportId, merchandiser, productName, price, description);
                    carports.add(carport);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't fetch the carports from the database: " + e);
        }

        return carports;
    }
}

