package mail.technopark.bulletin_board.local_database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;


import mail.technopark.bulletin_board.local_database.LocalDB;
import mail.technopark.bulletin_board.local_database.dao.UserDao;
import mail.technopark.bulletin_board.local_database.entity.User;


public class UserRepository {

    private UserDao mUserDao;
    private LiveData<User> mUser;

   public  UserRepository(Application application){
        LocalDB database = LocalDB.getDatabase(application);
        mUserDao = database.userDao();
        mUser = mUserDao.getUser();
    }

    public LiveData<User> getUser() {
        return mUser;
    }

    public void insert(User user) {
        LocalDB.databaseWriteExecutor.execute(() -> {
            mUserDao.insert(user);
        }
        );
    }
}
