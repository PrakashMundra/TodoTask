package com.todotask.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.todotask.R;
import com.todotask.ui.fragment.AddTodoFragment;
import com.todotask.ui.fragment.TodoListFragment;

public class TodoListActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        if (savedInstanceState == null)
            addFragment(new TodoListFragment(), TodoListFragment.TAG);
        setupFab();
    }

    private void setupFab() {
        FloatingActionButton fab = findViewById(R.id.add_todo);
        fab.setOnClickListener((View v) -> {
            showTodo(null);
        });
    }

    public void showTodo(Integer todoId) {
        Intent i = new Intent(this, AddTodoActivity.class);
        if (todoId != null)
            i.putExtra(AddTodoFragment.EXTRAS_TODO_ID, todoId);
        startActivity(i);
    }
}
