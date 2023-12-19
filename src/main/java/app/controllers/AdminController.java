package app.controllers;
import app.entities.*;
import app.persistence.*;
import app.exceptions.DatabaseException;
import io.javalin.http.Context;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class AdminController {

    public static void getUsersAndOrders(Context ctx, ConnectionPool connectionPool) throws DatabaseException {


        Map<User, List<Order>> usersAndOrders = AdminMapper.getUsersAndOrders(connectionPool);
        ctx.attribute("usersAndOrders", usersAndOrders);
        ctx.render("order.html");

        usersAndOrders.forEach((user, orders) -> {
            orders.sort(Comparator.comparing(Order::getStatus));
        });

        // Send de sorteret data til skabelonen
        ctx.attribute("usersAndOrders", usersAndOrders);

        ctx.render("order.html");


    }


    public static void getOrderDetails(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        int orderNumber = Integer.parseInt(ctx.pathParam("ordernumber"));

        List<Admin> orderDetail = AdminMapper.getOrderDetails(orderNumber, connectionPool);

        ctx.sessionAttribute("ordernumber", orderNumber);
        ctx.attribute("admin", orderDetail);
        ctx.render("tilbud.html");


    }

    public static void editBalance(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int orderNumber = Integer.parseInt(ctx.pathParam("ordernumber"));
        int price = Integer.parseInt(ctx.formParam("newPrice"));
        AdminMapper.updatePrice(orderNumber, price, connectionPool);
        ctx.sessionAttribute("ordernumber", orderNumber);
        ctx.render("users.html");
    }


    public static void getMaterial(Context ctx, ConnectionPool connectionPool) throws SQLException {
        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;
        }

        List<Material> materials = AdminMapper.getMaterials(connectionPool);
        ctx.attribute("materials", materials);

        ctx.render("materials.html", Map.of("isAdmin", isAdmin, "isUser", isUser));

    }

    public static void editMaterial(Context ctx, ConnectionPool connectionPool) throws DatabaseException, SQLException {
        int materialId = Integer.parseInt(ctx.pathParam("id"));
        Material material = AdminMapper.getMaterialById(materialId, connectionPool);
        ctx.attribute("material", material);
        ctx.render("edit_matreriel.html");
    }

    public static void updateMaterial(Context ctx, ConnectionPool connectionPool) throws DatabaseException, SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        String productName = (ctx.formParam("productName"));
        String productType = (ctx.formParam("productType"));
        String productSize = (ctx.formParam("productSize"));
        String unit = (ctx.formParam("unit"));
        short quantityInStock = (short) Integer.parseInt(ctx.formParam("quantityInStock"));
        double buyPrice = Double.parseDouble(ctx.formParam("buyPrice"));
        double purchasePrice = Double.parseDouble(ctx.formParam("purchasePrice"));

        Material material = new Material(id,productName, productType, productSize, unit, quantityInStock, buyPrice, purchasePrice);
        AdminMapper.updateMaterial(material, connectionPool);

        ctx.attribute("material", material);
        ctx.render("edit_matreriel.html");


    }


    public static void addMaterial(Context ctx, ConnectionPool connectionPool) throws DatabaseException, SQLException {
        String productName = (ctx.formParam("productName"));
        String productType = (ctx.formParam("productType"));
        String productSize = (ctx.formParam("productSize"));
        String unit = (ctx.formParam("unit"));
        short quantityInStock = (short) Integer.parseInt(ctx.formParam("quantityInStock"));
        double buyPrice = Double.parseDouble(ctx.formParam("buyPrice"));
        double purchasePrice = Double.parseDouble(ctx.formParam("purchasePrice"));

        Material material = new Material(productName, productType, productSize, unit, quantityInStock, buyPrice, purchasePrice);
        AdminMapper.addMaterial(material, connectionPool);

        ctx.redirect("/materials");
    }

    public static void deleteMaterial(Context ctx, ConnectionPool connectionPool) throws DatabaseException, SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        AdminMapper.deleteMaterial(id, connectionPool);
        ctx.redirect("/materials");
    }
    public static void getCalcMaterials(Context ctx, ConnectionPool connectionPool) throws DatabaseException, SQLException {

                List<Admin> calcMaterials = AdminMapper.getCalcMaterials(connectionPool);
                ctx.attribute("calcMaterials", calcMaterials);
                List<Material> materials = AdminMapper.getMaterials(connectionPool);
                ctx.attribute("materials", materials);
                ctx.render("admin_calculator.html");
    }

    public static void getCalcMaterialsById(Context ctx, ConnectionPool connectionPool) throws DatabaseException, SQLException {

        int materialId = Integer.parseInt(ctx.pathParam("id"));
        Admin calcMaterial = AdminMapper.getCalcMaterialsById(materialId, connectionPool);
        ctx.attribute("calcMaterial", calcMaterial);
        List<Material> materials = AdminMapper.getMaterials(connectionPool);
        ctx.attribute("materials", materials);
        ctx.render("admin_calculator_edit.html");
    }

    public static void editCalcMaterials(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        int materialsId = Integer.parseInt(ctx.formParam("materialsId"));
        AdminMapper.updateCalcMaterials(id,materialsId, connectionPool);
        ctx.redirect("/adminCalc");

        }


    public static void getDimensions(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        List<CarportLength> carportLength = CarportMapper.getCarportLength(connectionPool);
        ctx.attribute("carportLength", carportLength);
        List<CarportWidth> carportWidth = CarportMapper.getCarportWidth(connectionPool);
        ctx.attribute("carportWidth", carportWidth);
        List<ShedLength> shedLength = CarportMapper.getShedLength(connectionPool);
        ctx.attribute("shedLength", shedLength);
        List<ShedWidth> shedWidth = CarportMapper.getShedWidth(connectionPool);
        ctx.attribute("shedWidth", shedWidth);
        ctx.render("admin_carport_size.html");



    }

    public static void addCarportLength(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int length = Integer.parseInt(ctx.formParam("carportLength"));
        AdminMapper.addCarportLength(length, connectionPool);
        ctx.redirect("/carport_size");
    }

    public static void addCarportWidth(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int width = Integer.parseInt(ctx.formParam("carportWidth"));
        AdminMapper.addCarportWidth(width, connectionPool);
        ctx.redirect("/carport_size");
    }

    public static void addShedLength(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int length = Integer.parseInt(ctx.formParam("shedLength"));
        AdminMapper.addShedLength(length, connectionPool);
        ctx.redirect("/carport_size");
    }

    public static void addShedWidth(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int width = Integer.parseInt(ctx.formParam("shedWidth"));
        AdminMapper.addShedWidth(width, connectionPool);
        ctx.redirect("/carport_size");
    }

    public static void deleteCarportLength(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        AdminMapper.deleteCarportLength(id, connectionPool);
        ctx.redirect("/carport_size");
    }

    public static void deleteCarportWidth(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        AdminMapper.deleteCarportWidth(id, connectionPool);
        ctx.redirect("/carport_size");
    }

    public static void deleteShedLength(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        AdminMapper.deleteShedLength(id, connectionPool);
        ctx.redirect("/carport_size");
    }

    public static void deleteShedWidth(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        AdminMapper.deleteShedWidth(id, connectionPool);
        ctx.redirect("/carport_size");
    }
}






