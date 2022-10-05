package com.revature;

import com.revature.controller.AuthController;
import com.revature.controller.ReimbursementController;
import com.revature.repository.ReimbursementRepository;
import io.javalin.Javalin;

import java.sql.SQLException;


public class Main {

    public static void main(String[] args) {

        ReimbursementRepository reimbursementRepository = new ReimbursementRepository();

        Javalin app = Javalin.create();

        AuthController ac = new AuthController();
        ac.mapEndpoints(app);

        ReimbursementController reimbursementController = new ReimbursementController();
        reimbursementController.mapEndpoints(app);

        app.start(8080);
    }
}
