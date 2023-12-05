package app;

import app.config.ThymeleafConfig;
import app.controllers.AdminController;
import app.controllers.CarportController;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Main {
    private static final String USER = "postgres";
    private static final String PASSWORD = "gruppeg";
    private static final String URL = "jdbc:postgresql://46.101.146.168:5432/%s?currentSchema=public";
    private static final String DB = "carport";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    public static void main(String[] args)  {
// Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            JavalinThymeleaf.init(ThymeleafConfig.templateEngine());
        }).start(7070);

        // Routing
        app.get("/", ctx -> CarportController.carportDropdowns(ctx, connectionPool));
        app.post("/carport", ctx -> CarportController.makeCarport(ctx, connectionPool));
        app.get("/ordre", ctx -> AdminController.getUsersAndOrders(ctx, connectionPool));
        app.post("/tilbud/{ordernumber}", ctx -> AdminController.getOrderDetails(ctx, connectionPool));







    }
}
