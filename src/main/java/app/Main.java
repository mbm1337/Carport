package app;

import app.config.ThymeleafConfig;
import app.controllers.*;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Main {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    public static void main(String[] args)  {
        // Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            JavalinThymeleaf.init(ThymeleafConfig.templateEngine());

        }).start(7078

        );

        // User Routing
        app.get("/", ctx -> AdminController.cheklogin(ctx, connectionPool));
        app.post("/byg-selv", ctx -> CarportController.carportDropdowns(ctx, connectionPool));
        app.get("/byg-selv", ctx -> CarportController.carportDropdowns(ctx, connectionPool));
        app.post("/carport", ctx -> CarportController.makeCarport(ctx, connectionPool));
        app.post("/adresse",ctx-> ZipController.cityAndZip(ctx,connectionPool));
        app.post("/status", ctx -> OrderController.getStatus(ctx, connectionPool));
        app.post("/login", ctx -> UserController.login(ctx, connectionPool));
        app.get("/login", ctx -> ctx.render("login.html"));
        app.post("/createuser", ctx -> UserController.createuser(ctx, connectionPool));
        app.get("/createuser", ctx -> ctx.render("createuser.html"));
        app.get("/price", ctx -> OrderController.calculatePrice(ctx, connectionPool));
        app.post("/price", ctx -> OrderController.calculatePrice(ctx, connectionPool));
        app.post("/carports", ctx -> StandardCarportController.getStandardCarportsForFrontPage(ctx, connectionPool));
        app.get("/carports", ctx -> StandardCarportController.getStandardCarportsForFrontPage(ctx, connectionPool));
        app.post("/carport_info/{id}", ctx -> StandardCarportController.getStandardCarport(ctx, connectionPool));
        app.post("/shipping_cal", ctx -> ShippingController.getShippingInfoByZip(ctx, connectionPool));
        app.get("/shipping_cal", ctx -> ShippingController.getShippingInfoByZip(ctx, connectionPool));
        app.get("/svg/{ordernumber}",ctx -> SvgController.getSvg(ctx, connectionPool));
        app.get("/order",ctx -> OrderController.getOrders(ctx, connectionPool));
        app.post("/order",ctx -> OrderController.getOrders(ctx, connectionPool));
        app.get("/showOrderDetails/{ordernumber}",ctx -> OrderController.showOrderDetails(ctx, connectionPool));
        app.post("/showOrderDetails",ctx -> ctx.render("mymaterial.html"));
        app.post("/updateuser", ctx -> UserController.updateUser(ctx, connectionPool));
        app.get("/updateuser",ctx-> AdminController.checkIfAdminAndRender("usersetting.html",ctx, connectionPool));
        app.get("/logout", ctx -> UserController.logout(ctx));


        //Admin Routing
        app.get("/admin", ctx -> AdminController.checkIfAdminAndRender("admin.html",ctx, connectionPool));
        app.post("/updatePrice", ctx -> AdminController.editBalance(ctx, connectionPool));
        app.post("/edit_matreriel/{id}", ctx -> AdminController.editMaterial(ctx, connectionPool));
        app.post("/adminordre", ctx -> AdminController.getUsersAndOrders("adminordre.html",ctx, connectionPool));
        app.get("/adminordre", ctx -> AdminController.getUsersAndOrders("adminordre.html",ctx, connectionPool));
        app.post("/tilbud/{ordernumber}", ctx -> AdminController.getOrderDetails(ctx, connectionPool));
        app.get("/tilbud/{ordernumber}", ctx -> AdminController.getOrderDetails(ctx, connectionPool));
        app.post("/sendMail", ctx -> AdminController.changeStatus(ctx, connectionPool));
        app.post("/materials", ctx -> AdminController.getMaterial(ctx, connectionPool));
        app.get("/materials", ctx -> AdminController.getMaterial(ctx, connectionPool));
        app.post("/updatematerials/{id}", ctx -> AdminController.updateMaterial(ctx, connectionPool));
        app.get("/add_matreriel", ctx -> AdminController.checkIfAdminAndRender("add_matreriel.html",ctx, connectionPool));
        app.post("/add_matreriel", ctx -> AdminController.addMaterial(ctx, connectionPool));
        app.post("/delete_matreriel/{id}", ctx -> AdminController.deleteMaterial(ctx, connectionPool));
        app.post("/adminCalc", ctx -> AdminController.getCalcMaterials(ctx, connectionPool));
        app.get("/adminCalc", ctx -> AdminController.getCalcMaterials(ctx, connectionPool));
        app.get("/adminCalc/{id}", ctx -> AdminController.getCalcMaterialsById(ctx, connectionPool));
        app.post("/adminCalc/{id}", ctx -> AdminController.getCalcMaterialsById(ctx, connectionPool));
        app.post("/adminCalc/{id}/edit", ctx -> AdminController.editCalcMaterials(ctx, connectionPool));
        app.post("/carport_size", ctx -> AdminController.getDimensions(ctx, connectionPool));
        app.get("/carport_size", ctx -> AdminController.getDimensions(ctx, connectionPool));
        app.post("/add_carportlength", ctx -> AdminController.addCarportLength(ctx, connectionPool));
        app.post("/add_carportwidth", ctx -> AdminController.addCarportWidth(ctx, connectionPool));
        app.post("/add_shedlength", ctx -> AdminController.addShedLength(ctx, connectionPool));
        app.post("/add_shedwidth", ctx -> AdminController.addShedWidth(ctx, connectionPool));
        app.post("/delete_carportlength/{id}", ctx -> AdminController.deleteCarportLength(ctx, connectionPool));
        app.post("/delete_carportwidth/{id}", ctx -> AdminController.deleteCarportWidth(ctx, connectionPool));
        app.post("/delete_shedlength/{id}", ctx -> AdminController.deleteShedLength(ctx, connectionPool));
        app.post("/delete_shedwidth/{id}", ctx -> AdminController.deleteShedWidth(ctx, connectionPool));
        app.post("/delete_order/{orderId}", ctx -> OrderController.deleteOrder(ctx, connectionPool));
        app.get("/adminuser", ctx -> UserController.getUser(ctx, connectionPool));
        app.post("/adminuser", ctx -> UserController.getUser(ctx, connectionPool));
        app.get("/admin_user_details/{id}", ctx ->  UserController.showUserDetails(ctx, connectionPool));
        app.post("/admin_user_details/{id}", ctx ->  UserController.showUserDetails(ctx, connectionPool));
        app.post("/deleteUser/{id}", ctx ->  UserController.deleteUserWithOrders(ctx, connectionPool));
    }
}
