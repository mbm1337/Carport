package app.controllers;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.MaterialMapper;
import app.persistence.OrderMapper;
import app.persistence.UserMapper;
import app.util.Calculator;
import io.javalin.http.Context;
import org.apache.batik.svggen.SVGGraphics2DIOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderController {
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
            int userid;
            Shed shed = ctx.sessionAttribute("shed");

            Carport carport = ctx.sessionAttribute("carport");
            User user = ctx.sessionAttribute("currentUser");

            boolean admin = Boolean.parseBoolean(ctx.formParam("admin"));
            String firstname;
            String lastname;
            String address;
            int zip;
            int phone;
            String mail;
            String password;
            String comments = "";

            if (user == null) {
                firstname = ctx.formParam("navn");
                lastname = ctx.formParam("efternavn");
                address = ctx.formParam("adresse");
                zip = Integer.parseInt(ctx.formParam("zip"));
                phone = Integer.parseInt(ctx.formParam("telefonNummer"));
                mail = ctx.formParam("email");
                password = ctx.formParam("telefonNummer");
                comments = ctx.formParam("comments");
                user = new User(0, firstname, lastname, phone, mail, zip, address, admin, password);
                userid = UserMapper.createUserGenerated(user, connectionpool);

            } else {
                firstname = user.getFirstName();
                phone = user.getPhoneNumber();
                mail = user.getEmail();
                userid = user.getId();

            }

            MailSenderController.sendDetailsToCustomer(carport, mail, firstname, phone, mail, ctx);

            Order order = new Order("under process", userid, carport.getLength(), carport.getWidth(), comments);
            int newOrderId = OrderMapper.insertOrder(order, totalPrice, connectionpool);

            for (Material material : materials) {
                OrderMapper.createOrderDetailsDatabase(newOrderId, order, material.getId(), material.getQuantityordered(), connectionpool);
            }
            if (shed != null) {
                OrderMapper.createOrdershedDatabase(newOrderId, shed, connectionpool);
            }

            SvgController.getSvgWithParameter(newOrderId, ctx, connectionpool);
            ctx.attribute("firstname", firstname);
            ctx.attribute("phone", phone);
            ctx.attribute("mail", mail);
            MailSenderController.sendCarportDetailsEmail(carport, "fog.carports@gmail.com", firstname, phone, ctx);
            ctx.render("price.html");

        } catch (NumberFormatException | DatabaseException e) {
            // Handle errors
            e.printStackTrace();
            System.out.println(e);
            ctx.attribute("message", e.getMessage());
            ctx.attribute("error_message", "We couldn't save the order: " + e.getMessage());
            ctx.render("adresse.html");
        } catch (SVGGraphics2DIOException e) {
            throw new RuntimeException(e);
        }
    }

    public static double calculatePrice(Context ctx, ConnectionPool connectionPool) {
        List<Material> materials = new ArrayList<>();

        Calculator calc = new Calculator();
        Carport carport = ctx.sessionAttribute("carport");
        Shed shed = ctx.sessionAttribute("shed"); // Retrieve shed from session


        int length = carport.getLength();  // Hent længde fra session
        int width = carport.getWidth();    // Hent bredde fra session
        int shedwidth = 0;
        int shedLength = 0;
        if (shed != null) {
            shedwidth = shed.getWidth();
            shedLength = shed.getLength();
        }

        int spaer600 = MaterialMapper.getPrice(9, connectionPool);
        int spaer480 = MaterialMapper.getPrice(10, connectionPool);
        int priceOfposts = MaterialMapper.getPrice(12, connectionPool);
        int stolperPriceskur = MaterialMapper.getPrice(12, connectionPool);
        int beklædningprice = MaterialMapper.getPrice(13, connectionPool);
        int screwsSpaerPostPrice = MaterialMapper.getPrice(22, connectionPool);

        int beslagPerPostPrice = MaterialMapper.getPrice(20, connectionPool);
        int beslagPerSpaerPrice = MaterialMapper.getPrice(20, connectionPool);

        int numberOfPosts = calc.numberOfPosts(length);
        int numberOfBeams = calc.beamAmount(length);
        int numberOfspaer = calc.spaerAmount(length);
        int screwamount = calc.screwSpaer(length) + calc.screwPost(length);
        int numberBeslagPerPost = calc.beslagPost(length);
        int numberBeslagPerSpaer = calc.beslagspaer(length);
        int numberofstolperPerskur = calc.numberOfStolperPerSkur(shedwidth, shedLength);
        int numberOfBeklaedning = calc.beklaedning(shedwidth, shedLength);

        int totalPostsCost = numberOfPosts * priceOfposts;

        int totalPriceOfScrews = screwsSpaerPostPrice * screwamount;

        int screwpakke = 0;

        if (screwamount > 0) {
            int i;
            for (i = 0; screwamount > 0; screwamount -= 200) {
                i++;
            }
            screwpakke += i;
        }

        int totalCostBeslagPerPost = beslagPerPostPrice * numberBeslagPerPost;
        int totalCostBeslagPerSpaer = beslagPerSpaerPrice * numberBeslagPerSpaer;
        int totalCostbeklaedning = beklædningprice * numberOfBeklaedning;
        int totalCoststolperPerskur = stolperPriceskur * numberofstolperPerskur;

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

        int totalPrice = totalCostbeklaedning + totalCoststolperPerskur + totalCostBeslagPerPost + totalCostBeslagPerSpaer + totalPriceOfScrews + totalPostsCost + totalBeam + totalRaft;
        materials.add(new Material(9, "rem", numberOfBeams));
        materials.add(new Material(10, "spaer", numberOfspaer));
        materials.add(new Material(12, "Posts", numberOfPosts));
        materials.add(new Material(20, "BeslagPerSpaer", numberBeslagPerSpaer));
        materials.add(new Material(20, "BeslagPerPost", numberBeslagPerPost));
        materials.add(new Material(22, "ScrewPerSpaer", screwpakke));
        materials.add(new Material(13, "brædt", numberOfBeklaedning));
        materials.add(new Material(12, "stoplerPerSkur", numberofstolperPerskur));

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

        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            boolean isAdmin = currentUser.isAdmin();
            boolean isUser = true;
            List<Order> orders = OrderMapper.getOrders(currentUser.getId(), connectionPool);
            ctx.attribute("username", ctx.sessionAttribute("username"));
            ctx.attribute("orders", orders);
            ctx.render("order.html", Map.of("isAdmin", isAdmin, "isUser", isUser));
        } else {
            ctx.redirect("/");
        }
    }


    public static void deleteOrder(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;

            int orderId = Integer.parseInt(ctx.pathParam("orderId"));
            OrderMapper.deleteOrderDatabase(orderId, connectionPool);
            ctx.redirect("/adminordre");
        } else {
            ctx.redirect("/");
        }
    }

    public static void showOrderDetails(Context ctx, ConnectionPool connectionPool) throws DatabaseException, SVGGraphics2DIOException {
        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;

            int orderNumber = Integer.parseInt(ctx.pathParam("ordernumber"));

            List<OrderDetail> orderDetail = OrderMapper.getOrderDetailsWithProduct(orderNumber, connectionPool);
            ctx.sessionAttribute("ordernumber", orderNumber);
            ctx.attribute("user", orderDetail);
            SvgController.getSvg(ctx, connectionPool);
            ctx.render("mymaterial.html", Map.of("isAdmin", isAdmin, "isUser", isUser));
        } else {
            ctx.redirect("/");
        }
    }
}
