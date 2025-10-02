package com.paysystem.repository.impl;

import com.paysystem.model.entities.Agent;
import com.paysystem.model.entities.Director;
import com.paysystem.model.entities.Responsible;
import com.paysystem.model.entities.User;
import com.paysystem.model.enums.UserRole;
import com.paysystem.repository.interfaces.AuthInterface;
import com.paysystem.utils.Crud;
import com.paysystem.utils.HashPassword;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AuthRepositoryImpl implements AuthInterface {


    @Override
    public boolean register(String firstName, String lastName, String email, String password, String role) {
        String hashPassword = HashPassword.hashPassword(password);
        return Crud.create("users", Map.of(
                "first_name", firstName,
                "last_name", lastName,
                "email", email,
                "password", hashPassword,
                "role", role
        ));
    }

    public Optional<User> login(String email, String password) {
        Map<String, String> user = findByEmail(email).getFirst();
        if (!user.isEmpty() && HashPassword.verifyPassword(password, user.get("password"))) {
            UserRole role = UserRole.valueOf(user.get("role").toUpperCase());
            return switch (role) {
                case DIRECTOR ->
                        Optional.of(new Director(user.get("email"), user.get("first_name"), user.get("last_name"), user.get("password"), role));
                case RESPONSIBLE ->
                        Optional.of(new Responsible(user.get("email"), user.get("first_name"), user.get("last_name"), user.get("password"), role, null));
                case AGENT, INTERN ->
                        Optional.of(new Agent(user.get("email"), user.get("first_name"), user.get("last_name"), user.get("password"), role, null, null));
                default -> throw new IllegalArgumentException("Unknown user role: " + role);
            };

        }
        return Optional.empty();
    }


    @Override
    public List<Map<String, Object>> listAll() {
        return Crud.readAll("users");
    }


    @Override
    public List<Map<String, String>> findByEmail(String email) {
        List<Map<String, String>> userExist = Crud.readByCondition("users", "email", email);
        if (userExist.isEmpty()) {
            return List.of();
        }
        return userExist;
    }

    @Override
    public boolean updateEmployee(String employeeEmail, Map<String, Object> data) {
        String employeeId = Crud.readByCondition("users", "email", employeeEmail).getFirst().get("id");
        try {
            return Crud.update("users", data, "id", employeeId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}

