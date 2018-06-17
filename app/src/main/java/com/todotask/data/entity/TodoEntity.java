package com.todotask.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.todotask.data.model.Todo;

@Entity(tableName = "todo")
public class TodoEntity implements Todo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private boolean isCompleted;

    public TodoEntity(String title, String description) {
        this.title = title;
        this.description = description;
        this.isCompleted = false;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
