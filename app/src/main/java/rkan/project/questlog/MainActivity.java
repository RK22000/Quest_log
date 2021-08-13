package rkan.project.questlog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
                        quests) { //TODO this may cause a null pointer error
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


        /*
        questInput = findViewById(R.id.questInputText);
        importantBoard  = findViewById(R.id.importantQuestBoard);
        urgentBoard     = findViewById(R.id.urgentQuestBoard);

        questViewModel = new ViewModelProvider(this).get(QuestViewModel.class);

        QuestBoard.QuestCallback updateQuestCallback = new QuestBoard.QuestCallback() {
            @Override
            public void call(Quest quest) {
                questViewModel.updateQuest(quest);
            }
        };

        questViewModel.getImportantQuests().observe(this, new Observer<List<Quest>>() {
            @Override
            public void onChanged(List<Quest> quests) {
                importantBoard.submitQuests(quests, updateQuestCallback);
            }
        });
        questViewModel.getUrgentQuests().observe(this, new Observer<List<Quest>>() {
            @Override
            public void onChanged(List<Quest> quests) {
                urgentBoard.submitQuests(quests, updateQuestCallback);
            }
        });

        QuestBoard.QuestCallback deleteQuestCallback = new QuestBoard.QuestCallback() {
            @Override
            public void call(Quest quest) {
                questViewModel.deleteQuest(quest);
            }
        };

        importantBoard.setDeleteQuestCallback(deleteQuestCallback);
        urgentBoard.setDeleteQuestCallback(deleteQuestCallback);

        QuestBoard.QuestCallback addRequestCallback = new QuestBoard.QuestCallback() {
            @Override
            public void call(Quest quest) {
                quest.info = questInput.getText().toString();
                if (quest.info.equals("")) {
                    return;
                }
                questViewModel.insert(quest);
                questInput.setText("");
            }
        };
        importantBoard.setAddRequestCallback(addRequestCallback);
        urgentBoard.setAddRequestCallback(addRequestCallback);

         */


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