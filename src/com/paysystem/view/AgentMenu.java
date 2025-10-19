package com.paysystem.view;

import com.paysystem.controller.PaymentController;
import com.paysystem.model.entities.User;
import com.paysystem.repository.impl.AuthRepositoryImpl;
import com.paysystem.repository.impl.PaymentRepositoryImpl;
import com.paysystem.repository.interfaces.PaymentInterface;
import com.paysystem.service.StatisticsService;
import com.paysystem.utils.Crud;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AgentMenu {
    private final PaymentController paymentController;
    private final StatisticsService statisticsService;
    private final Scanner scanner;
    private final User loggedUser;

    public AgentMenu(User loggedUser) {
        this.loggedUser = loggedUser;
        PaymentInterface paymentRepository = new PaymentRepositoryImpl();
        this.paymentController = new PaymentController(paymentRepository, loggedUser);
        this.statisticsService = new StatisticsService();
        this.scanner = new Scanner(System.in);
    }

    public void AgentMenuConsole() {
        boolean running = true;

        while (running) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("                  AGENT DASHBOARD");
            System.out.println("=".repeat(60));
            System.out.println("1. View My Payments");
            System.out.println("2. View My Statistics");
            System.out.println("3. Exit");
            System.out.println("=".repeat(60));
            System.out.print("Your choice (1-3): ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        viewMyPayments();
                        break;
                    case 2:
                        viewMyStatistics();
                        break;
                    case 3:
                        System.out.println("\nGood bye!");
                        running = false;
                        break;
                    default:
                        System.out.println("\nInvalid choice! Please select 1 to 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter a valid number!");
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
    }

    private void viewMyPayments() {
        try {
            // Get agent ID from email
            List<Map<String, String>> agentData = Crud.readByCondition("users", "email", loggedUser.getEmail());

            if (agentData.isEmpty()) {
                System.out.println("\nUser not found!");
                return;
            }

            int agentId = Integer.parseInt(agentData.get(0).get("id"));

            // Get all payments for this agent
            List<Map<String, String>> payments = Crud.readByCondition("payments", "agent_id", agentId);

            if (payments.isEmpty()) {
                System.out.println("\n" + "=".repeat(60));
                System.out.println("No payments found for you yet!");
                System.out.println("=".repeat(60));
                return;
            }

            // Calculate total amount
            double totalAmount = 0;
            for (Map<String, String> payment : payments) {
                totalAmount += Double.parseDouble(payment.get("amount"));
            }

            System.out.println("\n" + "=".repeat(100));
            System.out.println("                           MY PAYMENTS");
            System.out.println("=".repeat(100));
            System.out.println("Total Payments: " + payments.size());
            System.out.println("Total Amount: " + String.format("%.2f", totalAmount) + " MAD");
            System.out.println("=".repeat(100));
            System.out.printf("%-5s | %-12s | %-15s | %-35s | %-20s%n",
                    "ID", "Amount (MAD)", "Type", "Reason", "Date");
            System.out.println("=".repeat(100));

            for (Map<String, String> payment : payments) {
                String reason = payment.get("reason");
                if (reason != null && reason.length() > 35) {
                    reason = reason.substring(0, 32) + "...";
                }

                System.out.printf("%-5s | %-12s | %-15s | %-35s | %-20s%n",
                        payment.get("id"),
                        String.format("%.2f", Double.parseDouble(payment.get("amount"))),
                        payment.get("type").toUpperCase(),
                        reason,
                        payment.get("created_at"));
            }
            System.out.println("=".repeat(100));

        } catch (Exception e) {
            System.out.println("\nError retrieving payments: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void viewMyStatistics() {
        Map<String, Object> stats = statisticsService.getAgentStatistics(loggedUser.getEmail());
        statisticsService.displayAgentStatistics(stats);
    }
}