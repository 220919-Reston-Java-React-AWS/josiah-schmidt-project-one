package com.revature.controller;

import com.revature.exception.InvalidLoginException;
import com.revature.exception.UsernameAlreadyExistsException;
import com.revature.model.User;
import com.revature.service.AuthService;
import io.javalin.Javalin;
import javax.servlet.http.HttpSession;

public class AuthController {

    private AuthService authService = new AuthService();

    public void mapEndpoints(Javalin app) {
        app.post("/login", (ctx) -> {
            User credentials = ctx.bodyAsClass(User.class);

            try {
                User user = authService.login(credentials.getUsername(), credentials.getPassword());

                HttpSession session = ctx.req.getSession(); // the cookie
                session.setAttribute("user", user);
            } catch (InvalidLoginException e){
                ctx.status(400);
                ctx.result(e.getMessage());
            }
        });

        app.post("/logout", (ctx) -> {
            ctx.req.getSession().invalidate(); // delete cookie
        });

       //register
        app.post("/users", (ctx) -> {
            User userToAdd = ctx.bodyAsClass(User.class);

            try {
                User addedUser = authService.register(userToAdd);

                ctx.json(addedUser);
                ctx.status(201);
            } catch (UsernameAlreadyExistsException e) {
                ctx.result(e.getMessage());
                ctx.status(400);
            }
        });
    }
}
