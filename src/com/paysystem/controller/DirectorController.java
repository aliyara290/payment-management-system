package com.paysystem.controller;

import com.paysystem.model.entities.Department;
import com.paysystem.repository.interfaces.DirectorInterface;
import com.paysystem.service.DirectorService;

import java.util.Optional;
import java.util.Scanner;

public class DirectorController {
    private final DirectorService directorService;
    private final Scanner scanner;

    public DirectorController(DirectorInterface directorRepository) {
        this.directorService = new DirectorService(directorRepository);
        this.scanner = new Scanner(System.in);
    }

    public Optional<Department> createDepartment() {
        System.out.println("===============================================");
        System.out.println("------------[ CREATE NEW DEPARTMENT ]----------");
        System.out.println("===============================================");
        System.out.println("\n Department name: ");
        String name = scanner.nextLine();
        Optional<Department> deptCreation = directorService.createDepartment(name);
        deptCreation.ifPresent(department -> System.out.println(department.getName() + " Department created successfully!"));
        return deptCreation;
    }

    public void updateDepartment() {
        System.out.println("=============================================");
        System.out.println("-------------[ UPDATE DEPARTMENT ]-----------");
        System.out.println("=============================================");
        System.out.println("\n Department old name: ");
        String oldName = scanner.nextLine();
        System.out.println("Department new name: ");
        String newName = scanner.nextLine();
        boolean isUpdated = directorService.updateDepartment(oldName, newName);

        if (isUpdated) {
            System.out.println("Department updated successfully!");
        }
    }

    public void deleteDepartment() {
        System.out.println("=============================================");
        System.out.println("-------------[ DELETE DEPARTMENT ]-----------");
        System.out.println("=============================================");
        System.out.println("\n Department name you want to delete: ");
        String name = scanner.nextLine();
        boolean isDeleted = directorService.deleteDepartment(name);
        if (isDeleted) {
            System.out.println(name + " Department deleted successfully!");
        } else  {
            System.out.println(name + " Department could not be deleted!");
        }
    }

    public void assignResponsibleToDepartment() {
        System.out.println("==========================================================");
        System.out.println("------------[ ASSIGN RESPONSIBLE TO DEPARTMENT ]----------");
        System.out.println("==========================================================");
        System.out.println("\n Department name: ");
        String deptName = scanner.nextLine();
        System.out.println("Department Responsible Email: ");
        String responsibleEmail = scanner.nextLine();
        boolean isAssigned = directorService.assignResponsibleToDepartment(deptName, responsibleEmail);
        if(isAssigned) {
            System.out.println("Department's responsible assigned successfully!");
        } else {
            System.out.println("Department responsible couldn't be assigned!");
        }
    }


}
