package app;

import app.config.ThymeleafConfig;

import app.controllers.*;
import app.controllers.AdminController;
import app.controllers.ShippingController;
import app.controllers.StandardCarportController;
import app.entities.StandardCarport;
import app.controllers.CarportController;
import app.controllers.OrderController;
import app.controllers.UserController;
import app.controllers.ZipController;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Main {
    
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    public static void main(String[] args)  {
        // Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            JavalinThymeleaf.init(ThymeleafConfig.templateEngine());

        }).start(7070);

        // Routing
        app.get("/", ctx -> ctx.render("index.html"));
        app.post("/byg-selv", ctx -> CarportController.carportDropdowns(ctx, connectionPool));
        app.get("/byg-selv", ctx -> CarportController.carportDropdowns(ctx, connectionPool));
        app.post("/carport", ctx -> CarportController.makeCarport(ctx, connectionPool));
        app.post("/adresse",ctx-> ZipController.cityAndZip(ctx,connectionPool));
        app.post("/status", ctx -> OrderController.getStatus(ctx, connectionPool));
        app.post("/login", ctx -> UserController.login(ctx, connectionPool));
        app.get("/login", ctx -> ctx.render("login.html"));
       // app.get("/createuser", ctx -> UserController.createuser(ctx, connectionPool));
        app.post("/createuser", ctx -> UserController.createuser(ctx, connectionPool));
        app.get("/createuser", ctx -> ctx.render("createuser.html"));
        //app.get("/carportone", ctx -> ctx.render("carportone.html"));
        app.get("/carportone", ctx -> ShippingController.getShippingInfoByZip(ctx, connectionPool));
        app.post("/carportone", ctx -> ShippingController.getShippingInfoByZip(ctx, connectionPool));
        app.get("/carports", ctx -> StandardCarportController.getStandardCarportsForFrontPage(ctx, connectionPool));
        app.post("/carport_info/{id}", ctx -> StandardCarportController.getStandardCarport(ctx, connectionPool));
        app.post("/shipping_cal", ctx -> ShippingController.getShippingInfoByZip(ctx, connectionPool));
        app.get("/shipping_cal", ctx -> ShippingController.getShippingInfoByZip(ctx, connectionPool));
        //admin funtioner

        app.post("/updateUser", ctx -> AdminController.editBalance(ctx, connectionPool));
        app.post("/edit_matreriel/{id}", ctx -> AdminController.editMaterial(ctx, connectionPool));
        app.get("/ordre", ctx -> AdminController.getUsersAndOrders(ctx, connectionPool));
        app.post("/tilbud/{ordernumber}", ctx -> AdminController.getOrderDetails(ctx, connectionPool));
        app.get("/tilbud/{ordernumber}", ctx -> AdminController.getOrderDetails(ctx, connectionPool));
        app.get("/materials", ctx -> AdminController.getMaterial(ctx, connectionPool));
        app.post("/updatematerials/{id}", ctx -> AdminController.updateMaterial(ctx, connectionPool));
        app.get("/add_matreriel", ctx -> ctx.render("add_matreriel.html"));
        app.post("/add_matreriel", ctx -> AdminController.addMaterial(ctx, connectionPool));
        app.post("/delete_matreriel/{id}", ctx -> AdminController.deleteMaterial(ctx, connectionPool));
        app.get("/adminCalc", ctx -> AdminController.getCalcMaterials(ctx, connectionPool));
        app.get("/adminCalc/{id}", ctx -> AdminController.getCalcMaterialsById(ctx, connectionPool));
        app.post("/adminCalc/{id}", ctx -> AdminController.getCalcMaterialsById(ctx, connectionPool));
        app.post("/adminCalc/{id}/edit", ctx -> AdminController.editCalcMaterials(ctx, connectionPool));
        app.get("/carport_size", ctx -> AdminController.getDimensions(ctx, connectionPool));
        app.post("/add_carportlength", ctx -> AdminController.addCarportLength(ctx, connectionPool));
        app.post("/add_carportwidth", ctx -> AdminController.addCarportWidth(ctx, connectionPool));
        app.post("/add_shedlength", ctx -> AdminController.addShedLength(ctx, connectionPool));
        app.post("/add_shedwidth", ctx -> AdminController.addShedWidth(ctx, connectionPool));
        app.post("/delete_carportlength/{id}", ctx -> AdminController.deleteCarportLength(ctx, connectionPool));
        app.post("/delete_carportwidth/{id}", ctx -> AdminController.deleteCarportWidth(ctx, connectionPool));
        app.post("/delete_shedlength/{id}", ctx -> AdminController.deleteShedLength(ctx, connectionPool));
        app.post("/delete_shedwidth/{id}", ctx -> AdminController.deleteShedWidth(ctx, connectionPool));

        app.get("/svg", SvgController::getSvg);

    }
}
