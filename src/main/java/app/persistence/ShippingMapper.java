package app.persistence;

import app.entities.Shipping;
import app.entities.StandardCarport;
import app.exceptions.DatabaseException;
import io.javalin.http.Context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShippingMapper {

/*    public static List<Shipping> getInfoByZip(ConnectionPool connectionPool) throws DatabaseException {
        List<Shipping> shippingList = new ArrayList<>();

        String sql = "SELECT zip, shipping_price " +
                "FROM shipping";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String zipCode = rs.getString("zipCode");
                    int shippingPrice = rs.getInt("shipping_price");

                    Shipping shipping = new Shipping(zip, shippingPrice);
                    shippingList.add(shipping);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't fetch the shipping info from the database for zip: " + e);
        }

        return shippingList;
    }*/

    public static Shipping getShippingInfoByZip(int zip, ConnectionPool connectionPool) throws DatabaseException {
        Shipping shipping = null;

        String sql = "SELECT shipping_price, shipping_time " +
                "FROM shipping " +
                "WHERE zip_code = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                if(zip >= 1001 && zip <= 4990) {
                    ps.setString(1, "1001-4990");
                } else if(zip >= 5000 && zip <= 5885) {
                    ps.setString(2, "5000-5885");
                } else if(zip >= 6000 && zip <= 9990) {
                    ps.setString(3, "6000-9990");
                }
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int shippingPrice = rs.getInt("shipping_price");
                    String shippingTime = rs.getString("shipping_time");

                    shipping = new Shipping(zip, shippingPrice, shippingTime);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't fetch the shipping info from the database for zip: " + e);
        }

        return shipping;
    }
}
