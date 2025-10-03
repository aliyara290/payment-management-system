package com.paysystem.repository.impl;

import com.paysystem.model.entities.Payment;
import com.paysystem.repository.interfaces.PaymentInterface;
import com.paysystem.utils.Crud;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PaymentRepositoryImpl implements PaymentInterface {

    @Override
    public Optional<Payment> createPayment(Payment payment, String employeeEmail) {
        try {
            List<Map<String, String>> userResult = Crud.readByCondition("users", "email", employeeEmail);

            if (userResult.isEmpty()) {
                System.out.println("Employee not found with email: " + employeeEmail);
                return Optional.empty();
            }

            int employeeId = Integer.parseInt(userResult.getFirst().get("id"));

            boolean isCreated = Crud.create("payments", Map.of(
                    "amount", payment.getAmount(),
                    "reason", payment.getReason(),
                    "agent_id", employeeId,
                    "type", payment.getTypePayment().name().toLowerCase()
            ));

            if (isCreated) {
                payment.setEmployeeId(employeeId);
                return Optional.of(payment);
            }
        } catch (Exception e) {
            System.out.println("Error creating payment: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Payment> updatePayment(Payment payment) {
        try {
            List<Map<String, String>> paymentResult = Crud.readByCondition("payments", "id", payment.getPaymentId());

            if (paymentResult.isEmpty()) {
                System.out.println("Payment not found");
                return Optional.empty();
            }

            Map<String, String> existingPayment = paymentResult.getFirst();

            boolean isUpdated = Crud.update("payments", Map.of(
                    "amount", payment.getAmount(),
                    "reason", payment.getReason(),
                    "type", payment.getTypePayment().name().toLowerCase()
            ), "id", payment.getPaymentId());

            if (isUpdated) {
                return Optional.of(payment);
            }
        } catch (Exception e) {
            System.out.println("Error updating payment: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public boolean deletePayment(Integer paymentId) {
        try {
            List<Map<String, String>> paymentResult = Crud.readByCondition("payments", "id", paymentId);

            if (paymentResult.isEmpty()) {
                System.out.println("Payment not found");
                return false;
            }

            return Crud.delete("payments", "id", paymentId);
        } catch (Exception e) {
            System.out.println("Error deleting payment: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Map<String, String> getPaymentById(Integer paymentId) {
        try {
            List<Map<String, String>> paymentResult = Crud.readByCondition("payments", "id", paymentId);

            if (!paymentResult.isEmpty()) {
                return paymentResult.get(0);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving payment: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getAllPayment() {
        try {
            return Crud.readAll("payments");
        } catch (Exception e) {
            System.out.println("Error retrieving all payments: " + e.getMessage());
        }
        return null;
    }
}