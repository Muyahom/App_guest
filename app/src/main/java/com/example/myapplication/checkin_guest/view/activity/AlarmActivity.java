package com.example.myapplication.checkin_guest.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.adapter.ViewPagerAdapterAlarm;
import com.example.myapplication.checkin_guest.databinding.ActivityAlarmBindingImpl;
import com.google.android.material.tabs.TabLayout;

public class AlarmActivity extends AppCompatActivity {
    private ActivityAlarmBindingImpl alarmBinding;
    private ViewPagerAdapterAlarm adapterAlarm;
    @Override
    //알람 액티비티
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ViewPager vp= findViewById(R.id.viewpager);
        //뷰페이저활용 탭화면 전환
        ViewPagerAdapterAlarm adapterAlarm = new ViewPagerAdapterAlarm(getSupportFragmentManager());
        vp.setAdapter(adapterAlarm);
        TabLayout tabLayout=findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(vp);

        ImageView close=findViewById(R.id.im_x);
        //취소버튼 누르면 종료
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });










    }
}
