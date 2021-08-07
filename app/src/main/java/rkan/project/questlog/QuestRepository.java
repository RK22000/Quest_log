package rkan.project.questlog;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class QuestRepository {

    private QuestDao questDao;
    private LiveData<List<Quest>> importantQuests, urgentQuests;

    public QuestRepository(Application application) {
        QuestRoomDatabase db = QuestRoomDatabase.getDatabase(application);
        questDao = db.questDao();
        importantQuests = questDao.getQuests(Quest.Type.IMPORTANT);
        urgentQuests    = questDao.getQuests(Quest.Type.URGENT);
    }

    public LiveData<List<Quest>> getImportantQuests() {
        return importantQuests;
    }

    public LiveData<List<Quest>> getUrgentQuests() {
        return urgentQuests;
    }

    public void insertQuest(Quest quest) {
        QuestRoomDatabase.databaseWriteExecutor.execute(() -> {
            questDao.insertQuest(quest);
        });
    }

    public void deleteQuest(Quest quest) {
        QuestRoomDatabase.databaseWriteExecutor.execute(() -> {
            questDao.deleteQuest(quest);
        });
    }

    public void updateQuest(Quest quest) {
        QuestRoomDatabase.databaseWriteExecutor.execute(() -> {
            questDao.updateQuest(quest);
        });
    }
}
