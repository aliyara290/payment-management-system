package com.paysystem.model.entities;

import com.paysystem.model.enums.UserRole;

import javax.management.relation.Role;

public class Director extends User {
    private int departmentId;
    public Director(String email, String firstName, String lastName, String password, UserRole userRole) {
        super(email, firstName, lastName, password, userRole);
    }

    public int getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}