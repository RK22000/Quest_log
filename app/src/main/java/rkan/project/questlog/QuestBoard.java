package rkan.project.questlog;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class QuestBoard extends RelativeLayout {
    private final String TAG = "QuestBoard";
    private String title;
    private RecyclerView questRecycler;
    private QuestAdapter questAdapter;
    private ItemTouchHelper touchHelper;
    private QuestCallback deleteQuestCallback, updateQuestCallback;
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
                TextView titleView = findViewById(R.id.questBoardTitle);
                titleView.setText(title);
                titleView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String  important   = getResources().getString(R.string.important),
                                urgent      = getResources().getString(R.string.urgent);

                        Quest newQuest = new Quest();
                        newQuest.weight = questAdapter.getQuests().size();
                        if (title.equals(important)) {
                            newQuest.questType = Quest.Type.IMPORTANT;
                        } else if (title.equals(urgent)) {
                            newQuest.questType = Quest.Type.URGENT;
                        } else return;
                        addRequestCallback.call(newQuest);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
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

            touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                    ItemTouchHelper.UP | ItemTouchHelper.DOWN
                    , ItemTouchHelper.RIGHT
            ) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    int origin = viewHolder.getAdapterPosition(), destination = target.getAdapterPosition();
                    Quest qO = questAdapter.getQuests().get(origin), qD = questAdapter.getQuests().get(destination);
                    int t       = qO.weight;
                    qO.weight   = qD.weight;
                    qD.weight   = t;
                    Collections.sort(questAdapter.getQuests(), new Comparator<Quest>() {
                        @Override
                        public int compare(Quest o1, Quest o2) {
                            return o1.weight-o2.weight;
                        }
                    });
                    questAdapter.notifyItemMoved(origin, destination);
                    updateQuestCallback.call(qD, qO);
                    return true;
                }




                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    deleteQuest(questAdapter.getQuests().get(viewHolder.getAdapterPosition()));
                    //quests.remove(viewHolder.getAdapterPosition());
                    //questAdapter.notifyDataSetChanged();
                    //adapter.submitList(quests);
                }

                @Override
                public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                        float alpha = 1 - (Math.abs(dX) / recyclerView.getWidth());
                        viewHolder.itemView.setAlpha(alpha);
                    }
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            });
        }


    }

    public void submitQuests(List<Quest> pQuests, QuestCallback pQuestUpdateCallback) {
        questAdapter.submitQuests(pQuests, pQuestUpdateCallback);
        updateQuestCallback = pQuestUpdateCallback;
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
        touchHelper.attachToRecyclerView(questRecycler);
    }


    public void setAddRequestCallback(QuestCallback pAddRequestCallback) {
        addRequestCallback = pAddRequestCallback;
    }

    public interface QuestCallback {
        void call(Quest... quest);
        void call(List<Quest> quests);
    }

    public QuestAdapter getQuestAdapter() {
        return questAdapter;
    }


}
