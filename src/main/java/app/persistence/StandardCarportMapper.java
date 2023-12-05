package app.persistence;

import app.entities.City;
import app.entities.StandardCarport;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StandardCarportMapper {

    public static StandardCarport getShippingInfoByZip(int zip, ConnectionPool connectionPool) throws DatabaseException {
        StandardCarport standardCarport = null;

        String sql = "SELECT c.zip, cp.shipping_days, cp.shipping_price " +
                "FROM city c " +
                "JOIN carports cp ON c.zip = cp.zip " +
                "WHERE c.zip = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, zip);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int postnummer = rs.getInt("zip");
                    int shippingDays = rs.getInt("shipping_days");
                    int shippingPrice = rs.getInt("shipping_price");

                    standardCarport = new StandardCarport(postnummer, shippingDays, shippingPrice);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't fetch the shipping info from the database for zip: " + zip);
        }

        return standardCarport;
    }
    /*
    public static List<City> citiesbyzip(ConnectionPool connectionPool) throws DatabaseException{

        String sql= "SELECT zip,city_name from \"city\"";
        List<City> city = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int zip = rs.getInt("zip");
                String by = rs.getString("city_name");

                city.add(new City(zip, by));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't upload the toppings from database"+city,e );

        }

        return city;
    }*/
}

