package com.todotask.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.todotask.data.entity.TodoEntity;

import java.util.List;

@Dao
public interface TodoDao {
    @Query("SELECT * FROM todo ORDER BY id ASC")
    LiveData<List<TodoEntity>> getTodoList();

    @Query("UPDATE todo SET isCompleted = :completed WHERE id = :todoId")
    void updateCompleted(int todoId, boolean completed);

    @Query("SELECT * FROM todo WHERE id = :todoId")
    LiveData<TodoEntity> getTodoById(int todoId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTodo(TodoEntity todo);

    @Query("DELETE FROM todo WHERE id = :todoId")
    int deleteTaskById(int todoId);
}
