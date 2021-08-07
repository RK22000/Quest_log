package rkan.project.questlog;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class QuestListAdapter extends ListAdapter<Quest, QuestViewHolder> {
    protected QuestListAdapter(@NonNull DiffUtil.ItemCallback<Quest> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public QuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return QuestViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestViewHolder holder, int position) {
        holder.setQuest(getItem(position));
    }

    public static class QuestDiff extends DiffUtil.ItemCallback<Quest> {

        @Override
        public boolean areItemsTheSame(@NonNull Quest oldItem, @NonNull Quest newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Quest oldItem, @NonNull Quest newItem) {
            return oldItem.equals(newItem);
        }
    }
}
