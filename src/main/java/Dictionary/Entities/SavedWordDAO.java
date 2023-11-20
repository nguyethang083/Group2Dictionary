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

    public List<EngWord> queryListWordByUser(String user) throws SQLException {
        QueryBuilder<SavedWord, Long> IdWordByUser = this.queryBuilder().where().eq("User_id", user).queryBuilder();
        Where<EngWord, Long> english = WordDAO.queryBuilder().where().in("Id", IdWordByUser.selectColumns("English_id"));
        return new ArrayList<>(english.query());
    }
}
