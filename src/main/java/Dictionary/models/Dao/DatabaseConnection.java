package Dictionary.models.Dao;

import java.sql.*;

public class DatabaseConnection {
    private static final String DBPATH = "jdbc:sqlite:engData.db";

    /**
     * Get a new connection to database.
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DBPATH);
            System.out.println("Connect success");
        } catch (SQLException e) {
            System.out.println("Connect fail");
            e.printStackTrace();
        }
        return conn;
    }

    public static WordsDao wordsDao;

    public static void main(String[] args) {
        System.out.println(getConnection());
    }
}