package Dictionary.Entities;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Dictionary.DatabaseConn.CurrentUser;
import static Dictionary.DatabaseConn.WordDAO;
import static Dictionary.DatabaseConn.SearchedWordDAO;

public class SearchedWordDAO extends BaseDaoImpl<SearchedWord, Long> {
    public SearchedWordDAO(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, SearchedWord.class);
    }

    // theo alphabet (thích thì dùng)
    public List<EngWord> queryListWordByUser() throws SQLException {
        QueryBuilder<SearchedWord, Long> IdWordByUser = this.queryBuilder().where().eq("User_id", CurrentUser).queryBuilder();
        Where<EngWord, Long> english = WordDAO.queryBuilder().where().in("Id", IdWordByUser.selectColumns("English_id"));
        ArrayList<EngWord> res = new ArrayList<>(english.query());
        if (res.size() < 51) return res;
        return res.subList(0, 49);
    }

    // ham theo newest
    public List<EngWord> queryListWordByUserNewest() throws SQLException {
        List<SearchedWord> searchedWords = new ArrayList<>(this.queryBuilder().where().eq("User_id", CurrentUser).query());
        List<EngWord> res = new ArrayList<>();
        int i = 0;
        for (SearchedWord x : searchedWords) {
            i++;
            if (i > 50) break;
            res.add(WordDAO.queryBuilder().where().in("Id", x.getEnglish_id()).queryForFirst());
        }
        return res;
    }

    // theo newest
    public List<SearchedWord> queryListSearchedWordByUser() throws SQLException {
        ArrayList<SearchedWord> res = new ArrayList<>(this.queryBuilder().where().eq("User_id", CurrentUser).query());
        Collections.reverse(res);
        if (res.size() < 51) return res;
        return res.subList(0, 49);
    }

    public List<SearchedWord> searchSearchedWordByUserNewest(String prefix) throws SQLException {
        prefix = prefix.replaceAll("\'", "''");
        QueryBuilder<EngWord, Long> containWord = WordDAO.queryBuilder().where().like("Word", prefix + "%").queryBuilder();
        Where<SearchedWord, Long> res = this.queryBuilder().where().eq("User_id", CurrentUser).and().in("English_id", containWord.selectColumns("Id"));
        List<SearchedWord> r = new ArrayList<>(res.query());
        Collections.reverse(r);
        return r;
    }

    /**
     * hàm add id từ vào user được chọn
     *
     * @param x bao gồm Userid và Englishid
     * @return true nếu add thành công, false nếu đã có tuple đó rồi (từ đã lưu mà bị ngáo cố ấn)
     * @throws SQLException
     */
    public boolean addSearchedWord(EngWord x) throws SQLException {
        long EngId = x.getId();
        String UserId = CurrentUser;
        SearchedWord y = new SearchedWord(EngId, UserId);
        if (EngId == 0 || UserId.isEmpty()) {
            return false;
        }
        try {
            SearchedWord tuple = this.queryBuilder().where().eq("User_id", UserId).and().eq("English_id", EngId).queryForFirst();
            if (tuple != null) {
                this.delete(tuple);
            }
            this.create(y);
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

    public boolean deleteAllWordsByUser() throws SQLException {
        try {
            List<SearchedWord> tuples = this.queryBuilder().where().eq("User_id", CurrentUser).query();
            for (SearchedWord tuple : tuples) {
                this.delete(tuple);
            }
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " deleteAllWordsByUser");
            return false;
        }
    }

    public static void main(String[] args) throws SQLException {
        SearchedWordDAO.deleteAllWordsByUser();
    }
}