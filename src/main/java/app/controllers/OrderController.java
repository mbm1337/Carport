package app.controllers;

import app.entities.City;

import app.entities.Order;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
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
}