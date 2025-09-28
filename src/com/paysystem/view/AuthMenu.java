package com.paysystem.view;

import com.paysystem.controller.AuthController;
import com.paysystem.model.entities.User;
import com.paysystem.repository.impl.AuthRepositoryImpl;
import com.paysystem.repository.interfaces.AuthInterface;

import java.util.Optional;
import java.util.Scanner;

public class AuthMenu {
    private final AuthController authController;
    private  AuthInterface authRepository;
    private final Scanner scanner;

    public AuthMenu() {
        this.authRepository = new AuthRepositoryImpl();
        this.authController = new AuthController(this.authRepository);
        this.scanner = new Scanner(System.in);
    }

    public void authMenuConsole() {
        boolean running = true;

        while (running) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("WELCOME TO PAYSYSTEM PLATFORM");
            System.out.println("=".repeat(50));
            System.out.println("Please choose an option to continue:");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.println("=".repeat(50));
            System.out.print("Your choice (1-3): ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        handleLogin();
                        break;
                    case 2:
//                        handleRegistration();
                        System.out.println("not yet");
                        break;
                    case 3:
                        System.out.println("\nThank you for using PaySystem! Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice! Please select 1, 2, or 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
    }

    private void handleLogin() {
        Optional<User> user = authController.login();

        if (user.isPresent()) {
            // Show main dashboard based on user role
            showMainDashboard(user);
        }
    }

//    private void handleRegistration() {
//        boolean success = authController.register();
//
//        if (success) {
//            System.out.println("\n Welcome to PaySystem! Please login with your new account.");
//        }
//    }

    private void showMainDashboard(Optional<User> user) {
        boolean inDashboard = true;

        while (inDashboard) {
            System.out.println("\n" + "=".repeat(60));
            System.out.printf(" WELCOME %s %s (%s) 👤\n", user.get().getFirstName().toUpperCase(), user.get().getLastName().toUpperCase(), user.get().getUserRole());
            System.out.println("=".repeat(60));
            System.out.println("Dashboard Options:");

            // Show options based on user role
            if(user.isPresent()) {

            switch (user.get().getUserRole()) {
                case DIRECTOR:
                    showManagerOptions();
                    break;
                case RESPONSIBLE:
                    showResponsibleOptions();
                    break;
                case AGENT:
                    showAgentOptions();
                    break;
                case INTERN:
                    System.out.println("Unfortunately there is no options for you!");
                    break;
            }
            }

            System.out.println("0. Logout");
            System.out.println("=".repeat(60));
            System.out.print("Your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                if (choice == 0) {
//                    authController.logout();
                    inDashboard = false;
                } else {
                    handleDashboardChoice(choice, user);
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }

            if (inDashboard) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
    }

    private void showManagerOptions() {
        System.out.println("1. Manage Users");
        System.out.println("2. Manage Departments");
        System.out.println("3. View All Payments");
        System.out.println("4. View System Statistics");
        System.out.println("5. System Settings");
    }

    private void showResponsibleOptions() {
        System.out.println("1. Manage Department Users");
        System.out.println("2. Approve Payments");
        System.out.println("3. Department Statistics");
        System.out.println("4. Generate Reports");
    }

    private void showAgentOptions() {
        System.out.println("1. Create Payment Request");
        System.out.println("2. View My Payments");
        System.out.println("3. Update Profile");
        System.out.println("4. My Statistics");
    }


    private void handleDashboardChoice(int choice, Optional<User> user) {
        System.out.println("\n🚧 Feature under development! Coming soon...");
        System.out.printf("You selected option %d as %s\n", choice, user.get().getUserRole());
    }
}