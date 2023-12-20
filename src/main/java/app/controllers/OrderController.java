package app.controllers;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.*;
import app.util.Calculator;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class OrderController {


    private static int beslagId = 20;




    public static void getStatus(Context ctx, ConnectionPool connectionpool) {
        try {
            int userId = Integer.parseInt(ctx.formParam("userid"));
            String order = OrderMapper.getOrderStatusByUserId(userId, connectionpool);
            ctx.attribute("status", order);
            ctx.render("status.html");
        } catch (NumberFormatException | DatabaseException e) {
            ctx.attribute("error", "We couldn't track your order: " + e.getMessage());
            ctx.render("status.html");
        }
    }

    public static void insertingAnOrder(int totalPrice, Context ctx, ConnectionPool connectionpool) throws DatabaseException {
        try {
            List<Material> materials = ctx.sessionAttribute("quantityordered");
            int userid = 0;
            Shed shed = ctx.sessionAttribute("shed");

            Carport carport = ctx.sessionAttribute("carport");
            User user = ctx.sessionAttribute("currentUser");
            String navn = ctx.formParam("navn");
            String efternavn = ctx.formParam("efternavn");
            String adresse = ctx.formParam("adresse");
            int zip = Integer.parseInt(ctx.formParam("zip"));
            int phone = Integer.parseInt(ctx.formParam("telefonNummer"));
            String mail = ctx.formParam("email");
            String password = ctx.formParam("telefonNummer");
            String comments = ctx.formParam("comments");


            MailSenderController.sendCarportDetailsEmail(carport, "fog.carports@gmail.com", navn, phone);

            boolean admin = Boolean.parseBoolean(ctx.formParam("admin"));

            User currentUser = ctx.sessionAttribute("currentUser");
            if (user == null) {
                user = new User(0, navn, efternavn, phone, mail, zip, adresse, admin, password);
                int userID = UserMapper.createUserGenerated(user, connectionpool);
                userid = userID;

            }   else {
                userid = user.getId();
            }

            Order order = new Order("under process", userid, carport.getLength(), carport.getWidth(), comments);
            int newOrderId = OrderMapper.insertOrder(order, totalPrice, connectionpool);

            for (Material material : materials) {
                OrderMapper.createOrderDetailsDatabase(newOrderId, order, material.getId(), material.getQuantityordered(), connectionpool);
            }
            if (shed != null) {
                OrderMapper.createOrdershedDatabase(newOrderId, shed, connectionpool);
            }

            ctx.render("price.html");

        } catch (NumberFormatException | DatabaseException e) {
            // Handle errors
            e.printStackTrace();
            System.out.println(e);
            ctx.attribute("error_message", "We couldn't save the order: " + e.getMessage());
            ctx.render("adresse.html");
        }
    }


    public static double calculatePrice(Context ctx, ConnectionPool connectionPool) {
        List<Material> materials = new ArrayList<>();

        Calculator calc = new Calculator();
        Carport carport = ctx.sessionAttribute("carport");

        int spaer600 = MaterialMapper.getPrice(9, connectionPool);
        int spaer480 = MaterialMapper.getPrice(10, connectionPool);
        int priceOfposts = MaterialMapper.getPrice(12, connectionPool);
        int length = carport.getLength();  // Hent længde fra session
        int width = carport.getWidth();    // Hent bredde fra session

        int screwsPerPerPost = MaterialMapper.getPrice(22, connectionPool);
        int screwPerSpaer = MaterialMapper.getPrice(22, connectionPool);
        int beslagPerPost = MaterialMapper.getPrice(beslagId, connectionPool);
        int beslagPerSpaer = MaterialMapper.getPrice(beslagId, connectionPool);


        int numberOfPosts = calc.numberOfPosts(length);
        int numberOfBeams = calc.beamAmount(length);
        int numberOfspaer = calc.spaerAmount(length);
        int numberPerscrewPost = calc.screwPost(length);
        int numberScrewPerSpaer = calc.screwSpaer(length);
        int numberBeslagPerPost = calc.beslagPost(length);
        int numberBeslagPerSpaer = calc.beslagspaer(length);

        int totalPostsCost = numberOfPosts * priceOfposts;
        int totalScrewPerPost = numberOfPosts * numberPerscrewPost;
        int totalScrewPerSPaer = numberOfspaer * numberScrewPerSpaer;
        int totalBeslagPerPost = beslagPerPost * numberBeslagPerPost;
        int totalBeslagPerSpaer = beslagPerSpaer * numberBeslagPerSpaer;


        int totalRaft;
        if (length <= 480) {
            totalRaft = numberOfspaer * spaer480;
        } else {
            totalRaft = numberOfspaer * spaer600;
        }

        int totalBeam;
        if (length <= 480) {
            totalBeam = numberOfBeams * spaer480;
        } else {
            totalBeam = numberOfBeams * spaer600;
        }

        int totalPrice = totalPostsCost + totalRaft + totalBeam + totalScrewPerPost + totalBeslagPerPost + totalScrewPerSPaer + totalBeslagPerSpaer;

        materials.add(new Material(9, "Spaer", numberOfBeams));
        materials.add(new Material(10, "Raft", numberOfspaer));
        materials.add(new Material(12, "Posts", numberOfPosts));
        materials.add(new Material(22, "ScrewPerPost", numberPerscrewPost));
        materials.add(new Material(22, "ScrewPerSpaer", numberScrewPerSpaer));
        materials.add(new Material(20, "BeslagPerPost", numberBeslagPerPost));
        materials.add(new Material(20, "BeslagPerSpaer", numberOfspaer));


        ctx.sessionAttribute("carport", carport);
        ctx.attribute("length", length);
        ctx.attribute("width", width);
        ctx.sessionAttribute("quantityordered", materials);
        ctx.sessionAttribute("totalprice", totalPrice);
        try {
            OrderController.insertingAnOrder(totalPrice, ctx, connectionPool);
        } catch (DatabaseException e) {
            // Handle exception if needed
            e.printStackTrace();
        }


        return totalPrice;
    }

    public static void getOrders(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        User user = ctx.sessionAttribute("currentUser");

        if (user != null) {
            List<Order> orders = OrderMapper.getOrders(user.getId(), connectionPool);

            ctx.attribute("orders", orders);
            ctx.render("order.html");
        } else {
            ctx.render("index.html");
        }
    }

    public static void showOrderDetails(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int orderNumber = Integer.parseInt(ctx.pathParam("ordernumber"));

        List<OrderDetail> orderDetail = OrderMapper.getOrderDetailsWithProduct(orderNumber, connectionPool);
        ctx.sessionAttribute("ordernumber", orderNumber);
        ctx.attribute("user", orderDetail);
        ctx.render("mymaterial.html");
    }

    public static void deleteOrder(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int orderId = Integer.parseInt(ctx.pathParam("orderId"));
        OrderMapper.deleteOrderDatabase(orderId, connectionPool);
        ctx.redirect("/adminordre");
    }

}
