package com.example.myapplication.checkin_guest.viewModel.Executor;

import android.util.Log;

import com.example.myapplication.checkin_guest.callback.FireStorageExcutorListener;

import com.example.myapplication.checkin_guest.callback.GetLodgingTitleImg;
import com.example.myapplication.checkin_guest.data.FireStorageAttribute;
import com.example.myapplication.checkin_guest.model.Banner;
import com.example.myapplication.checkin_guest.model.LodgingItem;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class FireStorageExcutor {
    private final String TAG = FireStoreExcutor.class.getSimpleName();
    private FirebaseStorage storage;
    private FireStorageExcutorListener mListener;
    private FireStorageAttribute fireStorageAttribute;
    private GetLodgingTitleImg getLodgingTitleImgListener;
    LodgingItem lodgingItem=new LodgingItem();


    public FireStorageExcutor() {
        storage = FirebaseStorage.getInstance();
        fireStorageAttribute = FireStorageAttribute.getInstance();
    }

    public void setmListener(FireStorageExcutorListener mListener) {
        this.mListener = mListener;
    }

    //

    public void getBannerImage(ArrayList<Banner> arrayList) {
        StorageReference storageReference = storage.getReferenceFromUrl(FireStorageAttribute.getInstance().getSTORAGE_ROUTE());

        for (Banner banner : arrayList) {
            Log.d(TAG, FireStorageAttribute.getInstance().getSTORAGE_ROUTE() + "/" + fireStorageAttribute.getDOC_ROUTE_BANNER() + banner.getImg_path());
            StorageReference pathReference = storageReference.child(fireStorageAttribute.getDOC_ROUTE_BANNER() + banner.getImg_path());
            pathReference.getDownloadUrl().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d(TAG, "download Url onComplete");
                    String img_url = task.getResult().toString();
                    Log.d(TAG, img_url);
                    banner.setImg_url(img_url);
                }
                Log.d(TAG, String.valueOf(arrayList.size()));
                mListener.onSuccessGetBannerImg();
            });
        }
    }

    public void setGetLodgingTitleImg(GetLodgingTitleImg getLodgingTitleImg){
        this.getLodgingTitleImgListener = getLodgingTitleImg;
    }

    public void getLodgingImage(ArrayList<LodgingItem> arrayList){
        StorageReference storageReference = storage.getReferenceFromUrl(FireStorageAttribute.getInstance().getSTORAGE_ROUTE());

        for(LodgingItem lodgingItem:arrayList){
            Log.d(TAG, FireStorageAttribute.getInstance().getSTORAGE_ROUTE() + "/" + fireStorageAttribute.
                    getDOC_ROUTE_LODGING() + lodgingItem.getTitle_image_path());
            StorageReference pathReference = storageReference.child(fireStorageAttribute.
                    getDOC_ROUTE_LODGING() + lodgingItem.getTitle_image_path());
            pathReference.getDownloadUrl().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Log.d(TAG, "download Url onComplete");
                    String img_url = task.getResult().toString();
                    lodgingItem.setTitle_image_path(img_url);
                    getLodgingTitleImgListener.onSuccess();
                }
            });
        }
    }













}
