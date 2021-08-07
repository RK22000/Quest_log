package rkan.project.questlog;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuestDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertQuest(Quest quest);
    @Delete
    void deleteQuest(Quest quest);
    @Query("Select * from quest_table where questType = :type")
    LiveData<List<Quest>> getQuests(Quest.Type type);
}
