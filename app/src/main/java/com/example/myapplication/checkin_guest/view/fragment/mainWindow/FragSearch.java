package com.example.myapplication.checkin_guest.view.fragment.mainWindow;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.adapter.RecyclerViewAdapterPopular;
import com.example.myapplication.checkin_guest.adapter.RecyclerViewAdapterRecommendation;
import com.example.myapplication.checkin_guest.adapter.ViewPagerAdapterBanner;
import com.example.myapplication.checkin_guest.databinding.FragSearchBinding;
import com.example.myapplication.checkin_guest.model.Banner;
import com.example.myapplication.checkin_guest.model.PopularItem;
import com.example.myapplication.checkin_guest.model.RecommendationItem;
import com.example.myapplication.checkin_guest.model.ViewPageDataBanner;
import com.example.myapplication.checkin_guest.util.Util;
import com.example.myapplication.checkin_guest.view.activity.SearchActivity;
import com.example.myapplication.checkin_guest.viewModel.MainViewModel;

import java.util.ArrayList;

public class FragSearch extends Fragment {
    private final String TAG = "Frag_search";
    private FragSearchBinding fragSearchBinding = null;
    private MainViewModel mainViewModel;

    //배너 광고 관련 변수
    private ViewPagerAdapterBanner viewPagerAdapterBanner;
    private ArrayList<Banner> list_banner;

    //추천 숙소 관련 변수
    private RecyclerViewAdapterRecommendation recyclerViewAdapter_recommendation;
    private ArrayList<RecommendationItem> list_rc;

    //인기 있는 숙소 관련 변수
    private RecyclerViewAdapterPopular recyclerViewAdapter_popular;
    private ArrayList<PopularItem> list_pp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragSearchBinding = DataBindingUtil.inflate(inflater, R.layout.frag_search, container, false);

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())).get(MainViewModel.class);

        mainViewModel.setFireStoreExcutorListener();
        mainViewModel.setFireStorageExcutorListener();

        //사용자가 스크롤 하는 경우 UI 색상 변환을 위한 소스 코드
        fragSearchBinding.appbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            Log.d(TAG, "addOnOffsetChangedListener" + Math.abs(verticalOffset) + " " + getYMax() / 2);
            if (Math.abs(verticalOffset) >= getYMax() / 1.5) {
                //위로 거의 다 스크롤 하면 appbar 영역을 white로 설정 및 상태바 글자 색 black
                Log.d(TAG, "Math.abs(verticalOffset) >= getYMax() / 2");
                fragSearchBinding.appbar.setBackgroundResource(R.color.white);
                Util.setStatusBarColor(getActivity(), 2);

            } else {
                //내린다면 다시 기존 배경색으로 설정 및 상태바 글자 색 white
                Log.d(TAG, "else");
                fragSearchBinding.appbar.setBackgroundResource(R.drawable.gradient_main_logo);
                Util.setStatusBarColor(getActivity(), 1);
            }
        });

        fragSearchBinding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent_search();
            }
        });


        init();

        //배너 관련 변수 초기화
        mainViewModel.getListBanner().observe(getActivity(), data->{
            if(mainViewModel.getListBanner().getValue() != null){
                Log.d(TAG, "getBanner : Observe");
                list_banner = mainViewModel.getListBanner().getValue();
                viewPagerAdapterBanner.setListData(list_banner);
            }
        });

        getBanner();

        //삭제 예정 메서드
        insertTemp_rc();
        insertTemp_pp();
        return fragSearchBinding.getRoot();
    }

    private void init(){
        //adapter 초기화
        viewPagerAdapterBanner = new ViewPagerAdapterBanner(getContext());
        recyclerViewAdapter_recommendation = new RecyclerViewAdapterRecommendation();
        recyclerViewAdapter_popular = new RecyclerViewAdapterPopular();
        
        fragSearchBinding.viewPagerBanner.setAdapter(viewPagerAdapterBanner);
        
        //recyclerview 가로 지정
        list_banner = new ArrayList<>();
        fragSearchBinding.recyclerViewRc.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        list_rc = new ArrayList<>();
        fragSearchBinding.recyclerViewPp.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        list_pp = new ArrayList<>();
    }

    //app title 영역의 높이 반환
    private float getYMax() {
        return getResources().getDimension(R.dimen.height_CollapsingToolbarLayout);
    }

    // 상태바 글자 색상 변경
    @RequiresApi(api = Build.VERSION_CODES.R)
    public void setStatusBarColor(Activity activity, int mode) {
        Log.d(TAG, "change statusBar");
        Window window = activity.getWindow();
        WindowInsetsControllerCompat windowInsetsControllerCompat = new WindowInsetsControllerCompat(window, getView());
        if (mode == 1) {
            // 상태바 글자 색상 white
            windowInsetsControllerCompat.setAppearanceLightStatusBars(false);
        }else if (mode == 2){
            // 상태바 글자 색상 black
            windowInsetsControllerCompat.setAppearanceLightStatusBars(true);
        }
    }

    /*서버로 부터 banner를 가져온다.*/
    private void getBanner(){
        mainViewModel.getBanner();

    }

    //임시 데이터 삽입 - 삭제 예정
    private void insertTemp_rc(){
        ArrayList<RecommendationItem> list = new ArrayList<>();
        RecommendationItem recommendationItem1 = new RecommendationItem();
        BitmapDrawable drawable1 = (BitmapDrawable) getResources().getDrawable(R.drawable.gangneung);
        Bitmap bitmap1 = drawable1.getBitmap();
        recommendationItem1.setBitmap(bitmap1);
        RecommendationItem recommendationItem2 = new RecommendationItem();
        BitmapDrawable drawable2 = (BitmapDrawable) getResources().getDrawable(R.drawable.gangneung);
        Bitmap bitmap2 = drawable2.getBitmap();
        recommendationItem2.setBitmap(bitmap2);
        list.add(recommendationItem1);
        list.add(recommendationItem2);
        recyclerViewAdapter_recommendation.setListData(list);
        fragSearchBinding.recyclerViewRc.setAdapter(recyclerViewAdapter_recommendation);
    }

    //임시 데이터 삽입 - 삭제 예정
    private void insertTemp_pp(){
        ArrayList<PopularItem> list = new ArrayList<>();
        PopularItem popularItem1 = new PopularItem();
        BitmapDrawable drawable1 = (BitmapDrawable) getResources().getDrawable(R.drawable.top1);
        Bitmap bitmap1 = drawable1.getBitmap();
        popularItem1.setBitmap(bitmap1);
        PopularItem popularItem2 = new PopularItem();
        BitmapDrawable drawable2 = (BitmapDrawable) getResources().getDrawable(R.drawable.top2);
        Bitmap bitmap2 = drawable2.getBitmap();
        popularItem2.setBitmap(bitmap2);
        list.add(popularItem1);
        list.add(popularItem2);
        recyclerViewAdapter_popular.setListData(list);
        fragSearchBinding.recyclerViewPp.setAdapter(recyclerViewAdapter_popular);
    }

    private void intent_search(){
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.anim_slide_in_top, R.anim.anim_fade_out);
    }
}