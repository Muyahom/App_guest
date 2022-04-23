package com.example.myapplication.checkin_guest.viewModel.Executor;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.checkin_guest.callback.GetBannerListener;
import com.example.myapplication.checkin_guest.callback.GetUserInfoListener;
import com.example.myapplication.checkin_guest.callback.SearchListener;
import com.example.myapplication.checkin_guest.data.FStoreDatabaseAttribute;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class FireStoreExcutor {
    private final String TAG = FireStoreExcutor.class.getSimpleName();
    private GetBannerListener getBannerListener;
    private GetUserInfoListener getUserInfoListener;
    private SearchListener searchListener;

    private FirebaseFirestore db;
    private FStoreDatabaseAttribute attribute;

    public FireStoreExcutor() {
        db = FirebaseFirestore.getInstance();
        attribute = FStoreDatabaseAttribute.getInstance();
    }

    public void setGetUserInfoListener(GetUserInfoListener getUserInfoListener) {
        this.getUserInfoListener = getUserInfoListener;
    }

    public void setGetBannerListner(GetBannerListener getBannerListener) {
        this.getBannerListener = getBannerListener;
    }

    public void setSearchListener(SearchListener searchListener){
        this.searchListener = searchListener;
    }

    public void getUserInfo() {
        String userUid = FirebaseAuth.getInstance().getUid();
        Log.d(TAG, userUid);
        this.db.collection(attribute.getUSER_COLLECTION())
                .document(userUid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        getUserInfoListener.onSuccess(task);
                    }else{
                        getUserInfoListener.onFailed(task);
                    }
                });
    }

    public void getBanner() {
        this.db.collection(attribute.getBANNER_COLLECTION())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    getBannerListener.onSuccessGetBanner(queryDocumentSnapshots);
                });
    }

    // city만 설정된 검색 조건
    public void getSearchLodging(String city){
        Log.d(TAG, "검색 수행 getSearchLodging : " + city);
        CollectionReference lodgingRef = db.collection(attribute.getLODGING_COLLECTION());
        lodgingRef.whereEqualTo("city", city).get()
                .addOnSuccessListener(querySnapshot -> {
                    Log.d(TAG, "onSuccessListener");
                    searchListener.onSuccess(querySnapshot);
                }).addOnFailureListener(e -> {
                    Log.d(TAG, "onFailureListner");
                   e.printStackTrace();
                });
    }

    // get
    public void setPushToken(String token, String uid){
        Map<String, Object> pushToken = new HashMap<>();
        pushToken.put("pushtoken", token);

        CollectionReference userRef = db.collection(attribute.getUSER_COLLECTION());
        userRef.document(uid).update(pushToken)
                .addOnSuccessListener(unused -> Log.d(TAG, "DocumentSnapshot successfully written!"));
    }

}