package com.example.myapplication.checkin_guest.callback;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public interface GetBannerListener {
    public void onSuccessGetBanner(QuerySnapshot querySnapshot);
    public void onFailedGetBanner();
}
