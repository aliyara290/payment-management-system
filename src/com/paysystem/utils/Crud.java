package com.paysystem.utils;

import java.sql.Connection;
import java.sql.*;
import java.util.*;

public class Crud {

    private static Connection connection;

    public Crud() throws SQLException {
        connection = DatabaseConnection.getConnection();

    }

    public static boolean create(String table, Map<String, Object> data) {
        String columns = String.join(",", data.keySet());
        String placeholders = String.join(",", data.keySet().stream().map(k ->   "?").toList());
        String sql = "INSERT INTO " + table + " (" + columns + ") VALUES (" + placeholders + ")";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;
            for (Object value : data.values()) {
                stmt.setObject(index++, value);
            }
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Failed to insert data: " + e.getMessage());
            return false;
        }
    }

    public static boolean update(String table, Map<String, Object> data, String condition, Object condValue) {
        String setClause = String.join(",", data.keySet().stream().map(k -> k + " = ?").toList());
        String sql = "UPDATE " + table + " SET " + setClause + " WHERE " + condition + " = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;
            for (Object value : data.values()) {
                stmt.setObject(index++, value);
            }
            stmt.setObject(index, condValue);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Failed to update data: " + e.getMessage());
            return false;
        }
    }

    public static boolean delete(String table, String condition, Object condValue) {
        String sql = "DELETE FROM " + table + " WHERE " + condition + " = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, condValue);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Failed to delete data: " + e.getMessage());
            return false;
        }
    }

    public static List<Map<String, String>> readByCondition(String table, String condition, Object condValue) {
        String sql = "SELECT * FROM " + table + " WHERE " + condition + " = ?";
        List<Map<String, String>> results = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, condValue);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            while (rs.next()) {
                Map<String, String> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(meta.getColumnName(i), rs.getString(i));
                }
                results.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Failed to fetch data: " + e.getMessage());
        }
        return results;
    }

    public static List<Map<String, Object>> readAll(String table) {
        String sql = "SELECT * FROM " + table + " ORDER BY id";
        List<Map<String, Object>> results = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(meta.getColumnName(i), rs.getObject(i));
                }
                results.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Failed to fetch data: " + e.getMessage());
        }
        return results;
    }
}