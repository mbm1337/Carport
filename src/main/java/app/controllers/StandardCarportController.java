package app.controllers;

import app.entities.StandardCarport;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.StandardCarportMapper;
import io.javalin.http.Context;

import java.util.List;
import java.util.Objects;

public class StandardCarportController {

    public static void getStandardCarport(Context ctx, ConnectionPool connectionPool) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            StandardCarport standardCarport = StandardCarportMapper.getCarportById(id, connectionPool);
                    ctx.attribute("standardCarport", standardCarport);
                    ctx.render("carport_info.html");
        } catch (DatabaseException | NumberFormatException e) {
            ctx.attribute("message", e.getMessage());
        }
    }

    public static void getStandardCarportsForFrontPage(Context ctx, ConnectionPool connectionPool) {
        try {
            List<StandardCarport> allCarports = StandardCarportMapper.getAllCarports(connectionPool);
            ctx.attribute("allCarports", allCarports);
            ctx.render("carports.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
        }

    }
}