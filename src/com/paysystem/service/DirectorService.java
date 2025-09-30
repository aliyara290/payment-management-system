package com.paysystem.service;

import com.paysystem.model.entities.Department;
import com.paysystem.model.entities.User;
import com.paysystem.repository.interfaces.DirectorInterface;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DirectorService {
    private final DirectorInterface directorRepository;
    private User user;

    public DirectorService(DirectorInterface director) {
        this.directorRepository = director;
    }

    public Optional<Department> createDepartment(String departmentName) {

        if (departmentName.isEmpty()) {
            System.out.println("DepartmentName is empty!");
            return Optional.empty();
        }

        boolean isCreated = directorRepository.createDepartment(departmentName);
        if (isCreated) {
            Department dept = new Department(departmentName);
            return Optional.of(dept);
        }
        return Optional.empty();
    }

    public boolean updateDepartment(String departmentName, String newDepartmentName) {
        if (departmentName.isEmpty()) {
            System.out.println("Department name is empty!");
            return false;
        }

        if (!directorRepository.checkIfDepartmentNameAvailable(departmentName)) {
            System.out.println("Department name is already in use!");
            return false;
        }
        return directorRepository.updateDepartment(departmentName, newDepartmentName);
    }

    public boolean deleteDepartment(String departmentName) {
        if (departmentName.isEmpty()) {
            System.out.println("Department name is empty!");
            return false;
        }
        return directorRepository.deleteDepartment(departmentName);
    }

    public Map<String, String> getDepartment(String departmentName) {
        if (departmentName.isEmpty()) {
            System.out.println("Department name is empty!");
        }
        return directorRepository.getDepartment(departmentName);
    }

    public List<Map<String, Object>> getAllDepartments() {
        return directorRepository.getAllDepartment();
    }

    public boolean assignResponsibleToDepartment(String departmentName, String responsibleEmail) {
        if (departmentName.isEmpty() || responsibleEmail.isEmpty()) {
            System.out.println("Department name or Responsible email is empty!");
        }
        if (directorRepository.checkIfDepartmentAlreadyHaveResponsible(departmentName)) {
            System.out.println("This department is already have an responsible!");
            return false;
        }
        return directorRepository.assignResponsibleToDepartment(departmentName, responsibleEmail);
    }
}
