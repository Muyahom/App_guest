package com.example.myapplication.checkin_guest.callback;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public interface GetUserInfoListener {
    public void onSuccess(Task<DocumentSnapshot> task);
    public void onFailed(Task<DocumentSnapshot> task);

}
