package app.controllers;

import app.entities.Shipping;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.ShippingMapper;
import io.javalin.http.Context;

import java.util.List;

public class ShippingController {
// TODO: Make a method that gets the shipping price depending on the zip code
    public static void getShippingInfoByZip(Context ctx, ConnectionPool connectionPool) {
        try {
        String zipParam = ctx.formParam("zip");

        if (zipParam != null) {
            int zipCode = Integer.parseInt(zipParam);
            Shipping shipping = ShippingMapper.getShippingInfoByZip(zipCode, connectionPool);

            ctx.sessionAttribute("shipping", shipping);
            ctx.sessionAttribute("shippingPrice", shipping.getShippingPrice());
            ctx.sessionAttribute("shippingTime", shipping.getShippingTime());
         }
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
        }
        ctx.render("carport_info.html");
    }

}

