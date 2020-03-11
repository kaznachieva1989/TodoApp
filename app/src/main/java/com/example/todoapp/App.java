package com.example.todoapp;

import android.app.Application;

import androidx.multidex.MultiDexApplication;
import androidx.room.Room;

import com.example.todoapp.room.AppDatabase;

public class App extends MultiDexApplication {
    private static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
    }
    public static AppDatabase getDatabase(){
        return database;
    }
}
