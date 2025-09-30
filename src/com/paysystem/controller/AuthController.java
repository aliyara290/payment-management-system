package com.paysystem.controller;


import com.paysystem.model.entities.User;
import com.paysystem.model.enums.UserRole;
import com.paysystem.repository.interfaces.AuthInterface;
import com.paysystem.service.AuthService;
import com.paysystem.utils.HashPassword;

import java.util.Optional;
import java.util.Scanner;

public class AuthController {

    private final AuthService authService;
    private Scanner scanner;

    public AuthController(AuthInterface authRepository) {
        this.authService = new AuthService(authRepository);
        this.scanner = new Scanner(System.in);
    }

    public Optional<User> login() {
        System.out.println("====== LOGIN ======");
        System.out.println("Enter your email: ");
        String email = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();

        Optional<User> user = authService.login(email, password);

        if (user.isPresent()) {
            System.out.println("You logged in as " + user.get().getUserRole());
            return user;
        } else {
            System.out.println("Login failed!");
        }
        return user;
    }

    public boolean register(User loggedUser) {

        System.out.println("=================================================");
        System.out.println("============= EMPLOYEE REGISTRATION =============");
        System.out.println("=================================================");

        System.out.print("\n First Name: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine();


        System.out.println("Choose one of these roles: ");

        switch (loggedUser.getUserRole()) {
            case UserRole.DIRECTOR:
                System.out.println("1: Director");
                System.out.println("2: Responsible");
                System.out.println("3: Agent");
                System.out.println("4: Intern");
                break;
            case UserRole.RESPONSIBLE:
                System.out.println("1: Agent");
                System.out.println("2: Intern");
                break;
            default:
                System.out.println("Invalid role!");
                return false;
        }

        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        UserRole role = null;

        switch (loggedUser.getUserRole()) {
            case UserRole.DIRECTOR:
                switch (choice) {
                    case 1:
                        role = UserRole.DIRECTOR;
                        break;
                    case 2:
                        role = UserRole.RESPONSIBLE;
                        break;
                    case 3:
                        role = UserRole.AGENT;
                        break;
                    case 4:
                        role = UserRole.INTERN;
                        break;
                    default:
                        System.out.println("Invalid choice!");
                        break;
                }
                break;
            case UserRole.RESPONSIBLE:
                switch (choice) {
                    case 1:
                        role = UserRole.AGENT;
                        break;
                    case 2:
                        role = UserRole.INTERN;
                        break;
                    default:
                        System.out.println("Invalid choice!");
                        break;
                }
                break;
            default:
                System.out.println("Invalid role!");
                break;
        }

        if (role != null) {
            boolean success = authService.register(firstName, lastName, email, password, role);
            if (success) {
                System.out.println("User registered successfully!");
                return true;
            } else {
                System.out.println("Failed to register user.");
                return false;
            }
        } else {
            System.out.println("No valid role selected, registration aborted.");
        }
        return false;
    }

}