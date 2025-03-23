package com.mycompany.networks_project;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleDatabaseConnection {

    // Database credentials
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // Replace with your DB name
    private static final String USERNAME = "system"; // Replace with your DB username
    private static final String PASSWORD = "karmegam"; // Replace with your DB password

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Load the JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Establish the connection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection established successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
        return connection;
    }

    public static void describeTable(Connection connection, String tableName) {
        if (connection != null) {
            try {
                // Get the DatabaseMetaData
                DatabaseMetaData metaData = connection.getMetaData();
                ResultSet columns = metaData.getColumns(null, null, tableName, null);

                System.out.println("SQL> desc " + tableName);
                System.out.printf("%-40s %-10s %-30s%n", "Name", "Null?", "Type");
                System.out.println("---------------------------------------------------------");

                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    String isNullable = columns.getString("IS_NULLABLE");
                    String columnType = columns.getString("TYPE_NAME") + "(" + columns.getInt("COLUMN_SIZE") + ")";

                    System.out.printf("%-40s %-10s %-30s%n", columnName, isNullable, columnType);
                }

                columns.close();
            } catch (SQLException e) {
                System.err.println("Failed to retrieve table metadata: " + e.getMessage());
            }
        } else {
            System.err.println("Connection is null. Cannot describe table.");
        }
    }

    public static void displayTableData(Connection connection, String tableName) {
        if (connection != null) {
            String query = "SELECT * FROM " + tableName;

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                System.out.println("SQL> " + query);
                System.out.println("---------------------------------------------------------");

                // Assuming you know the column names
                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String username = resultSet.getString("USERNAME");
                    String password = resultSet.getString("PASSWORD");
                    String email = resultSet.getString("EMAIL");
                    String mobileNumber = resultSet.getString("MOBILE_NUMBER");
                    String address = resultSet.getString("ADDRESS");

                    System.out.printf("%-10d %-20s %-20s %-30s %-15s %-50s%n",
                            id, username, password, email, mobileNumber, address);
                }
            } catch (SQLException e) {
                System.err.println("Failed to retrieve data: " + e.getMessage());
            }
        } else {
            System.err.println("Connection is null. Cannot display table data.");
        }
    }

    public static void main(String[] args) {
        // Test the connection
        Connection connection = getConnection();

        // Describe the userss table
        describeTable(connection, "userss");

        // Display the data from the userss table
        displayTableData(connection, "userss");

        // Close the connection when done
        if (connection != null) {
            try {
                connection.close(); // Close the connection when done
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
