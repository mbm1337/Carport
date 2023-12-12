package app.persistence;

import app.entities.*;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarportMapper {

    public static List<CarportWidth> getCarportWidth(ConnectionPool connectionPool) throws DatabaseException {
        List<CarportWidth> carportWidth = new ArrayList<>();
        String sql = "SELECT * FROM carport_width";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int width = rs.getInt("width");
                    carportWidth.add(new CarportWidth(id, width));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't fetch the carport width from the database");
        }
        return carportWidth;
    }


    public static List<CarportLength> getCarportLength(ConnectionPool connectionPool) throws DatabaseException {
        List<CarportLength> carportLength = new ArrayList<>();
        String sql = "SELECT * FROM carport_lengths";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int length = rs.getInt("lengths");
                    carportLength.add(new CarportLength(id, length));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't fetch the carport length from the database");
        }
        return carportLength;
    }

    public static List<ShedWidth> getShedWidth(ConnectionPool connectionPool) throws DatabaseException {
        List<ShedWidth> shedWidth = new ArrayList<>();
        String sql = "SELECT * FROM shed_width";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int width = rs.getInt("width");
                    shedWidth.add(new ShedWidth(id, width));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't fetch the shed width from the database");
        }
        return shedWidth;
    }

    public static List<ShedLength> getShedLength(ConnectionPool connectionPool) throws DatabaseException {
        List<ShedLength> shedLength = new ArrayList<>();
        String sql = "SELECT * FROM shed_lengths";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int length = rs.getInt("lengths");
                    shedLength.add(new ShedLength(id, length));
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
