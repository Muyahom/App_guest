package com.example.myapplication.checkin_guest.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.ActivityMainBinding;
import com.example.myapplication.checkin_guest.util.Util;
import com.example.myapplication.checkin_guest.view.fragment.mainWindow.Frag_chatting;
import com.example.myapplication.checkin_guest.view.fragment.mainWindow.Frag_favorite;
import com.example.myapplication.checkin_guest.view.fragment.mainWindow.Frag_myInfo;
import com.example.myapplication.checkin_guest.view.fragment.mainWindow.Frag_search;
import com.example.myapplication.checkin_guest.viewModel.LoginViewModel;
import com.example.myapplication.checkin_guest.viewModel.MainViewModel;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding activityMainBinding;
    private MainViewModel mainViewModel;

    //뒤로가기 종료
    private long backKeyPressedTime = 0;

    private Fragment frag_search, fragChatting, frag_favorite, frag_myInfo;

    @Override
    public void onBackPressed() {
        // 기존 뒤로가기 버튼의 기능을 막기위해 주석처리 또는 삭제
        // super.onBackPressed();
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        } else if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            ActivityCompat.finishAffinity(this);
            moveTaskToBack(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider
                .AndroidViewModelFactory(getApplication())).get(MainViewModel.class);
        mainViewModel.setParentContext(this);

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

    @Override
    protected void onStart() {
        super.onStart();
        Logger.d(TAG, "자동로그인 기능 실행");
        // google 및 email 로그인 여부 확인
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
//        fragmentTransaction.addToBackStack(null);
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