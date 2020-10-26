package mail.technopark.bulletin_board.local_database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "text_messages_table")
public class TextMessages {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "text_message_id")
    private long id;

    @ColumnInfo(name = "text_message")
    private String message;

    public TextMessages(long id, String message) {
        this.id = id;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
