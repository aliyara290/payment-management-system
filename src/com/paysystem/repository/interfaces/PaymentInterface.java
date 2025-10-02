package com.paysystem.repository.interfaces;

import com.paysystem.model.entities.Payment;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PaymentInterface {

    Optional<Payment> createPayment(Payment payment, String employeeEmail);
    Optional<Payment> updatePayment(Payment payment);
    boolean deletePayment(Integer paymentId);
    Map<String, String> getPaymentById(Integer paymentId);
    List<Map<String, Object>> getAllPayment();

}
