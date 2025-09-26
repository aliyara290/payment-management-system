package com.paysystem.model.entities;

import com.paysystem.model.enums.UserRole;
import com.paysystem.utils.IdGenerator;

public abstract class User {
    protected String id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;
    protected UserRole userRole;

    public User(String email, String firstName, String lastName, String password, UserRole userRole) {
        this.id  = IdGenerator.generateId();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getUserRole() {
        return userRole;
    }
}
