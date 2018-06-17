package com.todotask.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.todotask.App;
import com.todotask.R;
import com.todotask.data.TodoDataRepository;
import com.todotask.data.entity.TodoEntity;
import com.todotask.data.model.Todo;

import java.util.List;

public class TodoListViewModel extends AndroidViewModel {
    public final ObservableBoolean loading = new ObservableBoolean(true);
    public final ObservableBoolean isEmpty = new ObservableBoolean(true);
    private TodoDataRepository mTodoDataRepository;

    private TodoListViewModel(@NonNull Application application, @NonNull TodoDataRepository repository) {
        super(application);
        this.mTodoDataRepository = repository;
    }

    public LiveData<List<TodoEntity>> getTodoList() {
        MediatorLiveData<List<TodoEntity>> observable = new MediatorLiveData<>();
        observable.setValue(null);
        LiveData<List<TodoEntity>> todoList = mTodoDataRepository.getTodoList();
        observable.addSource(todoList, observable::setValue);
        return observable;
    }

    public void completeTodo(Todo todo, boolean completed) {
        if (completed) {
            mTodoDataRepository.completeTodo(todo);
            Toast.makeText(getApplication().getBaseContext(),
                    R.string.todo_marked_complete, Toast.LENGTH_SHORT).show();
        } else {
            mTodoDataRepository.activateTodo(todo);
            Toast.makeText(getApplication().getBaseContext(),
                    R.string.todo_marked_active, Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteTodo(int todoId) {
        mTodoDataRepository.deleteTodo(todoId);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application mApplication;
        private final TodoDataRepository mRepository;

        public Factory(@NonNull Application application) {
            this.mApplication = application;
            this.mRepository = ((App) application).getRepository();
        }

        @SuppressWarnings("unchecked")
        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new TodoListViewModel(mApplication, mRepository);
        }
    }
}
