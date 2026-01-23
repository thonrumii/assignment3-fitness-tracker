package utils;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL =
            "jdbc:mysql://localhost:3306/fitness_tracker?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
                System.err.println("Database connection failed!");
                System.err.println("Message: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
    }

    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (Exception ignored) {}
        try { if (ps != null) ps.close(); } catch (Exception ignored) {}
        try { if (conn != null) conn.close(); } catch (Exception ignored) {}
    }
}
