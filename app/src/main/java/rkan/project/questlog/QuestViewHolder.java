package rkan.project.questlog;


import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

class QuestViewHolder extends RecyclerView.ViewHolder {
    private final String TAG = "QuestViewHolder";
    private CheckBox questDisplay;
    public QuestViewHolder(@NonNull View itemView) {
        super(itemView);
        questDisplay = itemView.findViewById(R.id.questInfoView);

    }
    public void setQuest(Quest quest, QuestBoard.QuestCallback questUpdateCallback) {
        Log.d(TAG, "Setting quest view " + quest.info + " id " + quest.questId);
        questDisplay.setText(quest.info);
        questDisplay.setChecked(quest.completed);
        if (quest.completed) {
            SpannableString strickedinfo = new SpannableString(quest.info);
            strickedinfo.setSpan(new StrikethroughSpan(), 0, quest.info.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            questDisplay.setText(strickedinfo);
        }
        if (questUpdateCallback != null) {
            questDisplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Clicked " + quest.info + " id " + quest.questId);
                    quest.completed = !quest.completed;
                    quest.completionDate = new Date().getTime();

                    questUpdateCallback.call(quest);
                }
            });
        }

    }

    public static QuestViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quest_view, parent, false);
        return new QuestViewHolder(view);
    }
}
