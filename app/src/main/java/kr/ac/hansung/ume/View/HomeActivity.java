package kr.ac.hansung.ume.View;



import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import kr.ac.hansung.ume.Board.BoardActivity;
import kr.ac.hansung.ume.Chatting.ChatMessage;
import kr.ac.hansung.ume.Client.MQTTClient;
import kr.ac.hansung.ume.R;

public class HomeActivity extends AppCompatActivity {
    private MQTTClient client;
    public static Context context;
    private String newtopic;
    private String topic_message;
    private ArrayList<String> messageArr;
    private boolean isMessageChange=false;

    private Button user1HomeProfileButton;
    private Button user2HomeProfileButton;
    private TextView user1HomeProfileText;
    private TextView user2HomeProfileText;

    //private ImageView addimage;

    private Button homeButton;
    private Button boardButton;
    private Button chatButton;
    private Button albumButton;
    private Button calendarButton;

    public static Context homeContext;

    private String id;
    private String partnerID;
    private String dbName;

    private String myName;
    private String partnerName;

    private String myImageString;
    private String partnerImageString;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        homeContext=this;
        setButton();
        setListener();
        setTextView();

        context=this;
        newtopic="hi";
        topic_message = newtopic + "_message";

        client=new MQTTClient();
        client.setCallback();

        client.subscribe(newtopic);
        client.subscribe(topic_message);


        messageArr=new ArrayList<>();

        id = getIntent().getStringExtra("id");
        partnerID = getIntent().getStringExtra("partnerID");

        myName = getIntent().getStringExtra("myName");
        partnerName = getIntent().getStringExtra("partnerName");
        dbName = getIntent().getStringExtra("dbName");

        user1HomeProfileText.setText(myName); // 프로필에 이름 설정
        user2HomeProfileText.setText(partnerName); // 파트너 프로필 이름 설정

        databaseReference.addValueEventListener(valueEventListener);

        user1HomeProfileButton.setOnClickListener(imageListener);
        //user2HomeProfileButton.setOnClickListener(imageListener);


    }

    //프로필 버튼 누르면 갤러리 실행되게
    View.OnClickListener imageListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select Picture"),1);

        }
    };
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==1){
            if(resultCode==RESULT_OK){
                try {
                    System.out.println("here?");
                    Uri uri=data.getData();
                    Bitmap img= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    Drawable drawable = new BitmapDrawable(getResources(), img);
                    user1HomeProfileButton.setBackground(drawable); // 갤러리에서 가져온 이미지로 버튼 배경 설정
                    databaseReference.child(dbName).child(id).child("image").setValue(getString(img)); // 이미지 -> String으로 변환해 데이터베이스에 저장

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("사진불러오기 실패");
                }
            }
        }
    }

    public String getString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imageBytes=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes,Base64.NO_WRAP);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if (dataSnapshot.child(dbName).child(id).child("image").getValue() != null) {
                myImageString = dataSnapshot.child(dbName).child(id).child("image").getValue().toString();
                byte[] decodeByteArray = Base64.decode(myImageString, Base64.NO_WRAP);
                Bitmap decodeBitmap = BitmapFactory.decodeByteArray(decodeByteArray,0,decodeByteArray.length); // String -> Bitmap 변환
                user1HomeProfileButton.setBackground(new BitmapDrawable(getResources(), decodeBitmap)); // 버튼 배경으로 설정
            }
            else {
                user1HomeProfileButton.setBackgroundColor(Color.GRAY);
            }

            if (dataSnapshot.child(dbName).child(partnerID).child("image").getValue() != null){
                partnerImageString = dataSnapshot.child(dbName).child(partnerID).child("image").getValue().toString();
                byte[] decodeByteArray = Base64.decode(partnerImageString, Base64.NO_WRAP);
                Bitmap decodeBitmap = BitmapFactory.decodeByteArray(decodeByteArray,0,decodeByteArray.length); // String -> Bitmap 변환
                user2HomeProfileButton.setBackground(new BitmapDrawable(getResources(), decodeBitmap)); // 버튼 배경으로 설정
            }
            else {
                user2HomeProfileButton.setBackgroundColor(Color.GRAY);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
     //   inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }
    public void setListener(){
        chatButton.setOnClickListener(ChatListener);
        boardButton.setOnClickListener(BoardListener);

    }

    View.OnClickListener ChatListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent chatIntent=new Intent(HomeActivity.this, ChatMessage.class);
            chatIntent.putExtra("dbArray",messageArr);
            startActivity(chatIntent);
        }
    };
    View.OnClickListener BoardListener= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent boardIntent=new Intent(HomeActivity.this, BoardActivity.class);
            startActivity(boardIntent);
        }
    };

    public void setButton(){
        // Find Button
        homeButton = (Button)findViewById(R.id.homeButton);
        boardButton = (Button)findViewById(R.id.boardButton);
        chatButton = (Button)findViewById(R.id.chatButton);
        albumButton = (Button)findViewById(R.id.albumButton);
        calendarButton = (Button)findViewById(R.id.calendarButton);

        user1HomeProfileButton = findViewById(R.id.user1HomeProfileButton);
        user2HomeProfileButton = findViewById(R.id.user2HomeProfileButton);

    }

    public void setTextView() {

        user1HomeProfileText = findViewById(R.id.user1HomeProfileText);
        user2HomeProfileText = findViewById(R.id.user2HomeProfileText);
    }

    public MQTTClient getMQTTClient() {
        return client;
    }

    public String getNewtopic() {
        return newtopic;
    }

    public String getTopic_message() {
        return topic_message;
    }

    public ArrayList<String> getMessageArr() {
        return messageArr;
    }

    public void setMessageArr(ArrayList<String> messageArr) {
        this.messageArr = messageArr;
    }

    public boolean isMessageChange() {
        return isMessageChange;
    }

    public void setMessageChange(boolean messageChange) {
        isMessageChange = messageChange;
    }
}
