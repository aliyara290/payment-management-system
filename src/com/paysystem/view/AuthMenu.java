package com.paysystem.view;

import com.paysystem.controller.AuthController;
import com.paysystem.model.entities.User;
import com.paysystem.repository.impl.AuthRepositoryImpl;
import com.paysystem.repository.interfaces.AuthInterface;

import java.util.Optional;
import java.util.Scanner;

public class AuthMenu {
    private final AuthController authController;
    private final Scanner scanner;

    public AuthMenu() {
        AuthInterface authRepository = new AuthRepositoryImpl();
        this.authController = new AuthController(authRepository);
        this.scanner = new Scanner(System.in);
    }

    public Optional<User> authMenuConsole() {
        boolean running = true;

        while (running) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("WELCOME TO PAYSYSTEM PLATFORM");
            System.out.println("=".repeat(50));
            System.out.println("Please choose an option to continue:");
            System.out.println("1. Login");
            System.out.println("3. Exit");
            System.out.println("=".repeat(50));
            System.out.print("Your choice (1-2): ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        return authController.login();
                    case 2:
                        System.out.println("\nThank you for using PaySystem! Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice! Please select 1 or 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        return Optional.empty();
    }

//    private void showResponsibleOptions() {
//        System.out.println("1. Manage Department Users");
//        System.out.println("2. Approve Payments");
//        System.out.println("3. Department Statistics");
//        System.out.println("4. Generate Reports");
//    }
//
//    private void showAgentOptions() {
//        System.out.println("1. Create Payment Request");
//        System.out.println("2. View My Payments");
//        System.out.println("3. Update Profile");
//        System.out.println("4. My Statistics");
//    }

}