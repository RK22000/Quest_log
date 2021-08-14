package rkan.project.questlog;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class QuestRepository {

    private QuestDao questDao;
    private LiveData<List<Quest>> importantQuests, urgentQuests, completedQuests, archivedQuests;

    public QuestRepository(Application application) {
        QuestRoomDatabase db = QuestRoomDatabase.getDatabase(application);
        questDao = db.questDao();
        importantQuests = questDao.getQuests(Quest.Type.IMPORTANT);
        urgentQuests    = questDao.getQuests(Quest.Type.URGENT);
        completedQuests = questDao.getCompletedQuests();
        archivedQuests  = questDao.getArchivedQuests();
    }

    public LiveData<List<Quest>> getImportantQuests() {
        return importantQuests;
    }

    public LiveData<List<Quest>> getUrgentQuests() {
        return urgentQuests;
    }

    public LiveData<List<Quest>> getCompletedQuests() {
        return completedQuests;
    }

    public LiveData<List<Quest>> getArchivedQuests() {
        return archivedQuests;
    }

    public void insertQuest(Quest... quest) {
        QuestRoomDatabase.databaseWriteExecutor.execute(() -> {
            questDao.insertQuest(quest);
        });
    }

    public void deleteQuest(Quest... quest) {
        QuestRoomDatabase.databaseWriteExecutor.execute(() -> {
            questDao.deleteQuest(quest);
        });
    }

    public void updateQuest(Quest... quest) {
        QuestRoomDatabase.databaseWriteExecutor.execute(() -> {
            questDao.updateQuest(quest);
        });
    }

    public void updateQuests(List<Quest> quests) {
        QuestRoomDatabase.databaseWriteExecutor.execute(() -> {
            questDao.updateQuests(quests);
        });
    }
}
