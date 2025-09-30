package com.paysystem.view;

import com.paysystem.model.entities.User;

import java.util.Optional;
import java.util.Scanner;

public class MainMenu {
    //    private Optional<User> user;
    private final Scanner scanner;
    private DirectorMenu directorMenu;
    private AuthMenu authMenu;

    public MainMenu() {
        this.authMenu = new AuthMenu();
        this.directorMenu = new DirectorMenu();
        this.scanner = new Scanner(System.in);
    }

    public void handleAuthenticate() {
        Optional<User> user = authMenu.authMenuConsole();
        if (user.isPresent()) {
            showMainDashboard(user);
        } else {
            System.out.println("You can now login with this account!");
            System.out.println("=============================================");
            System.out.println("Press any key to continue...");
            scanner.nextLine();
        }
    }


    private void showMainDashboard(Optional<User> user) {
        boolean inDashboard = true;

        while (inDashboard) {
            System.out.println("\n" + "=".repeat(60));
            System.out.printf(" WELCOME %s %s (%s) \n", user.get().getFirstName().toUpperCase(), user.get().getLastName().toUpperCase(), user.get().getUserRole());
            System.out.println("=".repeat(60));
            System.out.println("========= DASHBOARD OPTIONS =========");

            switch (user.get().getUserRole()) {
                case DIRECTOR:
                    directorMenu.DirectorMenuConsole();
                    break;
                case RESPONSIBLE:
//                    showResponsibleOptions();
                    System.out.println("not yet!");
                    break;
                case AGENT:
//                    showAgentOptions();
                    System.out.println("not yet!");
                    break;
                case INTERN:
                    System.out.println("Unfortunately there is no options for you!");
                    break;
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
//                    handleDashboardChoice(choice, user);
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
}
