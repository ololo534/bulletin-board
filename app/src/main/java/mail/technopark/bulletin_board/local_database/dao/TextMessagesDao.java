package mail.technopark.bulletin_board.local_database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


import mail.technopark.bulletin_board.local_database.entity.TextMessages;


@Dao
public interface TextMessagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TextMessages textMessage);

    @Update
    void update(TextMessages textMessage);

    @Query("DELETE FROM text_messages_table")
    void deleteAll();

    @Query("DELETE FROM text_messages_table WHERE text_message_id ==:id")
    void deleteByID(long id);

    @Query("SELECT * from text_messages_table ORDER BY RANDOM()")
    LiveData<TextMessages> getRandomMessage();

    @Query("SELECT * from text_messages_table")
    LiveData<List<TextMessages>> getMessages();
}
