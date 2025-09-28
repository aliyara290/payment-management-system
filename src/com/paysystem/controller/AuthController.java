package com.paysystem.controller;


import com.paysystem.model.entities.User;
import com.paysystem.repository.interfaces.AuthInterface;
import com.paysystem.service.AuthService;

import java.util.Optional;
import java.util.Scanner;

public class AuthController {

    private final AuthService authService;
    private Scanner scanner;

    public AuthController(AuthInterface authRepository) {
        this.authService = new AuthService(authRepository);
        this.scanner = new Scanner(System.in);
    }

    public Optional<User>  login() {
        System.out.println("--[ LOGIN ]--");
        String username = scanner.nextLine();
        String password = scanner.nextLine();

        Optional<User> user = authService.login(username, password);

        if(user.isPresent()) {
            System.out.println("--[ LOGIN SUCCESSFUL ]--");
            System.out.println("WELCOME " + user.get());
            return user;
        } else  {
            System.out.println("--[ LOGIN FAILED ]--");
        }
        return user;
    }





















































    //    private final AuthService authService;
//    private final Scanner scanner;
//    private User currentUser;
//
//    public AuthController() {
//        this.authService = new AuthService();
//        this.scanner = new Scanner(System.in);
//    }
//
//    public User login() {
//        System.out.println("=== LOGIN ===");
//
//        System.out.print("Email: ");
//        String email = scanner.nextLine().trim();
//
//        System.out.print("Password: ");
//        String password = scanner.nextLine();
//
//        if (email.isEmpty() || password.isEmpty()) {
//            System.out.println("Email and password cannot be empty!");
//            return null;
//        }
//
//        Optional<User> user = authService.login(email, password);
//
//        if (user.isPresent()) {
//            currentUser = user.get();
//            System.out.println("Login successful! Welcome " + currentUser.getFirstName());
//            return currentUser;
//        } else {
//            System.out.println("Invalid email or password!");
//            return null;
//        }
//    }
//
//    public boolean register() {
//        System.out.println("=== REGISTRATION ===");
//
//        System.out.print("First Name: ");
//        String firstName = scanner.nextLine().trim();
//
//        System.out.print("Last Name: ");
//        String lastName = scanner.nextLine().trim();
//
//        System.out.print("Email: ");
//        String email = scanner.nextLine().trim();
//
//        System.out.print("Password: ");
//        String password = scanner.nextLine();
//
//        System.out.print("Confirm Password: ");
//        String confirmPassword = scanner.nextLine();
//
//        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
//            System.out.println("All fields are required!");
//            return false;
//        }
//
//        if (!HashPassword.isValidEmail(email)) {
//            System.out.println("Invalid email format!");
//            return false;
//        }
//
//        if (authService.emailExists(email)) {
//            System.out.println("Email already exists!");
//            return false;
//        }
//
//        if (!password.equals(confirmPassword)) {
//            System.out.println("Passwords do not match!");
//            return false;
//        }
//
//        if (!HashPassword.isValidPassword(password)) {
//            System.out.println("Password must be at least 6 characters with uppercase, lowercase, and number!");
//            return false;
//        }
//
//        // Select role
//        UserRole role = UserRole.DIRECTOR;
////        if (role == null) return false;
//
//        boolean success = authService.register(email, firstName, lastName, password, role);
//
//        if (success) {
//            System.out.println("Registration successful! You can now login.");
//            return true;
//        } else {
//            System.out.println("Registration failed. Please try again.");
//            return false;
//        }
//    }
//
////    private UserRole selectUserRole() {
////        System.out.println("\nSelect User Role:");
////        System.out.println("1. Agent");
////        System.out.println("2. Responsible");
////        System.out.println("3. Director");
////        System.out.println("4. Intern");
////        System.out.print("Choose role (1-4): ");
////
////        try {
////            int choice = Integer.parseInt(scanner.nextLine().trim());
////
////            switch (choice) {
////                case 1: return UserRole.AGENT;
////                case 2: return UserRole.RESPONSIBLE;
////                case 3: return UserRole.DIRECTOR;
////                case 4: return UserRole.INTERN;
////                default:
////                    System.out.println("Invalid choice!");
////                    return null;
////            }
////        } catch (NumberFormatException e) {
////            System.out.println("Please enter a valid number!");
////            return null;
////        }
////    }
//
//    public User getCurrentUser() {
//        return currentUser;
//    }
//
//    public void logout() {
//        currentUser = null;
//        System.out.println("Logged out successfully!");
//    }
//
//    public boolean isLoggedIn() {
//        return currentUser != null;
//    }
}