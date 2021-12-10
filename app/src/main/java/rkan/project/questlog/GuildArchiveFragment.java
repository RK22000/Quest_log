package rkan.project.questlog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuildArchiveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuildArchiveFragment extends Fragment {

    public GuildArchiveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GuildArchiveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GuildArchiveFragment newInstance() {
        return new GuildArchiveFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guild_archive, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        QuestBoard archiveBoard = view.findViewById(R.id.archive_board);
        QuestViewModel questViewModel = new ViewModelProvider(this).get(QuestViewModel.class);

        questViewModel.getArchivedQuests().observe(getViewLifecycleOwner(), new Observer<List<Quest>>() {
            @Override
            public void onChanged(List<Quest> quests) {
                archiveBoard.setQuests(quests);
            }
        });

        archiveBoard.onQuestDeleted(questViewModel::deleteQuest);
    }
}