package Dictionary.Entities;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "ScoreWordle", daoClass = ScoreWordleDAO.class)
public class ScoreWordle {
    @DatabaseField(generatedId = true, index = true)
    private long id;
    @DatabaseField(generatedId = false, index = true)
    private String User_id = "";
    @DatabaseField(canBeNull = false, index = true)
    private long Streak;
    @DatabaseField(canBeNull = true, index = true)
    private long Num_play;
    @DatabaseField(canBeNull = true, index = true)
    private long Num_win;

    public long getId() {
        return id;
    }
    public long getStreak() {
        return Streak;
    }
    public void setStreak(long x) {
        Streak = x;
    }
    public long getNum_play() {
        return Num_play;
    }
    public void setNum_play(long x) {
        Num_play = x;
    }
    public long getNum_win() {
        return Num_win;
    }
    public void setNum_win(long x) {
        Num_win = x;
    }

    public String getUser_id() {
        return User_id;
    }
    public void setUser_id(String x) {
        User_id = x;
    }

    public ScoreWordle() {
    }

    public ScoreWordle(String User, long streak, long play, long win) {
        Streak = streak;
        User_id = User;
        Num_play = play;
        Num_win = win;
    }

    @Override
    public String toString(){
        return "The tuple is " + User_id + ' ' + Streak + ' '+ Num_play +' '+Num_win+  "\n";
    }
}