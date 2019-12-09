package kr.ac.hansung.ume.Board;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import kr.ac.hansung.ume.Board.CustomAdapter;
import kr.ac.hansung.ume.Chatting.ChatMessage;
import kr.ac.hansung.ume.R;
import kr.ac.hansung.ume.View.HomeActivity;

public class BoardActivity extends AppCompatActivity {
    private ListView listView;
    private CustomAdapter adapter;
    private boolean isNew;//글이 추가되는 순간을 알려주기 위한 flag
    public static Context boardContext;//외부에서 Class에 설정된 멤버변수를 직접 제어하기 위한 context

    private String newTitle;
    private String newContent;
    private Bitmap newImage;//새로운 글이 들어오게 되면 이에 대한 정보들을 담을 변수 선언

    private Button homeButton;
    private Button boardButton;
    private Button chatButton;
    private Button albumButton;
    private Button calendarButton;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
   // private ItemDetail lastItem;//추가된 Item
    private String dbName;
    private int index=0;
    private int lastIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        boardContext=this;

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();


        isNew=false;
        newTitle="";
        newContent="";
        newImage=BitmapFactory.decodeFile("C:\\Android\\Board\\app\\src\\main\\res\\drawable-v24\\peng.jpg");
        //초기화
        adapter=new CustomAdapter();

        listView=findViewById(R.id.listView);

        dbName=((HomeActivity)HomeActivity.homeContext).getDbName();
        //setData();
        databaseReference.addValueEventListener(valueEventListener);
//        System.out.println(adapter.getItem(1));
        setButton();
        setListener();
       // listView.setAdapter(adapter);

        listView.setOnItemClickListener(itemListener);
     //   lastItem=adapter.getLast();





        //System.out.println(lastItem.getItemTitle());
        listThread thread=new listThread();
        thread.start();





    }


    Handler listHandler =new Handler(){//Thread클래스는 MainThread가 아니여서 UI를 건들 수 없기 때문에 Handler로 관리
        public void handleMessage(Message msg){
             adapter=new CustomAdapter();
            listView.setAdapter(adapter);
            //lastItem=adapter.getLast();
           // System.out.println(lastItem.getItemTitle()+"ch");
        }
    };

    class listThread extends Thread{//계속 반복문을 돌며 새로운 글이 추가될 때 마다 array를 바꿔줌
        public void run(){
            while(true){

                if (isNew) {
                    ItemDetail newItem=new ItemDetail();
                    Bitmap bitmap= newImage;
                    newItem.setItmeImage(bitmap);
                    newItem.setItemTitle(newTitle);
                    newItem.setItemcontext(newContent);
                    adapter.addItem(newItem);
//                    listView.setAdapter(adapter);

                    // listView.setAdapter(adapter);
                    Message msg=listHandler.obtainMessage();
                    listHandler.sendMessage(msg);
                    isNew=false;
                }

            }
        }
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent boardWriteIntent=new Intent(BoardActivity.this, NewBoard.class);
        startActivity(boardWriteIntent);
        return true;
    }

    AdapterView.OnItemClickListener itemListener=new AdapterView.OnItemClickListener(){//하나의 item을 클릭하면 그 item에 대한 정보를 포함하여 DetailAcivity로 넘어감감
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            Bitmap image=((ItemDetail)adapter.getItem(position)).getItmeImage();
            String title=((ItemDetail)adapter.getItem(position)).getItemTitle();
            String content=((ItemDetail)adapter.getItem(position)).getItemcontent();

            ByteArrayOutputStream stream=new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG,100,stream);
            byte[] bytes=stream.toByteArray();
            Intent intent=new Intent(BoardActivity.this, DetailActivity.class);//Bitmap은 그 형태 그대로 putExtra가 불가능하므로 byte배열로 변환

            intent.putExtra("image",bytes);
            intent.putExtra("title",title);
            intent.putExtra("content",content);

            startActivity(intent);
        }

    };

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.child(dbName).hasChild("board")) {
                lastIndex = (Integer.parseInt((dataSnapshot.child(dbName).child("board").child("index")).getValue().toString()));
                for (int i = 0; i <= lastIndex; i++) {
                    String loopIndex = String.valueOf(i);
                    String title = dataSnapshot.child(dbName).child("board").child(loopIndex).child("title").getValue().toString();
                    String content = dataSnapshot.child(dbName).child("board").child(loopIndex).child("content").getValue().toString();
                    String bitmapString = dataSnapshot.child(dbName).child("board").child(loopIndex).child("image").getValue().toString();
                    System.out.println("dbtitle"+title);
                    byte[] decodeByteArray = Base64.decode(bitmapString, Base64.NO_WRAP);
                    Bitmap image = BitmapFactory.decodeByteArray(decodeByteArray, 0, decodeByteArray.length); // String -> Bitmap 변환

                    ItemDetail newItem = new ItemDetail();
                    newItem.setItemTitle(title);
                    newItem.setItmeImage(image);
                    newItem.setItemcontext(content);

                    adapter.addItem(newItem);
                    System.out.println(newItem.getItemcontent()+"contentlater");
                    System.out.println(adapter.getItem(0));
                }
                System.out.println(adapter.getItem(1));
                lastIndex++;

            }
            listView.setAdapter(adapter);
            databaseReference.removeEventListener(valueEventListener);

        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    /*private void setData(){//초기설정
        ItemDetail peng=new ItemDetail();
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.peng);
        peng.setItmeImage(bitmap);
        peng.setItemTitle("Hi Pengsoo");
        peng.setItemcontext("PengPeng!!!!!!!!!!");


        adapter.addItem(peng);
    }*/
    public void setListener(){
        chatButton.setOnClickListener(ChatListener);
        boardButton.setOnClickListener(HomeListener);

    }
    View.OnClickListener ChatListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent chatIntent=new Intent(BoardActivity.this, ChatMessage.class);
            HomeActivity homeContext=(HomeActivity)HomeActivity.homeContext;
            chatIntent.putExtra("dbArray",homeContext.getMessageArr());
            startActivity(chatIntent);
        }
    };
    View.OnClickListener HomeListener= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent boardIntent=new Intent(BoardActivity.this, HomeActivity.class);
            startActivity(boardIntent);
        }
    };

    public void setButton(){
        // Find Button
        homeButton = (Button)findViewById(R.id.homeButton);
        boardButton = (Button)findViewById(R.id.boardButton);
        chatButton = (Button)findViewById(R.id.chatButton);
        calendarButton = (Button)findViewById(R.id.calendarButton);
    }

    public int getIndex() {
        return lastIndex;
    }

    public void setIndex(int index) {
        this.lastIndex = index;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    public void setNewContent(String newContent) {
        this.newContent = newContent;
    }

    public void setNewImage(Bitmap newImage) {
        this.newImage = newImage;
    }
}
