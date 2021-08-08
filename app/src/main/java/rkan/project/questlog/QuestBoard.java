package rkan.project.questlog;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class QuestBoard extends RelativeLayout {
    private final String TAG = "QuestBoard";
    private String title;
    private RecyclerView questRecycler;
    private QuestAdapter questAdapter;
    private QuestCallback deleteQuestCallback;
    private QuestCallback addRequestCallback;

    public QuestBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.quest_board, this);

        //Set QuestBoard View attributes
        {
            TypedArray attributes = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.QuestBoard);

            try {
                title = attributes.getString(R.styleable.QuestBoard_boardTitle);
                Toast.makeText(context, title, Toast.LENGTH_SHORT).show();
                TextView titleView = findViewById(R.id.questBoardTitle);
                titleView.setText(title);
                titleView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String  important   = getResources().getString(R.string.important),
                                urgent      = getResources().getString(R.string.urgent);

                        Quest newQuest = new Quest();
                        if (title.equals(important)) {
                            newQuest.questType = Quest.Type.IMPORTANT;
                        } else if (title.equals(urgent)) {
                            newQuest.questType = Quest.Type.URGENT;
                        } else return;
                        addRequestCallback.call(newQuest);
                    }
                });
            } finally {
                attributes.recycle();
            }
        }

        //Set up the recycler view
        {
            questRecycler = findViewById(R.id.questListView);
            questRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            //questRecycler.setLayoutManager(new StaggeredGridLayoutMan);
            questAdapter = new QuestAdapter();
            questRecycler.setAdapter(questAdapter);
            //adapter = new QuestListAdapter(new QuestListAdapter.QuestDiff());
            //questRecycler.setAdapter(adapter);

            ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                    0
                    , ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
            ) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    deleteQuest(questAdapter.getQuests().get(viewHolder.getAdapterPosition()));
                    //quests.remove(viewHolder.getAdapterPosition());
                    //questAdapter.notifyDataSetChanged();
                    //adapter.submitList(quests);
                }
            });
            touchHelper.attachToRecyclerView(questRecycler);
        }


    }

    public void submitQuests(List<Quest> pQuests, QuestCallback pQuestUpdateCallback) {
        questAdapter.submitQuests(pQuests, pQuestUpdateCallback);
    }

    public void deleteQuest(Quest quest){
        //quests.remove(quest);
        //questAdapter.notifyDataSetChanged();
        try {
            deleteQuestCallback.call(quest);
        } catch (NullPointerException e) {
            Log.e(TAG, "Tried to call deleteQuestCallback, but it was set to null");
            e.printStackTrace();
        }
    }

    public void setDeleteQuestCallback(QuestCallback pDeleteQuestCallback) {
        deleteQuestCallback = pDeleteQuestCallback;
    }


    public void setAddRequestCallback(QuestCallback pAddRequestCallback) {
        addRequestCallback = pAddRequestCallback;
    }

    public interface QuestCallback {
        void call(Quest quest);
    }


}
