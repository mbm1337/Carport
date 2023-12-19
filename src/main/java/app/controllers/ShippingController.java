package app.controllers;

import app.entities.Shipping;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.ShippingMapper;
import io.javalin.http.Context;

public class ShippingController {
    public static void getShippingInfoByZip(Context ctx, ConnectionPool connectionPool) {
        try {
        String zipParam = ctx.formParam("zip");

        if(zipParam != null) {
            int zipCode = Integer.parseInt(zipParam);
            Shipping shipping = ShippingMapper.getShippingInfoByZip(zipCode, connectionPool);

            ctx.sessionAttribute("shipping", shipping);
            ctx.attribute("shippingPrice", shipping.getShippingPrice());
            ctx.attribute("shippingTime", shipping.getShippingTime());
        }
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
        }
        ctx.render("shipping_cal.html");
    }

}

