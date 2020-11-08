package mail.technopark.bulletin_board.local_database.view_model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import mail.technopark.bulletin_board.local_database.entity.TextMessages;
import mail.technopark.bulletin_board.local_database.repository.TextMessagesRepository;

public class TextMessagesViewModel extends AndroidViewModel {

    private final TextMessagesRepository mRepository;

    private final LiveData<TextMessages> mTextMessage;

    public TextMessagesViewModel(Application application) {
        super(application);
        mRepository = new TextMessagesRepository(application);
        mTextMessage = mRepository.getTextMessages();
    }

    LiveData<TextMessages> getTextMessage() {
        return mTextMessage;
    }
}
