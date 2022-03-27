package com.example.myapplication.checkin_guest.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.ActivitySearchBinding;
import com.example.myapplication.checkin_guest.util.Util;
import com.example.myapplication.checkin_guest.view.fragment.Frag_searchWindow1;
import com.example.myapplication.checkin_guest.view.fragment.Frag_searchWindow2;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding activitySearchBinding;
    Fragment frag_searchWindow1, frag_searchWindow2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        init();
        // 검색창 UI 관련 소스코드, 상태바 투명 및 바텀내비게이션 높이에 따른 레이아웃 페딩 설정
        Util.transparency_statusBar(this);
        activitySearchBinding.fragcontainer.setPadding(0, 0, 0, Util.getBottomNavigationHeight(getApplicationContext()));

        getSupportFragmentManager().beginTransaction().replace(R.id.fragcontainer, frag_searchWindow1).commit();
    }

    private void init(){
        frag_searchWindow1 = new Frag_searchWindow1();
        frag_searchWindow2 = new Frag_searchWindow2();
    }

    public void move_frag(int move){
        switch (move){
            case 1:
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.fragcontainer, frag_searchWindow2).commit();

                break;
            case 2:
            case 3:
        }
    }
}