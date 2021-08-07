package rkan.project.questlog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuestAdapter extends RecyclerView.Adapter<QuestViewHolder>{
    List<Quest> quests = new ArrayList<Quest>();
    QuestBoard.QuestCallback questUpdateCallback;

    public void submitQuests(List<Quest> pQuests, QuestBoard.QuestCallback pQuestUpdateCallback) {
        quests = pQuests;
        questUpdateCallback = pQuestUpdateCallback;
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
        holder.setQuest(quests.get(position), questUpdateCallback);
    }

    @Override
    public int getItemCount() {
        return quests.size();
    }
}

