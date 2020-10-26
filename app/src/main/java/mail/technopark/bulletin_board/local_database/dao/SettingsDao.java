package mail.technopark.bulletin_board.local_database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import mail.technopark.bulletin_board.local_database.entity.Settings;


@Dao
public interface SettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Settings setting);

    @Update
    void update(Settings setting);

    @Query("DELETE FROM settings_table")
    void deleteAll();

    @Query("DELETE FROM settings_table WHERE setting_id ==:id")
    void deleteByID(long id);

    @Query("SELECT * from settings_table WHERE setting_name ==:name")
    LiveData<Settings> getSettingsByName(String name);

    @Query("SELECT * from settings_table")
    LiveData<List<Settings>> getSettings();
}
