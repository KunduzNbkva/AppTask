package com.example.apptask.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class Task implements Serializable {  // если надо передавать интентом между активностями
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String Title;
    private String Desc;

    public Task() {

    }


    public Task(String title, String desc) {
        Title = title;
        Desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }
}
