package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.http.Context;

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
            ctx.render("login.html");
        }
    }

    public static void createuser(Context ctx, ConnectionPool connectionPool) {
        String forname = ctx.formParam("forname");
        String aftername = ctx.formParam("aftername");
        String email = ctx.formParam("email");
        int zip = Integer.parseInt(ctx.formParam("zip"));
        String adress =(ctx.formParam("address"));
        boolean admin = Boolean.parseBoolean(ctx.formParam("admin"));
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");
        int phone = Integer.parseInt(ctx.formParam("phone"));



        // Validering af passwords - at de to matcher
        if (password1.equals(password2)) {
            try {
                UserMapper.createuser(forname,aftername,email, zip, adress,admin,password1, phone ,connectionPool);
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

    public static void logout(Context ctx) {
        // Invalidate session
        ctx.req().getSession().invalidate();
        ctx.render("index.html");
    }



}
