package rkan.project.questlog;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class GuildPagerAdapter extends FragmentStateAdapter {

    public GuildPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1: return GuildArchiveFragment.newInstance();
            default: return GuildBoardFragment.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
