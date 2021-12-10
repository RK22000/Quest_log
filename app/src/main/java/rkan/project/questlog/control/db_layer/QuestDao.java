package rkan.project.questlog.control.db_layer;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import rkan.project.questlog.model.Quest;

@Dao
interface QuestDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertQuest(Quest... quest);
    @Delete
    void deleteQuest(Quest... quest);
    @Query("Select * from quest_table where questType = :type and archived = 0 order by weight asc")
    LiveData<List<Quest>> getQuests(Quest.Type type);
    @Update(entity = Quest.class)
    public void updateQuest(Quest... quest);
    @Update
    public void updateQuests(List<Quest> quests);
    @Query("Select * from quest_table where not archived = 0 order by completionDate asc")
    LiveData<List<Quest>> getArchivedQuests();
    @Query("Select * from quest_table where not completed = 0 and archived = 0")
    LiveData<List<Quest>> getCompletedQuests();
}
