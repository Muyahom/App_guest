package com.example.myapplication.checkin_guest.data;

public class FStoreDatabaseAttribute {
    private static FStoreDatabaseAttribute instance;
    private final String USER_COLLECTION = "user";
    private final String LODGING_COLLECTION = "lodging";
    private final String BANNER_COLLECTION = "banner";


    private FStoreDatabaseAttribute(){}

    public static FStoreDatabaseAttribute getInstance() {
        if(instance == null)
            instance = new FStoreDatabaseAttribute();
        return instance;
    }


    public String getUSER_COLLECTION() {
        return USER_COLLECTION;
    }

    public String getLODGING_COLLECTION() {
        return LODGING_COLLECTION;
    }

    public String getBANNER_COLLECTION() {
        return BANNER_COLLECTION;
    }
}
