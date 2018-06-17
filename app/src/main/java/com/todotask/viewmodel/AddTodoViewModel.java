package com.todotask.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.todotask.App;
import com.todotask.R;
import com.todotask.data.TodoDataRepository;
import com.todotask.data.entity.TodoEntity;

public class AddTodoViewModel extends AndroidViewModel {
    public final ObservableBoolean loading = new ObservableBoolean(false);
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> description = new ObservableField<>();
    public TodoEntity mTodoEntity;
    private TodoDataRepository mTodoDataRepository;

    private AddTodoViewModel(@NonNull Application application, @NonNull TodoDataRepository repository) {
        super(application);
        this.mTodoDataRepository = repository;
    }

    public void saveTodo() {
        String titleVal = title.get();
        String descVal = description.get();
        if (titleVal != null && descVal != null && !titleVal.trim().isEmpty() && !descVal.trim().isEmpty()) {
            TodoEntity entity = new TodoEntity(titleVal.trim(), descVal.trim());
            if (mTodoEntity != null) {
                entity.setId(mTodoEntity.getId());
                entity.setCompleted(mTodoEntity.isCompleted());
            }
            mTodoDataRepository.saveTodo(entity);
        } else
            Toast.makeText(getApplication().getBaseContext(),
                    R.string.empty_task_message, Toast.LENGTH_SHORT).show();
    }

    public LiveData<TodoEntity> getTodo(int todoId) {
        MediatorLiveData<TodoEntity> observable = new MediatorLiveData<>();
        observable.setValue(null);
        LiveData<TodoEntity> todo = mTodoDataRepository.getTodo(todoId);
        observable.addSource(todo, observable::setValue);
        return observable;
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
            return (T) new AddTodoViewModel(mApplication, mRepository);
        }
    }
}
