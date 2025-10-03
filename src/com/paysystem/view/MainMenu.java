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
            System.out.println("\n" + "=".repeat(60));
            System.out.printf("            WELCOME %s %s (%s) \n",
                    user.getFirstName().toUpperCase(),
                    user.getLastName().toUpperCase(),
                    user.getUserRole());
            System.out.println("=".repeat(60));

            switch (user.getUserRole()) {
                case DIRECTOR:
                    DirectorMenu directorMenu = new DirectorMenu(user);
                    directorMenu.DirectorMenuConsole();
                    inDashboard = false; // Exit after director menu
                    break;

                case RESPONSIBLE:
                    ResponsibleMenu responsibleMenu = new ResponsibleMenu(user);
                    responsibleMenu.ResponsibleMenuConsole();
                    inDashboard = false; // Exit after responsible menu
                    break;

                case AGENT:
                    AgentMenu agentMenu = new AgentMenu(user);
                    agentMenu.AgentMenuConsole();
                    inDashboard = false; // Exit after agent menu
                    break;

                case INTERN:
                    System.out.println("\n📝 INTERN DASHBOARD");
                    System.out.println("Unfortunately, there are no options available for interns.");
                    System.out.println("\nPress Enter to logout...");
                    scanner.nextLine();
                    inDashboard = false;
                    break;
            }
        }

        System.out.println("\n👋 Logged out successfully. Goodbye!");
    }
}