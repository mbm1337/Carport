package app.persistence;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaterialMapper {


    public static  int getPrice(int id, ConnectionPool connectionPool) {
        try (Connection connection = connectionPool.getConnection()) {
            String sql = "SELECT sellprice FROM materials WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int price = rs.getInt("sellprice");
                        return price;
                    }
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting material price: " + e.getMessage(), e);
        }
        return 0;
    }
}
