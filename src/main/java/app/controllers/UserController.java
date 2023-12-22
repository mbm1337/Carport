package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.http.Context;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UserController {
    public static void login(Context ctx, ConnectionPool connectionPool) {
        String name = ctx.formParam("username");
        String password = ctx.formParam("password");

        try {
            User user = UserMapper.login(name, password, connectionPool);

            ctx.sessionAttribute("currentUser", user);
            ctx.sessionAttribute("username", user.getFirstName());
            boolean isAdmin = user.isAdmin();
            ctx.sessionAttribute("isAdmin", isAdmin);
            ctx.sessionAttribute("hideDefaultUserMenu", isAdmin);

            if (isAdmin) {
                ctx.redirect("/admin");
            } else {
                ctx.redirect("/");
            }
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            System.out.println(e);
            ctx.render("fragments.html");
        }
    }

    public static void createuser(Context ctx, ConnectionPool connectionPool) {
        String forname = ctx.formParam("forname");
        String aftername = ctx.formParam("aftername");
        String email = ctx.formParam("email");
        int zip = Integer.parseInt(ctx.formParam("zip"));
        String adress = (ctx.formParam("address"));
        boolean admin = Boolean.parseBoolean(ctx.formParam("admin"));
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");
        int phone = Integer.parseInt(ctx.formParam("phone"));


        // Validering af passwords - at de to matcher
        if (password1.equals(password2)) {
            try {
                UserMapper.createuser(forname, aftername, email, zip, adress, admin, password1, phone, connectionPool);
                ctx.attribute("message", "Du er nu oprette. Log på for at komme i gang.");
                ctx.render("login.html");

            } catch (DatabaseException e) {
                ctx.attribute("message", e.getMessage());
                System.out.println(e);
                ctx.render("createuser.html");
            }
        } else {
            ctx.attribute("message", "Dine password matcher ikke!");
            ctx.render("createuser.html");
        }
    }

    public static void updateUser(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        User currentUser = ctx.sessionAttribute("currentUser");


        if (currentUser != null) {
            String firstname = ctx.formParam("firstname");
            String lastname = ctx.formParam("lastname");

            String adress = ctx.formParam("address");
            int zip = Integer.parseInt(ctx.formParam("zip"));
            int phone = Integer.parseInt(ctx.formParam("phoneNumber"));
            String password = ctx.formParam("password");
            boolean isAdmin = Boolean.parseBoolean(ctx.formParam("status"));
            try {
                if (currentUser.isAdmin()) {
                    int id = Integer.parseInt(ctx.formParam("id"));
                    UserMapper.updateUser(id, firstname, lastname, adress, zip, phone, password, isAdmin, connectionPool);
                    ctx.redirect("/admin_user_details/" + id);
                } else {


                    UserMapper.updateUser(currentUser.getId(), firstname, lastname, adress, zip, phone, password, isAdmin, connectionPool);

                    ctx.sessionAttribute("currentUser", currentUser);
                    ctx.req().getSession().invalidate();
                    ctx.render("index.html");
                }

            } catch (DatabaseException e) {
                e.printStackTrace();
                ctx.render("usersetting.html");
            }

        } else {
            ctx.redirect("/");
        }
    }


    public static void logout(Context ctx) {
        // Invalidate session
        ctx.req().getSession().invalidate();
        ctx.render("index.html");
    }

    public static void showUserDetails(Context ctx, ConnectionPool connectionPool) throws DatabaseException, SVGGraphics2DIOException {

        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;

            int id = Integer.parseInt(ctx.pathParam("id"));


            ctx.attribute("user", UserMapper.getUserById(id, connectionPool));
            ctx.sessionAttribute("user", id);
            ctx.render("adminuseredit.html", Map.of("isAdmin", isAdmin, "isUser", isUser));


        } else {
            ctx.redirect("/");
        }


    }

    public static void deleteUserWithOrders(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        boolean isAdmin = false;
        boolean isUser = false;

        // Tjek om sessionen er tilgængelig
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null) {
            isAdmin = currentUser.isAdmin();
            isUser = true;

            try (Connection connection = connectionPool.getConnection()) {
                int userId = Integer.parseInt(ctx.pathParam("id"));
                List<Integer> orderIds = UserMapper.getOrdersForUser(userId, connection);

                // Delete each order
                for (Integer orderId : orderIds) {
                    UserMapper.deleteOrderDetails(connection, orderId);
                    UserMapper.deleteHasShed(connection, orderId);
                    UserMapper.deleteOrder(connection, orderId);
                }

                UserMapper.deleteUser(userId, connection);

            } catch (SQLException e) {
                String msg = "An error occurred during user and associated orders deletion. Please try again.";
                throw new DatabaseException(msg, msg);
            }

            ctx.redirect("/adminuser");

        } else {
            ctx.redirect("/");
        }
    }
}



