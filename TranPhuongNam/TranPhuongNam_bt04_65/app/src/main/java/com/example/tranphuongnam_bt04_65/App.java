package com.example.tranphuongnam_bt04_65;

import android.app.Application;

import java.util.ArrayList;

public class App extends Application { // Singleton Pattern

    DBHelper dbHelper;
    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper = new DBHelper(this);
        dbHelper.CopyDataFromDB();
    }


}
