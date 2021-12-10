package rkan.project.questlog;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuildBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuildBoardFragment extends Fragment {


    public GuildBoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GuildBoardFragment.
     */
    public static GuildBoardFragment newInstance() {
        return new GuildBoardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guild_board, container, false);
    }

    private EditText questInput;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        QuestBoard importantBoard, urgentBoard;
        QuestViewModel questViewModel;

        questInput = view.findViewById(R.id.questInputText);

        importantBoard  = view.findViewById(R.id.importantQuestBoard);
        urgentBoard     = view.findViewById(R.id.urgentQuestBoard);

        questViewModel = new ViewModelProvider(this).get(QuestViewModel.class);

        importantBoard.onQuestAdded(quests -> {
            Quest quest = getInputQuest();
            if (quest == null) return;
            quest.questType = Quest.Type.IMPORTANT;
            importantBoard.addQuests(quest);
            questInput.setText("");
            questViewModel.insert(quest);
        });
        importantBoard.onQuestUpdated(questViewModel::updateQuest); // This is called a method reference
        importantBoard.onQuestDeleted(questViewModel::deleteQuest);

        urgentBoard.onQuestAdded(quests -> {
            Quest quest = getInputQuest();
            if (quest == null) return;
            quest.questType = Quest.Type.URGENT;
            urgentBoard.addQuests(quest);
            questInput.setText("");
            questViewModel.insert(quest);
        });
        urgentBoard.onQuestUpdated(questViewModel::updateQuest);
        urgentBoard.onQuestDeleted(questViewModel::deleteQuest);

        questViewModel.getImportantQuests().observe(getViewLifecycleOwner(), quests -> {
            Log.d("ImportantQuest Observer", "Important quests changed");
            if (!isSameQuestList(quests, importantBoard.getQuestAdapter().quests))
                importantBoard.setQuests(quests);
        });
        questViewModel.getUrgentQuests().observe(getViewLifecycleOwner(), quests ->{
            Log.d("UrgentQuest Observer", "Urgent quests changed");
            if(!isSameQuestList(quests, urgentBoard.getQuestAdapter().quests))
                urgentBoard.setQuests(quests);
        });

    }

    private Quest getInputQuest() {
        if(questInput.getText().toString().equals("")) return null;
        Quest quest = new Quest();
        quest.info = questInput.getText().toString();
        return  quest;
    }

    /**
     * Helper method to check if 2 List of Quests match each other
     * @param q1 is the first List of Quests
     * @param q2 is the second List of Quests
     * @return true if both lists are the same
     */
    public boolean isSameQuestList(List<Quest> q1, List<Quest> q2) {
        boolean same = false;
        if (q1.size() == q2.size()) {
            same = true;
            for (int i = 0; i < q1.size(); i++) {
                if (!Quest.areSame(q1.get(i), q2.get(i))) {
                    same = false;
                    break;
                }
            }
        }
        Log.d("isSameQuestList", same+"");
        return same;
    }
}