package com.todotask.ui.fragment;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.todotask.R;
import com.todotask.data.entity.TodoEntity;
import com.todotask.data.model.Todo;
import com.todotask.databinding.FragmentTodoListBinding;
import com.todotask.ui.activity.TodoListActivity;
import com.todotask.ui.adapter.TodoListAdapter;
import com.todotask.ui.callback.ITodoItemListener;
import com.todotask.util.IntentUtils;
import com.todotask.viewmodel.TodoListViewModel;

import java.util.List;

public class
TodoListFragment extends Fragment implements ITodoItemListener {
    public static final String TAG = TodoListFragment.class.getName();
    private FragmentTodoListBinding mBinding;
    private TodoListAdapter mAdapter;
    private TodoListViewModel mModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_todo_list, container, false);
        mAdapter = new TodoListAdapter(this);
        setUpRecyclerView();
        mBinding.todoList.setAdapter(mAdapter);
        return mBinding.getRoot();
    }

    private void setUpRecyclerView() {
        if (getContext() != null) {
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            mBinding.todoList.setLayoutManager(manager);
            DividerItemDecoration divider = new DividerItemDecoration(getContext(), manager.getOrientation());
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.divier);
            if (drawable != null)
                divider.setDrawable(drawable);
            mBinding.todoList.addItemDecoration(divider);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            TodoListViewModel.Factory factory = new TodoListViewModel.Factory(getActivity().getApplication());
            mModel = ViewModelProviders.of(this, factory).get(TodoListViewModel.class);
            mBinding.setViewmodel(mModel);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        subscribeToModel();
    }

    private void subscribeToModel() {
        mModel.loading.set(true);
        mModel.getTodoList().observe(this, (List<TodoEntity> todoList) -> {
            if (todoList != null && !todoList.isEmpty()) {
                mModel.isEmpty.set(false);
                mAdapter.setTodoList(todoList);
            } else
                mModel.isEmpty.set(true);
            mModel.loading.set(false);
        });
    }

    @Override
    public void onTodoClicked(Todo todo) {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            if (getActivity() != null)
                ((TodoListActivity) getActivity()).showTodo(todo.getId());
        }
    }

    @Override
    public void onTodoCompleteChanged(Todo todo, View v) {
        if (mModel != null)
            mModel.completeTodo(todo, ((CheckBox) v).isChecked());
    }

    @Override
    public void onMenuClicked(int menuItemId, Todo todo) {
        switch (menuItemId) {
            case R.id.menu_share:
                if (getContext() != null)
                    IntentUtils.shareTodo(getContext(), todo);
                break;

            case R.id.menu_delete:
                mModel.deleteTodo(todo.getId());
                subscribeToModel();
                break;
        }
    }
}
