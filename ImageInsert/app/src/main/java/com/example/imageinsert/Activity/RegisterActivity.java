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

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText name,password,confirmPassword,email,phone;
    private Button Register;
    ImageView imageView;
    Button getImg;
    Uri imageUri;
    Integer PICK_IMAGE =1;
    Integer REQUEST_CAMERA =0;
    private DatabaseHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setId();
        handler =new DatabaseHandler(this);
        getImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(RegisterActivity.this,
                        new String[]{Manifest.permission.CAMERA}
                        , REQUEST_CAMERA);
                openGallery();
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }
    private void openGallery() {
        final CharSequence[] items={"Camera","Gallery","Cancel"};
        AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(items[i].equals("Camera")){
                    Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(camera, REQUEST_CAMERA);
                }
                else if(items[i].equals("Gallery")){
                    Intent gallery= new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, PICK_IMAGE);//0
                }
                else if(items[i].equals("Cancel")){
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //1
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CAMERA){
                Bundle bundle= data.getExtras();
                final Bitmap bmp =(Bitmap)bundle.get("data");
                imageView.setImageBitmap(bmp);

            }
            else if(requestCode == PICK_IMAGE){
               imageUri = data.getData();
                    try{
                        InputStream inputStream= getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageView.setImageBitmap(bitmap);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                    // imageView.setImageURI(imageUri);
                }
               /* imageUri = data.getData();//
                imageView.setImageURI(imageUri);*/
            }
        }

    private void validation() {
        if(name.getText().toString().isEmpty()){
            name.setError("Fill Name");
            return;
        }
        if(email.getText().toString().isEmpty()){
            email.setError("Fill Email");
            return;
        }
        if(password.getText().toString().isEmpty()){
            password.setError("Fill Password");
            return;
        } if(phone.getText().toString().isEmpty()){
            password.setError("Fill Password");
            return;
        }
        if(confirmPassword.getText().toString().isEmpty()){
            confirmPassword.setError("Fill ConfirmPassword");
            return;
        }
        if(password.getText().toString().trim().equals(confirmPassword.getText().toString().trim())){

            boolean flag = false;
            byte[] entryimg = imageTobyte(imageView);
            User user = new User(name.getText().toString(),password.getText().toString(),
                    email.getText().toString(),phone.getText().toString(),entryimg);
            flag =handler.RegisterData(user);
            if(flag == true){
                Toast.makeText(this,"Account Create Successful",Toast.LENGTH_LONG).show();
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            }
            else {
                Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
            }

        }
        else {
            confirmPassword.setError("Password does not match");
            return;
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
        getImg = findViewById(R.id.btn_get);
        imageView = findViewById(R.id.img);
        name = findViewById(R.id.reg_name);
        password = findViewById(R.id.reg_edt_Pass);
        email = findViewById(R.id.reg_edt_mail);
        confirmPassword = findViewById(R.id.confirm_Pass);
        phone = findViewById(R.id.reg__edt_phone);
        Register = findViewById(R.id.btn_register);

    }
}
