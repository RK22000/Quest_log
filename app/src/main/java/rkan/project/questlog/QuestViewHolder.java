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
    //private Quest quest;
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
            quest.completionDate = new Date().getTime();
            SpannableString strickedinfo = new SpannableString(quest.info);
            strickedinfo.setSpan(new StrikethroughSpan(), 0, quest.info.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            questDisplay.setText(strickedinfo);
        }
        questDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quest.completed = !quest.completed;
                /*
                questDisplay.setChecked(quest.completed);
                if (quest.completed) {
                    quest.completionDate = new Date().getTime();
                    SpannableString strickedinfo = new SpannableString(quest.info);
                    strickedinfo.setSpan(new StrikethroughSpan(), 0, quest.info.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    questDisplay.setText(strickedinfo);
                }
                Log.d(TAG, String.valueOf(quest.getCompletionDate()));

                 */
                questUpdateCallback.call(quest);
            }
        });

        /*
        questDisplay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                quest.completed = isChecked;
                if (isChecked) {
                    quest.completionDate = new Date().getTime();
                    Log.d(TAG, String.valueOf(quest.getCompletionDate()));
                }
                questUpdateCallback.call(quest);
            }
        });

         */
    }

    public static QuestViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quest_view, parent, false);
        return new QuestViewHolder(view);
    }
}
