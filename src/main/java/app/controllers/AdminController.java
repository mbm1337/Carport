package app.controllers;
import app.entities.*;
import app.persistence.*;
import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.AdminMapper;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
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

        List<Material> materials = AdminMapper.getMaterials(connectionPool);
        ctx.attribute("materials", materials);

        ctx.render("materials.html");

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
        /*List<CarportWidth> carportWidth = AdminMapper.getCarportWidth(connectionPool);
        ctx.attribute("carportWidth", carportWidth);
        List<ShedLength> shedLengths = AdminMapper.getShedLength(connectionPool);
        ctx.attribute("shedLengths", shedLengths);
        List<ShedWidth> shedWidths = AdminMapper.getShedWidth(connectionPool);
        ctx.attribute("shedWidths", shedWidths);*/
        ctx.render("admin_carport_size.html");



    }
}






