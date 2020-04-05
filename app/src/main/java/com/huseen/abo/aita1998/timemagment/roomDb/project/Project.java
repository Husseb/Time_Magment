package com.huseen.abo.aita1998.timemagment.roomDb.project;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.huseen.abo.aita1998.timemagment.roomDb.task.Task;

import java.util.List;

@Entity(tableName = "project_table")
public class Project {

    private String name, details, time;

    private  int progress;

    @PrimaryKey(autoGenerate = true)
    private int id;

    public Project() {
    }
     public Project(String name, String details, String time, int progress) {
        this.progress = progress;
        this.time = time;
        this.name = name;
        this.details = details;
     }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
