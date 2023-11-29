package app.controllers;

import app.entities.Carport;
import app.entities.Shed;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import io.javalin.http.Context;

import java.util.ArrayList;

public class CarportController {

    public static void carportDropdowns(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        //TODO: Make dropdowns dynamic

        ArrayList<Integer> width = new ArrayList<>();
        width.add(240);
        width.add(300);
        width.add(360);
        width.add(420);
        width.add(480);
        ArrayList<Integer> length = new ArrayList<>();
        length.add(240);
        length.add(300);
        length.add(360);
        length.add(420);
        length.add(480);
        ArrayList<String> roof = new ArrayList<>();
        roof.add("Uden tagplader");
        roof.add("Plasttrapezplader");
        ArrayList<Integer> shedWidth = new ArrayList<>();
        shedWidth.add(240);
        shedWidth.add(300);
        shedWidth.add(360);
        shedWidth.add(420);
        shedWidth.add(480);
        ArrayList<Integer> shedLength = new ArrayList<>();
        shedLength.add(240);
        shedLength.add(300);
        shedLength.add(360);
        shedLength.add(420);
        shedLength.add(480);


        ctx.attribute("carportWidth", width);
        ctx.attribute("carportLength", length);
        ctx.attribute("roof", roof);
        ctx.attribute("shedWidth", shedWidth);
        ctx.attribute("shedLength", shedLength);

        ctx.render("index.html");

    }

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
