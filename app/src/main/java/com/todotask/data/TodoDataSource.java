package com.todotask.data;

import android.arch.lifecycle.LiveData;

import com.todotask.data.entity.TodoEntity;
import com.todotask.data.model.Todo;

import java.util.List;

public interface TodoDataSource {
    LiveData<List<TodoEntity>> getTodoList();

    void completeTodo(Todo todo);

    void activateTodo(Todo todo);

    LiveData<TodoEntity> getTodo(int todoId);

    void saveTodo(TodoEntity todo);

    void deleteTodo(int todoId);
}
