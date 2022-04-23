package com.example.myapplication.checkin_guest.view.activity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.ActivityAccountBinding;
import com.example.myapplication.checkin_guest.util.Util;

public class AccountActivity extends AppCompatActivity {
    ActivityAccountBinding account_binding;

    @Override
    //계정관리 액티비티
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        account_binding = DataBindingUtil.setContentView(this, R.layout.activity_account);
        // 검색창 UI 관련 소스코드, 상태바 투명 및 바텀내비게이션 높이에 따른 레이아웃 페딩 설정
        Util.transparency_statusBar(this);
        account_binding.linear.setPadding(0, 0, 0, Util.getBottomNavigationHeight(getApplicationContext()));

        //취소버튼 누르면 종료
        account_binding.imX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }
}
