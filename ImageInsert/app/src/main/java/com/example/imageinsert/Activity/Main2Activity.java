package com.example.imageinsert.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imageinsert.Database.DatabaseHandler;
import com.example.imageinsert.R;

import java.io.ByteArrayOutputStream;

public class Main2Activity extends AppCompatActivity {
private TextView name,phone,mail,txt_id;
private ImageView imgProfile;
private Button EditProfile;
private DatabaseHandler handler;
String pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setId();
        pass = getIntent().getStringExtra("password");
        handler = new DatabaseHandler(this);
        getprofileData();
        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] newEntryImg = imageTobyte(imgProfile);
                Intent intent = new Intent(Main2Activity.this,EditActivity.class);
                intent.putExtra("EName", name.getText().toString());
                intent.putExtra("Ephone", phone.getText().toString());
                intent.putExtra("Email", mail.getText().toString());
                intent.putExtra("Image", newEntryImg);
                intent.putExtra("ID", txt_id.getText().toString());
                intent.putExtra("Pass",pass);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getprofileData() {
        Cursor data = handler.getLoginData(getIntent().getStringExtra("name"),getIntent().getStringExtra("password"));
        if(data.moveToNext()){
            int id = data.getInt(0);
            String Sname = data.getString(1);
            String Smail = data.getString(2);
            String Sphone = data.getString(3);
            byte[] img= data.getBlob(4);
            Bitmap bitmap = BitmapFactory.decodeByteArray(img,0, img.length);
            imgProfile.setImageBitmap(bitmap);
            name.setText(Sname);
            mail.setText(Smail);
            phone.setText(Sphone);
            txt_id.setText(String.valueOf(id));

        }
    }
    private byte[] imageTobyte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }
    private void setId() {
        txt_id = findViewById(R.id.txt_id);
        name=findViewById(R.id.txt_name);
        phone=findViewById(R.id.txt_phone);
        mail=findViewById(R.id.txt_mail);
        EditProfile =findViewById(R.id.btn_update);
        imgProfile = findViewById(R.id.img_profile);
    }
}
