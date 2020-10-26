package mail.technopark.bulletin_board.local_database.view_model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import mail.technopark.bulletin_board.local_database.entity.Settings;
import mail.technopark.bulletin_board.local_database.repository.SettingsRepository;

public class SettingsViewModel extends AndroidViewModel {

    private SettingsRepository mRepository;

    private LiveData<List<Settings>> mSettings;

    public SettingsViewModel(Application application) {
        super(application);
        mRepository = new SettingsRepository(application);
        mSettings = mRepository.getSettings();
    }

    LiveData<List<Settings>> getSettings() {
        return mSettings;
    }


}
