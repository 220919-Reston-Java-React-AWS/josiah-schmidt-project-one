package com.revature.model;

import java.util.Objects;

public class Reimbursement {

    private int id;
    private String reimbursementReason;
    private int amount;
    private String status;
    private int employeeId;
    private int financialManagerId;

    public Reimbursement() {
    }

    public Reimbursement(int id, String reimbursementReason, int amount, String status, int employeeId, int financialManagerId) {
        this.id = id;
        this.reimbursementReason = reimbursementReason;
        this.amount = amount;
        this.status = status;
        this.employeeId = employeeId;
        this.financialManagerId = financialManagerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReimbursementReason() {
        return reimbursementReason;
    }

    public void setReimbursementReason(String reimbursementReason) {
        this.reimbursementReason = reimbursementReason;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getFinancialManagerId() {
        return financialManagerId;
    }

    public void setFinancialManagerId(int financialManagerId) {
        this.financialManagerId = financialManagerId;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id=" + id +
                ", reimbursementReason='" + reimbursementReason + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", employeeId=" + employeeId +
                ", financialManagerId=" + financialManagerId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reimbursement that = (Reimbursement) o;
        return id == that.id && amount == that.amount && employeeId == that.employeeId && financialManagerId == that.financialManagerId && Objects.equals(reimbursementReason, that.reimbursementReason) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reimbursementReason, amount, status, employeeId, financialManagerId);
    }
}

