package app.persistence;
import app.entities.*;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminMapper {


    public static Map<User, List<Order>> getUsersAndOrders(ConnectionPool connectionPool) {
        Map<User, List<Order>> usersAndOrders = new HashMap<>();
        try (Connection connection = connectionPool.getConnection()) {
            String sql = "SELECT \"user\".id, \"user\".forname, \"user\".aftername, \"user\".email, " +
                    "\"user\".phone, \"user\".zip, \"user\".address, " +
                    "orders.ordernumber, orders.orderdate, orders.user_id, orders.status, orders.comments, orders.price, orders.length ,orders.width  " +
                    "FROM \"user\" " +
                    "JOIN orders ON orders.user_id = \"user\".id " +
                    "ORDER BY \"user\".id, orders.ordernumber"; // Add JOIN and ORDER BY clauses

            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),      // Brugerens ID
                        rs.getString("email"), // Brugerens email
                        rs.getString("forname"),
                        rs.getString("aftername"),
                        rs.getInt("phone"),
                        rs.getInt("zip"),
                        rs.getString("address")
                );

                Order order = new Order(
                        rs.getInt("ordernumber"),
                        rs.getInt("user_id"),  // Ordrens nummer
                        rs.getString("status"), // Ordrens status
                        rs.getInt("price"),      // Ordrens pris
                        rs.getInt("length"),
                        rs.getInt("width"),
                        rs.getString("comments"),
                        rs.getString("orderdate")
                );

                if (!usersAndOrders.containsKey(user)) {
                    usersAndOrders.put(user, new ArrayList<>());
                }

                usersAndOrders.get(user).add(order);
            }

            return usersAndOrders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }






    public static Admin getOrderDetails(int orderNumber, ConnectionPool connectionPool) {
        Admin admin = new Admin();
        List<Admin> adminList = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection()) {
            String sql = "SELECT " +
                    "u.id AS user_id, u.forname, u.aftername, u.email, u.zip, u.address, u.admin, u.password, u.phone, " +
                    "o.ordernumber, o.orderdate, o.status, o.length, o.width, o.comments, o.user_id AS order_user_id, " +
                    "o.price AS order_price, od.materials_id, od.quantityordered, " +
                    "m.productname, m.producttype, m.productsize, m.unit, m.quantityinstock, m.sellprice, m.purchaseprice, " +
                    "hs.order_id AS shed_order_id, hs.length AS shed_length, hs.width AS shed_width, hs.side AS shed_side " +
                    "FROM \"user\" u " +
                    "JOIN orders o ON u.id = o.user_id " +
                    "JOIN orderdetails od ON o.ordernumber = od.ordernumber " +
                    "JOIN materials m ON od.materials_id = m.id " +

                    "LEFT JOIN has_shed hs ON o.ordernumber = hs.order_id " +  // Assuming LEFT JOIN, change it based on your requirements

                    "WHERE o.ordernumber = ?";


            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, orderNumber);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {

                        admin.setForname(resultSet.getString("forname"));
                        admin.setAftername(resultSet.getString("aftername"));
                        admin.setUserEmail(resultSet.getString("email"));
                        admin.setZip(resultSet.getInt("zip"));
                        admin.setAddress(resultSet.getString("address"));
                        admin.setAdmin(resultSet.getBoolean("admin"));
                        admin.setPassword(resultSet.getString("password"));
                        admin.setPhone(resultSet.getInt("phone"));

                        admin.setOrderId(resultSet.getInt("ordernumber"));
                        admin.setOrderDate(resultSet.getString("orderdate"));
                        admin.setStatus(resultSet.getString("status"));
                        admin.setComments(resultSet.getString("comments"));
                        admin.setUserId(resultSet.getInt("user_id"));
                        admin.setOrderPrice(resultSet.getDouble("order_price"));

                        admin.setMaterialsId(resultSet.getInt("materials_id"));
                        admin.setQuantityOrdered(resultSet.getInt("quantityordered"));



                        admin.setLength(resultSet.getInt("length"));
                        admin.setWidth(resultSet.getInt("width"));

                        // Shed details
                        admin.setShedLength(resultSet.getInt("shed_length"));
                        admin.setShedWidth(resultSet.getInt("shed_width"));
                        admin.setShedSide(resultSet.getBoolean("shed_side"));

                        // Material details
                        Admin adminMaterial = new Admin();
                        adminMaterial.setQuantityOrdered(resultSet.getInt("quantityordered"));
                        adminMaterial.setMaterialsId(resultSet.getInt("materials_id"));
                        adminMaterial.setProductName(resultSet.getString("productname"));
                        adminMaterial.setProductType(resultSet.getString("producttype"));
                        adminMaterial.setProductSize(resultSet.getString("productsize"));
                        adminMaterial.setUnit(resultSet.getString("unit"));
                        adminMaterial.setQuantityInStock(resultSet.getInt("quantityinstock"));
                        adminMaterial.setSellPrice(resultSet.getDouble("sellprice"));
                        adminMaterial.setPurchasePrice(resultSet.getDouble("purchaseprice"));
                        adminList.add(adminMaterial);
                    }

                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle database call errors
        }

        admin.setAdminList(adminList);
        return admin;
    }


    public static void updatePrice(int ordernumber, double price, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE orders SET price = ? WHERE ordernumber = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, price);
            ps.setInt(2, ordernumber);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i opdatering af top");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i opdatering af top");
        }
    }

    public static List<Material> getMaterials( ConnectionPool connectionPool) throws SQLException {
        List<Material> materials = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            String sql = "SELECT * FROM \"materials\"";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String productName = rs.getString("productname");
                String productType = rs.getString("producttype");
                String productSize = rs.getString("productsize");
                String unit = rs.getString("unit");
                short quantityInStock = rs.getShort("quantityinstock");
                double sellPrice = rs.getDouble("sellprice");
                double purchasePrice = rs.getDouble("purchaseprice");

                Material material = new Material(id, productName, productType, productSize,
                        unit, quantityInStock, sellPrice, purchasePrice);
                materials.add(material);
            }
            return materials;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Material getMaterialById(int id, ConnectionPool connectionPool) throws SQLException {
        try (Connection connection = connectionPool.getConnection()) {
            String sql = "SELECT * FROM \"materials\" WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);

                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        String productName = rs.getString("productname");
                        String productType = rs.getString("producttype");
                        String productSize = rs.getString("productsize");
                        String unit = rs.getString("unit");
                        short quantityInStock = rs.getShort("quantityinstock");
                        double sellPrice = rs.getDouble("sellprice");
                        double purchasePrice = rs.getDouble("purchaseprice");

                        return new Material(id, productName, productType, productSize,
                                unit, quantityInStock, sellPrice, purchasePrice);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static Material updateMaterial(Material material, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE \"materials\" SET productname = ?, producttype = ?, productsize = ?, unit = ?, quantityinstock = ?, sellprice = ?, purchaseprice = ? WHERE id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, material.getProductName());
            ps.setString(2, material.getProductType());
            ps.setString(3, material.getProductSize());
            ps.setString(4, material.getUnit());
            ps.setShort(5, material.getQuantityInStock());
            ps.setDouble(6, material.getBuyPrice());
            ps.setDouble(7, material.getPurchasePrice());
            ps.setInt(8, material.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i opdatering af material");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i opdatering af material");
        }

        return material;
    }


    public static Material addMaterial(Material material, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO \"materials\" (productname, producttype, productsize, unit, quantityinstock, sellprice, purchaseprice) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, material.getProductName());
            ps.setString(2, material.getProductType());
            ps.setString(3, material.getProductSize());
            ps.setString(4, material.getUnit());
            ps.setShort(5, material.getQuantityInStock());
            ps.setDouble(6, material.getBuyPrice());
            ps.setDouble(7, material.getPurchasePrice());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i opdatering af material");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i opdatering af material");
        }

        return material;
    }

    public static Material deleteMaterial(int id, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "DELETE FROM \"materials\" WHERE id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i sletning af material");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i sletning af material");
        }

        return null;
    }


    public static List<Admin> getCalcMaterials(ConnectionPool connectionPool) {

        List<Admin> calcMaterials = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            String sql = "SELECT * FROM public.carport_calculator";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int materialsId = rs.getInt("material_id");
                String comments = rs.getString("description");
                calcMaterials.add(new Admin(id,materialsId, comments));
            }
            return calcMaterials;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void updateCalcMaterials(int id, int materialId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE carport_calculator SET material_id = ? WHERE id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, materialId);
            ps.setInt(2, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i opdatering af CalcMaterials");
            }
        } catch (SQLException | DatabaseException e) {
            throw new DatabaseException("Fejl i opdatering af CalcMaterials");
        }
    }

    public static Admin getCalcMaterialsById(int id, ConnectionPool connectionPool) {

            try (Connection connection = connectionPool.getConnection()) {
                String sql = "SELECT * FROM public.carport_calculator WHERE id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setInt(1, id);

                    try (ResultSet rs = preparedStatement.executeQuery()) {
                        while (rs.next()) {
                            int materialsId = rs.getInt("material_id");
                            String comments = rs.getString("description");
                            return new Admin(id,materialsId, comments);
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null; // If material with the given ID is not found
    }

    public static void addCarportLength(int length, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO carport_length (length) VALUES (?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, length);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i opdatering af carport length");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i opdatering af carport length");
        }
    }

    public static void addCarportWidth(int width, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO carport_width (width) VALUES (?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, width);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i opdatering af carport width");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i opdatering af carport width");
        }
    }

    public static void addShedLength(int length, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO shed_length (length) VALUES (?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, length);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i opdatering af shed length");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i opdatering af shed length");
        }
    }

    public static void addShedWidth(int width, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO shed_width (width) VALUES (?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, width);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i opdatering af shed width");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i opdatering af shed width");
        }
    }

    public static void deleteCarportLength(int id, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "DELETE FROM carport_length WHERE id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i sletning af carport length");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i sletning af carport length");
        }
    }

    public static void deleteCarportWidth(int id, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "DELETE FROM carport_width WHERE id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i sletning af carport width");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i sletning af carport width");
        }
    }

    public static void deleteShedLength(int id, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "DELETE FROM shed_length WHERE id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i sletning af shed length");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i sletning af shed length");
        }
    }

    public static void deleteShedWidth(int id, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "DELETE FROM shed_width WHERE id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i sletning af shed width");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i sletning af shed width");
        }
    }

    public static void updatePrice(String updatePrice, ConnectionPool connectionPool) {
    }
}
