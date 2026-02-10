package ru.netology.util;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbHelper {
    private static final QueryRunner runner = new QueryRunner();

    @SneakyThrows
    public static void clearTables() {
        var connection = getConnection();

        runner.update(connection, "DELETE FROM order_entity;");
        runner.update(connection, "DELETE FROM payment_entity;");
        runner.update(connection, "DELETE FROM credit_request_entity;");
    }


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
        var orderSql = "SELECT payment_id FROM order_entity ORDER BY created DESC LIMIT 1;";
        var paymentId = runner.query(getConnection(), orderSql, new ScalarHandler<>());

        var statusSql = "SELECT status FROM payment_entity WHERE transaction_id = ?;";
        return runner.query(getConnection(), statusSql, new ScalarHandler<>(), paymentId);
    }
}


