package com.todotask.ui.callback;


import android.view.View;

import com.todotask.data.model.Todo;

public interface ITodoItemListener {
    void onTodoClicked(Todo todo);

    void onTodoCompleteChanged(Todo todo, View v);

    void onMenuClicked(int menuItemId, Todo todo);
}
