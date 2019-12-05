package kr.ac.hansung.ume.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import kr.ac.hansung.ume.R;

public class GridAdapter extends BaseAdapter {

        private final List<String> list;

        private final LayoutInflater inflater;

        private Calendar mCal;

        private Context calendarActivity;


        // Adapter 생성자 : 리스트 설정, Inflater 생성
        public GridAdapter(Context context, List<String> list) {
            this.calendarActivity = context; // Calendar Activity 의 Context
            this.list = list; // Calendar Activity 에서 초기화 dayList 넘겨받음
            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) { return position; }


        // GridView 의 Item 이 그려질 때 getView() 호출
        // 화면이 그려져야할 때 어댑터뷰로부터 호출된다.
        // 원본의 각 아이템들이 어댑터뷰에 어떻게 보일지 뷰를 그려서 반환하는 메서드
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            CalendarActivity.ViewHolder holder = null;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_calendar_gridview, parent, false);
                holder = CalendarActivity.getViewHolder();

                holder.tvItemGridView = (TextView) convertView.findViewById(R.id.tv_item_gridview);

                convertView.setTag(holder);
            } else {
                holder = (CalendarActivity.ViewHolder) convertView.getTag();
            }
            holder.tvItemGridView.setText("" + getItem(position));

            //해당 날짜 텍스트 컬러,배경 변경
            mCal = Calendar.getInstance();
            //오늘 day 가져옴
            Integer today = mCal.get(Calendar.DAY_OF_MONTH);
            String sToday = String.valueOf(today);
            if (sToday.equals(getItem(position))) { //오늘 day 텍스트 컬러 변경
                holder.tvItemGridView.setTextColor(calendarActivity.getResources().getColor(R.color.color_000000));
            }
            return convertView;
        }


}
