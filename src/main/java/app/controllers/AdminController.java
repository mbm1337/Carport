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
            ctx.sessionAttribute("ordernumber",orderNumber);
            ctx.attribute("order", orderDetail);
            ctx.render("tilbud.html");


    }
    }