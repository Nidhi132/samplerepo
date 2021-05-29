package org.c2tc.jss.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Arrays;

public class BatchUpdate {

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection(
        		"jdbc:mysql://127.0.0.1:3306/test", "root", "imhappy@123");
             Statement statement = conn.createStatement()) {

            // optional, for transaction
            // commit all or rollback all, if any errors
            conn.setAutoCommit(false);

            // add list of SQL commands and run as a batch

            // drop table
            //statement.addBatch(SQL_DROP);

            // create table
            statement.addBatch(SQL_CREATE);

            // insert
            statement.addBatch(generateInsert("Mahi", new BigDecimal(1000)));

            // insert
            statement.addBatch(generateInsert("Jane", new BigDecimal(2000)));

            // update
            statement.addBatch(updateSalaryByName("Mahi", new BigDecimal(888)));

            int[] rows = statement.executeBatch();

            System.out.println(Arrays.toString(rows)); // [0, 0, 1, 1, 1]

            // commit everything
            conn.commit();

            // java 8, not yet implemented by pgStatement 42.2.5
            // statement.executeLargeBatch();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static final String SQL_CREATE = "CREATE TABLE EMPLOYEE3"
            + "("
            + " ID serial,"
            + " NAME varchar(100) NOT NULL,"
            + " SALARY numeric(15, 2) NOT NULL,"
            + " CREATED_DATE Date NOT NULL ,"
            + " PRIMARY KEY (ID)"
            + ")";

   // private static final String SQL_DROP = "DROP TABLE IF EXISTS EMPLOYEE";

    private static String generateInsert(String name, BigDecimal salary) {

        return "INSERT INTO EMPLOYEE3 (NAME, SALARY, CREATED_DATE) " +
                "VALUES ('" + name + "','" + salary + "','" + LocalDateTime.now() + "')";

    }

    private static String updateSalaryByName(String name, BigDecimal salary) {

        return "UPDATE EMPLOYEE SET SALARY='" + salary + "' WHERE NAME='" + name + "'";

    }

}
