package com.example.demo1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection conn;

    public static DatabaseConnection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/student_records";
            String user = "root";
            String password = "Orange@123";
            conn = DriverManager.getConnection(url, user, password);
            return new DatabaseConnection();
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            return null;
        }
    }

    public PreparedStatement prepareStatement(String sql) {
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            return stmt;
        } catch (SQLException e) {
            System.out.println("Error preparing statement: " + e.getMessage());
            return null;
        }
    }

    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
            System.out.println("Connection closed.");
        }
    }
}
