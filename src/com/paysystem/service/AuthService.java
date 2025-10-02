package com.paysystem.service;

import com.paysystem.model.entities.User;
import com.paysystem.model.enums.UserRole;
import com.paysystem.repository.interfaces.AuthInterface;
import com.paysystem.utils.Crud;
import com.paysystem.utils.DatabaseConnection;
import com.paysystem.utils.HashPassword;
import com.paysystem.model.entities.Director;
import com.paysystem.model.entities.Responsible;
import com.paysystem.model.entities.Agent;

import java.sql.*;
import java.util.*;

public class AuthService {

    private final AuthInterface authService;
//    private User loggedInUser;

    public AuthService(AuthInterface authService) {
        this.authService = authService;
    }

    public Optional<User> login(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            System.out.println("Email or password is empty, please fill it in!");
        }
        Optional<User> isLogged = authService.login(email, password);
        if (isLogged.isPresent()) {
            User user = isLogged.get();
//            loggedInUser = isLogged.get();
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public boolean register(String firstName, String lastName, String email, String password, String role) {
        if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            System.out.println("Some fields are empty!");
            return false;
        }
        return authService.register(firstName, lastName, email, password, role);
    }

    public boolean updateUser(String employeeEmail, Map<String, Object> data) {
        if (employeeEmail == null || employeeEmail.isEmpty()) {
            System.out.println("Employee name is empty");
            return false;
        }

        Map<String, Object> newData = new HashMap<>();
        if(data.get("role") != null) {
            newData.put("role", data.get("role"));
        }
        if(data.get("last_name") != null) {
            newData.put("last_name", data.get("last_name"));
        }
        if(data.get("email") != null) {
            newData.put("email", data.get("email"));
        }
        if(data.get("first_name") != null) {
            newData.put("first_name", data.get("first_name"));
        }

        return authService.updateEmployee(employeeEmail, newData);
    }

    public List<Map<String, Object>> allEmployees() {
        return authService.listAll();
    }

    public boolean emailExists(String email) {
        Map<String, String> isExist = Crud.readByCondition("users", "email", email).getFirst();
        return isExist.get("email") != null;
    }
}