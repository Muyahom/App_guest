package com.example.myapplication.checkin_guest.model;

import java.util.ArrayList;

public class Lodging {
    private String address;
    private int acceptance;
    private ArrayList<String> convenience;
    private ArrayList<String> img_path;
    private String introductory;
    private ArrayList<Review> review;
    private int type;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(int acceptance) {
        this.acceptance = acceptance;
    }

    public ArrayList<String> getConvenience() {
        return convenience;
    }

    public void setConvenience(ArrayList<String> convenience) {
        this.convenience = convenience;
    }

    public ArrayList<String> getImg_path() {
        return img_path;
    }

    public void setImg_path(ArrayList<String> img_path) {
        this.img_path = img_path;
    }

    public String getIntroductory() {
        return introductory;
    }

    public void setIntroductory(String introductory) {
        this.introductory = introductory;
    }

    public ArrayList<Review> getReview() {
        return review;
    }

    public void setReview(ArrayList<Review> review) {
        this.review = review;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
