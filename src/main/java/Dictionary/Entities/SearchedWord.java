package Dictionary.Entities;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;

import static Dictionary.DatabaseConn.WordDAO;


@DatabaseTable(tableName = "SearchedWord", daoClass = SearchedWordDAO.class)
public class SearchedWord {
    @DatabaseField(generatedId = true, index = true)
    private long id;
    @DatabaseField(canBeNull = false, index = true)
    private String User_id = "";
    @DatabaseField(canBeNull = false, index = true)
    private long English_id;

    public long getId() {
        return id;
    }

    public long getEnglish_id() {
        return English_id;
    }

    public void setEnglish_id(long x) {
        English_id = x;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String x) {
        User_id = x;
    }

    public SearchedWord() {
    }

    public SearchedWord(long Eng, String User) {
        English_id = Eng;
        User_id = User;
    }

    public String getWord() throws SQLException {
        return WordDAO.queryEngWordbyId(this.getEnglish_id()).getWord();
    }

    @Override
    public String toString() {
        return "The word is " + User_id + English_id + "\n";
    }
}