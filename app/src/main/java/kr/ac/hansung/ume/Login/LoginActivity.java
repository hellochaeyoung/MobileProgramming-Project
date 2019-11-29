package kr.ac.hansung.ume.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import kr.ac.hansung.ume.MainActivity;
import kr.ac.hansung.ume.R;

public class LoginActivity extends AppCompatActivity {

    private EditText idText;
    private EditText pwText;
    private EditText partnerText;

    private String id;
    private String pw;
    private String partnerID;

    private String hasPartner;

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
        pwText = findViewById(R.id.passwordEditText);
        partnerText = findViewById(R.id.partnerEditText);

        hasPartner = "X";

        loginButton = findViewById(R.id.loginButton);
        assignButton = findViewById(R.id.assignButton);

        loginButton.setOnClickListener(loginClickListener);
        assignButton.setOnClickListener(assignClickListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.hasChild("Member")){
                if (dataSnapshot.child("Member").hasChild(id)){//이미 가입이 된 아이디인지 확인
                    if (pw.equals(dataSnapshot.child("Member").child(id).child("password").getValue())){// 아이디와 비밀번호가 일치하는지 확인
                        if ( hasPartner.equals(dataSnapshot.child("Member").child(id).child("hasPartner").getValue())){//아직 파트너가 정해지지 않앗는지 확인 ( X이면 정해지지않음, O이면 정해짐)
                            if(dataSnapshot.child("Member").hasChild(partnerID)){//파트너의 아이디가 가입된 아이디인지 확인
                                databaseReference.child("Member").child(id).child("hasPartner").setValue("O"); // 1로 바꿔주고
                                databaseReference.child("Member").child(partnerID).child("hasPartner").setValue("O"); // 파트너의 속성도 1로 설정

                                databaseReference.child(id + partnerID).child("name").setValue(id + partnerID);// 자신의 아이디 + 파트너 아이디 조합으로 노드만들기, 이 상위 노드에 둘의 데이터 저장
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class); //메인 액티비티 수정 필요
                                intent.putExtra("partnerID", partnerID); // 인텐트로 데이터 값 같이 보내서 디비에서 파트너 정보 가져와 설정
                                startActivity(intent);
                                finish();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setTitle("오류");
                                builder.setMessage("파트너의 아이디가 존재하지 않습니다.");
                                builder.setPositiveButton("OK", null);
                                builder.create().show();
                            }
                        }

                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("로그인 실패");
                        builder.setMessage("비밀번호가 일치하지 않습니다.");
                        builder.setPositiveButton("OK", null);
                        builder.create().show();
                    }
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("로그인 실패");
                    builder.setMessage("존재하지 않는 아이디입니다.");
                    builder.setPositiveButton("OK", null);
                    builder.create().show();
                }
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    View.OnClickListener loginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            id = idText.getText().toString();
            pw = pwText.getText().toString();
            partnerID = partnerText.getText().toString();
            databaseReference.addValueEventListener(valueEventListener);

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
