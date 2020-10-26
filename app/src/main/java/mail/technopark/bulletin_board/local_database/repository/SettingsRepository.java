package mail.technopark.bulletin_board.local_database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import mail.technopark.bulletin_board.local_database.LocalDB;
import mail.technopark.bulletin_board.local_database.dao.SettingsDao;
import mail.technopark.bulletin_board.local_database.entity.Settings;

public class SettingsRepository {

    private SettingsDao mSettingsDao;
    private LiveData<List<Settings>> mSettings;

   public SettingsRepository(Application application){
        LocalDB database = LocalDB.getDatabase(application);
        mSettingsDao = database.settingsDao();
        mSettings = mSettingsDao.getSettings();
    }

   public LiveData<List<Settings>> getSettings() {
        return mSettings;
    }

}
