package com.example.myapplication.checkin_guest.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.ActivitySelectBinding;
import com.example.myapplication.checkin_guest.util.Util;
import com.example.myapplication.checkin_guest.view.fragment.selectWindow.Frag_select;
import com.naver.maps.map.MapFragment;

public class SelectActivity extends AppCompatActivity {
    private Fragment frag_select;
    private ActivitySelectBinding activitySelectBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySelectBinding = DataBindingUtil.setContentView(this, R.layout.activity_select);


        init();
        // 검색창 UI 관련 소스코드, 상태바 투명 및 바텀내비게이션 높이에 따른 레이아웃 페딩 설정
        Util.transparency_statusBar(this);
        activitySelectBinding.linear.setPadding(0, 0, 0, Util.getBottomNavigationHeight(getApplicationContext()));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view2, frag_select).commit();



        FragmentManager fm = getSupportFragmentManager();


    }

    private void init() {
        frag_select = new Frag_select();
    }

}