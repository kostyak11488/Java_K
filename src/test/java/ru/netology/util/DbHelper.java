package ru.netology.util;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbHelper {

    private static final String url = "jdbc:postgresql://localhost:5432/app";
    private static final String user = "app";
    private static final String password = "pass";

    @SneakyThrows
    public static String getCreditStatus() {
        String status = null;
        Connection conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;");
        if (rs.next()) {
            status = rs.getString("status");
        }
        conn.close();
        return status;
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        String status = null;
        Connection conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;");
        if (rs.next()) {
            status = rs.getString("status");
        }
        conn.close();
        return status;
    }

    @SneakyThrows
    public static void clearTables() {
        Connection conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM order_entity;");
        stmt.executeUpdate("DELETE FROM payment_entity;");
        stmt.executeUpdate("DELETE FROM credit_request_entity;");
        conn.close();
    }
}

