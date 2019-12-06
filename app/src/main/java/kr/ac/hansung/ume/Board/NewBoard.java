package kr.ac.hansung.ume.Board;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import kr.ac.hansung.ume.R;
import kr.ac.hansung.ume.View.HomeActivity;

public class NewBoard extends AppCompatActivity {
    final int REQ_CODE_SELECT_IMAGE=100;
    private EditText titleText;
    private EditText contentText;
    private ImageView addimage;

    private Button submit;

    public BoardActivity boardActivity;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private String dbName;

    private int index ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newboard);
        boardActivity=(BoardActivity) boardActivity.boardContext;
        init();
        index=boardActivity.getIndex();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        dbName = ((HomeActivity)HomeActivity.homeContext).getDbName();



    }


    public void init(){
        titleText=findViewById(R.id.titleText);
        contentText=findViewById(R.id.contentText);
        addimage=findViewById(R.id.addImage);
        ItemDetail plus=new ItemDetail();
        Bitmap plusBitmap= BitmapFactory.decodeResource(getResources(),R.drawable.plus);
        addimage.setImageBitmap(plusBitmap);
        addimage.setOnClickListener(imageListener);

        submit=findViewById(R.id.submit);

        submit.setOnClickListener(btnListener);
    }

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
                    Bitmap img=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    int nh = (int) (img.getHeight() * (1024.0 / img.getWidth()));
                    Bitmap scaled = Bitmap.createScaledBitmap(img, 1024, nh, true);
                    ImageView image=(ImageView)findViewById(R.id.imageView) ;
                    addimage.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("사진불러오기 실패");
                }
            }
        }
    }

    View.OnClickListener btnListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String title=titleText.getText().toString();
            String content=contentText.getText().toString();
            BitmapDrawable drawable=(BitmapDrawable)addimage.getDrawable();
            Bitmap image=drawable.getBitmap();
            System.out.println("title here"+title);
            if(title!=null&& image!=null &&content!=null){
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG,100,stream);
                byte[] bytes=stream.toByteArray();
                databaseReference.child(dbName).child("board").child(Integer.toString(index)).child("image").setValue(getString(image));
                databaseReference.child(dbName).child("board").child(Integer.toString(index)).child("title").setValue(title);
                databaseReference.child(dbName).child("board").child(Integer.toString(index)).child("content").setValue(content);
                databaseReference.child(dbName).child("board").child("index").setValue(index);

                index++;
                boardActivity.setIndex(index);

               boardActivity.setNewTitle(title);
                boardActivity.setNewContent(content);
                boardActivity.setNewImage(image);//설정된 값들을 직접적으로 MainActivity에 설정

                boardActivity.setNew(true);//true로 바꿈으로서 새 글이 등록시도를 하려고 함을 MainActivity에 알려줌
                finish();


            }
        }
    };

    public String getString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imageBytes=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes,Base64.NO_WRAP);
    }

}
