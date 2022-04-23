package com.example.myapplication.checkin_guest.callback;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public interface SearchListener {
    public void onSuccess(QuerySnapshot querySnapshot);
}
