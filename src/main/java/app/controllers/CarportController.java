package app.controllers;

import app.entities.Carport;
import app.entities.Shed;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import io.javalin.http.Context;

public class CarportController {

    public static void makeCarportWithoutShed(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int width = Integer.parseInt(ctx.formParam("width"));
        int length = Integer.parseInt(ctx.formParam("length"));
        int height = Integer.parseInt(ctx.formParam("height"));

        Carport carport = new Carport(width, length, height);

        ctx.sessionAttribute("carport", carport);

    }

    public static void makeCarportWithShed(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int width = Integer.parseInt(ctx.formParam("width"));
        int length = Integer.parseInt(ctx.formParam("length"));
        int height = Integer.parseInt(ctx.formParam("height"));
        int shedWidth = Integer.parseInt(ctx.formParam("shedWidth"));
        int shedLength = Integer.parseInt(ctx.formParam("shedLength"));

        Shed shed = new Shed(shedWidth, shedLength);

        Carport carport = new Carport(width, length, height, shed);

        ctx.sessionAttribute("carport", carport);

    }

}
