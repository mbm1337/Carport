package app.controllers;

import app.entities.Carport;
import app.entities.City;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.ZipMapper;
import io.javalin.http.Context;



public class ZipController {
    public static void cityAndZip(Context ctx, ConnectionPool connectionpool) throws DatabaseException {

        try {
            int zip = Integer.parseInt(ctx.formParam("zip"));
            City city = ZipMapper.getCityByZip(zip, connectionpool);
            ctx.attribute("city", city);
            Carport carport = ctx.sessionAttribute("carport");
            ctx.render("index.html");


        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
        }


    }
}
