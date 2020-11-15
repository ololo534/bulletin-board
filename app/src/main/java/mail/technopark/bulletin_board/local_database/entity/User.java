package mail.technopark.bulletin_board.local_database.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey
    @ColumnInfo(name = "user_id")
    private long id;

    @ColumnInfo(name = "user_email")
    private String email;

    @ColumnInfo(name = "user_password")
    private String password;

    public User(String email, String password) {
        this.id = 1;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
