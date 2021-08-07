package rkan.project.questlog;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

class QuestViewHolder extends RecyclerView.ViewHolder {
    private final String TAG = "QuestViewHolder";
    //private Quest quest;
    private CheckBox questDisplay;
    public QuestViewHolder(@NonNull View itemView) {
        super(itemView);
        questDisplay = itemView.findViewById(R.id.questInfoView);
    }
    public void setQuest(Quest quest) {
        Log.d(TAG, "Setting quest view " + quest.info + " id " + quest.questId);
        questDisplay.setText(quest.info);
        questDisplay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                quest.completed = isChecked;
                if (isChecked) {
                    quest.completionDate = new Date().getTime();
                    Log.d(TAG, String.valueOf(quest.getCompletionDate()));
                }
            }
        });
    }

    public static QuestViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quest_view, parent, false);
        return new QuestViewHolder(view);
    }
}
