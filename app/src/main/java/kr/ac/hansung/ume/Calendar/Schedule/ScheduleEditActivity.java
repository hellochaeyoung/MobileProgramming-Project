package kr.ac.hansung.ume.Calendar.Schedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kr.ac.hansung.ume.R;
import kr.ac.hansung.ume.View.HomeActivity;

public class ScheduleEditActivity extends AppCompatActivity {

    private TextView dateTextView;

    private EditText scheduleNameEditText;

    private EditText scheduleContentEditText;

    private TextView pickedTimeTextView;

    private Button editButton;

    private Button closeButton;

    public static Context context;

    private ArrayList<Schedule> schedules = new ArrayList<Schedule>();

    private String date;

    private String dbName = ((HomeActivity)HomeActivity.context).getDbName();

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private String name;
    private String content;
    private String time;

    private int changedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_edit);

        context = this;

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        dateTextView = (TextView)findViewById(R.id.dateTextView);
        scheduleNameEditText = (EditText)findViewById(R.id.scheduleNameEditText);
        scheduleContentEditText = (EditText)findViewById(R.id.scheduleContentEditText);
        pickedTimeTextView = (TextView)findViewById(R.id.pickedTimeTextView);
        editButton = (Button)findViewById(R.id.editButton);
        closeButton = (Button)findViewById(R.id.closeButton);


        Intent intent = getIntent();

        date = intent.getExtras().getString("Date");
        //System.out.println("in Edit : " + date);
        dateTextView.setText(date);

        changedPosition = intent.getExtras().getInt("Position");

        editButton.setOnClickListener(new EditScheduleButtonOnClickListener());
        closeButton.setOnClickListener(new EditScheduleButtonOnClickListener());

        databaseReference.addValueEventListener(valueEventListener);

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.child(dbName).hasChild("Calendar")) {
                if (dataSnapshot.child(dbName).child("Calendar").hasChild(date)){
                    name = dataSnapshot.child(dbName).child("Calendar").child(date).child("name").getValue().toString();
                    content = dataSnapshot.child(dbName).child("Calendar").child(date).child("content").getValue().toString();
                    time = dataSnapshot.child(dbName).child("Calendar").child(date).child("time").getValue().toString();

                    scheduleNameEditText.setText(name);
                    scheduleContentEditText.setText(content);
                    pickedTimeTextView.setText(time);
                }
            }
            databaseReference.removeEventListener(valueEventListener);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };



    public EditText getScheduleNameEditText() { return scheduleNameEditText; }

    public EditText getScheduleContentEditText() { return scheduleContentEditText; }

    public TextView getPickedTimeTextView() { return pickedTimeTextView; }

    public ArrayList<Schedule> getSchedules() { return schedules; }

    public String getDate() { return date;}

    public int getChangedPosition() { return changedPosition; }
}
