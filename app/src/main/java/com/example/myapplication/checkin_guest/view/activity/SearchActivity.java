package com.example.myapplication.checkin_guest.view.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.ActivitySearchBinding;
import com.example.myapplication.checkin_guest.util.Util;
import com.example.myapplication.checkin_guest.view.fragment.searchWindow.Frag_searchWindow1;
import com.example.myapplication.checkin_guest.view.fragment.searchWindow.Frag_searchWindow2;
import com.example.myapplication.checkin_guest.view.fragment.searchWindow.Frag_searchWindow3;
import com.example.myapplication.checkin_guest.viewModel.LoginViewModel;
import com.example.myapplication.checkin_guest.viewModel.SearchViewModel;

public class SearchActivity extends AppCompatActivity {
    private final String TAG = "SearchActivity";
    private ActivitySearchBinding activitySearchBinding;
    private SearchViewModel searchViewModel;
    private Fragment frag_searchWindow1, frag_searchWindow2, frag_searchWindow3;

    private String searchWord;
    private boolean isPeriodCheck;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        // view model 사용을 위한 초기화 작업
        searchViewModel = new ViewModelProvider(this, new ViewModelProvider
                .AndroidViewModelFactory(getApplication())).get(SearchViewModel.class);
        searchViewModel.setParentContext(this);

        init();

        // 검색창 UI 관련 소스코드, 상태바 투명 및 바텀내비게이션 높이에 따른 레이아웃 페딩 설정
        Util.transparency_statusBar(this);
        activitySearchBinding.fragcontainer.setPadding(0, 0, 0, Util.getBottomNavigationHeight(getApplicationContext()));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragcontainer, frag_searchWindow1).commit();
    }

    private void init(){
        frag_searchWindow1 = new Frag_searchWindow1();
        frag_searchWindow2 = new Frag_searchWindow2();
        frag_searchWindow3 = new Frag_searchWindow3();
    }

    public void move_frag(int move){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (move){
            case 1:
                fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.fragcontainer, frag_searchWindow2).commit();
                break;
            case 2:
                fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.fragcontainer, frag_searchWindow3).commit();
                break;
            case 3:
        }
    }

    public void setSearchWord(String searchWord){
        Log.d(TAG, "검색어 : " + searchWord);
        this.searchWord = searchWord;
    }

    public void setIsPeriodCheck(boolean check){
        this.isPeriodCheck = check;
    }
}