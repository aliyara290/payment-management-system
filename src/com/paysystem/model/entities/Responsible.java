package com.paysystem.model.entities;

import com.paysystem.model.enums.UserRole;

public class Responsible extends User {
    private String departementId;

    public Responsible(String email, String firstName, String lastName, String password, UserRole userRole, String departementId) {
        super(email, firstName, lastName, password, userRole);
        this.departementId = departementId;
    }

    public String getDepartementId() {
        return departementId;
    }

    public void setDepartementId(String departementId) {
        this.departementId = departementId;
    }
}