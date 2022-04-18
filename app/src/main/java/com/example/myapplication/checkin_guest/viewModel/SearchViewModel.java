package com.example.myapplication.checkin_guest.viewModel;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.checkin_guest.callback.RTListener;
import com.example.myapplication.checkin_guest.viewModel.Executor.RealTimeDatabaseExcutor;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SearchViewModel extends ViewModel {
    private final String TAG = SearchViewModel.class.getSimpleName();
    private WeakReference<Activity> mActivity;

    //RealTimeDataBase 처리 관련 변수
    private RealTimeDatabaseExcutor realTimeDatabaseExcutor = new RealTimeDatabaseExcutor();

    //city_list
    private MutableLiveData<ArrayList<String>> city_list = new MutableLiveData<>();
    ;

    public SearchViewModel() {

    }

    //activity setting
    public void setParentContext(Activity parentContext) {
        mActivity = new WeakReference<>(parentContext);
        realTimeDatabaseExcutor.setRtListener(getRTListener());
    }

    private void finishActivity() {
        if (mActivity.get() != null)
            mActivity.get().finish();
    }

    private RTListener getRTListener() {
        return new RTListener() {
            @Override
            public void notifyGetCitySuccess(ArrayList<String> city_result) {
                city_list.setValue(city_result);
            }
        };
    }

    /*
           RealTimeDatabase 관련 메서드 모음
                                                */
    //RealTimeDataBase 처리를 위한 RTDatabase 생성
    public LiveData<ArrayList<String>> getCityList() {
        return city_list;
    }

    public void onRequestGetCityList() {
        Log.d(TAG, "onRequestgetCityList");
        realTimeDatabaseExcutor.getCityList();
    }
}
