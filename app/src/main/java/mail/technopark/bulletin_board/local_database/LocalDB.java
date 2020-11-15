package mail.technopark.bulletin_board.local_database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mail.technopark.bulletin_board.local_database.dao.SettingsDao;
import mail.technopark.bulletin_board.local_database.dao.TextMessagesDao;
import mail.technopark.bulletin_board.local_database.dao.UserDao;
import mail.technopark.bulletin_board.local_database.entity.Settings;
import mail.technopark.bulletin_board.local_database.entity.TextMessages;
import mail.technopark.bulletin_board.local_database.entity.User;

@Database(entities = {User.class, Settings.class, TextMessages.class}, version = 1, exportSchema = false)
public abstract class LocalDB extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract SettingsDao settingsDao();

    public abstract TextMessagesDao textMessagesDao();

    private static volatile LocalDB INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService DB_WRITE_EXECUTOR =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static LocalDB getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (LocalDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LocalDB.class, "local_database")
                            .addCallback(ROOM_DB_CALLBACK_CREATE)
                            .addCallback(ROOM_DB_CALLBACK_OPEN)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback ROOM_DB_CALLBACK_CREATE = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            DB_WRITE_EXECUTOR.execute(() -> {

                        SettingsDao settingsDao = INSTANCE.settingsDao();

                        Settings setting = new Settings(0, "is_phone_visible", "false");
                        settingsDao.insert(setting);
                        setting = new Settings(1, "is_name_visible", "true");
                        settingsDao.insert(setting);
                        setting = new Settings(2, "dark_theme", "false");
                        settingsDao.insert(setting);
                        setting = new Settings(3, "language", "ru");
                        settingsDao.insert(setting);

                    }
            );
        }
    };

    private static final RoomDatabase.Callback ROOM_DB_CALLBACK_OPEN = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            DB_WRITE_EXECUTOR.execute(() -> {
                        UserDao userDao = INSTANCE.userDao();
                    }
            );
        }
    };
}
