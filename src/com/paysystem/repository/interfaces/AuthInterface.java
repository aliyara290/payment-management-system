package com.paysystem.repository.interfaces;

import com.paysystem.model.entities.User;
import com.paysystem.model.enums.UserRole;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AuthInterface {
    boolean register(String firstName, String lastName, String email, String password, UserRole role);
    Optional<User> login(String email, String password);
    List<Map<String, Object>> listAll();
    List<Map<String, String>> findByEmail(String email);

//    Optional<User> findById(String id);
//    void deleteById(String id);
}
