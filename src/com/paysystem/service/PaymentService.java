package com.paysystem.service;

import com.paysystem.model.entities.Payment;
import com.paysystem.repository.interfaces.PaymentInterface;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PaymentService {
    private final PaymentInterface paymentRepository;

    public PaymentService(PaymentInterface paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Optional<Payment> createPayment(Payment payment, String employeeEmail) {
        if (payment.getAmount() <= 0.0) {
            System.out.println("Amount must be greater than 0");
            return Optional.empty();
        }

        if (payment.getTypePayment() == null) {
            System.out.println("Payment type is required");
            return Optional.empty();
        }

        if (employeeEmail == null || employeeEmail.isEmpty()) {
            System.out.println("Employee email is required");
            return Optional.empty();
        }

        try {
            return paymentRepository.createPayment(payment, employeeEmail);
        } catch (Exception e) {
            System.out.println("Error creating payment: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<Payment> updatePayment(Payment payment) {
        if (payment.getPaymentId() == 0) {
            System.out.println("Payment ID is required");
            return Optional.empty();
        }

        try {
            return paymentRepository.updatePayment(payment);
        } catch (Exception e) {
            System.out.println("Error updating payment: " + e.getMessage());
            return Optional.empty();
        }
    }

    public boolean deletePayment(Integer paymentId) {
        if (paymentId == null || paymentId <= 0) {
            System.out.println("Invalid payment ID");
            return false;
        }

        try {
            return paymentRepository.deletePayment(paymentId);
        } catch (Exception e) {
            System.out.println("Error deleting payment: " + e.getMessage());
            return false;
        }
    }

    public Map<String, String> getPaymentById(Integer paymentId) {
        if (paymentId == null || paymentId <= 0) {
            System.out.println("Invalid payment ID");
            return null;
        }

        try {
            return paymentRepository.getPaymentById(paymentId);
        } catch (Exception e) {
            System.out.println("Error retrieving payment: " + e.getMessage());
            return null;
        }
    }

    public List<Map<String, Object>> getAllPayment() {
        try {
            return paymentRepository.getAllPayment();
        } catch (Exception e) {
            System.out.println("Error retrieving payments: " + e.getMessage());
            return null;
        }
    }
}