package com.example.myapplication.checkin_guest.viewModel;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.checkin_guest.callback.FireStorageExcutorListener;
import com.example.myapplication.checkin_guest.callback.GetBannerListener;
import com.example.myapplication.checkin_guest.callback.GetPushToken;
import com.example.myapplication.checkin_guest.callback.GetUserInfoListener;
import com.example.myapplication.checkin_guest.model.Banner;
import com.example.myapplication.checkin_guest.model.Guest;
import com.example.myapplication.checkin_guest.viewModel.Executor.FireStorageExcutor;
import com.example.myapplication.checkin_guest.viewModel.Executor.FireStoreExcutor;
import com.example.myapplication.checkin_guest.viewModel.Executor.FirebaseMessageExcutor;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainViewModel extends ViewModel {
    private final String TAG = MainViewModel.class.getSimpleName();
    private WeakReference<Activity> mActivityRef;
    private FirebaseUser user;
    private Guest guest = Guest.getInstance();

    private FireStoreExcutor fireStoreExcutor = new FireStoreExcutor();
    private FireStorageExcutor fireStorageExcutor = new FireStorageExcutor();

    //배너 관련 변수
    private MutableLiveData<ArrayList<Banner>> listBanner = new MutableLiveData<>();
    private ArrayList<Banner> listBannerTemp = new ArrayList<>();

    private FirebaseMessageExcutor firebaseMessageExcutor = new FirebaseMessageExcutor();
    private MutableLiveData<Boolean> isSetUserInfo = new MutableLiveData<Boolean>();
    private String push_token;


    public MainViewModel() {
        Logger.addLogAdapter(new AndroidLogAdapter());
        Log.d(TAG, "create");
    }

    /* FirebaseToken 관련 */

    public GetPushToken getPushToken(){
        return token -> {
            Log.d(TAG, "getPushToken : " + token);
            push_token = token;
            Log.d(TAG, "push_token : " + push_token);
        };
    }

    public String getPush_token(){
        return push_token;
    }

    public void setGetPushTokenListener(){
        firebaseMessageExcutor.setGetPushToken(getPushToken());
    }

    public void getFirebaseToken(){
        firebaseMessageExcutor.getToken();
    }

    /* token값 비교 */
    public void compareToken(){
        Log.d(TAG, "compareToken");

        if(guest.getPushToken() == null){
            fireStoreExcutor.setPushToken(push_token, FirebaseAuth.getInstance().getUid());
        }else if(guest.getPushToken().equals(push_token)){
            Log.d(TAG, "true");
        }else{
            Log.d(TAG, "token : " + push_token + " getPushToken : " + guest.getPushToken());
            fireStoreExcutor.setPushToken(push_token, FirebaseAuth.getInstance().getUid());
        }
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
        fireStoreExcutor.setGetBannerListner(getFireStoreExcutorListener());
    }

    public void setFireStorageExcutorListener(){
        fireStorageExcutor.setmListener(getFireStorageExcutorListener());
    }

    private GetBannerListener getFireStoreExcutorListener() {
        return new GetBannerListener() {
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
                Toast.makeText(mActivityRef.get(), "회원정보를 받아오지 못했습니다.", Toast.LENGTH_SHORT).show();
            }
        };
    }

    /*  FireStore로 부터 계정 정보를 제공받음. */

    public void setGetUserInfoListener(){
        fireStoreExcutor.setGetUserInfoListener(getUserInfoListener());
    }

    public MutableLiveData<Boolean> getIsSetUserInfo(){
        return isSetUserInfo;
    }

    private GetUserInfoListener getUserInfoListener(){
        return new GetUserInfoListener() {
            @Override
            public void onSuccess(Task<DocumentSnapshot> task) {
                Log.d(TAG, "getUserInfoSuccess");
                DocumentSnapshot documentSnapshot = task.getResult();
                guest.setEmail((String) documentSnapshot.get("email"));
                guest.setFavorites((ArrayList<String>) documentSnapshot.get("favorites"));
                guest.setImg_path((String) documentSnapshot.get("img_path"));
                guest.setNicName((String) documentSnapshot.get("nickName"));
                guest.setPenalty((long) documentSnapshot.get("penalty"));
                guest.setPhoneNumber((String) documentSnapshot.get("phoneNumber"));
                guest.setPoint((long) documentSnapshot.get("point"));
                guest.setReservationList((ArrayList<String>) documentSnapshot.get("reservationList"));
                String token = (String)documentSnapshot.get("pushtoken");
                guest.setPushToken(token);
                Log.d(TAG, guest.toString());
                isSetUserInfo.setValue(true);
            }
            @Override
            public void onFailed(Task<DocumentSnapshot> task) {
                Toast.makeText(mActivityRef.get(), "회원정보를 가져오는데 실패했습니다. 재로그인 해주세요.", Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void getUserInfo(){
        isSetUserInfo.setValue(false);
        fireStoreExcutor.getUserInfo();
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
