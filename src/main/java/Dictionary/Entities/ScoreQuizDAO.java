package Dictionary.Entities;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Dictionary.DatabaseConn.ScoreQuizDAO;
import static Dictionary.DatabaseConn.CurrentUser;

public class ScoreQuizDAO extends BaseDaoImpl<ScoreQuiz, Long> {
    public ScoreQuizDAO(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, ScoreQuiz.class);
    }

    // lưu ý cực mạnh: nếu user mới chưa chơi thì khi dùng queryListScoreByUser
    // phải kiểm tra .isEmpty() = 0 thì mới dùng được tiếp

    /**
     * @return Danh sách kiểu ArrayList<ScoreQuiz> của User đó (lúc hiện lên màn thì dùng hàm này)
     * @throws SQLException
     */
    public List<ScoreQuiz> queryListScoreByUser() throws SQLException {
        Where<ScoreQuiz, Long> score = this.queryBuilder().where().in("User_id", CurrentUser);
        List<ScoreQuiz> r = new ArrayList<>(score.query());
        Collections.reverse(r);
        return r;
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

    public long getTotalScorebyUser() throws SQLException {
        long res = 0;
        List<ScoreQuiz> listScoreByUser = ScoreQuizDAO.queryListScoreByUser();
        if (listScoreByUser.isEmpty()) return res;
        for (ScoreQuiz x : listScoreByUser) {
            res += x.getScore();
        }
        return res;
    }

    public long getNumPlaybyUser() throws SQLException {
        return this.queryBuilder().where().in("User_id", CurrentUser).countOf();
    }

    // Xóa lượt chơi khi xóa user nè
    public boolean deleteScoreQuizbyUser() throws SQLException {
        try {
            ScoreQuiz tuple;
            do {
                tuple = this.queryBuilder().where().eq("User_id", CurrentUser).queryForFirst();
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
        List<ScoreQuiz> hi = ScoreQuizDAO.queryListScoreByUser();
        System.out.println(hi.size());
        for (ScoreQuiz n : hi) {
            System.out.println(n.getUser_id() + " " + n.getScore());
        }
        System.out.println(ScoreQuizDAO.getTotalScorebyUser());
        System.out.println(ScoreQuizDAO.getNumPlaybyUser());
        //ScoreQuizDAO.deleteScoreQuizbyUser("lam");
    }
}
