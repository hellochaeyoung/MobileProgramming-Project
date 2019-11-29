package kr.ac.hansung.ume.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import kr.ac.hansung.ume.R;

public class LoginActivity extends AppCompatActivity {

    private EditText idText;
    private EditText pwText;

    private String id;
    private String pw;

    private Button loginButton;
    private Button assignButton;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        idText = findViewById(R.id.idEditText);
        pwText = findViewById(R.id.pwEditText);

        id = idText.getText().toString();
        pw = pwText.getText().toString();

        //loginButton = findViewById(R.id.loginButton);
        assignButton = findViewById(R.id.assignButton);

        loginButton.setOnClickListener(loginClickListner);
        assignButton.setOnClickListener(assignClickListener);
    }

    /*ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.hasChild("Member")){
                if (dataSnapshot.child("Member").hasChild(id)){
                    if (pw.equals(dataSnapshot.child("Member").child(id).child(pw).getValue())){
                        //여기부터 다시 구현
                    }
                }
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };*/

    View.OnClickListener loginClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    View.OnClickListener assignClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, AssignActivity.class);
            startActivity(intent);
            finish();
        }
    };
}
