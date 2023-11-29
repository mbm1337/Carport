package app.persistence;

import app.entities.City;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class ZipMapper {

    public static City getCityByZip(int zip, ConnectionPool connectionPool) throws DatabaseException {
        City byen = null;

        String sql = "SELECT postnummer, city FROM city WHERE zip = ?";

        try (Connection connection = connectionPool.getConnection()){;
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, zip);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int postnummer = rs.getInt("postnummer");
                String cityName = rs.getString("city");
                byen = new City(postnummer, cityName);

                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't fetch the city from the database for zip code: " + zip, e);
        }

        return byen;
    }
}
