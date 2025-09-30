package com.paysystem.repository.impl;

import com.paysystem.repository.interfaces.DirectorInterface;
import com.paysystem.utils.Crud;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectorRepositoryImpl implements DirectorInterface {
    @Override
    public boolean createDepartment(String departmentName) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", departmentName);
        return Crud.create("departments", data);
    }

    @Override
    public boolean updateDepartment(String departmentName, String newDepartmentName) {
        return Crud.update("departments", Map.of("name", newDepartmentName), "id", getDepartmentId(departmentName));
    }

    @Override
    public boolean deleteDepartment(String departmentName) {
        return Crud.delete("departments", "id", getDepartmentId(departmentName));
    }

    @Override
    public Map<String, String> getDepartment(String departmentName) {
        return Crud.readByCondition("department", "id", getDepartmentId(departmentName)).getFirst();
    }

    @Override
    public List<Map<String, Object>> getAllDepartment() {
        return Crud.readAll("departments");
    }

    @Override
    public boolean assignResponsibleToDepartment(String departmentName, String responsibleEmail) {
        int responsibleId = Integer.parseInt(Crud.readByCondition("users", "email", responsibleEmail).getFirst().get("id"));
        return Crud.update("users", Map.of(
                "department_id", getDepartmentId(departmentName),
                "role", "responsible"
        ), "id", responsibleId);
    }

    @Override
    public boolean checkIfDepartmentNameAvailable(String departmentName) {
        return Crud.readByCondition("departments", "name", departmentName).getFirst().get("name") != null;
    }

    @Override
    public boolean checkIfDepartmentAlreadyHaveResponsible(String departmentName) {
        String departmentId = Crud.readByCondition("departments", "name", departmentName).getFirst().get("id");
        return !Crud.readByCondition("users", "department_id", departmentId).isEmpty();
    }

    public static int getDepartmentId(String departmentName) {
        return Integer.parseInt(Crud.readByCondition("departments", "name", departmentName).getFirst().get("id"));
    }
}
