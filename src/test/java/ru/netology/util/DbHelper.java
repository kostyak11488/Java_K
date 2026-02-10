package ru.netology.util;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbHelper {

    private static final QueryRunner runner = new QueryRunner();

    @SneakyThrows
    private static Connection getConnection() {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/app",
                "app",
                "pass"
        );
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        var sql = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
        return runner.query(getConnection(), sql, new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getCreditStatus() {
        var sql = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        return runner.query(getConnection(), sql, new ScalarHandler<>());
    }

    @SneakyThrows
    public static void clearTables() {
        var conn = getConnection();
        runner.update(conn, "DELETE FROM payment_entity;");
        runner.update(conn, "DELETE FROM credit_request_entity;");
        runner.update(conn, "DELETE FROM order_entity;");
    }
}


