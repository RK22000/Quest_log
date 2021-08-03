package rkan.project.questlog;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QuestBoard extends RelativeLayout {
    private String title;
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
    }



}
