package kr.ac.hansung.ume.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;
import kr.ac.hansung.ume.Chatting.ChatMessage;
import kr.ac.hansung.ume.R;

public class BoardActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;
    private ImageView imageView;

    private Button homeButton;
    private Button boardButton;
    private Button chatButton;
    private Button albumButton;
    private Button calendarButton;
    private HomeActivity homeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        setButton();
        setListener();

        homeActivity=(HomeActivity)HomeActivity.context;


        imageView = findViewById(R.id.image);

        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }

    View.OnClickListener ChatListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent chatIntent=new Intent(BoardActivity.this, kr.ac.hansung.ume.Chatting.ChatMessage.class);
            chatIntent.putExtra("dbArray",homeActivity.getMessageArr());
            startActivity(chatIntent);
            finish();
        }
    };
    View.OnClickListener HomeListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    public void setButton(){
        // Find Button
        homeButton = (Button)findViewById(R.id.homeButton);
        boardButton = (Button)findViewById(R.id.boardButton);
        chatButton = (Button)findViewById(R.id.chatButton);
        albumButton = (Button)findViewById(R.id.albumButton);
        calendarButton = (Button)findViewById(R.id.calendarButton);
    }

    public void setListener(){
        chatButton.setOnClickListener(ChatListener);
        homeButton.setOnClickListener(HomeListener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                try{
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    imageView.setImageBitmap(img);
                }catch(Exception e)
                {

                }
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
}