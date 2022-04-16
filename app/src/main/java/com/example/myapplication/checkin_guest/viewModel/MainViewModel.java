package com.example.myapplication.checkin_guest.viewModel;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.checkin_guest.callback.FireStorageExcutorListener;
import com.example.myapplication.checkin_guest.callback.FireStoreExcutorListener;
import com.example.myapplication.checkin_guest.model.Banner;
import com.example.myapplication.checkin_guest.model.ViewPageDataBanner;
import com.example.myapplication.checkin_guest.viewModel.Executor.FireStorageExcutor;
import com.example.myapplication.checkin_guest.viewModel.Executor.FireStoreExcutor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainViewModel extends ViewModel {
    private final String TAG = LoginViewModel.class.getSimpleName();
    private WeakReference<Activity> mActivityRef;
    private FirebaseUser user;

    private FireStoreExcutor fireStoreExcutor = new FireStoreExcutor();
    private FireStorageExcutor fireStorageExcutor = new FireStorageExcutor();

    //배너 관련 변수
    private MutableLiveData<ArrayList<Banner>> listBanner = new MutableLiveData<>();
    private ArrayList<Banner> listBannerTemp = new ArrayList<>();

    public MainViewModel() {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    // activity setting
    public void setParentContext(Activity parentContext) {
        mActivityRef = new WeakReference<>(parentContext);
    }

    //로그인 체크
    public boolean isLogin() {
        boolean check = false;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        this.user = mAuth.getCurrentUser();
        if (user != null) {
            check = true;
        }
        Log.d(TAG, String.valueOf(check));
        return check;
    }

    public void setFireStoreExcutorListener() {
        fireStoreExcutor.setmListner(getFireStoreExcutorListener());
    }

    public void setFireStorageExcutorListener(){
        fireStorageExcutor.setmListener(getFireStorageExcutorListener());
    }

    private FireStoreExcutorListener getFireStoreExcutorListener() {
        return new FireStoreExcutorListener() {
            @Override
            public void onSuccessGetBanner(QuerySnapshot querySnapshot) {
                for (QueryDocumentSnapshot result : querySnapshot) {
                    Log.d(TAG, String.valueOf(result.getData()));
                    Banner banner = new Banner();
                    banner.setContent((String) result.get("content"));
                    banner.setImg_path((String) result.get("img_path"));
                    banner.setTitle((String) result.get("title"));

                    listBannerTemp.add(banner);
                }
                getBannerImg();
            }

            @Override
            public void onFailedGetBanner() {

            }
        };
    }

    private FireStorageExcutorListener getFireStorageExcutorListener(){
        return new FireStorageExcutorListener(){

            @Override
            public void onSuccessGetBannerImg() {
                Log.d(TAG, "onSuccessGetBannerImg");
                listBanner.setValue(listBannerTemp);
            }

            @Override
            public void onFailedGetBannerImg() {

            }
        };
    }

    /*          배너 관련 메서드           */
    public MutableLiveData<ArrayList<Banner>> getListBanner() {
        return this.listBanner;
    }

    public void getBanner() {
        listBannerTemp.clear();
        fireStoreExcutor.getBanner();
    }

    private void getBannerImg(){
        fireStorageExcutor.getBannerImage(listBannerTemp);

    }
}
