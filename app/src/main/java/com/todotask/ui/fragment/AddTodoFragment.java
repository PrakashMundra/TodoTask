package com.todotask.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.todotask.R;
import com.todotask.databinding.FragmentAddTodoBinding;
import com.todotask.viewmodel.AddTodoViewModel;

public class AddTodoFragment extends Fragment {
    public static final String TAG = AddTodoFragment.class.getName();
    public static final String EXTRAS_TODO_ID = "EXTRAS_TODO_ID";
    private FragmentAddTodoBinding mBinding;
    private AddTodoViewModel mModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            AddTodoViewModel.Factory factory = new AddTodoViewModel.Factory(getActivity().getApplication());
            mModel = ViewModelProviders.of(this, factory).get(AddTodoViewModel.class);
            mBinding.setViewmodel(mModel);
            subscribeToModel();
            setupFab();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_todo, container, false);
        return mBinding.getRoot();
    }


    private void setupFab() {
        if (getActivity() != null) {
            FloatingActionButton fab = getActivity().findViewById(R.id.save_todo);
            fab.setOnClickListener((View v) -> {
                mModel.saveTodo();
                getActivity().finish();
            });
        }
    }

    private void subscribeToModel() {
        if (getActivity() != null) {
            Bundle b = getActivity().getIntent().getExtras();
            if (b != null) {
                mModel.loading.set(true);
                int id = b.getInt(EXTRAS_TODO_ID);
                mModel.getTodo(id).observe(this, todo -> {
                    if (todo != null) {
                        mModel.mTodoEntity = todo;
                        mModel.title.set(todo.getTitle());
                        mModel.description.set(todo.getDescription());
                        mModel.loading.set(false);
                    }
                });
            }
        }
    }
}
