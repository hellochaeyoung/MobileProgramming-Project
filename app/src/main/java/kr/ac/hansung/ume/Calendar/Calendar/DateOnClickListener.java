package kr.ac.hansung.ume.Calendar.Calendar;

import android.content.Intent;
import android.view.View;

import kr.ac.hansung.ume.Calendar.Schedule.ScheduleActivity;
import kr.ac.hansung.ume.R;

public class DateOnClickListener implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.tv_item_gridview:
                Intent intent = new Intent(CalendarActivity.context, ScheduleActivity.class);
                CalendarActivity.context.startActivity(intent);
        }
    }
}
