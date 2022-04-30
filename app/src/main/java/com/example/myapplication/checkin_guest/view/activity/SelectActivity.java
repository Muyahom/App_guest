package com.example.myapplication.checkin_guest.view.activity;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.adapter.ViewPagerAdapterSelect;
import com.example.myapplication.checkin_guest.databinding.ActivitySelectBinding;
import com.example.myapplication.checkin_guest.model.LodgingItem;
import com.example.myapplication.checkin_guest.util.Util;
import com.example.myapplication.checkin_guest.view.fragment.selectWindow.Frag_select;
import com.example.myapplication.checkin_guest.viewModel.SearchResultViewModel;
import com.google.firebase.firestore.GeoPoint;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

import java.util.ArrayList;

public class SelectActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Fragment frag_select;
    private ActivitySelectBinding activitySelectBinding;
    private LodgingItem select_lodging;
    private SearchResultViewModel searchResultViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //searchResultViewModel = new ViewModelProvider(frag_select.requireActivity()).get(SearchResultViewModel.class);

        Intent intent = getIntent();
        select_lodging = (LodgingItem) intent.getSerializableExtra("lodging");

        activitySelectBinding = DataBindingUtil.setContentView(this, R.layout.activity_select);
        //예약화면 이동
        activitySelectBinding.btnReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ReservationActivity.class);
                startActivity(intent);

            }
        });

        ArrayList<String> a=select_lodging.getImg_path();
        for(int i=0;i<a.size();i++){
            String ab=a.get(i);
            ArrayList<ViewPagerAdapterSelect>img=new ArrayList<>();
            Log.d(TAG, "onCreateView: "+ab);

        }
        Log.d(TAG, "title: "+select_lodging.getTitle_image_path());

        activitySelectBinding.roomTitle.setText(select_lodging.getName());
        activitySelectBinding.roomDetail.setText(select_lodging.getIntroductory());
        activitySelectBinding.roomPrice.setText(Long.toString(select_lodging.getFare()));
        activitySelectBinding.address.setText(select_lodging.getAddress());
        switch ((int) select_lodging.getType()){
            case 1:
                activitySelectBinding.roomType.setText("집 전체");
                break;
            case 2:
                activitySelectBinding.roomType.setText("개인실");
                break;
            case 3:
                activitySelectBinding.roomType.setText("호텔객실");
                break;
        }
        //nfc발급숙소가 아닐경우
        if(select_lodging.isNfc_distance()==false){
            activitySelectBinding.imageView2.setVisibility(View.INVISIBLE);
        }
        //숙소 편의시설
        ArrayList<String>conven=select_lodging.getConvenience();
        for (int i=0;i<conven.size();i++){
            String conven1=conven.get(i);
            Log.d(TAG, "onCreateView: "+conven1);
            if(conven1.equals("WIFI")){
                activitySelectBinding.wifi.setVisibility(View.VISIBLE);
            }
            if(conven1.equals("주차장")){
                activitySelectBinding.park.setVisibility(View.VISIBLE);
            }
            if(conven1.equals("주방")){
                activitySelectBinding.kitchen.setVisibility(View.VISIBLE);
            }
            if(conven1.equals("에어컨")){
                activitySelectBinding.air.setVisibility(View.VISIBLE);
            }
            if (conven1.equals("TV")) {
                activitySelectBinding.tv.setVisibility(View.VISIBLE);
            }

        }


        //init();
        // 검색창 UI 관련 소스코드, 상태바 투명 및 바텀내비게이션 높이에 따른 레이아웃 페딩 설정
        Util.transparency_statusBar(this);
        activitySelectBinding.hi.setPadding(0, 0, 0, Util.getBottomNavigationHeight(getApplicationContext()));

        //map
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if(mapFragment==null){
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);



    }


    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        Marker marker = new Marker();
        marker.setPosition(new LatLng(select_lodging.getLatitude(), select_lodging.getLongitude()));
        marker.setMap(naverMap);
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(select_lodging.getLatitude(),select_lodging.getLongitude()));
        naverMap.moveCamera(cameraUpdate);

    }
}