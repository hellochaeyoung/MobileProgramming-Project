package kr.ac.hansung.ume.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


import kr.ac.hansung.ume.R;

public class CalendarActivity extends AppCompatActivity {
    private TextView tvDate;

    private GridAdapter gridAdapter;

    private ArrayList<String> dayList;

    private GridView gridView;

    private Calendar mCal;

    private static ViewHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendar);


        viewHolder = new ViewHolder();

        tvDate = (TextView)findViewById(R.id.dateTextView);
        gridView = (GridView)findViewById(R.id.gridView);

        // 오늘 날짜 세
        long now = System.currentTimeMillis(); // 현 시간을 밀리초 단위로 받기
        final Date date = new Date(now); // 1970년 1월 1일 0시 0초 부터 msec를 1/1000초 단위로 하여 경과한 날짜와 시간을 저장

        // 시간 포멧 설정
        // Date() 클래스와 Calendar 클래스를
        // SimpleDateFormat 에 format()으로 포매팅 하면
        // 앞서 현재 날짜 시간으로 지정한 포맷으로 데이터를 변환
        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);

        // 년/월 형식으로 현재 날짜를 Text View 의 Text 로 지정환
        // yyyy/MM/dd
        tvDate.setText(curYearFormat.format(date) + "/" + curMonthFormat.format(date));

        //GridView 요일 표시를 위해 ArrayList에 요일 문자 데이터 저장
        dayList = new ArrayList<String>();
        dayList.add("일");
        dayList.add("월");
        dayList.add("화");
        dayList.add("수");
        dayList.add("목");
        dayList.add("금");
        dayList.add("토");

        mCal = Calendar.getInstance(); // 현재 날짜와 시간 정보를 가진 Calendar 객체를 생성

        /* 오늘 날을 기준으로 요일 구하고, List에 저장 */

        //이번달 1일 무슨요일인지 판단 mCal.set(Year,Month,Day)
        // currentTimeMillis() 를 통해 얻은 밀리초를 바탕으로
        // Date() 객체 생성하고 이 객체를 바탕으로 포맷을 지정해 날짜를 구했음 ('월' 이 +1 , -1 된 값이 아니다)
        // 따라서 Date 객체를 통해 얻은 Month 로 Calendar 의 날짜를 지정해주려면
        // 구한 값에서 -1 해 줘야 한다. (Calendar에서 나타내는 달은 == (현재 달 - 1))
        mCal.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date)) - 1, 1);
        int dayNum = mCal.get(Calendar.DAY_OF_WEEK);

        //1일 - 요일 매칭 시키기 위해 공백 add
        for (int i = 1; i < dayNum; i++) {
            dayList.add("");
        }
        setCalendarDate(mCal.get(Calendar.MONTH) + 1);

        gridAdapter = new GridAdapter(getApplicationContext(), dayList);
        gridView.setAdapter(gridAdapter);

    }

    public static ViewHolder getViewHolder() { return viewHolder; }

    private void setCalendarDate(int month) {
        // Calendar의 Month 를 지정
        mCal.set(Calendar.MONTH, month - 1);

        // 요일 정보를 배열 리스트에 추가
        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dayList.add("" + (i + 1));
        }

    }


    public class ViewHolder {
        TextView tvItemGridView;
    }

}

