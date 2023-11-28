package Dictionary.Entities;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Dictionary.DatabaseConn.ScoreWordleDAO;

public class ScoreWordleDAO extends BaseDaoImpl<ScoreWordle, Long> {
    public ScoreWordleDAO(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, ScoreWordle.class);
    }

    // lưu ý cực mạnh: nếu user mới chưa chơi thì khi dùng getStreakByUser
    // phải kiểm tra != -1 thì mới dùng được tiếp
    // còn cái tuple thì != null

    /**
     * @param user chọn 1 user (lúc login - biến static toàn cục)
     */
    public long getStreakbyUser(String user) throws SQLException {
        ScoreWordle streak = this.queryBuilder().where().in("User_id", user).queryForFirst();
        if (streak == null) return 0;
        return streak.getStreak();
    }

    public ScoreWordle getTupleStreakbyUser(String user) throws SQLException {
        return this.queryBuilder().where().in("User_id", user).queryForFirst();
    }

    // đã bao gồm cả update nếu bị lỗi constraint pk Userid
    public boolean addScoreWordle(ScoreWordle x) throws SQLException {
        long Streak = x.getStreak();
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
                tuple.setStreak(streak);
                tuple.setNum_play(play);
                tuple.setNum_win(win);
                this.update(tuple);
            }
            else this.create(x);
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " insertSW");
            return false;
        }
        return true;
    }

    public long getNumPlaybyUser(String user) throws SQLException {
        ScoreWordle streak = this.queryBuilder().where().in("User_id", user).queryForFirst();
        if (streak == null) return 0;
        return streak.getNum_play();
    }

    public long getNumWinbyUser(String user) throws SQLException {
        ScoreWordle streak = this.queryBuilder().where().in("User_id", user).queryForFirst();
        if (streak == null) return 0;
        return streak.getNum_win();
    }

    // Xóa lượt chơi khi xóa user nè
    public boolean deleteScoreWordlebyUser(String user) throws SQLException {
        try {
            ScoreWordle tuple = this.queryBuilder().where().eq("User_id", user).queryForFirst();;
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
        ScoreWordleDAO.deleteScoreWordlebyUser("hang");
        System.out.println(ScoreWordleDAO.getStreakbyUser("lam"));
        System.out.println(ScoreWordleDAO.getNumPlaybyUser("lam"));
        System.out.println(ScoreWordleDAO.getNumWinbyUser("lam"));
    }
}