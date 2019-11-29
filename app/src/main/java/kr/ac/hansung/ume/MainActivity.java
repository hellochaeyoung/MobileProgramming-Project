package kr.ac.hansung.ume;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static Context context;
    private String newtopic;
    private String topic_message;

    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;

    }

    public String getNewtopic() {
        return newtopic;
    }
}
