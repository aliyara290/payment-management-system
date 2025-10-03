package com.paysystem.repository.impl;

import com.paysystem.model.entities.*;
import com.paysystem.model.enums.UserRole;
import com.paysystem.repository.interfaces.AuthInterface;
import com.paysystem.repository.interfaces.PaymentInterface;
import com.paysystem.utils.Crud;
import com.paysystem.utils.HashPassword;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AuthRepositoryImpl implements AuthInterface {


    @Override
    public boolean register(String firstName, String lastName, String email, String password, String role) {
        String hashPassword = HashPassword.hashPassword(password);
        return Crud.create("users", Map.of(
                "first_name", firstName,
                "last_name", lastName,
                "email", email,
                "password", hashPassword,
                "role", role
        ));
    }

    public Optional<User> login(String email, String password) {
        Map<String, String> user = findByEmail(email).getFirst();
        if (!user.isEmpty() && HashPassword.verifyPassword(password, user.get("password"))) {
            UserRole role = UserRole.valueOf(user.get("role").toUpperCase());
            return switch (role) {
                case DIRECTOR ->
                        Optional.of(new Director(user.get("email"), user.get("first_name"), user.get("last_name"), user.get("password"), role));
                case RESPONSIBLE ->
                        Optional.of(new Responsible(user.get("email"), user.get("first_name"), user.get("last_name"), user.get("password"), role, null));
                case AGENT, INTERN ->
                        Optional.of(new Agent(user.get("email"), user.get("first_name"), user.get("last_name"), user.get("password"), role, null, null));
                default -> throw new IllegalArgumentException("Unknown user role: " + role);
            };

        }
        return Optional.empty();
    }


    @Override
    public List<Map<String, Object>> listAll() {
        return Crud.readAll("users");
    }


    @Override
    public List<Map<String, String>> findByEmail(String email) {
        List<Map<String, String>> userExist = Crud.readByCondition("users", "email", email);
        if (userExist.isEmpty()) {
            return List.of();
        }
        return userExist;
    }

    @Override
    public boolean updateEmployee(String employeeEmail, Map<String, Object> data) {
        String employeeId = Crud.readByCondition("users", "email", employeeEmail).getFirst().get("id");
        try {
            return Crud.update("users", data, "id", employeeId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static class PaymentRepositoryImpl implements PaymentInterface {

        @Override
        public Optional<Payment> createPayment(Payment payment, String employeeEmail) {
            try {
                List<Map<String, String>> userResult = Crud.readByCondition("users", "email", employeeEmail);

                if (userResult.isEmpty()) {
                    System.out.println("Employee not found with email: " + employeeEmail);
                    return Optional.empty();
                }

                int employeeId = Integer.parseInt(userResult.get(0).get("id"));

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

                Map<String, String> existingPayment = paymentResult.get(0);

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
}

