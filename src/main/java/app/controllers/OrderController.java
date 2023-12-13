package app.controllers;

import app.entities.Calculator;
import app.entities.Carport;
import app.entities.Material;
import app.entities.Order;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.MaterialMapper;
import app.persistence.OrderMapper;

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
    public static void insertingAnOrder(Context ctx, ConnectionPool connectionpool) throws DatabaseException {

        Carport carport = ctx.sessionAttribute("carport");
        int width = Integer.parseInt(ctx.formParam("carportWidth"));
        int length = Integer.parseInt(ctx.formParam("carportLength"));
        int shedWidth = Integer.parseInt(ctx.formParam("shedWidth"));
        int shedLength = Integer.parseInt(ctx.formParam("shedLength"));

        double total = calculatePrice(ctx, connectionpool);
         Order order = new Order("under process",  total,  length,  width);



            try {
            OrderMapper.insertOrder(order, connectionpool);
            ctx.render("price.html");

        } catch (NumberFormatException | DatabaseException e) {
            ctx.attribute("we couldnt save the order " + e.getMessage());
            ctx.render("adresse.html");
        }


    }
        public static double calculatePrice(Context ctx, ConnectionPool connectionPool) {
            List<Material>materials = new ArrayList<>();

            Calculator calc = new Calculator();
            Carport carport = ctx.sessionAttribute("carport");
            int width = Integer.parseInt(ctx.formParam("carportWidth"));
            int length = Integer.parseInt(ctx.formParam("carportLength"));

            int spær600 = MaterialMapper.getPrice(9, connectionPool);
            int spær480 = MaterialMapper.getPrice(10, connectionPool);
            int posts = MaterialMapper.getPrice(12, connectionPool);

            int numberOfPosts = calc.numberOfPosts(length);
            int numberOfBeams = calc.beamAmount(length);
            int numberOfRafts = calc.numberOfRaft(length, width);

            int totalPostsCost = numberOfPosts * posts;

            int totalRaft;
            if (numberOfRafts >= 480) {
                totalRaft = numberOfRafts * spær480;
            } else {
                totalRaft = numberOfRafts * spær600;
            }

            int totalBeam;
            if (numberOfBeams >= 480) {
                totalBeam = numberOfBeams * spær480;
            } else {
                totalBeam = numberOfBeams * spær600;
            }

            int totalPrice = totalPostsCost + totalRaft + totalBeam;

            ctx.attribute("totalPrice", totalPrice);
            carport = ctx.sessionAttribute("carport");

            return totalPrice;
        }


    }
