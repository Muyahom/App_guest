package com.example.myapplication.checkin_guest.view.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.ActivityReservationBinding;
import com.example.myapplication.checkin_guest.util.Util;
import com.example.myapplication.checkin_guest.view.fragment.reservationWindow.FragReservation;

public class ReservationActivity extends AppCompatActivity {
    Fragment frag_reservation = new FragReservation();
    ActivityReservationBinding activityReservationBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityReservationBinding= DataBindingUtil.setContentView(this,R.layout.activity_reservation);
        // 검색창 UI 관련 소스코드, 상태바 투명 및 바텀내비게이션 높이에 따른 레이아웃 페딩 설정
        Util.transparency_statusBar(this);
        activityReservationBinding.constraint.setPadding(0, 0, 0, Util.getBottomNavigationHeight(getApplicationContext()));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragcontainer, frag_reservation).commit();

    }
}
