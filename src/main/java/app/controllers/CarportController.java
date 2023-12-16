package app.controllers;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.CarportMapper;
import app.persistence.ConnectionPool;
import io.javalin.http.Context;
import java.util.List;

public class CarportController {

    public static void carportDropdowns(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        List<CarportWidth> carpotWidth = CarportMapper.getCarportWidth(connectionPool);
        ctx.attribute("carportWidth", carpotWidth);

        List<CarportLength> carportLength = CarportMapper.getCarportLength(connectionPool);
        ctx.attribute("carportLength", carportLength);

        List<ShedWidth> shedWidth = CarportMapper.getShedWidth(connectionPool);
        ctx.attribute("shedWidth", shedWidth);

        List<ShedLength> shedLength = CarportMapper.getShedLength(connectionPool);
        ctx.attribute("shedLength", shedLength);

        List<String> roof = CarportMapper.getRoof(connectionPool);
        ctx.attribute("roof", roof);


        ctx.render("carportbuilder.html");

    }

    public static void makeCarport(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        if (ctx.formParam("shed").equals("shed")){
            makeCarportWithShed(ctx);
        } else {
            makeCarportWithoutShed(ctx);
        }

         ctx.render("adresse.html");
        Carport carport = ctx.sessionAttribute("carport");
        MailSenderController.sendCarportDetailsEmail(carport, "fog.carports@gmail.com");

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
