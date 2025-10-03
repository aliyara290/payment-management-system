package com.paysystem.controller;

import com.paysystem.model.entities.Payment;
import com.paysystem.model.entities.User;
import com.paysystem.model.enums.TypePayment;
import com.paysystem.model.enums.UserRole;
import com.paysystem.repository.interfaces.PaymentInterface;
import com.paysystem.service.PaymentService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class PaymentController {
    private final PaymentService paymentService;
    private final Scanner scanner;
    private final User loggedUser;

    public PaymentController(PaymentInterface paymentRepository, User loggedUser) {
        this.paymentService = new PaymentService(paymentRepository);
        this.scanner = new Scanner(System.in);
        this.loggedUser = loggedUser;
    }

    private boolean isAuthorized() {
        if (loggedUser.getUserRole() != UserRole.RESPONSIBLE) {
            System.out.println("\n Access Denied: Only Responsible users can manage payments!");
            return false;
        }
        return true;
    }

    public Optional<Payment> createPayment() {
        if (!isAuthorized()) return Optional.empty();

        System.out.println("\n=================================================");
        System.out.println("================ CREATE PAYMENT =================");
        System.out.println("=================================================");

        try {
            System.out.print("\nEmployee Email: ");
            String employeeEmail = scanner.nextLine().trim();

            System.out.print("Payment Amount: ");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Payment Reason: ");
            String reason = scanner.nextLine().trim();

            System.out.println("\nSelect Payment Type:");
            System.out.println("1. Salary");
            System.out.println("2. Bonus");
            System.out.println("3. Prime");
            System.out.println("4. Compensation");
            System.out.print("Your choice (1-4): ");

            int typeChoice = Integer.parseInt(scanner.nextLine().trim());
            TypePayment typePayment = switch (typeChoice) {
                case 1 -> TypePayment.SALARY;
                case 2 -> TypePayment.BONUS;
                case 3 -> TypePayment.PRIME;
                case 4 -> TypePayment.COMPENSATION;
                default -> {
                    System.out.println("Invalid payment type!");
                    yield null;
                }
            };

            if (typePayment == null) {
                return Optional.empty();
            }

            Payment payment = new Payment(amount, typePayment, reason, 0);
            Optional<Payment> created = paymentService.createPayment(payment, employeeEmail);

            if (created.isPresent()) {
                System.out.println("\n Payment created successfully!");
                return created;
            } else {
                System.out.println("\n Failed to create payment!");
                return Optional.empty();
            }

        } catch (NumberFormatException e) {
            System.out.println("\n Invalid input! Please enter valid numbers.");
            return Optional.empty();
        } catch (Exception e) {
            System.out.println("\n Error creating payment: " + e.getMessage());
            return Optional.empty();
        }
    }

    public void updatePayment() {
        if (!isAuthorized()) return;

        System.out.println("\n=================================================");
        System.out.println("================ UPDATE PAYMENT =================");
        System.out.println("=================================================");

        try {
            System.out.print("\nPayment ID to update: ");
            int paymentId = Integer.parseInt(scanner.nextLine().trim());

            Map<String, String> existingPayment = paymentService.getPaymentById(paymentId);
            if (existingPayment == null || existingPayment.isEmpty()) {
                System.out.println("\n Payment not found!");
                return;
            }

            System.out.println("\n============================================================================");
            System.out.println("  Update payment - leave input empty if you don't want to update a field");
            System.out.println("============================================================================");

            System.out.print("\nNew Amount (current: " + existingPayment.get("amount") + "): ");
            String amountStr = scanner.nextLine().trim();
            double amount = amountStr.isEmpty() ?
                    Double.parseDouble(existingPayment.get("amount")) :
                    Double.parseDouble(amountStr);

            System.out.print("New Reason (current: " + existingPayment.get("reason") + "): ");
            String reason = scanner.nextLine().trim();
            if (reason.isEmpty()) {
                reason = existingPayment.get("reason");
            }

            System.out.println("\nUpdate Payment Type?");
            System.out.println("1. Salary");
            System.out.println("2. Bonus");
            System.out.println("3. Prime");
            System.out.println("4. Compensation");
            System.out.println("5. Keep current (" + existingPayment.get("type") + ")");
            System.out.print("Your choice (1-5): ");

            String typeChoiceStr = scanner.nextLine().trim();
            TypePayment typePayment;

            if (typeChoiceStr.isEmpty() || typeChoiceStr.equals("5")) {
                typePayment = TypePayment.valueOf(existingPayment.get("type").toUpperCase());
            } else {
                int typeChoice = Integer.parseInt(typeChoiceStr);
                typePayment = switch (typeChoice) {
                    case 1 -> TypePayment.SALARY;
                    case 2 -> TypePayment.BONUS;
                    case 3 -> TypePayment.PRIME;
                    case 4 -> TypePayment.COMPENSATION;
                    default -> TypePayment.valueOf(existingPayment.get("type").toUpperCase());
                };
            }

            Payment payment = new Payment(amount, typePayment, reason,
                    Integer.parseInt(existingPayment.get("agent_id")));
            payment.setPaymentId(paymentId);

            Optional<Payment> updated = paymentService.updatePayment(payment);
            if (updated.isPresent()) {
                System.out.println("\n Payment updated successfully!");
            } else {
                System.out.println("\n Failed to update payment!");
            }

        } catch (NumberFormatException e) {
            System.out.println("\n Invalid input! Please enter valid numbers.");
        } catch (Exception e) {
            System.out.println("\n Error updating payment: " + e.getMessage());
        }
    }

    public void deletePayment() {
        if (!isAuthorized()) return;

        System.out.println("\n=================================================");
        System.out.println("================ DELETE PAYMENT =================");
        System.out.println("=================================================");

        try {
            System.out.print("\nPayment ID to delete: ");
            int paymentId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Are you sure you want to delete this payment? (yes/no): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (!confirmation.equals("yes")) {
                System.out.println("\n Deletion cancelled.");
                return;
            }

            boolean deleted = paymentService.deletePayment(paymentId);
            if (deleted) {
                System.out.println("\n Payment deleted successfully!");
            } else {
                System.out.println("\n Failed to delete payment or payment not found!");
            }

        } catch (NumberFormatException e) {
            System.out.println("\n Invalid payment ID!");
        } catch (Exception e) {
            System.out.println("\n Error deleting payment: " + e.getMessage());
        }
    }

    public void viewPayment() {
        if (!isAuthorized()) return;

        System.out.println("\n=================================================");
        System.out.println("================= VIEW PAYMENT ==================");
        System.out.println("=================================================");

        try {
            System.out.print("\nPayment ID: ");
            int paymentId = Integer.parseInt(scanner.nextLine().trim());

            Map<String, String> payment = paymentService.getPaymentById(paymentId);
            if (payment != null && !payment.isEmpty()) {
                System.out.println("\n" + "=".repeat(50));
                System.out.println("Payment ID: " + payment.get("id"));
                System.out.println("Amount: " + payment.get("amount"));
                System.out.println("Reason: " + payment.get("reason"));
                System.out.println("Type: " + payment.get("type"));
                System.out.println("Agent ID: " + payment.get("agent_id"));
                System.out.println("Created: " + payment.get("created_at"));
                System.out.println("Updated: " + payment.get("updated_at"));
                System.out.println("=".repeat(50));
            } else {
                System.out.println("\n Payment not found!");
            }

        } catch (NumberFormatException e) {
            System.out.println("\n Invalid payment ID!");
        } catch (Exception e) {
            System.out.println("\n Error retrieving payment: " + e.getMessage());
        }
    }

    public void viewAllPayments() {
        if (!isAuthorized()) return;

        System.out.println("\n=================================================");
        System.out.println("============== ALL PAYMENTS LIST ================");
        System.out.println("=================================================");

        try {
            List<Map<String, Object>> payments = paymentService.getAllPayment();

            if (payments == null || payments.isEmpty()) {
                System.out.println("\n No payments found!");
                return;
            }

            System.out.println("\nTotal Payments: " + payments.size());
            System.out.println("=".repeat(100));
            System.out.printf("%-5s | %-10s | %-15s | %-30s | %-10s | %-20s%n",
                    "ID", "Amount", "Type", "Reason", "Agent ID", "Created");
            System.out.println("=".repeat(100));

            for (Map<String, Object> payment : payments) {
                System.out.printf("%-5s | %-10s | %-15s | %-30s | %-10s | %-20s%n",
                        payment.get("id"),
                        payment.get("amount"),
                        payment.get("type"),
                        payment.get("reason"),
                        payment.get("agent_id"),
                        payment.get("created_at"));
            }
            System.out.println("=".repeat(100));

        } catch (Exception e) {
            System.out.println("\n Error retrieving payments: " + e.getMessage());
        }
    }


}