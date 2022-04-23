package com.example.myapplication.checkin_guest.view.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.ActivitySearchBinding;
import com.example.myapplication.checkin_guest.util.Util;
import com.example.myapplication.checkin_guest.view.fragment.searchWindow.Frag_searchWindow1;
import com.example.myapplication.checkin_guest.view.fragment.searchWindow.Frag_searchWindow2;
import com.example.myapplication.checkin_guest.view.fragment.searchWindow.Frag_searchWindow3;
import com.example.myapplication.checkin_guest.viewModel.SearchViewModel;

public class SearchActivity extends AppCompatActivity {
    private final String TAG = "SearchActivity";
    private ActivitySearchBinding activitySearchBinding;
    private SearchViewModel searchViewModel;
    private Fragment frag_searchWindow1, frag_searchWindow2, frag_searchWindow3;

    private final String SEARCHTYPE = "searchType";
    private final String SEARCHWORD = "searchWord";

    private String searchWord;
    private boolean isPeriodCheck;
    private boolean isConditionSet;
    private String startP;
    private String endP;
    private int badCount;
    private int badRoomCount;
    private int toiletCount;
    private int checkTypeNum;
    private int searchType;

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

        searchViewModel.setSearchListener();
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

    public void setPeriod(long startP, long endP){
        Log.d(TAG, "시작 : " + startP + " 끝 : " + endP);
        this.startP = String.valueOf(startP); //13자리
        this.endP = String.valueOf(endP); //13자리
    }

    public void setIsPeriodCheck(boolean check){
        this.isPeriodCheck = check;
    }

    public void setConditionSet(boolean isConditionSet){
        this.isConditionSet = isConditionSet;

        Log.d(TAG, "검색 조건 : " + isConditionSet +  isPeriodCheck);

        //검색 작업 수행
        if(isPeriodCheck && isConditionSet){
            // 조건이 전부 설정된 경우
            this.searchType = 1;
            intent_result();
        }else if(isPeriodCheck){
            // 기간 조건만 설정된 경우
            this.searchType = 2;
            intent_result();
        }else if(isConditionSet){
            // 마지막 조건들만 설정 된 경우
            this.searchType = 3;
            intent_result();
        }else{
            // 조건이 설정되지 않은 경우
            this.searchType = 4;
            intent_result();

        }
    }

    public void setCondition(int badCount, int badRoomCount, int toiletCount, int checkTypeNum){
        this.badCount = badCount;
        this.badRoomCount = badRoomCount;
        this.toiletCount = toiletCount;
        this.checkTypeNum = checkTypeNum;
    }

    private void intent_result(){
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra(SEARCHTYPE, this.searchType);
        switch (this.searchType){
            case 1 :
                break;
            case 2 :
                break;
            case 3:
                break;
            case 4:
                Log.d(TAG,this.searchWord);
                intent.putExtra(SEARCHWORD, this.searchWord);
                break;
            default:
                Log.d(TAG, "error");
                return;
        }
        startActivity(intent);
    }

}