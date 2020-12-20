package mail.technopark.bulletin_board.local_database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "settings_table")
public class Settings {

    @PrimaryKey
    @ColumnInfo(name = "setting_id")
    private long id;

    @ColumnInfo(name = "setting_name")
    private String name;

    @ColumnInfo(name = "setting_value")
    private String value;

    public Settings(long id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
