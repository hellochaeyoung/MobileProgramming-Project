package kr.ac.hansung.ume.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;



import kr.ac.hansung.ume.R;

public class CalendarActivity extends AppCompatActivity {
    public static Context context;

    private TextView tvDate;

    private GridAdapter gridAdapter;

    private ArrayList<Integer> dayList;

    private GridView gridView;

    private Calendar mCal;

    private HashMap<String, ArrayList<String>> savedData; // 데이터베이스 연동 시 필요한 데이터

    private static ViewHolder viewHolder;

    private Button preMonthButton;
    private Button nextMonthButton;


    public static final int DAYS_OF_WEEK = 7;
    public static final int LOW_OF_CALENDAR = 6;

    // 달력에 다음날 첫 날을 표시하기 위해
    protected int nextMonthHeadOffset = 0;

    // 달력에 이전달 마지막 날을 표시하기 위해
    protected int preMonthTailOffset = 0;

    protected int currentMonthMaxDate = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendar);

        context = this;

        viewHolder = new ViewHolder();

        tvDate = (TextView)findViewById(R.id.dateTextView);
        gridView = (GridView)findViewById(R.id.gridView);

        preMonthButton = (Button)findViewById(R.id.previousMonthButton);
        nextMonthButton = (Button)findViewById(R.id.nextMonthButton);

        dayList = new ArrayList<Integer>();
        mCal = Calendar.getInstance();

        makeMonthDate();

        gridAdapter = new GridAdapter(getApplicationContext(), dayList);
        gridView.setAdapter(gridAdapter);

        // GridView 와 GridAdapter 생성 후 Listener 생성
        View.OnClickListener changeMonthButtonOnClickListener = new ChangeMonthButtonOnClickListener();
        preMonthButton.setOnClickListener(changeMonthButtonOnClickListener);
        nextMonthButton.setOnClickListener(changeMonthButtonOnClickListener);

    }

    /*
    private void setCalendarDate(int month) {
        // Calendar의 Month 를 지정
        mCal.set(Calendar.MONTH, month - 1);

        // 요일 정보를 배열 리스트에 추가
        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            //dayList.add("" + (i + 1));
            dayList.add(i+1);
        }

    }
    */

    protected void changeToPreMonth(){
        // 현재 날이 1월 달일 경우 년도를 이전 년도로 변경시켜줘야만 한다.
        if(mCal.get(Calendar.MONTH) == 0){
            // 현재 날짜의 년도 - 1 --> 이전 년도
            mCal.set(Calendar.YEAR, mCal.get(Calendar.YEAR) - 1);
            // 12월로 설정
            mCal.set(Calendar.MONTH, mCal.DECEMBER);
        }else{
            // 현재 날짜가 1월이 아니었다면
            // 현재 월에서 - 1
            mCal.set(Calendar.MONTH, mCal.get(Calendar.MONTH) - 1);
        }
    }

    protected void changeToNextMonth(){
        // 현재 날짜가 12월일 경우 년도를 다음 년도로 변경시켜줘야만 한다.
        if(mCal.get(Calendar.MONTH) == Calendar.DECEMBER) {
            // 현재 년도 + 1 --> 다음 년도
            mCal.set(Calendar.YEAR, mCal.get(Calendar.YEAR) + 1);
            // MONTH = 0 은 1월
            mCal.set(Calendar.MONTH, 0);
        }else{
            // 12월이 아닌 경우 년도를 변경할 필요 X
            // 따라서 현재 월 + 1
            mCal.set(Calendar.MONTH, mCal.get(Calendar.MONTH) + 1);
        }
    }

    protected void makeMonthDate(){
            dayList.clear();

            // 현재 월의 날짜를 가져온다
            mCal.set(Calendar.DATE, 1);

            // 현재 달의 마지막 날짜(일) 을 구하기
            currentMonthMaxDate = mCal.getActualMaximum(Calendar.DAY_OF_MONTH);

            /* 현재 달력에 이전달 표시 */
            // 현재 요일 - 1 -> 이전 달의 마지막 요일
            preMonthTailOffset = mCal.get(Calendar.DAY_OF_WEEK) - 1;
            makePreMonthTail((Calendar)mCal.clone());

            /* 현재 달력 표시 */
            makeCurrentMonth(mCal);

            /* 현재 달력에 다음달 표시 */
            // 달력에 최대로 날짜 표시 할 수 있는 날을 구하고 이전달 날짜 표시한것과 이번달 날짜 표시한 것 만큼 빼주기
            nextMonthHeadOffset = LOW_OF_CALENDAR * DAYS_OF_WEEK - (preMonthTailOffset + currentMonthMaxDate);
            makeNextMonthHead();

            tvDate.setText(mCal.get(Calendar.YEAR) + "  " + (mCal.get(Calendar.MONTH) + 1));

    }

    private void makePreMonthTail(Calendar mCal){
        // 현재 mCal 객체의 월을 이전 달로 설정
        mCal.set(Calendar.MONTH, mCal.get(Calendar.MONTH) - 1);

        // 이전 달의 마지막 날짜
        int maxDate = mCal.getActualMaximum(Calendar.DATE);
        // 이전 달의 마지막 날짜 - 이전 달의 마지막 요일 (31 - 5 = 26)
        int maxOffsetDate = maxDate - preMonthTailOffset;

        for(int i=0; i < preMonthTailOffset; i++)
            dayList.add(++maxOffsetDate);
    }

    private void makeCurrentMonth(Calendar mCal){
        for(int i=0; i<mCal.getActualMaximum(Calendar.DATE); i++)
            dayList.add(i+1);
    }

    private void makeNextMonthHead(){
        int date = 1;

        for(int i=0; i < nextMonthHeadOffset; i++)
            dayList.add(date++);
    }


    protected static ViewHolder getViewHolder() { return viewHolder; }

    protected GridAdapter getGridAdapter() { return gridAdapter; }

    protected GridView getGridView() { return gridView; }

    protected ArrayList<Integer> getDayList() { return dayList; }

    //protected TextView getTvDate() { return tvDate; }


    // 클릭 시 액티비티 변경 리스너 생성
    public class ViewHolder {
        TextView tvItemGridView;
    }

}

