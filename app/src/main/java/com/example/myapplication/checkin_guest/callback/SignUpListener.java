package com.example.myapplication.checkin_guest.callback;

import com.google.android.gms.tasks.Task;

public interface SignUpListener {
    public void onSuccessInsertUserInfo();
    public void onFailedInsertUserInfo();
    public void onSuccessRegisterAuth(Task task, String uid);
    public void onFailedRegisterAuth(Task task);
}
