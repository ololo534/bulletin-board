package mail.technopark.bulletin_board.local_database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;



import mail.technopark.bulletin_board.local_database.LocalDB;

import mail.technopark.bulletin_board.local_database.dao.TextMessagesDao;
import mail.technopark.bulletin_board.local_database.entity.TextMessages;

public class TextMessagesRepository {

    private final TextMessagesDao mTextMessagesDao;
    private final LiveData<TextMessages> mTextMessage;

   public TextMessagesRepository(Application application){
        LocalDB database = LocalDB.getDatabase(application);
        mTextMessagesDao = database.textMessagesDao();
        mTextMessage = mTextMessagesDao.getRandomMessage();
    }

    public LiveData<TextMessages> getTextMessages() {
        return mTextMessage;
    }
}
