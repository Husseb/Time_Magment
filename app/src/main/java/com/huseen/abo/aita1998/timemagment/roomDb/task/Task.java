package com.huseen.abo.aita1998.timemagment.roomDb.task;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class Task {

    public Task() {
    }

    private String name, details, time;
    int number;
    int select;
    @PrimaryKey(autoGenerate = true)
    private int id;

    public Task(String name, String details, String time, int number, int select) {
        this.name = name;
        this.details = details;
        this.time = time;
        this.number = number;
        this.select = select;
    }

    public int getSelect() {
        return select;
    }

    public void setSelect(int select) {
        this.select = select;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
