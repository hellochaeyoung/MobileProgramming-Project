package kr.ac.hansung.ume.Calendar.Schedule;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kr.ac.hansung.ume.R;
import kr.ac.hansung.ume.Calendar.Calendar.CalendarActivity;
import kr.ac.hansung.ume.View.HomeActivity;

public class EditScheduleButtonOnClickListener implements View.OnClickListener {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

    private String dbName = ((HomeActivity)HomeActivity.context).getDbName();

    private String date = ((ScheduleEditActivity)ScheduleEditActivity.context).getDate();

    private Schedule schedule;

    @Override
    public void onClick(View view){
        ScheduleEditActivity scheduleEditActivity;
        Intent intent;
        switch(view.getId()){
            case R.id.editButton:
                System.out.println("dbName " + dbName);
                System.out.println("date " + date);
                schedule = new Schedule(); // 일정의 내용을 저장할 객체 생성
                scheduleEditActivity = ((ScheduleEditActivity)ScheduleEditActivity.context); // ScheduleActivity

                // 사용자가 입력한 일정 내용을 Schedule 객체에 저장
                schedule.setName(scheduleEditActivity.getScheduleNameEditText().getText().toString());
                schedule.setContent(scheduleEditActivity.getScheduleContentEditText().getText().toString());
                schedule.setTime(scheduleEditActivity.getPickedTimeTextView().getText().toString());

                databaseReference.addValueEventListener(valueEventListener);

                System.out.println("=== Schedule Object ===" + " " + schedule.toString());
                scheduleEditActivity.getSchedules().add(schedule); // 배열 리스트에 일정 객체 추가


                //Intent recvIntent = getIntent();
                int changedPosition = scheduleEditActivity.getChangedPosition();


                intent = new Intent(ScheduleEditActivity.context, CalendarActivity.class);
                intent.putExtra("schedule", "ok");
                intent.putExtra("position", Integer.toString(changedPosition));
                ScheduleEditActivity.context.startActivity(intent);
                break;

            case R.id.closeButton:
                intent = new Intent(ScheduleEditActivity.context, CalendarActivity.class);
                ScheduleEditActivity.context.startActivity(intent);

                break;
        }
    }

    ValueEventListener valueEventListener  = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //Firebase에 캘린더 일정 데이터 저장
            if (dataSnapshot.child(dbName).hasChild("Calendar")){
                databaseReference.child(dbName).child("Calendar").child(date).child("name").setValue(schedule.getName());
                databaseReference.child(dbName).child("Calendar").child(date).child("content").setValue(schedule.getContent());
                databaseReference.child(dbName).child("Calendar").child(date).child("time").setValue(schedule.getTime());
            }
            else {
                databaseReference.child(dbName).child("Calendar").child(date).child("name").setValue(schedule.getName());
                databaseReference.child(dbName).child("Calendar").child(date).child("content").setValue(schedule.getContent());
                databaseReference.child(dbName).child("Calendar").child(date).child("time").setValue(schedule.getTime());
            }

            databaseReference.removeEventListener(valueEventListener);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
