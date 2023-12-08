package app.controllers;

import app.entities.StandardCarport;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.StandardCarportMapper;
import io.javalin.http.Context;

public class StandardCarportController {

    public static void getStandardCarport(Context ctx, ConnectionPool connectionPool) {
        try {
            String idParam = ctx.pathParam("id");


                int id = Integer.parseInt(idParam);
                StandardCarport standardCarport = StandardCarportMapper.getCarportById(id, connectionPool);


                if (standardCarport != null) {


                    ctx.sessionAttribute("standardCarport", standardCarport);
                    ctx.sessionAttribute("merchandiser", standardCarport.getMerchandiser());
                    ctx.sessionAttribute("productName", standardCarport.getProductName());
                    ctx.sessionAttribute("price", standardCarport.getPrice());
                    ctx.sessionAttribute("description", standardCarport.getDescription());
                    }

        } catch (DatabaseException | NumberFormatException e) {
            ctx.attribute("message", e.getMessage());
        }
        ctx.render("carport_info.html");
    }

}