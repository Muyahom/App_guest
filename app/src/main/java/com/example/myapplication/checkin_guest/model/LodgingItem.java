package com.example.myapplication.checkin_guest.model;

import java.util.ArrayList;

public class LodgingItem {
    private String uid; // 숙소 식별
    private String name; // 숙소 이름
    private int fare; // 숙소 1박 가격
    private String url_title_image; // 숙소 타이틀 이미지
    private boolean nfc_distance; // 숙소 ncf 비밀번호 발급 숙소 여부
    private String address;
    private ArrayList<String> convenience_item;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public String getUrl_title_image() {
        return url_title_image;
    }

    public void setUrl_title_image(String url_title_image) {
        this.url_title_image = url_title_image;
    }

    public boolean isNfc_distance() {
        return nfc_distance;
    }

    public void setNfc_distance(boolean nfc_distance) {
        this.nfc_distance = nfc_distance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<String> getConvenience_item() {
        return convenience_item;
    }

    public void setConvenience_item(ArrayList<String> convenience_item) {
        this.convenience_item = convenience_item;
    }
}
