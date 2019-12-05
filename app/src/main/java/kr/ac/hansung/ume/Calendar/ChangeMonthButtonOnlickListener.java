package kr.ac.hansung.ume.Calendar;

import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import kr.ac.hansung.ume.R;

class ChangeMonthButtonOnClickListener implements Button.OnClickListener {

    private CalendarActivity calendarActivity = (CalendarActivity)CalendarActivity.context;
    private GridView gridView = calendarActivity.getGridView();
    private GridAdapter gridAdapter = calendarActivity.getGridAdapter();

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.previousMonthButton:
                calendarActivity.changeToPreMonth();
                calendarActivity.makeMonthDate();
                gridAdapter.notifyDataSetChanged();
                gridView.setAdapter(gridAdapter);
                break;
            case R.id.nextMonthButton:
                calendarActivity.changeToNextMonth();
                calendarActivity.makeMonthDate();
                gridAdapter.notifyDataSetChanged();
                gridView.setAdapter(gridAdapter);
                break;
        }

    }
}
