package com.example.imageinsert;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imageinsert.Activity.Main2Activity;
import com.example.imageinsert.Activity.RegisterActivity;
import com.example.imageinsert.Database.DatabaseHandler;
import com.example.imageinsert.model.User;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button Add;
    private TextView Display;
    private ImageView img;
    private DatabaseHandler handler;
    private TextInputEditText name,password;
    List<User> listdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Add = findViewById(R.id.btn_add);
        name = findViewById(R.id.login_name);
        img = findViewById(R.id.img_data);
        password = findViewById(R.id.edt_Pass);
        listdata = new ArrayList<>();
        handler = new DatabaseHandler(this);
        Display = findViewById(R.id.btn_view);
        populateView();

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    name.setError("Fill name");
                    password.setError("Fill Password");
                    return;
                }
                else{
                    Cursor data = handler.getLoginData(name.getText().toString().trim(),password.getText().toString().trim());
                    if (data.moveToFirst()){
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        intent.putExtra("name",name.getText().toString());
                        intent.putExtra("password",password.getText().toString());
                        startActivity(intent);
                    }
                }

            }
        });
        Display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    private void populateView() {
        Cursor data = handler.getDataone();
        if (data.moveToFirst()){
            byte[] image = data.getBlob(0);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image,0, image.length);
            img.setImageBitmap(bitmap);
        }
    }
}
