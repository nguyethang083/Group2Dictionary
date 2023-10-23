package Dictionary.DictionaryCommandLine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DataBase {
    static Connection conn;
    static PreparedStatement pstmt;
    static Statement stmt;
    static final String DB_URL = "jdbc:sqlite:words.db";
    static final String READ = "SELECT * FROM words ORDER BY english_word ASC, meaning DESC";
    static final String INSERT = "INSERT INTO words (english_word, meaning) SELECT ?, ? " +
            "WHERE NOT EXISTS (SELECT * FROM words WHERE words.english_word = ?)";
    static final String DELETE = "DELETE FROM words WHERE english_word IN (?)";
    static final String REPEAT = "SELECT english_word FROM words WHERE words.english_word = ?";
    static final String UPDATE = "UPDATE words SET english_word = ?, meaning = ? WHERE english_word = ?";
    static final String LOOKUP = "SELECT * FROM words WHERE english_word LIKE ? || '%' ORDER BY english_word ASC, meaning DESC";
    static final String LOOKUP1 = "SELECT meaning FROM words WHERE english_word IN (?)";


    public static void connect() {
        try {
            //open connection
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertToDatabase(String englishWords, String meaning) {
        try {
            //xóa dấu cách để tránh lỗi sau này
            englishWords = englishWords.replace(" ", "");

            //chuyển sang lower case để tránh lỗi
            englishWords = englishWords.toLowerCase(Locale.ROOT);
            meaning = meaning.toLowerCase(Locale.ROOT);


            pstmt = conn.prepareStatement(INSERT);
            pstmt.setString(1, englishWords);
            pstmt.setString(2, meaning);
            pstmt.setString(3, englishWords);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String readFromDatabase() {
        StringBuilder result = new StringBuilder();
        try {
            //read
            ResultSet rs = stmt.executeQuery(READ);
            while (rs.next()) {
                result.append(rs.getString("english_word")).append("\t-\t")
                        .append(rs.getString("meaning")).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static void readFromDatabase(List<String> wordArray) {
        try {
            //read
            ResultSet rs = stmt.executeQuery(READ);
            while (rs.next()) {
                wordArray.add(rs.getString("english_word"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFromDatabase(String target) {
        try {
            //xóa dấu cách để tránh lỗi sau này
            target = target.replace(" ", "");

            pstmt = conn.prepareStatement(DELETE);
            pstmt.setString(1, target);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateDatabase(String newTarget, String explain, String oldTarget) {
        try {
            pstmt = conn.prepareStatement(UPDATE);
            pstmt.setString(1, newTarget);
            pstmt.setString(2, explain);
            pstmt.setString(3, oldTarget);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static String lookup(String target) {
        StringBuilder result = new StringBuilder();
        try {
            pstmt = conn.prepareStatement(LOOKUP);
            pstmt.setString(1, target);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.append(rs.getString("english_word")).append(" \t-\t")
                        .append(rs.getString("meaning")).append("\n");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result.toString();
    }

    public static String lookupSingleWord(String target) {
        try {
            pstmt = conn.prepareStatement(LOOKUP1);
            pstmt.setString(1, target);
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("meaning");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static boolean checkEmptyDatabase() throws SQLException {
        ResultSet rs = stmt.executeQuery(READ);
        return !rs.next();
    }

    public static boolean checkRepeatedWord(String target) {
        try {
            pstmt = conn.prepareStatement(REPEAT);
            pstmt.setString(1, target);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        connect();
    }
}
