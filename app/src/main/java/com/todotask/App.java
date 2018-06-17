package com.todotask;

import android.app.Application;

import com.todotask.data.TodoDataRepository;
import com.todotask.data.local.AppDatabase;

public class App extends Application {
    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public TodoDataRepository getRepository() {
        return TodoDataRepository.getInstance(new AppExecutors(), getDatabase().todoDao());
    }
}
