package Dictionary.Entities;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends BaseDaoImpl<User, Long> {
    public UserDAO(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, User.class);
    }

    /**
     * hàm add id từ vào user được chọn
     * @param x bao gồm Userid và Englishid
     * @return true nếu add thành công, false nếu đã có tuple đó rồi (từ đã lưu mà bị ngáo cố ấn)
     * @throws SQLException
     */
    public boolean addUser(User x) throws SQLException {
        String id = x.getId();
        String FirstN = x.getFirstname();
        String LastN = x.getLastname();
        if (id.isEmpty() || FirstN.isEmpty()) {
            return false;
        }
        try {
            Where<User, Long> tuple = this.queryBuilder().where().eq("Id", id);
            if (tuple.queryForFirst() != null) {
                System.out.println(tuple.queryForFirst().toString());
                System.out.println("User này đã được lưu rồi");
                return false;
            }
            else {
                this.create(x);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " insertUser");
            return false;
        }
        return true;
    }

    // Chức năng ngược add còn các cái khác (in & out) giống hoàn toàn
    public boolean deleteTuple(User x) throws SQLException {
        String id = x.getId();
        String FirstN = x.getFirstname();
        String LastN = x.getLastname();
        try {
            User tuple = this.queryBuilder().where().eq("Id", id).queryForFirst();
            System.out.println(tuple.toString());
            this.delete(tuple);
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " deleteSWord");
            return false;
        }
    }
}
