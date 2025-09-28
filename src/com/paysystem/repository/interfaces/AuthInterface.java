package com.paysystem.repository.interfaces;

import com.paysystem.model.entities.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AuthInterface {
    boolean register(String firstName, String lastName, String email, String password, String role);
    Optional<User> login(String email, String password);
    List<User> listAll();
    List<Map<String, String>> findByEmail(String email);
    Optional<User> findById(String id);
    void deleteById(String id);
}
