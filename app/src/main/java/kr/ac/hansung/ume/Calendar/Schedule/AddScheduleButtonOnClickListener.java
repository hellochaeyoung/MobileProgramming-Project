package kr.ac.hansung.ume.Calendar.Schedule;

import android.content.Intent;
import android.view.View;

import kr.ac.hansung.ume.Calendar.Calendar.CalendarActivity;
import kr.ac.hansung.ume.R;

public class AddScheduleButtonOnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View view){

        switch(view.getId()){
            case R.id.scheduleAddButton:
                Intent intent = new Intent(CalendarActivity.context, ScheduleEditActivity.class);
                CalendarActivity.context.startActivity(intent);
        }
    }
}
