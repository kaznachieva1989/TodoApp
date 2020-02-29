package com.example.todoapp;

import android.app.Application;

import androidx.room.Room;

import com.example.todoapp.room.AppDatabase;

public class App extends Application {
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
