package com.example.myapplication.checkin_guest.viewModel;

import android.app.Activity;

import androidx.lifecycle.ViewModel;

import com.example.myapplication.checkin_guest.viewModel.Executor.RealTimeDatabaseExcutor;

import java.lang.ref.WeakReference;

public class ChattingViewModel extends ViewModel {
    private final String TAG = ChattingViewModel.class.getSimpleName();
    private WeakReference<Activity> mActivityRef;
    private RealTimeDatabaseExcutor realTimeDatabaseExcutor = new RealTimeDatabaseExcutor();

    public ChattingViewModel(){}

    // activity setting
    public void setParentContext(Activity parentContext){
        mActivityRef = new WeakReference<>(parentContext);
    }

    public void get(){

    }
}
