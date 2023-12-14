package app.controllers;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.MaterialMapper;
import app.persistence.OrderMapper;

import app.persistence.UserMapper;
import app.util.Calculator;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OrderController {
    public static void getStatus(Context ctx, ConnectionPool connectionpool) {
        try {
            int userId = Integer.parseInt(ctx.formParam("userid"));
            String order= OrderMapper.getOrderStatusByUserId(userId, connectionpool);

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

            boolean admin = Boolean.parseBoolean(ctx.formParam("admin"));

            if (user == null) {
                user=new User(0,navn,efternavn,phone,mail,zip,adresse,admin,password);
                int userID= UserMapper.createUserGenerated(user,connectionpool);
                userid = userID;



            }

            Order order = new Order("under process", userid, carport.getLength(), carport.getWidth(), comments);
            int newOrderId = OrderMapper.insertOrder(order, totalPrice, connectionpool);

            for (Material material : materials) {
                OrderMapper.createOrderDetailsDatabase(newOrderId, order, material.getId(), material.getQuantityordered(), connectionpool);
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


    public static double calculatePrice( Context ctx, ConnectionPool connectionPool) {
        List<Material> materials = new ArrayList<>();

        Calculator calc = new Calculator();
        Carport carport = ctx.sessionAttribute("carport");

        int spaer600 = MaterialMapper.getPrice(9, connectionPool);
        int spaer480 = MaterialMapper.getPrice(10, connectionPool);
        int posts = MaterialMapper.getPrice(12, connectionPool);
        int length = carport.getLength();  // Hent længde fra session
        int width = carport.getWidth();    // Hent bredde fra session
        int screwsPerPerPost =MaterialMapper.getPrice(22,connectionPool);
        int screwPerSpaer = MaterialMapper.getPrice(22,connectionPool);
        int beslagPerPost = MaterialMapper.getPrice(20,connectionPool);
        int beslagPerSpaer =   MaterialMapper.getPrice(20,connectionPool);


        int numberOfPosts = calc.numberOfPosts(length);
        int numberOfBeams = calc.beamAmount(length);
        int numberOfRafts = calc.spaerAmount(length);

        int totalPostsCost = numberOfPosts * posts;

        int totalRaft;
        if (numberOfRafts >= 480) {
            totalRaft = numberOfRafts * spaer480;
        } else {
            totalRaft = numberOfRafts * spaer600;
        }

        int totalBeam;
        if (numberOfBeams >= 480) {
            totalBeam = numberOfBeams * spaer480;
        } else {
            totalBeam = numberOfBeams * spaer600;
        }

        int totalPrice = totalPostsCost + totalRaft + totalBeam;
        materials.add(new Material(9,numberOfBeams));
        materials.add(new Material(10, numberOfRafts));
        materials.add(new Material(12, numberOfPosts));
        materials.add(new Material(22,screwsPerPerPost));
        materials.add(new Material(22,screwPerSpaer));
        materials.add(new Material(20,beslagPerPost));
        materials.add(new Material(20,beslagPerSpaer));





        ctx.sessionAttribute("quantityordered", materials);
        ctx.attribute(String.valueOf(totalPrice),"totalprice");

        try {
            OrderController.insertingAnOrder(totalPrice,ctx, connectionPool);
        } catch (DatabaseException e) {
            // Handle exception if needed
            e.printStackTrace();
        }


        return totalPrice;
    }

}


