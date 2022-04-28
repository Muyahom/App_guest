package com.example.myapplication.checkin_guest.model;

import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;
import java.util.ArrayList;

public class LodgingItem implements Serializable {
    private String uid; // 숙소 식별
    private long acceptance;
    private String address;
    private String city;
    private long cnt_bad;
    private long cnt_badRoom;
    private long cnt_toilet;
    private ArrayList<String> convenience;
    private ArrayList<String> img_path;
    private long fare; // 숙소 1박 가격
    private String host_uid;
    private String introductory;
    private String name; // 숙소 이름
    private boolean nfc_distance; // 숙소 ncf 비밀번호 발급 숙소 여부
    private ArrayList<String> reservation_time_list;
    private ArrayList<String> review;
    private String state;
    private String title_image_path; // 숙소 타이틀 이미지
    private double latitude;
    private double longitude;
    private long type;

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

    public long getFare() {
        return fare;
    }

    public void setFare(long fare) {
        this.fare = fare;
    }

    public String getTitle_image_path() {
        return title_image_path;
    }

    public void setTitle_image_path(String title_image_path) {
        this.title_image_path = title_image_path;
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

    public ArrayList<String> getConvenience() {
        return convenience;
    }

    public void setConvenience(ArrayList<String> convenience_item) {
        this.convenience = convenience_item;
    }

    public long getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(long acceptance) {
        this.acceptance = acceptance;
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

    public ArrayList<String> getReview() {
        return review;
    }

    public void setReview(ArrayList<String> review) {
        this.review = review;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<String> getReservation_time_list() {
        return reservation_time_list;
    }

    public void setReservation_time_list(ArrayList<String> reservation_time_list) {
        this.reservation_time_list = reservation_time_list;
    }

    public String getHost_uid() {
        return host_uid;
    }

    public void setHost_uid(String host_uid) {
        this.host_uid = host_uid;
    }

    public long getCnt_bad() {
        return cnt_bad;
    }

    public void setCnt_bad(long cnt_bad) {
        this.cnt_bad = cnt_bad;
    }

    public long getCnt_badRoom() {
        return cnt_badRoom;
    }

    public void setCnt_badRoom(long cnt_badRoom) {
        this.cnt_badRoom = cnt_badRoom;
    }

    public long getCnt_toilet() {
        return cnt_toilet;
    }

    public void setCnt_toilet(long cnt_toilet) {
        this.cnt_toilet = cnt_toilet;
    }


    public void setGeoPoint(GeoPoint geoPoint) {

        this.latitude = geoPoint.getLatitude();
        this.longitude = geoPoint.getLongitude();
    }

    @Override
    public String toString() {
        return "LodgingItem{" +
                "uid='" + uid + '\'' +
                ", acceptance=" + acceptance +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", cnt_bad=" + cnt_bad +
                ", cnt_badRoom=" + cnt_badRoom +
                ", cnt_toilet=" + cnt_toilet +
                ", convenience=" + convenience +
                ", img_path=" + img_path +
                ", fare=" + fare +
                ", host_uid='" + host_uid + '\'' +
                ", introductory='" + introductory + '\'' +
                ", name='" + name + '\'' +
                ", nfc_distance=" + nfc_distance +
                ", reservation_time_list=" + reservation_time_list +
                ", review=" + review +
                ", state='" + state + '\'' +
                ", title_image_path='" + title_image_path + '\'' +
                ", type=" + type +
                '}';
    }
}
