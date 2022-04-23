package com.example.myapplication.checkin_guest.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.adapter.RecyclerViewAdapterSearchResult;
import com.example.myapplication.checkin_guest.databinding.ActivitySearchResultBinding;
import com.example.myapplication.checkin_guest.model.LodgingItem;
import com.example.myapplication.checkin_guest.util.Util;
import com.example.myapplication.checkin_guest.viewModel.SearchResultViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.naver.maps.map.MapFragment;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {
    private final String TAG = "SearchResultActivity";
    private ActivitySearchResultBinding activitySearchResultBinding;
    private SearchResultViewModel searchResultViewModel;

    private final String SEARCHTYPE = "searchType";
    private final String SEARCHWORD = "searchWord";
    private final String SEARCHING = "검색 중....";
    private final String SEARCH_RESULT = "개의 검색 결과가 있습니다.";

    private BottomSheetBehavior bottomSheetBehavior;
    private RecyclerViewAdapterSearchResult recyclerViewAdapter_searchResult;

    private Intent searchIntent;
    private int searchType;
    private String city;

    @Override
    public void onBackPressed() {
        // 기존 뒤로가기 버튼의 기능을 막기위해 주석처리 또는 삭제
        // super.onBackPressed();
        if (bottomSheetBehavior.getState() == bottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(bottomSheetBehavior.STATE_HALF_EXPANDED);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchResultBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_result);

        searchResultViewModel = new ViewModelProvider(this, new ViewModelProvider
                .AndroidViewModelFactory(getApplication())).get(SearchResultViewModel.class);
        searchResultViewModel.setParentContext(this);

        init();

        // 검색창 UI 관련 소스코드, 상태바 투명 및 바텀내비게이션 높이에 따른 레이아웃 페딩 설정
        //statusbar 설정 코드
        Util.setStatusBarColor(this);
        Util.setStatusBarColor(this, 2);
        activitySearchResultBinding.txtSearchResult.setText(SEARCHING);

        searchIntent = getIntent();
        this.searchType = searchIntent.getIntExtra(SEARCHTYPE, searchType);
        Log.d(TAG, "searchType : " + searchType);

        searchResultViewModel.setSearchListener();

        searchResultViewModel.getLodgingMLList().observe(this, data -> {
            ArrayList<LodgingItem> lodgingList = searchResultViewModel.getLodgingMLList().getValue();
            if (lodgingList != null) {
                //recyclerview 및 맵 업데이트
                Log.d(TAG, " searchResultViewModel.getLodgingMLList() observe");
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                activitySearchResultBinding.txtSearchResult.setText(lodgingList.size()+SEARCH_RESULT);
                //recyclerview 업데이트
                recyclerViewAdapter_searchResult.set_mList(lodgingList);
                //맵 업데이트
            }else{
                //검색결과가 없는 경우
            }
        });

        searchResultViewModel.getIsLoading().observe(this, data->{
            boolean isLoading = searchResultViewModel.getIsLoading().getValue();
            if(isLoading) {
                //로딩 화면 활성화
                activitySearchResultBinding.constraintLottie.setVisibility(View.VISIBLE);
            }else{
                //로딩 화면 비활성화
                activitySearchResultBinding.constraintLottie.setVisibility(View.INVISIBLE);

            }
        });

        // 검색에 필요한 변수 세팅 및 검색 수행
        searchValueSetting();


    }

    private void init() {
        //bottomSheet관련 초기설정
        bottomSheetBehavior = BottomSheetBehavior.from((View) activitySearchResultBinding.bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setFitToContents(false);

        activitySearchResultBinding.recyclerviewSr.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerViewAdapter_searchResult = new RecyclerViewAdapterSearchResult(this);
        activitySearchResultBinding.recyclerviewSr.setAdapter(recyclerViewAdapter_searchResult);

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
    }

    private void searchValueSetting() {
        switch (this.searchType) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                this.city = searchIntent.getStringExtra(SEARCHWORD);
                Log.d(TAG, this.city);
                activitySearchResultBinding.txtRegion.setText(this.city);
                searchType4();
                break;
        }
    }

    private void searchType4() {
        Log.d(TAG, "search type 4");
        searchResultViewModel.search(city);
    }

    private void mapSetting() {
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
    }
}