package com.paysystem.view;

import com.paysystem.controller.AuthController;
import com.paysystem.controller.DirectorController;
import com.paysystem.model.entities.User;
import com.paysystem.repository.impl.AuthRepositoryImpl;
import com.paysystem.repository.impl.DirectorRepositoryImpl;
import com.paysystem.repository.interfaces.AuthInterface;
import com.paysystem.repository.interfaces.DirectorInterface;
import com.paysystem.service.StatisticsService;

import java.util.Map;
import java.util.Scanner;

public class DirectorMenu {
    private DirectorController directorController;
    private DirectorInterface directorInterface;
    private AuthController authController;
    private StatisticsService statisticsService;
    private Scanner scanner;
    private User loggedUser;

    public DirectorMenu(User loggedUser) {
        this.loggedUser = loggedUser;
        this.directorInterface = new DirectorRepositoryImpl();
        this.directorController = new DirectorController(this.directorInterface);
        AuthInterface authRepository = new AuthRepositoryImpl();
        this.authController = new AuthController(authRepository);
        this.statisticsService = new StatisticsService();
        this.scanner = new Scanner(System.in);
    }

    public void DirectorMenuConsole() {
        boolean running = true;

        while (running) {
            System.out.println("=".repeat(50));
            System.out.println("1. Manage Users");
            System.out.println("2. Manage Departments");
            System.out.println("3. View Department Statistics");
            System.out.println("4. Exit");
            System.out.println("=".repeat(50));
            System.out.print("Your choice (1-5): ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        manageUsers();
                        break;
                    case 2:
                        manageDepartment();
                        break;
                    case 3:
                        viewDepartmentStatistics();
                        break;
                    case 4:
                        System.out.println("\nThank you for using PaySystem! Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice! Please select 1 to 5.");
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

    private void viewDepartmentStatistics() {
        System.out.println("\n=================================================");
        System.out.println("=========== DEPARTMENT STATISTICS ===============");
        System.out.println("=================================================");
        System.out.print("\nEnter Department Name: ");
        String departmentName = scanner.nextLine().trim();

        if (departmentName.isEmpty()) {
            System.out.println("Department name cannot be empty!");
            return;
        }

        Map<String, Object> stats = statisticsService.getDepartmentStatistics(departmentName);
        statisticsService.displayDepartmentStatistics(stats);
    }

    public void manageDepartment() {
        boolean running = true;
        while (running) {
            System.out.println("\n==============================================");
            System.out.println("-----------[ DEPARTMENT MANAGEMENT ]----------");
            System.out.println("==============================================");
            System.out.println("1: Create Department");
            System.out.println("2: Update Department");
            System.out.println("3: Get Department");
            System.out.println("4: Get All Department");
            System.out.println("5: Delete Department");
            System.out.println("6: Assign responsible to department");
            System.out.println("7: Exit");
            System.out.println("Your choice (1-7): ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        directorController.createDepartment();
                        break;
                    case 2:
                        directorController.updateDepartment();
                        break;
                    case 3:
                        getDepartment();
                        break;
                    case 4:
                        getAllDepartments();
                        break;
                    case 5:
                        directorController.deleteDepartment();
                        break;
                    case 6:
                        directorController.assignResponsibleToDepartment();
                        break;
                    case 7:
                        System.out.println("Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice! Please select from 1 to 7.");
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

    public void manageUsers() {
        boolean running = true;
        while (running) {
            System.out.println("\n===============================================");
            System.out.println("------------[ EMPLOYEES MANAGEMENT ]-----------");
            System.out.println("===============================================");
            System.out.println("1: Create Employee");
            System.out.println("2: Update Employee");
            System.out.println("3: Get Employee");
            System.out.println("4: Get All Employee");
            System.out.println("5: Delete Employee *");
            System.out.println("6: Assign responsible to employee");
            System.out.println("7: Exit");
            System.out.println("Your choice (1-7): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                        System.out.println("Logged user role: " + this.loggedUser.getUserRole());
                        authController.register(this.loggedUser);
                        break;
                    case 2:
                        authController.updateUser();
                        break;
                    case 3:
                        System.out.println("Not implemented yet!");
                        break;
                    case 4:
                        System.out.println("Not implemented yet!");
                        break;
                    case 5:
                        System.out.println("Not implemented yet!");
                        break;
                    case 6:
                        System.out.println("Not implemented yet!");
                        break;
                    case 7:
                        System.out.println("Exiting...");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice! Please select from 1 to 7.");
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

    private void getDepartment() {
        System.out.println("\n==============================================");
        System.out.println("-------------[ GET DEPARTMENT ]---------------");
        System.out.println("==============================================");
        System.out.print("\nEnter Department Name: ");
        String departmentName = scanner.nextLine().trim();

        if (departmentName.isEmpty()) {
            System.out.println("Department name cannot be empty!");
            return;
        }

        try {
            Map<String, String> department = directorController.getDepartment(departmentName);

            if (department == null || department.isEmpty()) {
                System.out.println("\nDepartment not found!");
                return;
            }

            System.out.println("\n" + "=".repeat(60));
            System.out.println("              DEPARTMENT DETAILS");
            System.out.println("=".repeat(60));
            System.out.println("ID: " + department.get("id"));
            System.out.println("Name: " + department.get("name"));
            System.out.println("Created At: " + department.get("created_at"));
            System.out.println("Updated At: " + department.get("updated_at"));
            System.out.println("=".repeat(60));

        } catch (Exception e) {
            System.out.println("\nError retrieving department: " + e.getMessage());
        }
    }

    private void getAllDepartments() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                      ALL DEPARTMENTS");
        System.out.println("=".repeat(80));

        try {
            java.util.List<Map<String, Object>> departments = directorController.getAllDepartments();

            if (departments == null || departments.isEmpty()) {
                System.out.println("No departments found!");
                System.out.println("=".repeat(80));
                return;
            }

            System.out.println("Total Departments: " + departments.size());
            System.out.println("=".repeat(80));
            System.out.printf("%-5s | %-30s | %-20s | %-20s%n",
                    "ID", "Name", "Created At", "Updated At");
            System.out.println("=".repeat(80));

            for (Map<String, Object> dept : departments) {
                System.out.printf("%-5s | %-30s | %-20s | %-20s%n",
                        dept.get("id"),
                        dept.get("name"),
                        dept.get("created_at"),
                        dept.get("updated_at"));
            }
            System.out.println("=".repeat(80));

        } catch (Exception e) {
            System.out.println("\nError retrieving departments: " + e.getMessage());
        }
    }
}