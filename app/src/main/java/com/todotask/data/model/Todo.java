package com.todotask.data.model;

public interface Todo {
    int getId();

    String getTitle();

    String getDescription();

    boolean isCompleted();
}
