package com.paysystem.model.entities;

import com.paysystem.model.enums.TypePayment;
import org.w3c.dom.Text;

import java.util.Date;

public class Payment {
    private int paymentId;
    private double amount;
    private String reason;
    private int employeeId;
    private TypePayment typePayment;

    public Payment(double amount, TypePayment typePayment, String reason, int employeeId) {
        this.amount = amount;
        this.reason = reason;
        this.typePayment = typePayment;
        this.employeeId = employeeId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public TypePayment getTypePayment() {
        return typePayment;
    }

    public void setTypePayment(TypePayment typePayment) {
        this.typePayment = typePayment;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "amount=" + amount +
                ", paymentId=" + paymentId +
                ", reason='" + reason + '\'' +
                ", employeeId=" + employeeId +
                '}';
    }
}
