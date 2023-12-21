package app.controllers;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.CarportMapper;
import app.persistence.ConnectionPool;
import io.javalin.http.Context;
import java.util.List;
import java.util.Map;

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


        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;

            ctx.render("carportbuilder.html", Map.of("isAdmin", isAdmin, "isUser", isUser));

        }else {
            ctx.render("carportbuilder.html");
        }



    }

    public static void makeCarport(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        if (ctx.formParam("shed").equals("shed")){
            makeCarportWithShed(ctx);
        } else {
            makeCarportWithoutShed(ctx);
        }



    }

    public static void makeCarportWithoutShed(Context ctx) {
        int width = Integer.parseInt(ctx.formParam("carportWidth"));
        int length = Integer.parseInt(ctx.formParam("carportLength"));
        //int height = Integer.parseInt(ctx.formParam("carportHeight"));
        Carport carport = new Carport(width, length, 270);
        ctx.sessionAttribute("carport", carport);
        ctx.render("adresse.html");


    }

    public static void makeCarportWithShed(Context ctx) throws DatabaseException {
        int carportWidth = Integer.parseInt(ctx.formParam("carportWidth"));
        int carportLength = Integer.parseInt(ctx.formParam("carportLength"));
        //int height = Integer.parseInt(ctx.formParam("height"));
        int shedWidth = Integer.parseInt(ctx.formParam("shedWidth"));
        int shedLength = Integer.parseInt(ctx.formParam("shedLength"));
        boolean shedside = Boolean.parseBoolean(ctx.formParam("shedside"));


        int eaves = 30;
        if (shedWidth > (carportWidth-eaves)|| shedLength > carportLength) {

            ctx.attribute("error", "Dit skur er for stort til din carport. Prøv igen." + "\n" +
                    "Hvis du ønsker at skuret skal være større end carporten, beskriv i kommentarfeltet.");
            carportDropdowns(ctx, ConnectionPool.getInstance());

        } else {
            Shed shed = new Shed(shedWidth, shedLength, shedside);
            String roof = ctx.formParam("roof");
            Carport carport = new Carport(carportWidth, carportLength, 270, roof, shed);
            ctx.sessionAttribute("shed", shed);
            ctx.sessionAttribute("carport", carport);
            ctx.render("adresse.html");

        }

    }



}
