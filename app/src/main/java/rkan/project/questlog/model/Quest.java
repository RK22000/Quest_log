package rkan.project.questlog.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "quest_table")
public class Quest {

    @PrimaryKey(autoGenerate = true)
    public int questId;

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
    public int      weight = 0;

    @Override
    public boolean equals(@Nullable Object obj) {
        Quest other = (Quest) obj;
        return questId == other.questId;
    }


    public static enum Type {
        IMPORTANT,
        URGENT
    }

    public static boolean areSame(Quest q1, Quest q2) {
        return q1.questId == q2.questId &&
                q1.info.equals(q2.info) &&
                q1.questType == q2.questType &&
                q1.completed == q2.completed &&
                q1.archived == q2.archived &&
                q1.completionDate == q2.completionDate &&
                q1.weight == q2.weight;
    }
}
