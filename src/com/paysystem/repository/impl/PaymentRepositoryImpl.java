package com.paysystem.repository.impl;

import com.paysystem.model.entities.Payment;
import com.paysystem.repository.interfaces.PaymentInterface;
import com.paysystem.utils.Crud;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PaymentRepositoryImpl implements PaymentInterface {

    @Override
    public Optional<Payment> createPayment(Payment payment, String employeeEmail) {
        int employeeId = Integer.parseInt(Crud.readByCondition("user", "email", employeeEmail).getFirst().get("id"));
        if (employeeId == 0) {
            return Optional.empty();
        }
        try {
            boolean isCreated = Crud.create("payments", Map.of(
                    "amount", payment.getAmount(),
                    "reason", payment.getReason(),
                    "agent_id", employeeId,
                    "type", payment.getTypePayment()
            ));
            if (isCreated) {
                return Optional.of(payment);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Payment> updatePayment(Payment payment) {
        Map<String, String> getPayment = Crud.readByCondition("payments", "id", payment.getPaymentId()).getFirst();
        try {
            boolean isUpdated = Crud.update("payments", Map.of(
                    "amount", payment.getAmount() != 0.0 ? payment.getAmount() : getPayment.get("amount"),
                    "reason", !payment.getReason().isEmpty() ? payment.getAmount() : getPayment.get("amount"),
                    "agent_id", !getPayment.get("agent_id").isEmpty() ? payment.getAmount() : getPayment.get("amount"),
                    "type", payment.getTypePayment() != null ? payment.getAmount() : getPayment.get("amount")
            ), "id", getPayment.get("id"));
            if (isUpdated) {
                return Optional.of(payment);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public boolean deletePayment(Integer paymentId) {
        boolean paymentExist = Integer.parseInt(Crud.readByCondition("payments", "id", paymentId).getFirst().get("id")) != 0;
        try {
            if (paymentExist) {
                return Crud.delete("payments", "id", paymentId);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public Map<String, String> getPaymentById(Integer paymentId) {
        try {
            Map<String, String> getPayment = Crud.readByCondition("payments", "id", paymentId).getFirst();
            if (!getPayment.isEmpty()) {
                return getPayment;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getAllPayment() {
        try {
            return Crud.readAll("payments");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
