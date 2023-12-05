package app.persistence;

import app.entities.Shipping;
import app.entities.StandardCarport;
import app.exceptions.DatabaseException;
import io.javalin.http.Context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShippingMapper {

    public static Shipping getShippingInfoByZip(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        Shipping shipping = null;

        String sql = "SELECT zip, shipping_price " +
                "FROM shipping " +
                "WHERE ? BETWEEN SUBSTRING_INDEX(zip, '-', 1) AND SUBSTRING_INDEX(zip, '-', -1)"; // The SUBSTRING_INDEX function is used to extract the lower and upper bounds of the zip code range for comparison.

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, zip);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String zipRange = rs.getString("zip");
                    int shippingPrice = rs.getInt("shipping_price");

                    shipping = new Shipping(zip, shippingPrice);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't fetch the shipping info from the database for zip: " + e);
        }

        return shipping;
    }
}
