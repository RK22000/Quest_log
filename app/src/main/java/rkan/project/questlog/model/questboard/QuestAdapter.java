package rkan.project.questlog.model.questboard;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
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
            int pos = holder.getAdapterPosition();
            notifyItemChanged(pos);
            // if only one quest updated
            if (updatedQuests.length == 1) {
                Quest quest = updatedQuests[0];
                // if that quest is now completed
                if (quest.completed) {
                    // move it to the bottom
                    quest.weight = getQuests().get(getItemCount()-1).weight+1;
                    Collections.sort(quests, (q1,q2)->q1.weight-q2.weight);
                    notifyItemMoved(pos , getItemCount()-1);
                }
            }
            questUpdateCallback.call(updatedQuests);
        });
    }

    @Override
    public int getItemCount() {
        return quests.size();
    }
}

