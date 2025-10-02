package com.paysystem.service;

import com.paysystem.model.entities.Payment;
import com.paysystem.repository.interfaces.PaymentInterface;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PaymentService {
    public PaymentInterface paymentRepository;
    public PaymentService(PaymentInterface paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Optional<Payment> createPayment(Payment payment, String employeeEmail) {
        if (payment.getAmount() <= 0.0 || payment.getTypePayment() == null || payment.getEmployeeId() == 0) {
            System.out.println("One of the fields is empty");
            return Optional.empty();
        }
        try {
            return paymentRepository.createPayment(payment, employeeEmail);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<Payment> updatePayment(Payment payment) {
        try {
            return paymentRepository.updatePayment(payment);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean deletePayment(Integer paymentId) {
        try {
            return paymentRepository.deletePayment(paymentId);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Map<String, String> getPaymentById(Integer paymentId) {
        try {
            return paymentRepository.getPaymentById(paymentId);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Map<String, Object>> getAllPayment() {
        try {
            return paymentRepository.getAllPayment();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
