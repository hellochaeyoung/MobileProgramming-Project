package kr.ac.hansung.ume.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import kr.ac.hansung.ume.R;

public class AssignActivity extends AppCompatActivity {

    private EditText idEditText;
    private EditText pwEditText;
    private EditText nameEditText;

    private RadioButton manRadioButton;
    private RadioButton womanRadioButton;

    private EditText yearEditText;
    private EditText monthEditText;
    private EditText dayEditText;

    private Button assignButton;

    private String id;
    private String password;
    private String name;

    private String sex;
    private String year;
    private String month;
    private String day;
    private String birthday;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        System.out.println(databaseReference.toString());

        idEditText = findViewById(R.id.idEditText);
        pwEditText = findViewById(R.id.pwEditText);
        nameEditText = findViewById(R.id.nameEditText);

        manRadioButton = findViewById(R.id.manRadioButton);
        womanRadioButton = findViewById(R.id.womanRadioButton);

        yearEditText = findViewById(R.id.yearEditText);
        monthEditText = findViewById(R.id.monthEditText);
        dayEditText = findViewById(R.id.dayEditText);

        assignButton = findViewById(R.id.assignButton);

        assignButton.setOnClickListener(assignOnClickListener);

    }

    View.OnClickListener assignOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            id = idEditText.getText().toString();
            password = pwEditText.getText().toString();
            name = nameEditText.getText().toString();


            if (manRadioButton != null){
                sex = "남자";
            }
            else if( womanRadioButton != null) {
                sex = "여자";
            }


            year = yearEditText.getText().toString();
            month = monthEditText.getText().toString();
            day = dayEditText.getText().toString();

            birthday = year +  month + day;

            if (id != null && password != null && name != null && sex != null && year != null && month != null && day != null) {
                databaseReference.addValueEventListener(valueEventListener);
                System.out.println("clickListener ok");
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(AssignActivity.this);
                builder.setTitle("회원가입 실패");
                builder.setMessage("빈칸 없이 입력하세요");
                builder.setPositiveButton("OK", null);
                builder.create().show();
            }
        }
    };

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.hasChild("Member")){
                if (dataSnapshot.child("Member").hasChild(id)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AssignActivity.this);
                    builder.setTitle("오류");
                    builder.setMessage("아이디가 중복됩니다.");
                    builder.setPositiveButton("OK", null);
                    builder.create().show();
                    databaseReference.removeEventListener(valueEventListener);
                }
                else {
                    System.out.println("ok");
                    databaseReference.child("Member").child(id).child("id").setValue(id);
                    databaseReference.child("Member").child(id).child("password").setValue(password);
                    databaseReference.child("Member").child(id).child("name").setValue(name);
                    databaseReference.child("Member").child(id).child("sex").setValue(sex);
                    databaseReference.child("Member").child(id).child("birthday").setValue(birthday);
                    databaseReference.child("Member").child(id).child("hasPartner").setValue("X");

                    AlertDialog.Builder builder = new AlertDialog.Builder(AssignActivity.this);
                    builder.setTitle("회원가입 성공");
                    builder.setMessage("회원가입을 완료했습니다.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(AssignActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.create().show();
                    databaseReference.removeEventListener(valueEventListener);
                }
            }
            else{
                System.out.println("ok");
                databaseReference.child("Member").child(id).child("id").setValue(id);
                databaseReference.child("Member").child(id).child("password").setValue(password);
                databaseReference.child("Member").child(id).child("name").setValue(name);
                databaseReference.child("Member").child(id).child("sex").setValue(sex);
                databaseReference.child("Member").child(id).child("birthday").setValue(birthday);
                databaseReference.child("Member").child(id).child("hasPartner").setValue("X");

                AlertDialog.Builder builder = new AlertDialog.Builder(AssignActivity.this);
                builder.setTitle("회원가입 성공");
                builder.setMessage("회원가입을 완료했습니다.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(AssignActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.create().show();
                databaseReference.removeEventListener(valueEventListener);
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
