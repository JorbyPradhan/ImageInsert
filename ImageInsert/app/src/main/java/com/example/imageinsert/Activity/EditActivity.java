package com.example.imageinsert.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.imageinsert.Database.DatabaseHandler;
import com.example.imageinsert.MainActivity;
import com.example.imageinsert.R;
import com.example.imageinsert.model.User;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditActivity extends AppCompatActivity {
    private TextInputEditText name, email, phone;
    private Button Register;
    ImageView imageView;
    private DatabaseHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setId();
        name.setText(getIntent().getStringExtra("Ename"));
        phone.setText(getIntent().getStringExtra("Ephone"));
        email.setText(getIntent().getStringExtra("Email"));
        byte[] byteArray = getIntent().getByteArrayExtra("Image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageView.setImageBitmap(bmp);
        handler = new DatabaseHandler(this);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }
    private void validation() {
        if (name.getText().toString().isEmpty()) {
            name.setError("Fill Name");
            return;
        }
        if (email.getText().toString().isEmpty()) {
            email.setError("Fill Email");
            return;
        }
        if (phone.getText().toString().isEmpty()) {
           phone.setError("Fill Phone");
            return;
        }
        else {

            boolean flag = false;
            String ename = name.getText().toString();
            String ephone = phone.getText().toString();
            String mail = email.getText().toString();
            String pass =getIntent().getStringExtra("Pass");

            flag = handler.updateData(ename,ephone,pass,mail);
            if (flag == true) {
                Toast.makeText(this, "Update Profile Successful", Toast.LENGTH_LONG).show();
                Intent intent =new Intent(EditActivity.this, Main2Activity.class);
                intent.putExtra("name",name.getText().toString());
                intent.putExtra("password",pass);
                finish();
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            }

        }
    }

    private void setId() {
        imageView = findViewById(R.id.img);
        name = findViewById(R.id.reg_name);
        email = findViewById(R.id.reg_edt_mail);
        phone = findViewById(R.id.reg__edt_phone);
        Register = findViewById(R.id.btn_register);
    }
}