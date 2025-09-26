package com.paysystem.model.entities;

import com.paysystem.model.enums.UserRole;

public class Director extends User {
    public Director(String email, String firstName, String lastName, String password, UserRole userRole) {
        super(email, firstName, lastName, password, userRole);
    }
}
