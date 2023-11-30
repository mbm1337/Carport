package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.http.Context;

import java.util.Map;

public class UserController {

    public static void login(Context ctx, ConnectionPool connectionPool) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        try {
            boolean status = UserMapper.login(email, password, connectionPool).isAdmin();
            User user = UserMapper.login(email, password, connectionPool);
            ctx.sessionAttribute("currentUser", user);
            ctx.sessionAttribute("email", user.getEmail());
            ctx.sessionAttribute("status", user.isAdmin());
            if (status) {
                ctx.redirect("/admin");
            } else {

                ctx.redirect("/frontpage");


            }
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("index.html");
        }
    }

    public static void createuser(Context ctx, ConnectionPool connectionPool) {
        String firstName = ctx.formParam("firstName");
        String lastName = ctx.formParam("lastName");
        int phoneNumber = Integer.parseInt(ctx.formParam("phoneNumber"));
        String email = ctx.formParam("email");
        int zip = Integer.parseInt(ctx.formParam("zip"));
        String address = ctx.formParam("address");
        boolean admin = Boolean.parseBoolean(ctx.formParam("admin"));
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");


        // Validering af passwords - at de to matcher
        if (password1.equals(password2)) {
            try {
                UserMapper.createuser(firstName, lastName, phoneNumber, email, zip, address, admin, password1, connectionPool);
                ctx.attribute("message", "Du er nu oprette. Log på for at komme i gang.");
                ctx.render("index.html");

            } catch (DatabaseException e) {
                ctx.attribute("message", e.getMessage());
                ctx.render("createuser.html");
            }
        } else {
            ctx.attribute("message", "Dine password matcher ikke!");
            ctx.render("createuser.html");
        }

    }

    public static void logout(Context ctx) {
        // Invalidate session
        ctx.req().getSession().invalidate();
        ctx.render("index.html");
    }



    public static void searchUser(Context ctx, ConnectionPool connectionPool) {
        String id = ctx.queryParam("email");
        try {
            User user = UserMapper.searchUser(id, connectionPool);
            ctx.attribute("user", user);
            ctx.render("users.html");
        } catch (DatabaseException e) {
            ctx.attribute("Kunne Ikke Finde Brugern", e.getMessage());
            ctx.render("users.html");
        }
    }
}


