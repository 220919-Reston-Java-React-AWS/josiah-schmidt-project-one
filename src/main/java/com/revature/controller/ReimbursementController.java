package com.revature.controller;

import com.revature.exception.AmountMustBeGreaterThan0Exception;
import com.revature.exception.ReimbursementAlreadyUpdatedException;
import com.revature.exception.ReimbursementNotFoundException;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.service.ReimbursementService;
import io.javalin.Javalin;

import javax.servlet.http.HttpSession;
import java.util.List;

public class ReimbursementController {

    private ReimbursementService reimbursementService = new ReimbursementService();

        //financial manager seeing all reimbursements
    public void mapEndpoints(Javalin app){
        app.get("/reimbursements", (ctx) -> {
            HttpSession httpSession = ctx.req.getSession();

            User user = (User) httpSession.getAttribute("user");

            if (user != null) {
                if (user.getRoleId() == 2) {
                    List<Reimbursement> reimbursements = reimbursementService.getAllReimbursements();

                    ctx.json(reimbursements);

                } else {
                    ctx.result("you are logged in, but not a financial manager");
                    ctx.status(401);
                }
            } else {
                ctx.result("You are not logged in");
                ctx.status(401);
            }

        });

            //financial manager Approved or Denied
        app.patch("/reimbursements/{reimbursementId}", (ctx) -> {

            HttpSession httpSession = ctx.req.getSession();
            User user = (User) httpSession.getAttribute("user");

            if (user != null) { // Check if logged in
                // Check if user is a financial manager
                if (user.getRoleId() == 2) {
                    int updaterId = user.getId();
                    int reimbursementId = Integer.parseInt(ctx.pathParam("reimbursementId"));

                    Reimbursement a = ctx.bodyAsClass(Reimbursement.class);

                    String status = a.getStatus();

                    try {
                        reimbursementService.updateReimbursement(reimbursementId, status, updaterId);

                        ctx.result("Reimbursement successfully updated!");
                    } catch (ReimbursementAlreadyUpdatedException | IllegalArgumentException e) {
                        ctx.result(e.getMessage());
                        ctx.status(400);
                    } catch (ReimbursementNotFoundException e) {
                        ctx.result(e.getMessage());
                        ctx.status(404); // 404 NOT FOUND
                    }

                } else {
                    ctx.result("You are logged in, but you are not a financial manager!");
                    ctx.status(401);
                }
            } else {
                ctx.result("You are not logged in!");
                ctx.status(401);
            }

        });

        // Endpoint is for employee to view their own reimbursement
        app.get("/users/{userId}/reimbursements", (ctx) -> {
            HttpSession httpSession = ctx.req.getSession();

            User user = (User) httpSession.getAttribute("user");

            if (user != null) { // Check if logged in
                if (user.getRoleId() == 1) {
                    // Check if user is who they say they are
                    int userId = Integer.parseInt(ctx.pathParam("userId"));
                    if (user.getId() == userId) {
                        List<Reimbursement> reimbursements = reimbursementService.getAllReimbursementsForEmployee(userId);
                        ctx.json(reimbursements);
                    } else {
                        ctx.result("You are not logged in as the user you are trying to retrieve reimbursements from");
                        ctx.status(401);
                    }
                } else {
                    ctx.result("You are logged in, but you're not an employee!");
                    ctx.status(401);
                }
            } else {
                ctx.result("You are not logged in!");
                ctx.status(401);
            }
        });

        //Reimbursement request
        app.post("/users/{userId}/reimbursements", (ctx) -> {
            Reimbursement reimbursementToAdd = ctx.bodyAsClass(Reimbursement.class);

            HttpSession httpSession = ctx.req.getSession();

            User user = (User) httpSession.getAttribute("user");

            if (user != null) { // Check if logged in
                if (user.getRoleId() == 1) {
                    // Check if user is who they say they are
                    int userId = Integer.parseInt(ctx.pathParam("userId"));
                    if (user.getId() == userId) {
                        try {
                            reimbursementToAdd.setEmployeeId(user.getId());
                            reimbursementToAdd = reimbursementService.addReimbursement(reimbursementToAdd);



                            ctx.json(reimbursementToAdd);
                            ctx.status(201);
                        } catch (AmountMustBeGreaterThan0Exception e) {
                            ctx.result(e.getMessage());
                            ctx.status(400);
                        }
                    } else {
                        ctx.result("You are not logged in as the user you are trying to submit reimbursements for");
                        ctx.status(401);
                    }
                } else {
                    ctx.result("You are logged in, but you're not an employee!");
                    ctx.status(401);
                }
            } else {
                ctx.result("You are not logged in!");
                ctx.status(401);
            }
        });
    }
}
