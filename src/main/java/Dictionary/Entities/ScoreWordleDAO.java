package Dictionary.Entities;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import static Dictionary.DatabaseConn.CurrentUser;
import static Dictionary.DatabaseConn.ScoreWordleDAO;

public class ScoreWordleDAO extends BaseDaoImpl<ScoreWordle, Long> {
    public ScoreWordleDAO(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, ScoreWordle.class);
    }

    // lưu ý cực mạnh: nếu user mới chưa chơi thì khi dùng getStreakByUser
    // phải kiểm tra != -1 thì mới dùng được tiếp
    // còn cái tuple thì != null

    /**
     *
     */
    public long getStreakbyUser() throws SQLException {
        ScoreWordle streak = this.queryBuilder().where().in("User_id", CurrentUser).queryForFirst();
        if (streak == null) return 0;
        return streak.getStreak();
    }

    public ScoreWordle getTupleStreakbyUser() throws SQLException {
        return this.queryBuilder().where().in("User_id", CurrentUser).queryForFirst();
    }

    // đã bao gồm cả update nếu bị lỗi constraint pk Userid
    public boolean addScoreWordle(ScoreWordle x) throws SQLException {
        String UserId = x.getUser_id();
        if (UserId.isEmpty()) {
            return false;
        }
        try {
            ScoreWordle tuple = this.queryBuilder().where().eq("User_id", UserId).queryForFirst();
            if (tuple != null) {
                System.out.println("User này đã có rồi -> update tuple");
                long streak = x.getStreak();
                long play = x.getNum_play();
                long win = x.getNum_win();
                long g1 = x.getGuess1();
                long g2 = x.getGuess2();
                long g3 = x.getGuess3();
                long g4 = x.getGuess4();
                long g5 = x.getGuess5();
                long g6 = x.getGuess6();
                tuple.setStreak(streak);
                tuple.setNum_play(play);
                tuple.setNum_win(win);
                tuple.setGuess1(g1);
                tuple.setGuess2(g2);
                tuple.setGuess3(g3);
                tuple.setGuess4(g4);
                tuple.setGuess5(g5);
                tuple.setGuess6(g6);
                this.update(tuple);
            }
            else this.create(x);
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " insertSW");
            return false;
        }
        return true;
    }

    public long getNumPlaybyUser() throws SQLException {
        ScoreWordle streak = this.queryBuilder().where().in("User_id", CurrentUser).queryForFirst();
        if (streak == null) return 0;
        return streak.getNum_play();
    }

    public long getNumWinbyUser() throws SQLException {
        ScoreWordle streak = this.queryBuilder().where().in("User_id", CurrentUser).queryForFirst();
        if (streak == null) return 0;
        return streak.getNum_win();
    }
    public long getGuessbyNum(int numGuess) throws SQLException {
        ScoreWordle tuple = this.queryBuilder().where().in("User_id", CurrentUser).queryForFirst();
        if (tuple == null) return 0;
        switch(numGuess) {
            case 1:
                return tuple.getGuess1();
            case 2:
                return tuple.getGuess2();
            case 3:
                return tuple.getGuess3();
            case 4:
                return tuple.getGuess4();
            case 5:
                return tuple.getGuess5();
            default:
                return tuple.getGuess6();
        }
    }

    // Xóa lượt chơi khi xóa user nè
    public boolean deleteScoreWordlebyUser() throws SQLException {
        try {
            ScoreWordle tuple = this.queryBuilder().where().eq("User_id", CurrentUser).queryForFirst();;
            if (tuple == null) return false;
            this.delete(tuple);
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " deleteSW");
            return false;
        }
    }

    // Test nè :>
    // Lưu ý: trc khi khởi tạo ScoreWordle phải check xem streak <= win <= play ko
    public static void main(String[] args) throws SQLException {
        ScoreWordle score1 = new ScoreWordle("lam", 1, 3, 2);
        ScoreWordle score3 = new ScoreWordle("lam", 2, 4 , 3);
        ScoreWordle score4 = new ScoreWordle("hang", 4, 10, 4);
        ScoreWordleDAO.addScoreWordle(score1);
        ScoreWordleDAO.addScoreWordle(score1);
        ScoreWordleDAO.addScoreWordle(score3);
        ScoreWordleDAO.addScoreWordle(score4);
        ScoreWordleDAO.deleteScoreWordlebyUser();
        System.out.println(ScoreWordleDAO.getStreakbyUser());
        System.out.println(ScoreWordleDAO.getNumPlaybyUser());
        System.out.println(ScoreWordleDAO.getNumWinbyUser());
    }
}
