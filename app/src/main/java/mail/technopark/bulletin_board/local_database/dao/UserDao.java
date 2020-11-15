package mail.technopark.bulletin_board.local_database.dao;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import mail.technopark.bulletin_board.local_database.entity.User;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update
    void update(User user);

    @Query("DELETE  from user_table")
    void delete();

    @Query("SELECT * from user_table WHERE user_id ==:email")
    LiveData<User> getUserByEmail(String email);

    @Query("SELECT * from user_table")
    User getUser();

    @Query("SELECT count(*) from user_table")
    Integer numOfRec();



}
