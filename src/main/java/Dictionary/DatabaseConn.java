package Dictionary;

import Dictionary.Entities.SavedWord;
import Dictionary.Entities.SavedWordDAO;
import Dictionary.Entities.UserDAO;
import Dictionary.Entities.WordDAO;
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
    public static SavedWordDAO SavedWordDAO;

    static {
        try {
            WordDAO = new WordDAO(connectionSource);
            SavedWordDAO = new SavedWordDAO(connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}