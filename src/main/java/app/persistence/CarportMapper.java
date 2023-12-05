package app.persistence;

import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarportMapper {

    public static List<Integer> getCarportWidth(ConnectionPool connectionPool) throws DatabaseException {
        List<Integer> carportWidth = new ArrayList<>();
        String sql = "SELECT * FROM carport_widths";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int width = rs.getInt("width");
                    carportWidth.add(width);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't fetch the carport width from the database");
        }
        return carportWidth;
    }


    public static List<Integer> getCarportLength(ConnectionPool connectionPool) throws DatabaseException {
        List<Integer> carportLength = new ArrayList<>();
        String sql = "SELECT * FROM carport_lengths";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int length = rs.getInt("length");
                    carportLength.add(length);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't fetch the carport length from the database");
        }
        return carportLength;
    }

    public static List<Integer> getShedWidth(ConnectionPool connectionPool) throws DatabaseException {
        List<Integer> shedWidth = new ArrayList<>();
        String sql = "SELECT * FROM shed_widths";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int width = rs.getInt("width");
                    shedWidth.add(width);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't fetch the shed width from the database");
        }
        return shedWidth;
    }

    public static List<Integer> getShedLength(ConnectionPool connectionPool) throws DatabaseException {
        List<Integer> shedLength = new ArrayList<>();
        String sql = "SELECT * FROM shed_lengths";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int length = rs.getInt("length");
                    shedLength.add(length);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't fetch the shed length from the database");
        }
        return shedLength;
    }

    public static List<String> getRoof(ConnectionPool connectionPool) throws DatabaseException {
        List<String> roof = new ArrayList<>();
        String sql = "SELECT * FROM roof";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String roofType = rs.getString("roof");
                    roof.add(roofType);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't fetch the roof from the database");
        }
        return roof;
    }
}
