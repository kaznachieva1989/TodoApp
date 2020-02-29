package com.example.todoapp.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.todoapp.com.example.todoapp.models.Work;

import java.util.List;

@Dao
public interface WorkDao {

    @Query("SELECT * FROM work")
    LiveData<List<Work>> getAll();

    @Insert
    void insert(Work work);
}
