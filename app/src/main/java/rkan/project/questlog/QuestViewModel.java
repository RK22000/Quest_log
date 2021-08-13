package rkan.project.questlog;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class QuestViewModel extends AndroidViewModel {

    private QuestRepository repository;
    private final LiveData<List<Quest>> importantQuests, urgentQuests, completedQuests, archivedQuests;

    public QuestViewModel(@NonNull Application application) {
        super(application);
        repository = new QuestRepository(application);
        importantQuests = repository.getImportantQuests();
        urgentQuests = repository.getUrgentQuests();
        completedQuests = repository.getCompletedQuests();
        archivedQuests  = repository.getArchivedQuests();
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

    public void insert(Quest quest) {
        repository.insertQuest(quest);
    }

    public void deleteQuest(Quest quest) {
        repository.deleteQuest(quest);
    }

    public void updateQuest(Quest quest) {
        repository.updateQuest(quest);
    }
}
