package kr.ac.hansung.ume.Calendar.Schedule;

import android.content.Intent;
import android.view.View;

import kr.ac.hansung.ume.R;
import kr.ac.hansung.ume.R;

public class EditScheduleButtonOnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View view){
        ScheduleEditActivity scheduleEditActivity;
        ScheduleActivity scheduleActivity;


        switch(view.getId()){
            case R.id.editButton:
                Schedule schedule = new Schedule(); // 일정의 내용을 저장할 객체 생성
                scheduleEditActivity = ((ScheduleEditActivity)ScheduleEditActivity.context); // ScheduleEditActivity
                scheduleActivity = ((ScheduleActivity)ScheduleActivity.context); // ScheduleActivity

                schedule.setName(scheduleEditActivity.getScheduleNameEditText().getText().toString());
                schedule.setContent(scheduleEditActivity.getScheduleContentEditText().getText().toString());
                schedule.setTime(scheduleEditActivity.getPickedTimeTextView().getText().toString());

                System.out.println("=== Schedule Object ===" + " " + schedule.toString());
                scheduleActivity.getSchedules().add(schedule); // 배열 리스트에 일정 객체 추가

                Intent intent = new Intent(ScheduleEditActivity.context, ScheduleActivity.class);
                ScheduleEditActivity.context.startActivity(intent);
        }
    }
}
