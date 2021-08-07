package rkan.project.questlog;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "quest_table")
public class Quest {

    @PrimaryKey(autoGenerate = true)
    public int questId;
    public int getQuestId() {
        return questId;
    }

    /**
     * Information on the quest
     */
    @NonNull
    public String info;

    public static enum Type {
        IMPORTANT,
        URGENT
    }

    public Type questType;

    public boolean completed  = false;
    public boolean archived = false;
    public long completionDate;
    public Date getCompletionDate() {
        return new Date(completionDate);
    }
}
