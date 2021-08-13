package rkan.project.questlog;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface QuestDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertQuest(Quest quest);
    @Delete
    void deleteQuest(Quest quest);
    @Query("Select * from quest_table where questType = :type and archived = 0")
    LiveData<List<Quest>> getQuests(Quest.Type type);
    @Update
    public void updateQuest(Quest quest);
    @Query("Select * from quest_table where not archived = 0 order by completionDate asc")
    LiveData<List<Quest>> getArchivedQuests();
    @Query("Select * from quest_table where not completed = 0 and archived = 0")
    LiveData<List<Quest>> getCompletedQuests();
}
