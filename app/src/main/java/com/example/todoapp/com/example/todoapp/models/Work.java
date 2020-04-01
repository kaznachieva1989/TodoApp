package com.example.todoapp.com.example.todoapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Work implements Serializable {

    private String title;
    private String desc;
    private String imageUrl;
    @PrimaryKey(autoGenerate = true)
    private long id;


    public Work(String title, String desc, String imageUrl) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.imageUrl = imageUrl;
    }
    public Work(String title, String desc) {
        this.id = id;
        this.title = title;
        this.desc = desc;
    }

    public Work() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}