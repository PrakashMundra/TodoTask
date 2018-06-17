package com.todotask.data;

import android.arch.lifecycle.LiveData;

import com.todotask.AppExecutors;
import com.todotask.data.entity.TodoEntity;
import com.todotask.data.local.TodoDao;
import com.todotask.data.local.TodoLocalDataSource;
import com.todotask.data.model.Todo;

import java.util.List;

public class TodoDataRepository implements TodoDataSource {
    private static TodoDataRepository INSTANCE;
    private TodoLocalDataSource mTodoLocalDataSource;

    private TodoDataRepository(AppExecutors executors, TodoDao todoDao) {
        this.mTodoLocalDataSource = new TodoLocalDataSource(executors, todoDao);
    }

    public static TodoDataRepository getInstance(final AppExecutors executors, final TodoDao todoDao) {
        if (INSTANCE == null) {
            synchronized (TodoDataRepository.class) {
                INSTANCE = new TodoDataRepository(executors, todoDao);
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<List<TodoEntity>> getTodoList() {
        return mTodoLocalDataSource.getTodoList();
    }

    @Override
    public void completeTodo(Todo todo) {
        mTodoLocalDataSource.completeTodo(todo);
    }

    @Override
    public void activateTodo(Todo todo) {
        mTodoLocalDataSource.activateTodo(todo);
    }

    @Override
    public LiveData<TodoEntity> getTodo(int todoId) {
        return mTodoLocalDataSource.getTodo(todoId);
    }

    @Override
    public void saveTodo(TodoEntity todo) {
        mTodoLocalDataSource.saveTodo(todo);
    }

    @Override
    public void deleteTodo(int todoId) {
        mTodoLocalDataSource.deleteTodo(todoId);
    }
}
