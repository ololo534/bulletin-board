package mail.technopark.bulletin_board.local_database.view_model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;


import mail.technopark.bulletin_board.local_database.entity.User;
import mail.technopark.bulletin_board.local_database.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository mRepository;
    private final User mUser;

    public UserViewModel(Application application) {
        super(application);
        mRepository = new UserRepository(application);
        mUser = mRepository.getUser();
    }

   public User getUser() {
        return mUser;
    }

    public void insert(User user) {
        mRepository.insert(user);
    }

    public void delete()
    {
        mRepository.delete();
    }

    public Integer numOfRec()
    {
        return mRepository.numOfRec();
    }


}
