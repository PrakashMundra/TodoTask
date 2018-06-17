package com.todotask.data.local;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.todotask.AppExecutors;
import com.todotask.data.TodoDataSource;
import com.todotask.data.entity.TodoEntity;
import com.todotask.data.model.Todo;

import java.util.List;

public class TodoLocalDataSource implements TodoDataSource {
    private AppExecutors mAppExecutors;
    private TodoDao mTodoDao;

    public TodoLocalDataSource(@NonNull AppExecutors executors, @NonNull TodoDao todoDao) {
        this.mAppExecutors = executors;
        this.mTodoDao = todoDao;
    }

    @Override
    public LiveData<List<TodoEntity>> getTodoList() {
        return mTodoDao.getTodoList();
    }

    @Override
    public void completeTodo(Todo todo) {
        mAppExecutors.diskIO().execute(() ->
                mTodoDao.updateCompleted(todo.getId(), true)
        );
    }

    @Override
    public void activateTodo(Todo todo) {
        mAppExecutors.diskIO().execute(() ->
                mTodoDao.updateCompleted(todo.getId(), false)
        );
    }

    @Override
    public LiveData<TodoEntity> getTodo(int todoId) {
        return mTodoDao.getTodoById(todoId);
    }

    public void saveTodo(TodoEntity todo) {
        mAppExecutors.diskIO().execute(() ->
                mTodoDao.insertTodo(todo)
        );
    }

    @Override
    public void deleteTodo(int todoId) {
        mAppExecutors.diskIO().execute(() ->
                mTodoDao.deleteTaskById(todoId)
        );
    }
}
