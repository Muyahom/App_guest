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
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, frag_search).commit();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        activityMainBinding.linear.setPadding(0, 0, 0, getBottomNavigationHeight(getApplicationContext()));

    }

    private void init() {
        frag_search = new Frag_search();
    }

    // 휴대폰의 하단 네비게이션바가 있는지 검사
    public static boolean isUseBottomNavigation(Context context) {
        int id = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        boolean useSoftNavigation = context.getResources().getBoolean(id);
        return useSoftNavigation;
    }
    // 휴대폰의 하단 네비게이션 바가 존재한다면 높이를 반환
    public static int getBottomNavigationHeight(Context context) {
        int bottomNavigation = 0;
        int screenSizeType = (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK);
        if (screenSizeType != Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            //태블릿 예외처리
            int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                bottomNavigation = context.getResources().getDimensionPixelSize(resourceId);
            }
        }
        if (!isUseBottomNavigation(context)) bottomNavigation = 0;
        return bottomNavigation;
    }
}