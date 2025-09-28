package com.paysystem.service;

import com.paysystem.model.entities.User;
import com.paysystem.model.enums.UserRole;
import com.paysystem.repository.interfaces.AuthInterface;
import com.paysystem.utils.DatabaseConnection;
import com.paysystem.utils.HashPassword;
import com.paysystem.model.entities.Director;
import com.paysystem.model.entities.Responsible;
import com.paysystem.model.entities.Agent;
import java.sql.*;
import java.util.*;

public class AuthService {

    private AuthInterface authService;

    public AuthService(AuthInterface authService) {
        this.authService = authService;
    }

    public Optional<User> login(String email, String password) {
        if(email.isEmpty() || password.isEmpty()) {
            System.out.println("Email or password is empty, please fill it in!");
        }
        Optional<User> isLogged = authService.login(email, password);
        if(isLogged.isPresent()) {
            User user = isLogged.get();
            return Optional.of(user);
        }
        return Optional.empty();
    }

//        public Optional<User> login(String email, String password) {
//            String sql = "SELECT id, firstName, lastName, email, password, role FROM users WHERE email = ?";
//
//            try (Connection conn = DatabaseConnection.getConnection();
//                 PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//                stmt.setString(1, email);
//                ResultSet rs = stmt.executeQuery();
//
//                if (rs.next()) {
//                    String storedPassword = rs.getString("password");
//
//                    if (HashPassword.verifyPassword(password, storedPassword)) {
//                        return Optional.of(createUserFromResultSet(rs));
//                    }
//                }
//            } catch (SQLException e) {
//                System.err.println("Login error: " + e.getMessage());
//            }
//
//            return Optional.empty();
//        }
//
//    public boolean register(String email, String firstName, String lastName,
//                            String password, UserRole role) {
//        String sql = "INSERT INTO users (id, firstName, lastName, email, password, role) VALUES (UUID_TO_BIN(?), ?, ?, ?, ?, ?)";
//
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            String userId = UUID.randomUUID().toString();
//            String hashedPassword = HashPassword.hashPassword(password);
//
//            stmt.setString(1, userId);
//            stmt.setString(2, firstName);
//            stmt.setString(3, lastName);
//            stmt.setString(4, email);
//            stmt.setString(5, hashedPassword);
//            stmt.setString(6, role.name().toLowerCase());
//
//            int affected = stmt.executeUpdate();
//            return affected > 0;
//
//        } catch (SQLException e) {
//            System.err.println("Registration error: " + e.getMessage());
//            return false;
//        }
//    }
//
//    public boolean emailExists(String email) {
//        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
//
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setString(1, email);
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                return rs.getInt(1) > 0;
//            }
//        } catch (SQLException e) {
//            System.err.println("Email check error: " + e.getMessage());
//        }
//
//        return false;
//    }
//
//    private User createUserFromResultSet(ResultSet rs) throws SQLException {
//        String id = rs.getString("id");
//        String firstName = rs.getString("firstName");
//        String lastName = rs.getString("lastName");
//        String email = rs.getString("email");
//        String password = rs.getString("password");
//        UserRole role = UserRole.valueOf(rs.getString("role").toUpperCase());
//
//        return switch (role) {
//            case DIRECTOR -> new Director(email, firstName, lastName, password, role);
//            case RESPONSIBLE -> new Responsible(email, firstName, lastName, password, role, null);
//            case AGENT, INTERN -> new Agent(email, firstName, lastName, password, role, null, null);
//            default -> throw new IllegalArgumentException("Unknown user role: " + role);
//        };
//    }
}