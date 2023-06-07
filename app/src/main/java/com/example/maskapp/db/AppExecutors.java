package com.example.maskapp.db;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {
    private final Executor mIOExecutor;
    public AppExecutors(){
        mIOExecutor = Executors.newSingleThreadExecutor();
    }
    public Executor getDiskIO(){
        return mIOExecutor;
    }
}
