package kr.ac.hansung.ume.Calendar.Schedule;

import android.app.TimePickerDialog;
import android.view.View;
import android.widget.TimePicker;

public class timePickButtonOnClickListener implements View.OnClickListener {

    private ScheduleEditActivity scheduleEditActivity = ((ScheduleEditActivity)ScheduleEditActivity.context);

    @Override
    public void onClick(View view){

        TimePickerDialog dialog =
                new TimePickerDialog(scheduleEditActivity.context,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                scheduleEditActivity.getPickedTimeTextView().setText(hourOfDay + " : " + minute);
                            }
                        }, // 값설정시 호출될 리스너 등록
                        4,19, false);

    }
}
