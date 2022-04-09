package com.example.myapplication.checkin_guest.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.ActivityMainBinding;
import com.example.myapplication.checkin_guest.util.Util;
import com.example.myapplication.checkin_guest.view.fragment.mainWindow.Frag_chatting;
import com.example.myapplication.checkin_guest.view.fragment.mainWindow.Frag_favorite;
import com.example.myapplication.checkin_guest.view.fragment.mainWindow.Frag_myInfo;
import com.example.myapplication.checkin_guest.view.fragment.mainWindow.Frag_search;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private ActivityMainBinding activityMainBinding;

    private Fragment frag_search, fragChatting, frag_favorite, frag_myInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();

        searchScreenSetting();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, frag_search).commit();

        activityMainBinding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navigation_main:
                    moveFrag(frag_search);
                    return true;
                case R.id.navigation_favorite:
                    basicScreenSetting();
                    moveFrag(frag_favorite);
                    return true;
                case R.id.navigation_message:
                    basicScreenSetting();
                    moveFrag(fragChatting);
                    return true;
                case R.id.navigation_myinfor:
                    Util.setStatusBarColor(this, 1);
                    moveFrag(frag_myInfo);
                    return true;
            }
            return false;
        });
    }

    private void init() {
        frag_search = new Frag_search();
        fragChatting = new Frag_chatting();
        frag_favorite = new Frag_favorite();
        frag_myInfo = new Frag_myInfo();
    }

    private void moveFrag(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment_container_view, fragment).commit();
    }

    private void searchScreenSetting(){
        // frag_search인 경우 처리할 UI 관련 소스코드, 상태바 투명 및 바텀내비게이션 높이에 따른 레이아웃 페딩 설정
        Util.transparency_statusBar(this);
        activityMainBinding.linear.setPadding(0, 0, 0, Util.getBottomNavigationHeight(getApplicationContext()));
    }

    private void basicScreenSetting() {
        Util.setStatusBarColor(this, 2);
    }
}