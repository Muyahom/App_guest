package com.example.myapplication.checkin_guest.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.ActivitySearchBinding;
import com.example.myapplication.checkin_guest.util.Util;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding activitySearchBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        // frag_search인 경우 처리할 UI 관련 소스코드, 상태바 투명 및 바텀내비게이션 높이에 따른 레이아웃 페딩 설정

        init();
        // frag_search인 경우 처리할 UI 관련 소스코드, 상태바 투명 및 바텀내비게이션 높이에 따른 레이아웃 페딩 설정
        Util.transparency_statusBar(this);
        activitySearchBinding.constraint.setPadding(0, 0, 0, Util.getBottomNavigationHeight(getApplicationContext()));
    }

    private void init(){
    }
}