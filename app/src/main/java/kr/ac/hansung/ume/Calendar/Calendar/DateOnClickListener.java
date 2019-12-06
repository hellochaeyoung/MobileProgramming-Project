package kr.ac.hansung.ume.Calendar.Calendar;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

import kr.ac.hansung.ume.Calendar.Schedule.ScheduleEditActivity;
import kr.ac.hansung.ume.R;

public class DateOnClickListener implements View.OnClickListener {

    private CalendarActivity calendarActivity = ((CalendarActivity)CalendarActivity.context);

    private int position;

    public DateOnClickListener(int position){
        this.position = position;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.tv_item_gridview:
                TextView gridViewItem = (TextView)calendarActivity.findViewById(R.id.tv_item_gridview);
                Intent intent = new Intent(CalendarActivity.context, ScheduleEditActivity.class);
                intent.putExtra("Date", calendarActivity.getmCal().get(Calendar.YEAR) + " " + position);
                //gridViewItem.setBackgroundColor();
                CalendarActivity.context.startActivity(intent);
        }
    }
}
