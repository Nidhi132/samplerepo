package org.c2tc.jss.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class RowInsert {

    public static void main(String[] args) {

        // auto close connection and statement
        try (Connection conn = DriverManager.getConnection(
        		"jdbc:mysql://127.0.0.1:3306/test", "root", "imhappy@123");
             Statement statement = conn.createStatement()) {

            int row = statement.executeUpdate(generateInsert("Amy", new BigDecimal(2500.80)));

            // rows affected
            System.out.println(row);

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String generateInsert(String name, BigDecimal salary) {

        return "INSERT INTO EMPLOYEE (NAME, SALARY, CREATED_DATE) " +
                "VALUES ('" + name + "','" + salary + "','" + LocalDateTime.now() + "')";

    }

}