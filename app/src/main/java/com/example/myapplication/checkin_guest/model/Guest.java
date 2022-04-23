package com.example.myapplication.checkin_guest.model;

import java.util.ArrayList;

public class Guest {
    private static Guest instance;
    private String pushToken;
    private String email;
    private ArrayList<String> favorites;
    private String img_path;
    private String nickName;
    private long penalty;
    private String phoneNumber;
    private long point;
    private ArrayList<String> reservationList;

    private Guest(){}

    public static Guest getInstance(){
        if(instance == null){
            instance = new Guest();
        }
        return instance;
    }

    public String getEmail(Object email) {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(ArrayList<String> favorites) {
        this.favorites = favorites;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getNicName() {
        return nickName;
    }

    public void setNicName(String nicName) {
        this.nickName = nicName;
    }

    public long getPenalty() {
        return penalty;
    }

    public void setPenalty(long penalty) {
        this.penalty = penalty;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getPoint() {
        return point;
    }

    public void setPoint(long point) {
        this.point = point;
    }

    public ArrayList<String> getReservationList() {
        return reservationList;
    }

    public void setReservationList(ArrayList<String> reservationList) {
        this.reservationList = reservationList;
    }

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }

    @Override
    public String toString() {
        return "Guest{" +
                "pushToken='" + pushToken + '\'' +
                ", email='" + email + '\'' +
                ", favorites=" + favorites +
                ", img_path='" + img_path + '\'' +
                ", nickName='" + nickName + '\'' +
                ", penalty=" + penalty +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", point=" + point +
                ", reservationList=" + reservationList +
                '}';
    }
}
