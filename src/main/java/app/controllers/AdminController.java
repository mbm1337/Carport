package app.controllers;
import app.entities.*;
import app.persistence.*;
import app.exceptions.DatabaseException;
import io.javalin.http.Context;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class AdminController {
    static Map<User, List<Order>> usersAndOrders;

    public static void getUsersAndOrders(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;
        }

        Map<User, List<Order>> usersAndOrders = AdminMapper.getUsersAndOrders(connectionPool);
        ctx.attribute("usersAndOrders", usersAndOrders);
        ctx.render("adminordre.html");

        usersAndOrders.forEach((user, orders) -> {
            orders.sort(Comparator.comparing(Order::getStatus));
        });

        // Send de sorteret data til skabelonen

        Boolean loggedIn = ctx.sessionAttribute("isAdmin");
        if (loggedIn != null && loggedIn) {

            ctx.attribute("usersAndOrders", usersAndOrders);

            ctx.render("adminordre.html", Map.of("isAdmin", isAdmin, "isUser", isUser));

        } else {
            ctx.redirect("/");
        }


    }


    public static void getOrderDetails(Context ctx, ConnectionPool connectionPool) throws DatabaseException, SVGGraphics2DIOException {
        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;
            int orderNumber = Integer.parseInt(ctx.pathParam("ordernumber"));

            Admin admin = AdminMapper.getOrderDetails(orderNumber, connectionPool);

            ctx.sessionAttribute("ordernumber", orderNumber);

            Boolean loggedIn = ctx.sessionAttribute("isAdmin");
            ctx.attribute("city", ZipMapper.getCityByZip(admin.getZip(), connectionPool));

            ctx.attribute("admin", admin);
            ctx.attribute("adminList", admin.getAdminList());

            SvgController.getSvg(ctx, connectionPool);

            ctx.render("tilbud.html", Map.of("isAdmin", isAdmin, "isUser", isUser));

        } else {
            ctx.redirect("/");
        }


    }

    public static void editBalance(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;
            double updatePrice = Double.parseDouble(ctx.formParam("newPrice"));
            int id = Integer.parseInt(ctx.formParam("orderId"));
            AdminMapper.updatePrice(id, updatePrice, connectionPool);
            ctx.redirect("/tilbud/" + id);
        } else {
            ctx.redirect("/");
        }

    }


    public static void getMaterial(Context ctx, ConnectionPool connectionPool) throws SQLException {
        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;
            List<Material> materials = AdminMapper.getMaterials(connectionPool);

            ctx.attribute("materials", materials);

            ctx.render("materials.html", Map.of("isAdmin", isAdmin, "isUser", isUser));
        } else {
            ctx.redirect("/");
        }

    }

    public static void editMaterial(Context ctx, ConnectionPool connectionPool) throws DatabaseException, SQLException {
        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;
            int materialId = Integer.parseInt(ctx.pathParam("id"));
            Material material = AdminMapper.getMaterialById(materialId, connectionPool);
            Boolean loggedIn = ctx.sessionAttribute("isAdmin");

            ctx.attribute("material", material);
            ctx.render("edit_matreriel.html", Map.of("isAdmin", isAdmin, "isUser", isUser));
        } else {
            ctx.redirect("/");
        }

    }

    public static void updateMaterial(Context ctx, ConnectionPool connectionPool) throws DatabaseException, SQLException {
        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;
        }
        int id = Integer.parseInt(ctx.pathParam("id"));
        String productName = (ctx.formParam("productName"));
        String productType = (ctx.formParam("productType"));
        String productSize = (ctx.formParam("productSize"));
        String unit = (ctx.formParam("unit"));
        short quantityInStock = (short) Integer.parseInt(ctx.formParam("quantityInStock"));
        double buyPrice = Double.parseDouble(ctx.formParam("buyPrice"));
        double purchasePrice = Double.parseDouble(ctx.formParam("purchasePrice"));

        Material material = new Material(id, productName, productType, productSize, unit, quantityInStock, buyPrice, purchasePrice);
        AdminMapper.updateMaterial(material, connectionPool);
        Boolean loggedIn = ctx.sessionAttribute("isAdmin");
        if (loggedIn != null && loggedIn) {
            ctx.attribute("material", material);
            ctx.render("edit_matreriel.html", Map.of("isAdmin", isAdmin, "isUser", isUser));

        } else {
            ctx.redirect("/");
        }


    }


    public static void addMaterial(Context ctx, ConnectionPool connectionPool) throws DatabaseException, SQLException {
        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;
        }
        String productName = (ctx.formParam("productName"));
        String productType = (ctx.formParam("productType"));
        String productSize = (ctx.formParam("productSize"));
        String unit = (ctx.formParam("unit"));
        short quantityInStock = (short) Integer.parseInt(ctx.formParam("quantityInStock"));
        double buyPrice = Double.parseDouble(ctx.formParam("buyPrice"));
        double purchasePrice = Double.parseDouble(ctx.formParam("purchasePrice"));

        Material material = new Material(productName, productType, productSize, unit, quantityInStock, buyPrice, purchasePrice);
        AdminMapper.addMaterial(material, connectionPool);
        Boolean loggedIn = ctx.sessionAttribute("isAdmin");
        if (loggedIn != null && loggedIn) {
            ctx.redirect("/materials");
        } else {
            ctx.redirect("/");
        }

    }

    public static void deleteMaterial(Context ctx, ConnectionPool connectionPool) throws DatabaseException, SQLException {
        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;
        }
        int id = Integer.parseInt(ctx.pathParam("id"));
        AdminMapper.deleteMaterial(id, connectionPool);
        ctx.redirect("/materials");
    }

    public static void getCalcMaterials(Context ctx, ConnectionPool connectionPool) throws DatabaseException, SQLException {
        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;

            List<Admin> calcMaterials = AdminMapper.getCalcMaterials(connectionPool);
            ctx.attribute("calcMaterials", calcMaterials);
            List<Material> materials = AdminMapper.getMaterials(connectionPool);
            ctx.attribute("materials", materials);
            ctx.render("admin_calculator.html", Map.of("isAdmin", isAdmin, "isUser", isUser));
        } else {
            ctx.redirect("/");
        }


    }

    public static void getCalcMaterialsById(Context ctx, ConnectionPool connectionPool) throws DatabaseException, SQLException {
        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;
            int materialId = Integer.parseInt(ctx.pathParam("id"));
            Admin calcMaterial = AdminMapper.getCalcMaterialsById(materialId, connectionPool);
            ctx.attribute("calcMaterial", calcMaterial);
            List<Material> materials = AdminMapper.getMaterials(connectionPool);
            ctx.attribute("materials", materials);
            ctx.render("admin_calculator_edit.html", Map.of("isAdmin", isAdmin, "isUser", isUser));
        } else {
            ctx.redirect("/");
        }

    }

    public static void editCalcMaterials(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;

            int id = Integer.parseInt(ctx.pathParam("id"));
            int materialsId = Integer.parseInt(ctx.formParam("materialsId"));
            AdminMapper.updateCalcMaterials(id, materialsId, connectionPool);
            ctx.redirect("/adminCalc");

        } else {
            ctx.redirect("/");
        }


    }


    public static void getDimensions(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;
            List<CarportLength> carportLength = CarportMapper.getCarportLength(connectionPool);
            ctx.attribute("carportLength", carportLength);
            List<CarportWidth> carportWidth = CarportMapper.getCarportWidth(connectionPool);
            ctx.attribute("carportWidth", carportWidth);
            List<ShedLength> shedLength = CarportMapper.getShedLength(connectionPool);
            ctx.attribute("shedLength", shedLength);
            List<ShedWidth> shedWidth = CarportMapper.getShedWidth(connectionPool);
            ctx.attribute("shedWidth", shedWidth);
            ctx.render("admin_carport_size.html", Map.of("isAdmin", isAdmin, "isUser", isUser));
        } else {
            ctx.redirect("/");
        }


    }

    public static void addCarportLength(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;
            int length = Integer.parseInt(ctx.formParam("carportLength"));
            AdminMapper.addCarportLength(length, connectionPool);
            ctx.redirect("/carport_size");

        } else {
            ctx.redirect("/");
        }


    }

    public static void addCarportWidth(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;
            int width = Integer.parseInt(ctx.formParam("carportWidth"));
            AdminMapper.addCarportWidth(width, connectionPool);
            ctx.redirect("/carport_size");
        } else {
            ctx.redirect("/");
        }


    }

    public static void addShedLength(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;
            int length = Integer.parseInt(ctx.formParam("shedLength"));
            AdminMapper.addShedLength(length, connectionPool);
            ctx.redirect("/carport_size");
        } else {
            ctx.redirect("/");
        }


    }

    public static void addShedWidth(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;
            int width = Integer.parseInt(ctx.formParam("shedWidth"));
            AdminMapper.addShedWidth(width, connectionPool);
            ctx.redirect("/carport_size");
        } else {
            ctx.redirect("/");
        }


    }

    public static void deleteCarportLength(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;
            int id = Integer.parseInt(ctx.pathParam("id"));
            AdminMapper.deleteCarportLength(id, connectionPool);
            ctx.redirect("/carport_size");
        } else {
            ctx.redirect("/");
        }


    }

    public static void deleteCarportWidth(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;
            int id = Integer.parseInt(ctx.pathParam("id"));
            AdminMapper.deleteCarportWidth(id, connectionPool);
            ctx.redirect("/carport_size");
        } else {
            ctx.redirect("/");
        }


    }

    public static void deleteShedLength(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;
            int id = Integer.parseInt(ctx.pathParam("id"));
            AdminMapper.deleteShedLength(id, connectionPool);
            ctx.redirect("/carport_size");
        } else {
            ctx.redirect("/");
        }


    }

    public static void deleteShedWidth(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;
            int id = Integer.parseInt(ctx.pathParam("id"));
            AdminMapper.deleteShedWidth(id, connectionPool);
            ctx.redirect("/carport_size");
        } else {
            ctx.redirect("/");
        }


    }

    public static void changeStatus(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        boolean isAdmin = false;
        boolean isUser = false;
        User currentUser = ctx.sessionAttribute("currentUser");
        String email = ctx.formParam("email");

        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;
            int orderId = Integer.parseInt(ctx.formParam("orderId"));
            String newStatus = ctx.formParam("newStatus");

            Order order = OrderMapper.getOrderById(orderId, connectionPool);

            if (newStatus.equals("under process") || newStatus.equals("paid") || newStatus.equals("cancelled")) {
                AdminMapper.updateStatus(orderId, newStatus, connectionPool);

                if (order != null) {
                    // Send status update email
                    MailSenderController.sendStatusToCustomer(order, newStatus, email);
                }
                ctx.redirect("/admin/orders"); //??
            } else {
                ctx.status(400).result("Invalid status update request");
            }
        } else {
            ctx.redirect("/");
        }
    }
}






