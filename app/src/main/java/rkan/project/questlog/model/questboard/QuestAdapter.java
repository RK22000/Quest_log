package rkan.project.questlog.model.questboard;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import rkan.project.questlog.model.Quest;

class QuestAdapter extends RecyclerView.Adapter<QuestViewHolder>{
    private List<Quest> quests = new ArrayList<>();
    private QuestBoard.QuestCallback questUpdateCallback;

    public void setQuests(List<Quest> quests) {
        this.quests = quests;
        notifyDataSetChanged();
    }

    public void onQuestUpdated(QuestBoard.QuestCallback questUpdated) {
        questUpdateCallback = questUpdated;
        notifyDataSetChanged();
    }

    public List<Quest> getQuests() {
        return quests;
    }

    @NonNull
    @Override
    public QuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return QuestViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestViewHolder holder, int position) {
        holder.setQuest(quests.get(position), updatedQuests -> {
            notifyItemChanged(position);
            questUpdateCallback.call(updatedQuests);
        });
    }

    @Override
    public int getItemCount() {
        return quests.size();
    }
}

