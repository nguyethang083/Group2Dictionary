package Dictionary.Entities;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "ScoreQuiz", daoClass = ScoreQuizDAO.class)
public class ScoreQuiz {
    @DatabaseField(generatedId = true, index = true)
    private long id;
    @DatabaseField(canBeNull = false, index = true)
    private String User_id = "";
    @DatabaseField(canBeNull = false, index = true)
    private long Score;

    public long getId() {
        return id;
    }
    public long getScore() {
        return Score;
    }
    public void setScore(long x) {
        Score = x;
    }

    public String getUser_id() {
        return User_id;
    }
    public void setUser_id(String x) {
        User_id = x;
    }
    public ScoreQuiz() {
    }

    public ScoreQuiz(String User, long score) {
        Score = score;
        User_id = User;
    }

    @Override
    public String toString(){
        return "The tuple is " + User_id + Score + "\n";
    }
}