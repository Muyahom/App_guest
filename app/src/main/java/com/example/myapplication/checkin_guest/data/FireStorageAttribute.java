package com.example.myapplication.checkin_guest.data;

public class FireStorageAttribute {
    private final String TAG = FireStorageAttribute.class.getSimpleName();
    private static FireStorageAttribute instance;
    private final String STORAGE_ROUTE = "gs://moyahome-23245.appspot.com";
    private final String DOC_ROUTE_LODGING = "banner/";

    private FireStorageAttribute(){}

    public static FireStorageAttribute getInstance(){
        if(instance == null){
            instance = new FireStorageAttribute();
        }
        return instance;
    }

    public String getSTORAGE_ROUTE() {
        return STORAGE_ROUTE;
    }

    public String getDOC_ROUTE_LODGING() {
        return DOC_ROUTE_LODGING;
    }
}
