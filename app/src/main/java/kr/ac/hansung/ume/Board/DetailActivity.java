package kr.ac.hansung.ume.Board;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import kr.ac.hansung.ume.R;

public class DetailActivity extends AppCompatActivity {
    private ImageView image;
    private TextView title;
    private TextView content;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        Intent intent=getIntent();



        byte[] bytes=intent.getByteArrayExtra("image");
        image.setImageBitmap(BitmapFactory.decodeByteArray(bytes,0,bytes.length));//byte배열을 다시 bitmap으로 바꿔 imageView에 등록
        title.setText(intent.getStringExtra("title"));
        content.setText(intent.getStringExtra("content"));


    }
    public void init(){
        image=findViewById(R.id.Image);
        title=findViewById(R.id.Title);
        content=findViewById(R.id.Content);
    }
}
