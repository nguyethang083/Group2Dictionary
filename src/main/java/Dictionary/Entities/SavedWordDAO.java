package Dictionary.Entities;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Dictionary.DatabaseConn.WordDAO;

public class SavedWordDAO extends BaseDaoImpl<SavedWord, Long> {
    public SavedWordDAO(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, SavedWord.class);
    }

    // lưu ý cực mạnh: nếu ko muốn dùng id của word để làm input mà dùng word dạng string
    // thì có thể dùng hàm WordDAO.queryIdbyWord

    /**
     *
     * @param user chọn 1 user (lúc login)
     * @return Danh sách kiểu ArrayList<EngWord> của User đó (lúc hiện lên màn thì dùng hàm này)
     * @throws SQLException
     */
    public List<EngWord> queryListWordByUser(String user) throws SQLException {
        QueryBuilder<SavedWord, Long> IdWordByUser = this.queryBuilder().where().eq("User_id", user).queryBuilder();
        Where<EngWord, Long> english = WordDAO.queryBuilder().where().in("Id", IdWordByUser.selectColumns("English_id"));
        return new ArrayList<>(english.query());
    }

    /**
     * hàm add id từ vào user được chọn
     * @param x bao gồm Userid và Englishid
     * @return true nếu add thành công, false nếu đã có tuple đó rồi (từ đã lưu mà bị ngáo cố ấn)
     * @throws SQLException
     */
    public boolean addSavedWord(SavedWord x) throws SQLException {
        long EngId = x.getEnglish_id();
        String UserId = x.getUser_id();
        if (EngId == 0 || UserId.isEmpty()) {
            return false;
        }
        try {
            Where<SavedWord, Long> tuple = this.queryBuilder().where().eq("User_id", UserId).and().eq("English_id",EngId);
            if (tuple.query() != null) {
                System.out.println("Từ này đã được lưu rồi");
                return false;
            }
            else {
                this.create(x);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " insertSWord");
            return false;
        }
        return true;
    }

    // Chức năng ngược add còn các cái khác (in & out) giống hoàn toàn
    public boolean deleteTuple(SavedWord x) throws SQLException {
        long EngId = x.getEnglish_id();
        String UserId = x.getUser_id();
        try {
            SavedWord tuple = this.queryBuilder().where().eq("User_id", UserId).and().eq("English_id", EngId).queryForFirst();
            System.out.println(tuple.toString());
            this.delete(tuple);
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " deleteSWord");
            return false;
        }
    }
}