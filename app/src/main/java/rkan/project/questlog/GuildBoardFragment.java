package rkan.project.questlog;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        QuestBoard importantBoard, urgentBoard;
        EditText questInput;
        QuestViewModel questViewModel;

        questInput = view.findViewById(R.id.questInputText);

        importantBoard  = view.findViewById(R.id.importantQuestBoard);
        urgentBoard     = view.findViewById(R.id.urgentQuestBoard);

        questViewModel = new ViewModelProvider(this).get(QuestViewModel.class);

        QuestBoard.QuestCallback updateQuestCallback = new QuestBoard.QuestCallback() {
            @Override
            public void call(Quest... quest) {
                questViewModel.updateQuest(quest);
            }

            @Override
            public void call(List<Quest> quests) {
                questViewModel.updateQuests(quests);
            }
        };

        questViewModel.getImportantQuests().observe(getViewLifecycleOwner(), new Observer<List<Quest>>() {
            @Override
            public void onChanged(List<Quest> quests) {
                importantBoard.submitQuests(quests, updateQuestCallback);
            }
        });
        questViewModel.getUrgentQuests().observe(getViewLifecycleOwner(), new Observer<List<Quest>>() {
            @Override
            public void onChanged(List<Quest> quests) {
                urgentBoard.submitQuests(quests, updateQuestCallback);
            }
        });

        QuestBoard.QuestCallback deleteQuestCallback = new QuestBoard.QuestCallback() {
            @Override
            public void call(Quest... quest) {
                questViewModel.deleteQuest(quest);
            }

            @Override
            public void call(List<Quest> quests) {
            }
        };

        importantBoard.setDeleteQuestCallback(deleteQuestCallback);
        urgentBoard.setDeleteQuestCallback(deleteQuestCallback);

        QuestBoard.QuestCallback addRequestCallback = new QuestBoard.QuestCallback() {
            @Override
            public void call(Quest... quest) {
                quest[0].info = questInput.getText().toString();
                if (quest[0].info.equals("")) {
                    return;
                }
                questViewModel.insert(quest);
                questInput.setText("");
            }

            @Override
            public void call(List<Quest> quests) {
            }
        };
        importantBoard.setAddRequestCallback(addRequestCallback);
        urgentBoard.setAddRequestCallback(addRequestCallback);
    }
}