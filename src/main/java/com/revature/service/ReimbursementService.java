package com.revature.service;


import com.revature.exception.AmountMustBeGreaterThan0Exception;
import com.revature.exception.ReimbursementAlreadyUpdatedException;
import com.revature.exception.ReimbursementNotFoundException;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.repository.ReimbursementRepository;

import java.sql.SQLException;
import java.util.List;

public class ReimbursementService {

    private ReimbursementRepository reimbursementRepository = new ReimbursementRepository();

    public List<Reimbursement> getAllReimbursements() throws SQLException {
        return reimbursementRepository.getAllReimbursements();


    }

    public List<Reimbursement> getAllReimbursementsForEmployee(int employeeId) throws SQLException {
        return reimbursementRepository.getAllReimbursementsForEmployee(employeeId);
    }

    //financial manager update status
    public boolean updateReimbursement(int reimbursementId, String status, int financialManagerId) throws SQLException, ReimbursementNotFoundException, ReimbursementAlreadyUpdatedException {

        // Check if status is either approved or not approved

        // Check if reimbursement does not exist
        Reimbursement reimbursement = reimbursementRepository.getReimbursementById(reimbursementId);
        if (reimbursement == null) {
            throw new ReimbursementNotFoundException("Reimbursement with id " + reimbursementId + " was not found");
        }

        // Already updated
        if (!reimbursement.getStatus().equals("pending") && reimbursement.getFinancialManagerId() != 0) {
            throw new ReimbursementAlreadyUpdatedException("Reimbursement with id " + reimbursementId + " has already been updated");
        }

        if (!status.equals("approved")) {
            if (!status.equals("denied"))
            throw new IllegalArgumentException("Status must be either approved or denied");
        }


        //  updated reimbursement
        return reimbursementRepository.updateReimbursement(reimbursementId, status, financialManagerId);
    }

    public Reimbursement addReimbursement(Reimbursement reimbursement) throws AmountMustBeGreaterThan0Exception, SQLException {
        if (reimbursement.getAmount() <= 0) {
            throw new AmountMustBeGreaterThan0Exception("The amount you request must be greater than 0");
        }

        Reimbursement addReimbursement = reimbursementRepository.addReimbursement(reimbursement);

        return addReimbursement;
    }

}
