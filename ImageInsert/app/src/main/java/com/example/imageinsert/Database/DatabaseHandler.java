package com.example.imageinsert.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.imageinsert.model.User;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private Context context;
    private static final String DATABASE_NAME=" login_db";
    private static final String TABLE_NAME = "login";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME="Name";
    private static final String KEY_PASSWORD="password";
    private static final String KEY_PHONE="phone";
    private static final String KEY_EMAIL ="mail";
    private static final String KEY_IMG ="img";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Create_Login_Table = " CREATE TABLE " + TABLE_NAME +
                " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, " + KEY_PASSWORD + " TEXT, " + KEY_EMAIL + " TEXT," +
                " " + KEY_PHONE + " TEXT, " + KEY_IMG + " blob " + " ) ";
        db.execSQL(Create_Login_Table);//Table Create
        Log.i("Hello", "Create Table ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
   //Insert
    public boolean RegisterData(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_NAME,user.getName());
        values.put(KEY_PASSWORD,user.getPassword());
        values.put(KEY_EMAIL,user.getEmail());
        values.put(KEY_PHONE,user.getPhone());
        values.put(KEY_IMG,user.getImg());
        db.insert(TABLE_NAME,null, values);
        db.close();
        return true;
    }
    public Cursor getDataone(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " Select img from " + TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " Select name,mail,phone,img from " + TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }
    public Cursor getLoginData (String name, String password){
        SQLiteDatabase db = this.getWritableDatabase();//main
        String query = " SELECT id,name,mail,phone,img FROM "+ TABLE_NAME + " WHERE " + KEY_NAME +  " = " +
                "'" + name +"' and " + KEY_PASSWORD + " = '" + password + "' ";
        Cursor data = db.rawQuery(query,null);
        return data;
    }
    public boolean updateData(String name,String ph,String id,String mail)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL( " UPDATE " + TABLE_NAME + " SET " + KEY_NAME + " = '" + name + "' , " + KEY_PHONE + " = '" + ph + "' , " + KEY_EMAIL + " = '" + mail + "'  WHERE " + KEY_PASSWORD + " = '" + id + "'");
        return true;
    }
    //select
}
