package rkan.project.questlog;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
}