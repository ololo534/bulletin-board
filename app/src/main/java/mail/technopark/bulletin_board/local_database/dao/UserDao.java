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

    @Query("DELETE FROM user_table WHERE user_id ==:id")
    void deleteByID(long id);

    @Query("SELECT * from user_table WHERE user_id ==:id")
    LiveData<User> getUserByID(long id);

    @Query("SELECT * from user_table")
    LiveData<User> getUser();
}
