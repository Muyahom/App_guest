package com.example.myapplication.checkin_guest.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.ActivityMainBinding;
import com.example.myapplication.checkin_guest.util.Util;
import com.example.myapplication.checkin_guest.view.fragment.Frag_search;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private ActivityMainBinding activityMainBinding;

    private Fragment frag_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();

        // frag_search인 경우 처리할 UI 관련 소스코드, 상태바 투명 및 바텀내비게이션 높이에 따른 레이아웃 페딩 설정
        Util.transparency_statusBar(this);
        activityMainBinding.linear.setPadding(0, 0, 0, Util.getBottomNavigationHeight(getApplicationContext()));

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, frag_search).commit();

    }

    private void init() {
        frag_search = new Frag_search();
    }

}