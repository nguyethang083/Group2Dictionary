package Dictionary.Entities;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Dictionary.DatabaseConn.ScoreQuizDAO;

public class ScoreQuizDAO extends BaseDaoImpl<ScoreQuiz, Long> {
    public ScoreQuizDAO(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, ScoreQuiz.class);
    }

    // lưu ý cực mạnh: nếu user mới chưa chơi thì khi dùng queryListScoreByUser
    // phải kiểm tra .isEmpty() = 0 thì mới dùng được tiếp

    /**
     *
     * @param user chọn 1 user (lúc login - biến static toàn cục)
     * @return Danh sách kiểu ArrayList<ScoreQuiz> của User đó (lúc hiện lên màn thì dùng hàm này)
     * @throws SQLException
     */
    public List<ScoreQuiz> queryListScoreByUser(String user) throws SQLException {
        Where<ScoreQuiz, Long> score = this.queryBuilder().where().in("User_id", user);
        return new ArrayList<>(score.query());
    }

    public boolean addScoreQuiz(ScoreQuiz x) throws SQLException {
        long Score = x.getScore();
        String UserId = x.getUser_id();
        if (UserId.isEmpty()) {
            return false;
        }
        try {
            this.create(x);
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " insertSQ");
            return false;
        }
        return true;
    }

    public long getTotalScorebyUser(String user) throws SQLException {
        long res = 0;
        List<ScoreQuiz> listScoreByUser = ScoreQuizDAO.queryListScoreByUser(user);
        if (listScoreByUser.isEmpty()) return res;
        for (ScoreQuiz x : listScoreByUser) {
            res += x.getScore();
        }
        return res;
    }

    public long getNumPlaybyUser(String user) throws SQLException {
        return this.queryBuilder().where().in("User_id", user).countOf();
    }

    // Xóa lượt chơi khi xóa user nè
    public boolean deleteScoreQuizbyUser(String user) throws SQLException {
        try {
            ScoreQuiz tuple;
            do {
                tuple = this.queryBuilder().where().eq("User_id", user).queryForFirst();
                this.delete(tuple);
            } while (tuple != null);
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " deleteSQ");
            return false;
        }
    }

    // Test nè :>
    public static void main(String[] args) throws SQLException {
        ScoreQuiz scoreQuiz1 = new ScoreQuiz("lam", 3);
        ScoreQuiz scoreQuiz3 = new ScoreQuiz("lam", 4);
        ScoreQuiz scoreQuiz4 = new ScoreQuiz("hang", 4);
        ScoreQuizDAO.addScoreQuiz(scoreQuiz1);
        ScoreQuizDAO.addScoreQuiz(scoreQuiz1);
        ScoreQuizDAO.addScoreQuiz(scoreQuiz3);
        ScoreQuizDAO.addScoreQuiz(scoreQuiz4);
        List<ScoreQuiz> hi = ScoreQuizDAO.queryListScoreByUser("lam");
        System.out.println(hi.size());
        for (ScoreQuiz n : hi) {
            System.out.println(n.getUser_id() + " " + n.getScore());
        }
        System.out.println(ScoreQuizDAO.getTotalScorebyUser("Lam"));
        System.out.println(ScoreQuizDAO.getNumPlaybyUser("Lam"));
        //ScoreQuizDAO.deleteScoreQuizbyUser("lam");
    }
}
