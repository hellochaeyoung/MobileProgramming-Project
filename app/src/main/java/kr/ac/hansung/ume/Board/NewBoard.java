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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import kr.ac.hansung.ume.R;

public class NewBoard extends AppCompatActivity {
    final int REQ_CODE_SELECT_IMAGE=100;
    private EditText titleText;
    private EditText contentText;
    private ImageView addimage;

    private Button submit;

    public BoardActivity boardActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newboard);
        boardActivity=(BoardActivity) boardActivity.boardContext;
        init();




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
                    // 선택한 이미지에서 비트맵 생성
                    /*InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    */
                    Uri uri=data.getData();
                    Bitmap img=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    int nh = (int) (img.getHeight() * (1024.0 / img.getWidth()));
                    Bitmap scaled = Bitmap.createScaledBitmap(img, 1024, nh, true);

                    //Uri에서 이미지 이름을 얻어온다.
                    // String name_Str = getImageNameToUri(data.getData());
                    // 이미지 데이터를 비트맵으로 받아온다.
                    // Bitmap image_bitmap = Images.Media.getBitmap(getContentResolver(), data.getData());
                    // ImageView image = (ImageView)findViewById(R.id.imageView1);

                    ImageView image=(ImageView)findViewById(R.id.imageView) ;
                   // System.out.println("Bitmap"+img);
                    // 이미지 표시
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

               boardActivity.setNewTitle(title);
                boardActivity.setNewContent(content);
                boardActivity.setNewImage(image);//설정된 값들을 직접적으로 MainActivity에 설정

                boardActivity.setNew(true);//true로 바꿈으로서 새 글이 등록시도를 하려고 함을 MainActivity에 알려줌
                finish();


            }
        }
    };

}
