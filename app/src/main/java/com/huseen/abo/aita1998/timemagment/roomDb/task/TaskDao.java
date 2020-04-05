package com.huseen.abo.aita1998.timemagment.roomDb.task;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
     void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("DELETE FROM task_table")
    void deleteAllProduct();

    @Query("SELECT * FROM task_table  ")
    LiveData<List<Task>> getAllTask();

    @Query("SELECT * FROM task_table  ")
    List<Task> getAll();

}