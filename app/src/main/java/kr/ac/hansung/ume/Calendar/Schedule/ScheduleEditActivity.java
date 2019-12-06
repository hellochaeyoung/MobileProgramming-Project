package kr.ac.hansung.ume.Calendar.Schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import kr.ac.hansung.ume.R;

public class ScheduleEditActivity extends AppCompatActivity {

    private TextView dateTextView;

    private EditText scheduleNameEditText;

    private EditText scheduleContentEditText;

    private TextView pickedTimeTextView;

    private Button editButton;

    private Button closeButton;

    public static Context context;

    private ArrayList<Schedule> schedules = new ArrayList<Schedule>();

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
        closeButton = (Button)findViewById(R.id.closeButton);


        editButton.setOnClickListener(new EditScheduleButtonOnClickListener());
        closeButton.setOnClickListener(new EditScheduleButtonOnClickListener());

        Intent intent = getIntent();

        String date = intent.getExtras().getString("Date");
        dateTextView.setText(date);


    }



    public EditText getScheduleNameEditText() { return scheduleNameEditText; }

    public EditText getScheduleContentEditText() { return scheduleContentEditText; }

    public TextView getPickedTimeTextView() { return pickedTimeTextView; }

    public ArrayList<Schedule> getSchedules() { return schedules; }
}
