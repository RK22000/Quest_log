package rkan.project.questlog.model.questboard;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rkan.project.questlog.model.Quest;
import rkan.project.questlog.R;

/**
 * A layout that has displays a list of Quests and a title for the List.
 *
 * This layout maintains its own list of quests but it allows callbacks to be
 * set so that the actual list of quests can be synced with that of this layout's
 * when the user interacts with the QuestBoard.
 */
public class QuestBoard extends RelativeLayout {
    private final QuestAdapter questAdapter;
    private QuestCallback addQuest, questUpdated, questDeleted;
    private final float DRAG_SCALE_FACTOR = 1.2f;
    private final float DRAG_TRANSLATE_FACTOR = 1.2f;

    public QuestBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.quest_board, this);

        //Set QuestBoard View attributes
        {
            TypedArray attributes = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.QuestBoard);

            try {
                String title = attributes.getString(R.styleable.QuestBoard_boardTitle);
                TextView titleView = findViewById(R.id.questBoardTitle);
                titleView.setText(title);
                titleView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (addQuest != null) {
                            addQuest.call(new Quest());
                        }
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
            RecyclerView questRecycler = findViewById(R.id.questListView);
            questRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            questAdapter = new QuestAdapter();
            questRecycler.setAdapter(questAdapter);

            ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                    ItemTouchHelper.UP | ItemTouchHelper.DOWN
                    , ItemTouchHelper.RIGHT
            ) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    int origin = viewHolder.getAdapterPosition(), destination = target.getAdapterPosition();
                    Quest qO = questAdapter.getQuests().get(origin), qD = questAdapter.getQuests().get(destination);
                    int t = qO.weight;
                    qO.weight = qD.weight;
                    qD.weight = t;
                    Collections.sort(questAdapter.getQuests(), new Comparator<Quest>() {
                        @Override
                        public int compare(Quest o1, Quest o2) {
                            return o1.weight - o2.weight;
                        }
                    });
                    questAdapter.notifyItemMoved(origin, destination);
                    if (questUpdated != null)
                        questUpdated.call(qD, qO);
                    return true;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    Quest deletedQuest = questAdapter.getQuests().remove(viewHolder.getAdapterPosition());
                    questAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    if (questDeleted != null)
                        questDeleted.call(deletedQuest);
                }

                @Override
                public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                    View itemView = viewHolder.itemView;
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                        float alpha = 1 - (Math.abs(dX) / recyclerView.getWidth());
                        itemView.setAlpha(alpha);
                    } else if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                        if (isCurrentlyActive) {
                            itemView.setScaleY(DRAG_SCALE_FACTOR);
                        }
                        else {
                            itemView.setScaleY(1f);
                        }
                    }
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }

            });
            touchHelper.attachToRecyclerView(questRecycler);
        }
    }

    @FunctionalInterface
    public interface QuestCallback {
        void call(Quest... quests);
    }

    public QuestAdapter getQuestAdapter() {
        return questAdapter;
    }

    public void addQuests(Quest... quests) {
        if (quests == null) return;
        for (Quest quest : quests) {
            if (questAdapter.getQuests().size() == 0) quest.weight = 0;
            else quest.weight = 1+questAdapter.getQuests().get(questAdapter.getQuests().size()-1).weight;
            questAdapter.getQuests().add(quest);
            questAdapter.notifyItemInserted(questAdapter.getQuests().size()-1);
        }
    }
    /**
     * Set callback to call when a new quest is added
     * @param qc is the QuestCallback that will be called with the newly added Quest
     */
    public void onQuestAdded(QuestCallback qc){
        addQuest = qc;
    }

    /**
     * Set callback to call after a quest is updated
     */
    public void onQuestUpdated(QuestCallback qc){
        questUpdated = qc;
        questAdapter.onQuestUpdated(questUpdated);
    }

    /**
     * Set callback to call after a quest is deleted
     */
    public void onQuestDeleted(QuestCallback qc){
        questDeleted = qc;
    }

    /**
     * Set the list of Quests for the QuestBoard
     * @param quests
     */
    public void setQuests(List<Quest> quests){
        questAdapter.setQuests(quests);
    }

    public List<Quest> getQuests(){
        return questAdapter.getQuests();
    }
}
