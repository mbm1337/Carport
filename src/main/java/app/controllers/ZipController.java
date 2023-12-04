package app.controllers;

import app.entities.Carport;
import app.entities.City;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.ZipMapper;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ZipController {
    public static void cityAndZip(Context ctx, ConnectionPool connectionpool) throws DatabaseException {

        try {

            List<City> cities = new ArrayList<>(ZipMapper.citiesbyzip(connectionpool));
            ctx.sessionAttribute("cities", cities);
            ctx.render("adresse.html");


        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            System.out.println(e);
        }


    }

    public static void getCityByZip(Context ctx, ConnectionPool connectionpool) {

        int zip = Integer.parseInt(ctx.formParam("zip"));
        try {

            City city = ZipMapper.getCityByZip(zip, connectionpool);
            ctx.attribute("city", city);
            ctx.render("adresse.html");
        } catch (DatabaseException e) {
            ctx.attribute("kunne ikke finde zip", e.getMessage());
            ctx.render("adresse.html");
        }
    }
}

