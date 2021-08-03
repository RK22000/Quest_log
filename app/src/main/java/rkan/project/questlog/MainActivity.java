package rkan.project.questlog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private QuestBoard importantBoard, urgentBoard;
    private EditText questInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questInput = findViewById(R.id.questInputText);
        questInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                View layout = findViewById(R.id.questButtonLayout);
                if (s.length() == 0) {
                    layout.setVisibility(View.GONE);
                } else {
                    layout.setVisibility(View.VISIBLE);
                }

            }
        });

        importantBoard  = findViewById(R.id.importantQuestBoard);
        urgentBoard     = findViewById(R.id.urgentQuestBoard);
    }

    public void addUrgentQuest(View view) {
        Quest quest = new Quest();
        quest.info = questInput.getText().toString();
        urgentBoard.addQuest(quest);
    }

    public void addImportantQuest(View view) {
        Quest quest = new Quest();
        quest.info = questInput.getText().toString();
        importantBoard.addQuest(quest);
    }
}