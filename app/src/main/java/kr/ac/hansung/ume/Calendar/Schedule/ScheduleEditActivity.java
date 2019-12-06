package kr.ac.hansung.ume.Calendar.Schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import kr.ac.hansung.ume.R;

public class ScheduleEditActivity extends AppCompatActivity {

    private TextView dateTextView;

    private EditText scheduleNameEditText;

    private EditText scheduleContentEditText;

    private TextView pickedTimeTextView;

    private Button editButton;

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_edit);

        context = this;

        dateTextView = (TextView)findViewById(R.id.dateTextView);
        scheduleNameEditText = (EditText)findViewById(R.id.scheduleNameEditText);
        scheduleContentEditText = (EditText)findViewById(R.id.scheduleContentEditText);
        pickedTimeTextView = (TextView)findViewById(R.id.pickedTimeTextView);
        editButton = (Button)findViewById(R.id.editButton);

        editButton.setOnClickListener(new EditScheduleButtonOnClickListener());
    }



    public TextView getDateTextView() { return dateTextView; }

    public EditText getScheduleNameEditText() { return scheduleNameEditText; }

    public EditText getScheduleContentEditText() { return scheduleContentEditText; }

    public TextView getPickedTimeTextView() { return pickedTimeTextView; }

    public Button getEditButton() { return editButton; }
}
