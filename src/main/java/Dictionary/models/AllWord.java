package Dictionary.models;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Dictionary.DatabaseConn.WordDAO;

public class AllWord {
    public static List<EngWord> allWord;
    static {
        try {
            allWord = WordDAO.getAllWords();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int dbSize;
    static {
        dbSize = allWord.size();
    }

    public static Map<String, String> WordMapMean = new HashMap<>();
    public static Map<String, String> MeanMapWord = new HashMap<>();
    static {
        for(EngWord engword : allWord) {
            WordMapMean.put(engword.getWord(), engword.getMeaning());
            MeanMapWord.put(engword.getMeaning(), engword.getWord());
        }
    }
}
