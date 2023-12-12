package app.controllers;

import app.entities.Calculator;
import app.entities.Carport;
import app.entities.Order;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.MaterialMapper;
import app.persistence.OrderMapper;

import io.javalin.http.Context;

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

    public static void insertingAnOrder(Context ctx, ConnectionPool connectionPool) {
        try {
            Carport carport = ctx.sessionAttribute("carport");
            String comment = ctx.formParam("comment");
            int width = Integer.parseInt(ctx.formParam("carportWidth"));
            int length = Integer.parseInt(ctx.formParam("carportLength"));

            double total = calculatePrice(ctx, connectionPool, length);

            Order order = new Order("under process", total, length, width, comment);

            OrderMapper.insertOrder(order, connectionPool);
            ctx.render("price.html");
        } catch (NumberFormatException | DatabaseException e) {
            ctx.attribute("error", "We couldn't save the order: " + e.getMessage());
            ctx.render("adresse.html");
        }
    }

    public static double calculatePrice(Context ctx, ConnectionPool connectionPool, int carportLength) {
        Calculator calc = new Calculator();
        int width = Integer.parseInt(ctx.formParam("carportWidth"));

        int numberOfPosts = calc.numberOfPosts(carportLength);
        int numberOfBeams = calc.beamAmount(carportLength);
        int numberOfRafts = calc.numberOfRafts(carportLength, width);

        int totalPrice = calculateTotalCost(numberOfPosts, numberOfBeams, numberOfRafts);

        ctx.attribute("totalPrice", totalPrice);
        return totalPrice;
    }

    private static int calculateTotalCost(int numberOfPosts, int numberOfBeams, int numberOfRafts) {
        // Provide the implementation for the total cost calculation
        return 0;
    }
}
