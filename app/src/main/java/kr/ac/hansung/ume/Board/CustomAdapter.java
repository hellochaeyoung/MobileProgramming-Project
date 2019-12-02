package kr.ac.hansung.ume.Board;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import kr.ac.hansung.ume.R;


public class CustomAdapter extends BaseAdapter {
    private ArrayList<ItemDetail> listCustom=new ArrayList<>();
    private ItemDetail last;
    //LayoutInflater inflater;
    //Context context;
    //int layout;

   /* public CustomAdapter(Context context, int layout){
        this.context=context;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout=layout;
    }*/
    @Override
    public int getCount(){
        return listCustom.size();
    }

    @Override
    public Object getItem(int position){
        return listCustom.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom,null,false);
        }
        ImageView itemImage=(ImageView)convertView.findViewById(R.id.imageView);
        itemImage.setImageBitmap(listCustom.get(position).getItmeImage());

        TextView itemTitle=(TextView)convertView.findViewById(R.id.textTitle);
        itemTitle.setText(listCustom.get(position).getItemTitle());

        TextView itemContent=(TextView)convertView.findViewById(R.id.textContent);
        itemContent.setText(listCustom.get(position).getItemcontent());

        return convertView;
    }


    public void addItem(ItemDetail item){
        listCustom.add(item);
    }

    public ItemDetail getLast(){
        return listCustom.get(listCustom.size()-1);
    }
}
