package com.example.apptask.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.apptask.models.Task;

import java.util.List;

@Dao
public interface TaskDao {
    //метод для записи
    @Insert
    void insert(Task task);

    //метод для чтения
    @Query("SELECT*FROM task")
    List<Task> getAll();

    //чтение
    @Query("SELECT*FROM task")
    LiveData<List<Task>> getAllLive();

    @Delete
    void Delete(Task task);

    @Update
    void update(Task task);
}
