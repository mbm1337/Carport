package app.controllers;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.MaterialMapper;
import app.persistence.OrderMapper;

import app.util.Calculator;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class OrderController {
    public static void getStatus(Context ctx, ConnectionPool connectionpool) {
        try {
            int userId = Integer.parseInt(ctx.formParam("userid"));
            String order= OrderMapper.getOrderStatusByUserId(userId, connectionpool);

            ctx.attribute("status", order);
            ctx.render("status.html");
        } catch (NumberFormatException | DatabaseException e) {
            ctx.attribute("error", "We couldn't track your order: " + e.getMessage());
            ctx.render("status.html");
        }
    }
    public static void insertingAnOrder(int totalPrice, Context ctx, ConnectionPool connectionpool) throws DatabaseException {
        try {
            List<Material> materials = ctx.sessionAttribute("quantityordered");

            String comments = ctx.formParam("comments");
            Carport carport = ctx.sessionAttribute("carport");
            User user = ctx.sessionAttribute("currentUser");
            Order order = new Order("under process", user.getId(), carport.getLength(), carport.getWidth(), comments);
            int newOrderId = OrderMapper.insertOrder(order, totalPrice,connectionpool);


            // Loop through the materials list and create order details
            for (Material material : materials) {
                OrderMapper.createOrderDetailsDatabase(newOrderId, order ,material.getId(), material.getQuantityordered(), connectionpool);
            }

            ctx.render("price.html");

        } catch (NumberFormatException | DatabaseException e) {
            // Handle errors
            e.printStackTrace();
            ctx.attribute("error_message", "We couldn't save the order: " + e.getMessage());
            ctx.render("adresse.html");
        }
    }


    public static double calculatePrice(Context ctx, ConnectionPool connectionPool) {
        List<Material> materials = new ArrayList<>();

        Calculator calc = new Calculator();
        Carport carport = ctx.sessionAttribute("carport");

        int spaer600 = MaterialMapper.getPrice(9, connectionPool);
        int spaer480 = MaterialMapper.getPrice(10, connectionPool);
        int posts = MaterialMapper.getPrice(12, connectionPool);

        int length = carport.getLength();  // Hent længde fra session
        int width = carport.getWidth();    // Hent bredde fra session

        int numberOfPosts = calc.numberOfPosts(length);
        int numberOfBeams = calc.beamAmount(length);
        int numberOfRafts = calc.numberOfRaft(length, width);

        int totalPostsCost = numberOfPosts * posts;

        int totalRaft;
        if (numberOfRafts >= 480) {
            totalRaft = numberOfRafts * spaer480;
        } else {
            totalRaft = numberOfRafts * spaer600;
        }

        int totalBeam;
        if (numberOfBeams >= 480) {
            totalBeam = numberOfBeams * spaer480;
        } else {
            totalBeam = numberOfBeams * spaer600;
        }

        int totalPrice = totalPostsCost + totalRaft + totalBeam;
        materials.add(new Material(9,numberOfBeams));
        materials.add(new Material(10, numberOfRafts));
        materials.add(new Material(12, numberOfPosts));


        ctx.sessionAttribute("quantityordered", materials);

        try {
            OrderController.insertingAnOrder(totalPrice,ctx, connectionPool);
        } catch (DatabaseException e) {
            // Handle exception if needed
            e.printStackTrace();
        }


        return totalPrice;
    }

}


