package com.example.myapplication.checkin_guest.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.adapter.RecyclerViewAdapter_searchResult;
import com.example.myapplication.checkin_guest.databinding.ActivitySearchResultBinding;
import com.example.myapplication.checkin_guest.model.LodgingItem;
import com.example.myapplication.checkin_guest.util.Util;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {
    private final String TAG = "SearchResultActivity";
    private ActivitySearchResultBinding activitySearchResultBinding;
    private BottomSheetBehavior bottomSheetBehavior;
    private Util util;
    private RecyclerViewAdapter_searchResult recyclerViewAdapter_searchResult;
    private ArrayList<LodgingItem> list_lodging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchResultBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_result);
        // 검색창 UI 관련 소스코드, 상태바 투명 및 바텀내비게이션 높이에 따른 레이아웃 페딩 설정
        init();

        //statusbar 설정 코드
        util.setStatusBarColor(this);
        util.setStatusBarColor(this, 2);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
        bottomSheetBehavior.setFitToContents(false);
        insert();




    }
    public void init(){
        bottomSheetBehavior = BottomSheetBehavior.from((View) activitySearchResultBinding.bottomSheet);
        activitySearchResultBinding.recyclerviewSr.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerViewAdapter_searchResult = new RecyclerViewAdapter_searchResult();
        list_lodging = new ArrayList<LodgingItem>();
    }
    // 실험을 위해 작성 삭제 예정
    public void insert(){
        util = new Util();
        list_lodging.add(new LodgingItem());
        list_lodging.add(new LodgingItem());
        list_lodging.add(new LodgingItem());
        list_lodging.add(new LodgingItem());
        recyclerViewAdapter_searchResult.set_mList(list_lodging);
        activitySearchResultBinding.recyclerviewSr.setAdapter(recyclerViewAdapter_searchResult);
    }
}