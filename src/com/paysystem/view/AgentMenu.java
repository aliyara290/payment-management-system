package com.paysystem.view;

import com.paysystem.controller.PaymentController;
import com.paysystem.model.entities.User;
import com.paysystem.repository.impl.AuthRepositoryImpl;
import com.paysystem.repository.interfaces.PaymentInterface;

import java.util.Scanner;

public class AgentMenu {
    private final PaymentController paymentController;
    private final Scanner scanner;
    private final User loggedUser;

    public AgentMenu(User loggedUser) {
        this.loggedUser = loggedUser;
        PaymentInterface paymentRepository = new AuthRepositoryImpl.PaymentRepositoryImpl();
        this.paymentController = new PaymentController(paymentRepository, loggedUser);
        this.scanner = new Scanner(System.in);
    }

    public void AgentMenuConsole() {
        boolean running = true;

        while (running) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("                  AGENT DASHBOARD");
            System.out.println("=".repeat(60));
            System.out.println("1. View My Payments");
            System.out.println("2. View Payment Statistics");
            System.out.println("3. Exit");
            System.out.println("=".repeat(60));
            System.out.print("Your choice (1-4): ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        System.out.println("\nMy Payments - Not implemented yet!");
                        break;
                    case 2:
                        System.out.println("\nPayment Statistics - Not implemented yet!");
                        break;
                    case 3:
                        System.out.println("\nReturning to main menu...");
                        running = false;
                        break;
                    default:
                        System.out.println("\nInvalid choice! Please select 1 to 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter a valid number!");
            }

            if (running) {
                System.out.println("\n Press Enter to continue...");
                scanner.nextLine();
            }
        }
    }
}