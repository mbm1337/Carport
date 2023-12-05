package app.controllers;

import app.entities.StandardCarport;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.StandardCarportMapper;
import io.javalin.http.Context;

public class StandardCarportController {
    //TODO: Make shipping calculator. The code that is need is in the CarportController class.
    // When that is done, change the parameters and variables to fit the shipping calculator.
    // The data needs to come from the database. Where the data is coming from CalShippingMapper class.

    public static void getShippingInfoByZip(Context ctx, ConnectionPool connectionpool) throws DatabaseException {

        try {
            int zip = Integer.parseInt(ctx.formParam("zip"));
            int shippingPrice = Integer.parseInt(ctx.formParam("shippingPrice"));
            int shippingTime = Integer.parseInt(ctx.formParam("shippingTime"));


            StandardCarportMapper.getShippingInfoByZip(zip, connectionpool);
            StandardCarport standardCarport = ctx.sessionAttribute("standardCarport");
            ctx.attribute("zip", zip);
            ctx.attribute("shippingPrice", shippingPrice);
            ctx.attribute("shippingTime", shippingTime);

            ctx.render("carportone.html");


        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
        }

    }
}
