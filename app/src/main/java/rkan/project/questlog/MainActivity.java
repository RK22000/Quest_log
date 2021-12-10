package rkan.project.questlog;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rkan.project.questlog.control.GuildPagerAdapter;
import rkan.project.questlog.control.db_layer.QuestViewModel;
import rkan.project.questlog.model.Quest;
import rkan.project.questlog.model.questboard.QuestBoard;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private QuestBoard importantBoard, urgentBoard;
    private EditText questInput;
    private QuestViewModel questViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questViewModel = new ViewModelProvider(this).get(QuestViewModel.class);

        LiveData<List<Quest>> LD = questViewModel.getCompletedQuests();
        List<Quest> completedQuests = LD.getValue();
        LD.observe(this, new Observer<List<Quest>>() {
            @Override
            public void onChanged(List<Quest> quests) {

                for (Quest q :
                        quests) {
                    if (diffInDays(new Date(), q.getCompletionDate()) >= 1) {
                        Log.d(TAG, "Archiving a quest " + q.info);
                        q.archived = true;
                        questViewModel.updateQuest(q);
                    }
                }
                LD.removeObserver(this);
            }
        });
        if (completedQuests != null) {
            Log.d(TAG, "checking completed quests");
        }

        ViewPager2 guildPager = findViewById(R.id.guild_pager);
        guildPager.setAdapter(new GuildPagerAdapter(getSupportFragmentManager(), getLifecycle()));
    }

    public long diffInDays(Date d1, Date d2) {
        return TimeUnit.DAYS.convert(d1.getTime() - d2.getTime(), TimeUnit.MILLISECONDS);
    }

    public void addQuest(View view) {
        Quest quest = new Quest();
        quest.info = questInput.getText().toString();
        switch (view.getId()) {
            case R.id.urgentQuestButton:
                //board = urgentBoard;
                quest.questType = Quest.Type.URGENT;
                break;
            case R.id.importantQuestButton:
                //board = importantBoard;
                quest.questType = Quest.Type.IMPORTANT;
                break;
            default:
                Log.w(TAG, "Something other than the two add quest buttons tried to add a quest");
                return;
        }
        questViewModel.insert(quest);
        questInput.setText("");
    }

    @Override
    public void onBackPressed() {
        View view = getCurrentFocus();
        if (view != null) {
            view.clearFocus();
            return;
        }
        super.onBackPressed();
    }
}