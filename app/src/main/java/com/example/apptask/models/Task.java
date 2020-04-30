package com.example.apptask.models;

import java.io.Serializable;

public class Task implements Serializable {  // если надо передавать интентом между активностями
    private String Title;
    private String Desc;

    public Task(String title, String desc) {
        Title = title;
        Desc = desc;
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
