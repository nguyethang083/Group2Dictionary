package Dictionary.Entities;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "User", daoClass = User.class)
public class User {
    @DatabaseField(generatedId = false, index = true)
    private String Id;
    @DatabaseField(canBeNull = false, index = true)
    private String Firstname = "";
    @DatabaseField(canBeNull = true, index = true)
    private String Lastname;

    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }
    public String getFirstname() {
        return Firstname;
    }
    public void setFirstname(String x) {
        Firstname = x;
    }

    public String getLastname() {
        return Lastname;
    }
    public void setLastname(String x) {
        Lastname = x;
    }
    public User() {
    }

    public User(String id, String F, String L) {
        Id = id;
        Firstname = F;
        Lastname = L;
    }

    @Override
    public String toString(){
        return "This user is " + Id + "" + Firstname + "" + Lastname + "\n";
    }
}