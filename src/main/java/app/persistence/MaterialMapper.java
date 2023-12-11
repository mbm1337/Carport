package app.persistence;

import app.entities.Materials;
import app.entities.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialMapper {


    public static void getPrice(Materials material, int id, ConnectionPool connectionPool) {
        try (Connection connection = connectionPool.getConnection()) {
            String sql = "SELECT sellprice FROM materials WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int price = rs.getInt("sellprice");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting material price: " + e.getMessage(), e);
        }
    }
}