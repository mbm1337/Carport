package app.controllers;

import app.entities.Shipping;
import app.entities.StandardCarport;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.ShippingMapper;
import io.javalin.http.Context;

public class ShippingController {

    public static void getShippingInfoByZip(Context ctx, ConnectionPool connectionPool) {
        try {
            int shippingPrice = Integer.parseInt(ctx.formParam("shippingPrice"));
            Shipping shipping = ShippingMapper.getShippingInfoByZip(ctx, connectionPool);

            if (shipping != null) {
                ctx.sessionAttribute("shipping", shipping);
                ctx.attribute("shippingPrice", shippingPrice);
                ctx.render("carportone.html");
            }

        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
        }
    }
}

