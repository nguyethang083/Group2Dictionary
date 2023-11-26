package Dictionary;

import Dictionary.Entities.*;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;


public class DatabaseConn {
    private static final String DATABASE_URL = "jdbc:sqlite:engData.db";

    public static ConnectionSource connectionSource;

    static {
        try {
            connectionSource = new JdbcConnectionSource(DATABASE_URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static WordDAO WordDAO;
    public static UserDAO UserDAO;
    public static SavedWordDAO SavedWordDAO;
    public static ScoreQuizDAO ScoreQuizDAO;
    public static ScoreWordleDAO ScoreWordleDAO;

    static {
        try {
            WordDAO = new WordDAO(connectionSource);
            SavedWordDAO = new SavedWordDAO(connectionSource);
            UserDAO = new UserDAO(connectionSource);
            ScoreQuizDAO = new ScoreQuizDAO(connectionSource);
            ScoreWordleDAO = new ScoreWordleDAO(connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}