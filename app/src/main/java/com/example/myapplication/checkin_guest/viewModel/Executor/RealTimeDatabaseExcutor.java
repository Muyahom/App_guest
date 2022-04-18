package com.example.myapplication.checkin_guest.viewModel.Executor;

import android.util.Log;

import com.example.myapplication.checkin_guest.callback.RTListener;
import com.example.myapplication.checkin_guest.data.RTDatabaseAttribute;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RealTimeDatabaseExcutor {
    private final String TAG = RealTimeDatabaseExcutor.class.getSimpleName();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private RTDatabaseAttribute rtDatabaseAttribute = RTDatabaseAttribute.getInstance();
    private ArrayList<String> city_result = null;
    private RTListener rtListener;

    public void setRtListener(RTListener rtListener) {
        this.rtListener = rtListener;
    }

    //지역 목록 가져오기
    public void getCityList() {
        Log.d(TAG, "getCityList");
        DatabaseReference ref = database.getReference();
        ref.child(rtDatabaseAttribute.getCITY()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                city_result = ((ArrayList<String>) task.getResult().getValue());
                Log.d(TAG, city_result.toString());
                rtListener.notifyGetCitySuccess(city_result);
            }
        });
    }
}
