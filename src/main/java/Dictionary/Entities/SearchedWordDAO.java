package Dictionary.Entities;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Dictionary.DatabaseConn.WordDAO;

public class SearchedWordDAO extends BaseDaoImpl<SearchedWord, Long> {
    public SearchedWordDAO(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, SearchedWord.class);
    }

    // theo alphabet (thích thì dùng)
    public List<EngWord> queryListWordByUser(String user) throws SQLException {
        QueryBuilder<SearchedWord, Long> IdWordByUser = this.queryBuilder().where().eq("User_id", user).queryBuilder();
        Where<EngWord, Long> english = WordDAO.queryBuilder().where().in("Id", IdWordByUser.selectColumns("English_id"));
        ArrayList<EngWord> res = new ArrayList<>(english.query());
        if (res.size() < 51) return res;
        return res.subList(0, 49);
    }

    // ham theo newest
    public List<EngWord> queryListWordByUserNewest(String user) throws SQLException {
        List<SearchedWord> searchedWords = new ArrayList<>(this.queryBuilder().where().eq("User_id", user).query());
        List<EngWord> res = new ArrayList<>();
        int i = 0;
        for(SearchedWord x : searchedWords) {
            i++;
            if (i > 50) break;
            res.add(WordDAO.queryBuilder().where().in("Id", x.getEnglish_id()).queryForFirst());
        }
        return res;
    }

    // theo newest
    public List<SearchedWord> queryListSearchedWordByUser(String user) throws SQLException {
        ArrayList<SearchedWord> res = new ArrayList<>(this.queryBuilder().where().eq("User_id", user).query());
        if (res.size() < 51) return res;
        return res.subList(0, 49);
    }

    /**
     * hàm add id từ vào user được chọn
     * @param x bao gồm Userid và Englishid
     * @return true nếu add thành công, false nếu đã có tuple đó rồi (từ đã lưu mà bị ngáo cố ấn)
     * @throws SQLException
     */
    public boolean addSearchedWord(SearchedWord x) throws SQLException {
        long EngId = x.getEnglish_id();
        String UserId = x.getUser_id();
        if (EngId == 0 || UserId.isEmpty()) {
            return false;
        }
        try {
            SearchedWord tuple = this.queryBuilder().where().eq("User_id", UserId).and().eq("English_id",EngId).queryForFirst();
            if (tuple != null) {
                this.delete(tuple);
                System.out.println("Đã xóa bản ghi cũ");
            }
            this.create(x);
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " insertSWord");
            return false;
        }
        return true;
    }

    // Chức năng ngược add còn các cái khác (in & out) giống hoàn toàn
    public boolean deleteTuple(SearchedWord x) throws SQLException {
        long EngId = x.getEnglish_id();
        String UserId = x.getUser_id();
        try {
            SearchedWord tuple = this.queryBuilder().where().eq("User_id", UserId).and().eq("English_id", EngId).queryForFirst();
            //System.out.println(tuple.toString());
            this.delete(tuple);
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " deleteSWord");
            return false;
        }
    }

    public boolean deleteAllWordsByUser(String UserId) throws SQLException {
        try {
            List<SearchedWord> tuples = this.queryBuilder().where().eq("User_id", UserId).query();
            for (SearchedWord tuple : tuples) {
                this.delete(tuple);
            }
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " deleteAllWordsByUser");
            return false;
        }
    }
}
