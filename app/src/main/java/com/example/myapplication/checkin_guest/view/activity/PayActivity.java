package com.example.myapplication.checkin_guest.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.ActivityPayBinding;
import com.example.myapplication.checkin_guest.util.Util;

public class PayActivity extends AppCompatActivity {
    ActivityPayBinding activityPayBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        activityPayBinding= DataBindingUtil.setContentView(this,R.layout.activity_pay);
        super.onCreate(savedInstanceState);
        // 검색창 UI 관련 소스코드, 상태바 투명 및 바텀내비게이션 높이에 따른 레이아웃 페딩 설정
        Util.transparency_statusBar(this);
        activityPayBinding.asv.setPadding(0, 0, 0, Util.getBottomNavigationHeight(getApplicationContext()));
    }

}
