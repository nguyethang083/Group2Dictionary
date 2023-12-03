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
    @DatabaseField(canBeNull = true, index = true)
    private long Guess1;
    @DatabaseField(canBeNull = true, index = true)
    private long Guess2;
    @DatabaseField(canBeNull = true, index = true)
    private long Guess3;
    @DatabaseField(canBeNull = true, index = true)
    private long Guess4;
    @DatabaseField(canBeNull = true, index = true)
    private long Guess5;
    @DatabaseField(canBeNull = true, index = true)
    private long Guess6;

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

    public long getGuess1() {
        return Guess1;
    }
    public long getGuess2() {
        return Guess2;
    }
    public long getGuess3() {
        return Guess3;
    }
    public long getGuess4() {
        return Guess4;
    }
    public long getGuess5() {
        return Guess5;
    }
    public long getGuess6() {
        return Guess6;
    }
    public void setGuess1(long guess1) {
        Guess1 = guess1;
    }
    public void setGuess2(long guess2) {
        Guess2 = guess2;
    }
    public void setGuess3(long guess3) {
        Guess3 = guess3;
    }
    public void setGuess4(long guess4) {
        Guess4 = guess4;
    }
    public void setGuess5(long guess5) {
        Guess5 = guess5;
    }
    public void setGuess6(long guess6) {
        Guess6 = guess6;
    }

    public ScoreWordle() {
    }

    public ScoreWordle(String User, long streak, long play, long win) {
        Streak = streak;
        User_id = User;
        Num_play = play;
        Num_win = win;
    }
    public ScoreWordle(String User, long streak, long play, long win, long[] g) {
        Streak = streak;
        User_id = User;
        Num_play = play;
        Num_win = win;
        Guess1 = g[0];
        Guess2 = g[1];
        Guess3 = g[2];
        Guess4 = g[3];
        Guess5 = g[4];
        Guess6 = g[5];
    }

    @Override
    public String toString(){
        return "The tuple is " + User_id + ' ' + Streak + ' '+ Num_play +' '+Num_win+  "\n";
    }
}