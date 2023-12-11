package app.controllers;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;

import app.persistence.UserMapper;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;




public class OrderController {
    public static void getStatus(Context ctx, ConnectionPool connectionpool) {
        try {
            int userId = Integer.parseInt(ctx.formParam("userid"));
            String order = OrderMapper.getOrderStatusByUserId(userId, connectionpool);

            ctx.attribute("status", order);
            ctx.render("status.html");
        } catch (NumberFormatException | DatabaseException e) {
            ctx.attribute("error", "We couldn't track your order: " + e.getMessage());
            ctx.render("status.html");
        }


    }

    public static void insertingAnOrder(Context ctx, ConnectionPool connectionpool) throws DatabaseException {

        Carport carport = ctx.sessionAttribute("carport");
        String comment = ctx.formParam("comment");
        int width = Integer.parseInt(ctx.formParam("carportWidth"));
        int length = Integer.parseInt(ctx.formParam("carportLength"));
        int shedWidth = Integer.parseInt(ctx.formParam("shedWidth"));
        int shedLength = Integer.parseInt(ctx.formParam("shedLength"));
      //  int price = Integer.parseInt(ctx.formParam("price"));

        Order order = new Order("paid",comment,100,length,width);
        try {
            OrderMapper.insertOrder(order, connectionpool);
            ctx.render("order.html");

        } catch (NumberFormatException | DatabaseException e) {
            ctx.attribute("we couldnt save the order " + e.getMessage());
            ctx.render("adresse.html");
        }


    }
}