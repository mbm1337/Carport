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

   /* public static City getCityByZip(int zip, ConnectionPool connectionPool) throws DatabaseException {
        City city = null;

        String sql = "SELECT  * FROM \"city\" WHERE zip = ?";

        try (Connection connection = connectionPool.getConnection()){
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, zip);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int postnummer = rs.getInt("zip");
                String cityName = rs.getString("city_name");
                city = new City(postnummer, cityName);

                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't fetch the city from the database  " + zip);
        }

        return city;
    }
*/
     public static String getCityByZip(int zip, ConnectionPool connectionPool) throws DatabaseException {
        String city = "";
        String sql = "SELECT city_name FROM \"city\" WHERE zip = ?";

        try (Connection connection = connectionPool.getConnection()){
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, zip);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {

                    city = rs.getString("city_name");


                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't fetch the city from the database  " + zip);
        }

        return city;
    }


    public static List<City>citiesbyzip(ConnectionPool connectionPool) throws DatabaseException{

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
            throw new DatabaseException("Couldn't upload the toppings from database"+city);

        }

        return city;
    }
}



