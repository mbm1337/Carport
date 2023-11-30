package app.controllers;

import app.entities.Carport;
import app.entities.Shed;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.Objects;

public class CarportController {

    public static void carportDropdowns(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        //TODO: Make dropdowns dynamic

        ArrayList<Integer> carpotWidth = new ArrayList<>();
        carpotWidth.add(240);
        carpotWidth.add(300);
        carpotWidth.add(360);
        carpotWidth.add(420);
        carpotWidth.add(480);
        ArrayList<Integer> carportLength = new ArrayList<>();
        carportLength.add(240);
        carportLength.add(300);
        carportLength.add(360);
        carportLength.add(420);
        carportLength.add(480);
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


        ctx.attribute("carportWidth", carpotWidth);
        ctx.attribute("carportLength", carportLength);
        ctx.attribute("roof", roof);
        ctx.attribute("shedWidth", shedWidth);
        ctx.attribute("shedLength", shedLength);

        ctx.render("index.html");

    }

    public static void makeCarport(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        makeCarportWithShed(ctx);
        /*
        if (ctx.formParam("shedWidth").equals("Uden redskabsrum") || ctx.formParam("shedLength").equals("Uden redskabsrum")) {
            makeCarportWithoutShed(ctx, connectionPool);
        } else {
            makeCarportWithShed(ctx, connectionPool);
        }

         */
        ctx.render("contact.html");
    }

    public static void makeCarportWithoutShed(Context ctx) {
        int width = Integer.parseInt(ctx.formParam("carportWidth"));
        int length = Integer.parseInt(ctx.formParam("carportLength"));
        //int height = Integer.parseInt(ctx.formParam("carportHeight"));
        if (ctx.formParam("roof").equals("Uden tagplader")) {
            Carport carport = new Carport(width, length, 250);
            ctx.sessionAttribute("carport", carport);
        } else {
            String roof = ctx.formParam("roof");
            Carport carport = new Carport(width, length, 250, roof);
            ctx.sessionAttribute("carport", carport);
        }

    }

    public static void makeCarportWithShed(Context ctx) throws DatabaseException {
        int width = Integer.parseInt(ctx.formParam("carportWidth"));
        int length = Integer.parseInt(ctx.formParam("carportLength"));
        //int height = Integer.parseInt(ctx.formParam("height"));
        int shedWidth = Integer.parseInt(ctx.formParam("shedWidth"));
        int shedLength = Integer.parseInt(ctx.formParam("shedLength"));

        Shed shed = new Shed(shedWidth, shedLength);

        if (ctx.formParam("roof").equals("Uden tagplader")) {
            Carport carport = new Carport(width, length, 250, shed);
            ctx.sessionAttribute("carport", carport);
        } else {
            String roof = ctx.formParam("roof");
            Carport carport = new Carport(width, length, 250, roof ,shed);
            ctx.sessionAttribute("carport", carport);
        }
    }

}
