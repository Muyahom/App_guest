package com.example.myapplication.checkin_guest.view.fragment.selectWindow;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.adapter.ViewPagerAdapterSelect;
import com.example.myapplication.checkin_guest.databinding.FragSelectBinding;
import com.example.myapplication.checkin_guest.model.ViewPageDataSelect;
import com.example.myapplication.checkin_guest.util.Util;
import com.example.myapplication.checkin_guest.view.activity.ReservationActivity;

import java.util.ArrayList;
import java.util.Objects;

public class Frag_select extends Fragment {

    private FragSelectBinding fragSelectBinding = null;
    private ViewPagerAdapterSelect viewPagerAdapterSelect;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragSelectBinding = DataBindingUtil.inflate(inflater, R.layout.frag_select, container, false);
        //예약버튼 눌렀을때 페이지이동
        fragSelectBinding.btnReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent_select();
            }
        });

        init();
        insertTemp();
        return fragSelectBinding.getRoot();

    }
    private void init(){
        viewPagerAdapterSelect = new ViewPagerAdapterSelect();


    }
    //임시 데이터 삽입 - 삭제 예정
    private void insertTemp(){
        ArrayList<ViewPageDataSelect> list = new ArrayList<>();
        ViewPageDataSelect viewPageDataBanner1 = new ViewPageDataSelect();
        BitmapDrawable drawable1 = (BitmapDrawable) getResources().getDrawable(R.drawable.main);
        Bitmap bitmap1 = drawable1.getBitmap();
        viewPageDataBanner1.setBitmap(bitmap1);
        ViewPageDataSelect viewPageDataBanner2 = new ViewPageDataSelect();
        BitmapDrawable drawable2 = (BitmapDrawable) getResources().getDrawable(R.drawable.main);
        Bitmap bitmap2 = drawable2.getBitmap();
        viewPageDataBanner2.setBitmap(bitmap2);
        list.add(viewPageDataBanner1);
        list.add(viewPageDataBanner2);
        viewPagerAdapterSelect.setListData(list);
        fragSelectBinding.viewPagerSelectFrag.setAdapter(viewPagerAdapterSelect);
        //뷰페이저에 인디케이터추가
        //fragSelectBinding.dotsIndicator.setViewPager2(fragSelectBinding.viewPagerSelectFrag);

    }
    //예약 페이지 이동함수
    private void intent_select(){
        Intent intent = new Intent(getActivity(), ReservationActivity.class);
        startActivity(intent);
    }




}