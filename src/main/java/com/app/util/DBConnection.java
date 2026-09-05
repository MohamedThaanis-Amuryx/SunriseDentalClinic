package com.app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class to get a JDBC connection to the MySQL database.
 * Every DAO class will call DBConnection.getConnection() when it needs
 * to talk to the database, instead of writing connection code everywhere.
 */
public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3307/sunrise_dental_clinic?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";  

   
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found. Check mysql-connector jar is in WEB-INF/lib.", e);
        }
    }

    // Private constructor so no one creates an object of this class by mistake
    private DBConnection() {
    }

    /**
     * Returns a new database connection.
     * Call this inside a try-with-resources block so it closes automatically.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
    }
}