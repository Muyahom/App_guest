package com.example.myapplication.checkin_guest.data;

public class RTDatabaseAttribute {
    private static RTDatabaseAttribute instance;
    private final String CITY = "city"; //지역리스트 키값
    private final String CHATTING = "chat"; //채팅리스트 키값
    private final String REVIEW = "review"; //리뷰리스트 키값

    private RTDatabaseAttribute(){}

    public static RTDatabaseAttribute getInstance(){
        if(instance == null)
            instance = new RTDatabaseAttribute();
        return instance;
    }

    public String getCITY() {
        return CITY;
    }

    public String getCHATTING() {
        return CHATTING;
    }

    public String getREVIEW() {
        return REVIEW;
    }
}
