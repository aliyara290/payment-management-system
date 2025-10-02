package com.paysystem.view;

import com.paysystem.model.entities.User;

import java.util.Optional;
import java.util.Scanner;

public class MainMenu {
    private final Scanner scanner;
    private AuthMenu authMenu;

    public MainMenu() {
        this.authMenu = new AuthMenu();
        this.scanner = new Scanner(System.in);
    }

    public void handleAuthenticate() {
        Optional<User> user = authMenu.authMenuConsole();
        if (user.isPresent()) {
            showMainDashboard(user.get());
        } else {
            System.out.println("You can now login with this account!");
            System.out.println("=============================================");
            System.out.println("Press any key to continue...");
            scanner.nextLine();
        }
    }

    private void showMainDashboard(User user) {
        boolean inDashboard = true;

        while (inDashboard) {
            System.out.println("\n ================================================");
            System.out.printf("            WELCOME %s %s (%s) \n",
                    user.getFirstName().toUpperCase(),
                    user.getLastName().toUpperCase(),
                    user.getUserRole());
            System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
            System.out.println("\n ================================================");
            System.out.println("/n =============== DASHBOARD OPTIONS ==============");
            System.out.println("\n ================================================");

            switch (user.getUserRole()) {
                case DIRECTOR:
                    // Pass the logged user to DirectorMenu
                    DirectorMenu directorMenu = new DirectorMenu(user);
                    directorMenu.DirectorMenuConsole();
                    break;
                case RESPONSIBLE:
                    // ResponsibleMenu responsibleMenu = new ResponsibleMenu(user);
                    // responsibleMenu.ResponsibleMenuConsole();
                    System.out.println("not yet!");
                    break;
                case AGENT:
                    // AgentMenu agentMenu = new AgentMenu(user);
                    // agentMenu.AgentMenuConsole();
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
                    System.out.println("Logging out...");
                    inDashboard = false;
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