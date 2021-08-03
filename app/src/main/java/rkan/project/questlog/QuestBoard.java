package rkan.project.questlog;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuestBoard extends RelativeLayout {
    private final String TAG = "QuestBoard";
    private String title;
    private RecyclerView questRecycler;
    private QuestListAdapter questListAdapter;
    private ArrayList<Quest> quests;
    public QuestBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.quest_board, this);

        TypedArray attributes = context.obtainStyledAttributes(
                attrs,
                R.styleable.QuestBoard);

        try {
            title = attributes.getString(R.styleable.QuestBoard_boardTitle);
            Toast.makeText(context, title, Toast.LENGTH_SHORT).show();
            TextView titleView = findViewById(R.id.questBoardTitle);
            titleView.setText(title);
        } finally {
            attributes.recycle();
        }

        quests = new ArrayList<>();
        questRecycler = findViewById(R.id.questListView);
        questRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        questListAdapter = new QuestListAdapter(quests);
        questRecycler.setAdapter(questListAdapter);

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
                quests.remove(viewHolder.getAdapterPosition());
                questListAdapter.notifyDataSetChanged();
            }
        });
        touchHelper.attachToRecyclerView(questRecycler);

    }

    public void addQuest(Quest quest) {
        Log.d(TAG, "Adding Quest. Current length = " + quests.size());
        quests.add(quest);
        questListAdapter.notifyDataSetChanged();
    }



}
