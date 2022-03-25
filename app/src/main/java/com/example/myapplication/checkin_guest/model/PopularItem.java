package com.example.myapplication.checkin_guest.model;

import android.graphics.Bitmap;

public class PopularItem {
    private Bitmap bitmap;
    private int uid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
