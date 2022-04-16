package com.example.myapplication.checkin_guest.viewModel.Executor;

import android.util.Log;

import com.example.myapplication.checkin_guest.callback.FireStorageExcutorListener;
import com.example.myapplication.checkin_guest.data.FireStorageAttribute;
import com.example.myapplication.checkin_guest.model.Banner;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class FireStorageExcutor {
    private final String TAG = FireStoreExcutor.class.getSimpleName();
    private FirebaseStorage storage;
    private FireStorageExcutorListener mListener;
    private FireStorageAttribute fireStorageAttribute;

    private int countBanner = 0;

    public FireStorageExcutor() {
        storage = FirebaseStorage.getInstance();
        fireStorageAttribute = FireStorageAttribute.getInstance();
    }

    public void setmListener(FireStorageExcutorListener mListener){
        this.mListener = mListener;
    }

    public void getBannerImage(ArrayList<Banner> arrayList) {
        StorageReference storageReference = storage.getReferenceFromUrl(FireStorageAttribute.getInstance().getSTORAGE_ROUTE());
        countBanner = 0;

        for (Banner banner : arrayList) {
            Log.d(TAG, FireStorageAttribute.getInstance().getSTORAGE_ROUTE()+"/"+fireStorageAttribute.getDOC_ROUTE_LODGING() + banner.getImg_path());
            StorageReference pathReference = storageReference.child(fireStorageAttribute.getDOC_ROUTE_LODGING() + banner.getImg_path());
            pathReference.getDownloadUrl().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Log.d(TAG, "download Url onComplete");
                    String img_url = task.getResult().toString();
                    Log.d(TAG, img_url);
                    banner.setImg_url(img_url);
                    countBanner += 1;
                }else{
                    countBanner += 1;
                }
                Log.d(TAG, arrayList.size() + " " + countBanner);
                if(countBanner == arrayList.size()){
                    mListener.onSuccessGetBannerImg();
                }
            });
        }

    }
}
