package rkan.project.questlog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "quest_table")
public class Quest {

    @PrimaryKey(autoGenerate = true)
    public int questId;
    /*public int getQuestId() {
        return questId;
    }*/

    /**
     * Information on the quest
     */
    @NonNull
    public String   info;
    public Type     questType;
    public boolean  completed  = false;
    public boolean  archived = false;
    public long     completionDate; // TODO: refactor completionDate to completionTime
    public Date getCompletionDate() {
        return new Date(completionDate);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Quest other = (Quest) obj;
        return questId == other.questId;
    }


    public static enum Type {
        IMPORTANT,
        URGENT
    }
}
