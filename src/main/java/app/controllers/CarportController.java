package app.controllers;

import app.entities.Carport;
import app.entities.Shed;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import io.javalin.http.Context;

public class CarportController {

    public static void makeCarport(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        if (ctx.formParam("shedWidth").equals("Uden redskabsrum") || ctx.formParam("shedLength").equals("Uden redskabsrum")) {
            makeCarportWithoutShed(ctx, connectionPool);
        } else {
            makeCarportWithShed(ctx, connectionPool);
        }
    }

    public static void makeCarportWithoutShed(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int width = Integer.parseInt(ctx.formParam("width"));
        int length = Integer.parseInt(ctx.formParam("length"));
        int height = Integer.parseInt(ctx.formParam("height"));
        if (ctx.formParam("trapezroof").equals("Uden tagplader")) {
            Carport carport = new Carport(width, length, height, false);
            ctx.sessionAttribute("carport", carport);
        } else {
            Carport carport = new Carport(width, length, height, true);
            ctx.sessionAttribute("carport", carport);
        }

    }

    public static void makeCarportWithShed(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int width = Integer.parseInt(ctx.formParam("width"));
        int length = Integer.parseInt(ctx.formParam("length"));
        int height = Integer.parseInt(ctx.formParam("height"));
        int shedWidth = Integer.parseInt(ctx.formParam("shedWidth"));
        int shedLength = Integer.parseInt(ctx.formParam("shedLength"));

        Shed shed = new Shed(shedWidth, shedLength);

        if (ctx.formParam("trapezroof").equals("Uden tagplader")) {
            Carport carport = new Carport(width, length, height, false, shed);
            ctx.sessionAttribute("carport", carport);
        } else {
            Carport carport = new Carport(width, length, height, true, shed);
            ctx.sessionAttribute("carport", carport);
        }


    }

}
