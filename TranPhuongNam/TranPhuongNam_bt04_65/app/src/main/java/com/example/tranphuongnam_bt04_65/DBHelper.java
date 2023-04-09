package com.example.tranphuongnam_bt04_65;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBHelper {
    String DB_NAME = "DBcontact.db";
    SQLiteDatabase db;
    Context context;
    private InputStream is;

    public DBHelper(Context context){
        this.context = context;
    }
    public SQLiteDatabase openDB(){
        return context.openOrCreateDatabase(DB_NAME,Context.MODE_PRIVATE,null);
    }
    public void CopyDataFromDB() {
        File dbFile = context.getDatabasePath(DB_NAME);
        if(!dbFile.exists()){
            try {
                InputStream is = context.getAssets().open(DB_NAME);
                OutputStream os = new FileOutputStream(dbFile);
                byte[] buffer = new byte[1024];
                while (is.read(buffer) >0){
                    os.write(buffer);
                }
                os.flush();
                os.close();
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public ArrayList<Contact> getContact(){
        ArrayList<Contact> tmp = new ArrayList<>();
        db = openDB();
        String sql = "SELECT * FROM Contact";
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String fname= cursor.getString(1);
            String lname = cursor.getString(2);
            String phone = cursor.getString(3);
            String email = cursor.getString(4);
            String image = cursor.getString(5);
            String birthday = cursor.getString(6);
            Contact contact = new Contact(id,fname,lname,phone,email,image,birthday);
            tmp.add(contact);
        }
        db.close();
        return tmp;
    }

    public long insertContact(Contact contact){
        db = openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FNAME", contact.getFirstname());
        contentValues.put("LNAME", contact.getFirstname());
        contentValues.put("PHONE", contact.getFirstname());
        contentValues.put("EMAIL", contact.getFirstname());
        contentValues.put("IMAGE", contact.getFirstname());
        contentValues.put("BIRTHDAY", contact.getFirstname());
        long tmp = db.insert("Contact","", contentValues);
        db.close();
        return tmp;
    }

    public long updateContact(Contact contact){
        db = openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FNAME", contact.getFirstname());
        contentValues.put("LNAME", contact.getFirstname());
        contentValues.put("PHONE", contact.getFirstname());
        contentValues.put("EMAIL", contact.getFirstname());
        contentValues.put("IMAGE", contact.getFirstname());
        contentValues.put("BIRTHDAY", contact.getFirstname());
        long tmp = db.update("Contact", contentValues,"ID=" + contact.getId(), null);
        db.close();
        return tmp;
    }

    public long deleteContact(Contact contact){
        db = openDB();
        long tmp = db.delete("Contact", "ID= " + contact.getId(), null);
        db.close();
        return tmp;
    }
}
