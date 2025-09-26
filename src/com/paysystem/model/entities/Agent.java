package com.paysystem.model.entities;

import com.paysystem.model.enums.UserRole;

import java.util.ArrayList;
import java.util.List;

public class Agent extends User{
    private String departementId;
    private List<Payment> payments;

    public Agent(String email, String firstName, String lastName, String password, UserRole userRole, String departementId, List<Payment> payments) {
        super(email, firstName, lastName, password, userRole);
        this.departementId = departementId;
        this.payments = new ArrayList<>();
    }
}
