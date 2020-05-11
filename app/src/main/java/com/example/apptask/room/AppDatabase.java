package com.example.apptask.room;

import androidx.room.Dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.apptask.models.Task;

@Database(entities={Task.class},version=1)//версия структуры тб
public abstract class AppDatabase extends RoomDatabase {
    public abstract  TaskDao taskDao();
}
