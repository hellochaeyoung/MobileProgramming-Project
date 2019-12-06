package kr.ac.hansung.ume.Calendar.Schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

import kr.ac.hansung.ume.R;

public class ScheduleActivity extends AppCompatActivity {

    private Button scheduleAddButton;

    private ArrayList<Schedule> schedules;

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        context = this;

        schedules = new ArrayList<Schedule>();

        scheduleAddButton = (Button)findViewById(R.id.scheduleAddButton);
        scheduleAddButton.setOnClickListener(new AddScheduleButtonOnClickListener());

    }

    public ArrayList<Schedule> getSchedules(){ return schedules; }
}
