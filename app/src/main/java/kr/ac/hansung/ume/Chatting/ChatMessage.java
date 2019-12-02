package kr.ac.hansung.ume.Chatting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
//import android.support.annotation.NonNull;
//import android.support.v7.app.ActionBar;

//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import kr.ac.hansung.ume.Client.MQTTClient;
import kr.ac.hansung.ume.Board.BoardActivity;
import kr.ac.hansung.ume.View.HomeActivity;
import kr.ac.hansung.ume.R;


public class ChatMessage extends AppCompatActivity {
    EditText editText;
    Button sentBtn;
    MQTTClient client;
    ListView listView;

    HomeActivity homeActivity;
    private String topic = "painting";
    private ArrayList<String> chatArr;
    private MessageAdapter arrayAdapter;
    ArrayList<String> arrivedArray;
    //private GestureDetector gestureDetector;
    public static Context context;

    private String topic_message;
    private String newtopic;

    private Button homeButton;
    private Button boardButton;
    private Button chatButton;
    private Button albumButton;
    private Button calendarButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setButton();
        setListener();
        homeActivity=(HomeActivity) HomeActivity.context;
        Intent intent = getIntent();
        System.out.println("Intent"+intent);
        if( intent.getExtras().getStringArrayList("dbArray")!=null)
            arrivedArray = intent.getExtras().getStringArrayList("dbArray");


        chatArr = new ArrayList<>(arrivedArray);


        context = this;
        client = homeActivity.getMQTTClient();
        System.out.println("Chatmessage arr" + chatArr);

        // client.setCallback();
        editText = findViewById(R.id.message);
        listView = findViewById(R.id.listView);
        sentBtn = findViewById(R.id.sendBtn);
        sentBtn.setOnClickListener(onClickListener);

        newtopic = homeActivity.getNewtopic();

        arrayAdapter = new MessageAdapter(ChatMessage.this, chatArr);
        //arrayAdapter = new ArrayAdapter<String>(ChatMessage.this, android.R.layout.simple_list_item_1, chatArr);



        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        //setListViewHeightBasedOnChildren(listView);
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);


        topic_message = homeActivity.getTopic_message();
        System.out.println(topic_message);



        MessageThread messageThread=new MessageThread();
        messageThread.start();

        //   messageDatabaseRef.child(newtopic).addValueEventListener(messageListener);


    }
    Handler messageHandler=new Handler(){
        public void handleMessage(Message msg){
            arrayAdapter = new MessageAdapter(ChatMessage.this, chatArr);
            listView.setAdapter(arrayAdapter);

        }
    } ;
    class MessageThread extends Thread{
        public void run(){

            while(true){
                if(homeActivity.isMessageChange()){
                    System.out.println("isMessageChange?");
                    chatArr=homeActivity.getMessageArr();
                    System.out.println("chatArr in getMessage"+chatArr);
                    Message msg = messageHandler.obtainMessage();
                    messageHandler.sendMessage(msg);
                    homeActivity.setMessageChange(false);
                }
            }
        }
    }




    View.OnClickListener BoardListener= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent boardIntent=new Intent(ChatMessage.this, BoardActivity.class);
            startActivity(boardIntent);
            finish();
        }
    };
    View.OnClickListener HomeListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };



    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String message =editText.getText().toString();
            byte[] bytes = message.getBytes();
            client.publish(topic_message, bytes);

            editText.setText("");
            System.out.println("chatMessage chatArr"+chatArr);

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
        boardButton.setOnClickListener(BoardListener);
        homeButton.setOnClickListener(HomeListener);

    }

  /*  @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.activity_slide_enter, R.anim.activity_slide_exit);
        super.onBackPressed();
    }

    View.OnClickListener userBoardListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

*/

    public ArrayList<String> getChatArr() {

        return chatArr;
    }

    public void setChatArr(ArrayList<String> chatArr) {

        this.chatArr = chatArr;

       /* System.out.println("MessageSendJson ok");
        String json = messageSaveJsonDatabase.toJSONString();//스트로크 json 구조로 변환
        System.out.println("save firebase :  " + json);

        messageSaveJsonDatabase.saveDatabase(json);//변환된 json 구조 Firebase에 저장
        System.out.println(json + "saveDatabase json ");
    */
    }

    public MessageAdapter getArrayAdapter() {
        return arrayAdapter;
    }

    public void setArrayAdapter(MessageAdapter arrayAdapter) {
        this.arrayAdapter = arrayAdapter;
        arrayAdapter.notifyDataSetChanged();
    }

    public ListView getListView() {
        return listView;
    }
}