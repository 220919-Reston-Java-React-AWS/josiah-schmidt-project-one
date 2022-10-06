package com.revature.repository;

import com.revature.model.Reimbursement;
import com.revature.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementRepository {

    // see all reimbursements
    public List<Reimbursement> getAllReimbursements() throws SQLException {
        try (Connection connectionObject = ConnectionFactory.createConnection()) {

            List<Reimbursement> reimbursements = new ArrayList<>();

            String sql = "SELECT * FROM reimbursements";

            Statement stmt = connectionObject.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String reimbursementReason = rs.getString("reimbursement_reason");
                int amount = rs.getInt("amount");
                String status = rs.getString("status");
                int employeeId = rs.getInt("employee_id");
                int financialManagerId = rs.getInt("financial_manager_id");

                Reimbursement reimbursement = new Reimbursement(id, reimbursementReason, amount, status, employeeId, financialManagerId);

                reimbursements.add(reimbursement);
            }

            return reimbursements;
        }
    }

    //list all of your (employee) reimbursements
    public List<Reimbursement> getAllReimbursementsForEmployee(int employeeId) throws SQLException {
        try (Connection connectionObject = ConnectionFactory.createConnection()) {

            List<Reimbursement> reimbursements = new ArrayList<>();

            String sql = "SELECT * FROM reimbursements WHERE employee_id =?";

            PreparedStatement pstmt = connectionObject.prepareStatement(sql);

            pstmt.setInt(1, employeeId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String reimbursementReason = rs.getString("reimbursement_reason");
                int amount = rs.getInt("amount");
                String status = rs.getString("status");
                int eId = rs.getInt("employee_id");
                int financialManagerId = rs.getInt("financial_manager_id");

                Reimbursement reimbursement = new Reimbursement(id, reimbursementReason, amount, status, eId, financialManagerId);

                reimbursements.add(reimbursement);
            }

            return reimbursements;
        }
    }

    public Reimbursement getReimbursementById(int id) throws SQLException{
        try (Connection connectionObj = ConnectionFactory.createConnection()){
            String sql = "SELECT * FROM reimbursements WHERE id = ?";

            PreparedStatement pstmt = connectionObj.prepareStatement(sql);

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                int reimbursementId = rs.getInt("id");
                String reimbursementReason = rs.getString("reimbursement_reason");
                int amount = rs.getInt("amount");
                String status = rs.getString("status");
                int employeeId = rs.getInt("employee_id");
                int financialManagerId = rs.getInt("financial_manager_id");

                return new Reimbursement(reimbursementId, reimbursementReason, amount, status, employeeId, financialManagerId);
            } else {
                return null;
            }
        }
    }

    //update status (approve or deny)
    public boolean updateReimbursement(int reimbursementId, String status, int financialManagerId) throws SQLException {
        try (Connection connectionObj = ConnectionFactory.createConnection()) {
            String sql = "UPDATE reimbursements SET status = ?, financial_manager_id = ? WHERE id = ?";

            PreparedStatement pstmt = connectionObj.prepareStatement(sql);

            pstmt.setString(1, status);
            pstmt.setInt(2, financialManagerId);
            pstmt.setInt(3, reimbursementId);

            int numberOfRecordsUpdated =pstmt.executeUpdate();

            return numberOfRecordsUpdated ==1;

        }
    }

    //reimbursement request sent by employee
    public Reimbursement addReimbursement(Reimbursement reimbursement) throws SQLException {

        try (Connection connectionObject = ConnectionFactory.createConnection()) {
            String sql = "insert into reimbursements (reimbursement_reason, amount, employee_id) values (?, ?, ?)";

            PreparedStatement pstmt = connectionObject.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, reimbursement.getReimbursementReason());
            pstmt.setInt(2, reimbursement.getAmount());
            pstmt.setInt(3, reimbursement.getEmployeeId());

            int numberOfRecordsAdded = pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);

            return new Reimbursement(id, reimbursement.getReimbursementReason(), reimbursement.getAmount(), reimbursement.getStatus(), reimbursement.getEmployeeId(), reimbursement.getFinancialManagerId());
        }
    }
}
