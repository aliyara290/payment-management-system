package com.paysystem.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface DirectorInterface {
    boolean createDepartment(String departmentName);
    boolean updateDepartment(String departmentName, String newDepartmentName);
    boolean deleteDepartment(String departmentName);
    Map<String, String> getDepartment(String departmentName);
    List<Map<String, Object>> getAllDepartment();
    boolean assignResponsibleToDepartment(String departmentName, String responsibleEmail);
//    boolean isEmailExist(String email);
    boolean checkIfDepartmentNameAvailable(String departmentName);
    boolean checkIfDepartmentAlreadyHaveResponsible(String departmentName);
}
