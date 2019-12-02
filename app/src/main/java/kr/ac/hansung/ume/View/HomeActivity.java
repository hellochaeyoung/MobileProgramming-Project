package kr.ac.hansung.ume.View;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import kr.ac.hansung.ume.Board.BoardActivity;
import kr.ac.hansung.ume.Client.MQTTClient;
import kr.ac.hansung.ume.R;

public class HomeActivity extends AppCompatActivity {
    private MQTTClient client;
    public static Context context;
    private String newtopic;
    private String topic_message;
    private ArrayList<String> messageArr;
    private boolean isMessageChange=false;

    private Button homeButton;
    private Button boardButton;
    private Button chatButton;
    private Button albumButton;
    private Button calendarButton;

    public static Context homeContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeContext=this;
        setButton();
        setListener();

        context=this;
        newtopic="hi";
        topic_message = newtopic + "_message";

        client=new MQTTClient();
        client.setCallback();

        client.subscribe(newtopic);
        client.subscribe(topic_message);


        messageArr=new ArrayList<>();

    }


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
            Intent chatIntent=new Intent(HomeActivity.this, kr.ac.hansung.ume.Chatting.ChatMessage.class);
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
