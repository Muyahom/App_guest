package com.example.myapplication.checkin_guest.view.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.view.fragment.reservationWindow.FragReservation;

public class ReservationActivity extends AppCompatActivity {
    Fragment frag_reservation = new FragReservation();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragcontainer, frag_reservation).commit();

    }
}
