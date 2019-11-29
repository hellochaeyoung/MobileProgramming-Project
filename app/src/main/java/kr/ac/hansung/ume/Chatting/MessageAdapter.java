package kr.ac.hansung.ume.Chatting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kr.ac.hansung.ume.R;


public class MessageAdapter extends BaseAdapter {

    private ArrayList<String> arrayList;
    private Context context;

    public MessageAdapter(Context context, ArrayList<String> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.activity_messageadapter, parent, false);
        }

        TextView textView = (TextView)convertView.findViewById(R.id.messageTextView);
        textView.setTextSize(18);
        textView.setText(arrayList.get(position));
        textView.setBackgroundResource(R.drawable.message);
        return convertView;
    }
}
