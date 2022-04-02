package com.example.myapplication.checkin_guest.model;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String email;
    private ArrayList<String> favorites;
    private String img_path;
    private String nicName;
    private int penalty;
    private String phoneNumber;
    private String point;
    private ArrayList<String> reservationList;


}
