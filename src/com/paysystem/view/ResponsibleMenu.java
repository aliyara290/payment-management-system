package com.paysystem.view;

import com.paysystem.controller.PaymentController;
import com.paysystem.model.entities.User;
import com.paysystem.repository.impl.AuthRepositoryImpl;
import com.paysystem.repository.interfaces.PaymentInterface;
import com.paysystem.service.StatisticsService;
import com.paysystem.utils.Crud;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ResponsibleMenu {
    private final PaymentController paymentController;
    private final StatisticsService statisticsService;
    private final Scanner scanner;
    private final User loggedUser;

    public ResponsibleMenu(User loggedUser) {
        this.loggedUser = loggedUser;
        PaymentInterface paymentRepository = new AuthRepositoryImpl.PaymentRepositoryImpl();
        this.paymentController = new PaymentController(paymentRepository, loggedUser);
        this.statisticsService = new StatisticsService();
        this.scanner = new Scanner(System.in);
    }

    public void ResponsibleMenuConsole() {
        boolean running = true;

        while (running) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("           RESPONSIBLE DASHBOARD - PAYMENT MANAGEMENT");
            System.out.println("=".repeat(60));
            System.out.println("1. Create Payment");
            System.out.println("2. Update Payment");
            System.out.println("3. Delete Payment");
            System.out.println("4. View Payment");
            System.out.println("5. View All Payments");
            System.out.println("6. View My Department Statistics");
            System.out.println("7. Exit");
            System.out.println("=".repeat(60));
            System.out.print("Your choice (1-7): ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        paymentController.createPayment();
                        break;
                    case 2:
                        paymentController.updatePayment();
                        break;
                    case 3:
                        paymentController.deletePayment();
                        break;
                    case 4:
                        paymentController.viewPayment();
                        break;
                    case 5:
                        paymentController.viewAllPayments();
                        break;
                    case 6:
                        viewMyDepartmentStatistics();
                        break;
                    case 7:
                        System.out.println("\n Returning to main menu...");
                        running = false;
                        break;
                    default:
                        System.out.println("\n Invalid choice! Please select 1 to 7.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n Please enter a valid number!");
            }

            if (running) {
                System.out.println("\n➡  Press Enter to continue...");
                scanner.nextLine();
            }
        }
    }

    private void viewMyDepartmentStatistics() {
        try {
            // Get the responsible's department
            List<Map<String, String>> responsibleData = Crud.readByCondition("users", "email", loggedUser.getEmail());

            if (responsibleData.isEmpty()) {
                System.out.println("\nUser not found!");
                return;
            }

            String departmentIdStr = responsibleData.get(0).get("department_id");

            if (departmentIdStr == null || departmentIdStr.equals("null")) {
                System.out.println("\nYou are not assigned to any department!");
                return;
            }

            int departmentId = Integer.parseInt(departmentIdStr);

            // Get department name
            List<Map<String, String>> deptData = Crud.readByCondition("departments", "id", departmentId);

            if (deptData.isEmpty()) {
                System.out.println("\nDepartment not found!");
                return;
            }

            String departmentName = deptData.get(0).get("name");

            Map<String, Object> stats = statisticsService.getDepartmentStatistics(departmentName);
            statisticsService.displayDepartmentStatistics(stats);

        } catch (Exception e) {
            System.out.println("\nError retrieving department statistics: " + e.getMessage());
        }
    }
}