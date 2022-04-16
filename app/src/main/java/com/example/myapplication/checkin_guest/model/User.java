package com.example.myapplication.checkin_guest.model;

import java.util.ArrayList;

public class User {
    private String email;
    private ArrayList<String> favorites;
    private String img_path;
    private String nickName;
    private int penalty;
    private String phoneNumber;
    private int point;
    private ArrayList<String> reservationList;

    public String getEmail() {
        return email;
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

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public ArrayList<String> getReservationList() {
        return reservationList;
    }

    public void setReservationList(ArrayList<String> reservationList) {
        this.reservationList = reservationList;
    }
}
