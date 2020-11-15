package mail.technopark.bulletin_board.local_database.repository;

import android.app.Application;
import mail.technopark.bulletin_board.local_database.LocalDB;
import mail.technopark.bulletin_board.local_database.dao.UserDao;
import mail.technopark.bulletin_board.local_database.entity.User;


public class UserRepository {

    private final UserDao mUserDao;
    private final User mUser;

   public  UserRepository(Application application) {
        LocalDB database = LocalDB.getDatabase(application);
        mUserDao = database.userDao();
        mUser = mUserDao.getUser();
    }

    public User getUser() {
        return mUser;
    }

    public void insert(User user) {
        LocalDB.DB_WRITE_EXECUTOR.execute(() -> mUserDao.insert(user));
    }

    public void delete()
    {
        LocalDB.DB_WRITE_EXECUTOR.execute(mUserDao::delete);
    }

    public Integer numOfRec()
    {
        return mUserDao.numOfRec();
    }
}
