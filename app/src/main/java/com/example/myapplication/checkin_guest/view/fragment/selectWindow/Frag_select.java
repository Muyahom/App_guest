package com.example.myapplication.checkin_guest.view.fragment.selectWindow;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.adapter.ViewPagerAdapterSelect;
import com.example.myapplication.checkin_guest.databinding.FragSelectBinding;
import com.example.myapplication.checkin_guest.model.LodgingItem;
import com.example.myapplication.checkin_guest.view.activity.ReservationActivity;
import com.example.myapplication.checkin_guest.viewModel.SearchResultViewModel;


import java.util.ArrayList;

public class Frag_select extends Fragment {

    private FragSelectBinding fragSelectBinding = null;
    private ViewPagerAdapterSelect viewPagerAdapterSelect;
    private ArrayList<LodgingItem> room_img;
    int i=0;
    private LodgingItem select_lodging;
    private SearchResultViewModel searchResultViewModel;
    private ArrayList<LodgingItem> room;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        searchResultViewModel = new ViewModelProvider(requireActivity()).get(SearchResultViewModel.class);


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

        Intent intent = getActivity().getIntent();
        select_lodging = (LodgingItem) intent.getSerializableExtra("lodging");



        ArrayList<String>a=select_lodging.getImg_path();
        for(int i=0;i<a.size();i++){
            String ab=a.get(i);
            ArrayList<ViewPagerAdapterSelect>img=new ArrayList<>();
            Log.d(TAG, "onCreateView: "+ab);

        }
        Log.d(TAG, "title: "+select_lodging.getTitle_image_path());

        fragSelectBinding.roomTitle.setText(select_lodging.getName());
        fragSelectBinding.roomDetail.setText(select_lodging.getIntroductory());
        fragSelectBinding.roomPrice.setText(Long.toString(select_lodging.getFare()));
        fragSelectBinding.address.setText(select_lodging.getAddress());
        switch ((int) select_lodging.getType()){
            case 1:
                fragSelectBinding.roomType.setText("집 전체");
                break;
            case 2:
                fragSelectBinding.roomType.setText("개인실");
                break;
            case 3:
                fragSelectBinding.roomType.setText("호텔객실");
                break;
        }
        //nfc발급숙소가 아닐경우
        if(select_lodging.isNfc_distance()==false){
            fragSelectBinding.imageView2.setVisibility(View.INVISIBLE);
        }
        //숙소 편의시설
        ArrayList<String>conven=select_lodging.getConvenience();
        for (int i=0;i<conven.size();i++){
            String conven1=conven.get(i);
            Log.d(TAG, "onCreateView: "+conven1);
            if(conven1.equals("WIFI")){
                fragSelectBinding.wifi.setVisibility(View.VISIBLE);
            }
            if(conven1.equals("주차장")){
                fragSelectBinding.park.setVisibility(View.VISIBLE);
            }
            if(conven1.equals("주방")){
                fragSelectBinding.kitchen.setVisibility(View.VISIBLE);
            }
            if(conven1.equals("에어컨")){
                fragSelectBinding.air.setVisibility(View.VISIBLE);
            }
            if (conven1.equals("TV")) {
                fragSelectBinding.tv.setVisibility(View.VISIBLE);
            }

        }
        //숙소주소


        return fragSelectBinding.getRoot();





    }
    private void init(){

        viewPagerAdapterSelect = new ViewPagerAdapterSelect();



    }



    //예약 페이지 이동함수
    private void intent_select(){
        Intent intent = new Intent(getActivity(), ReservationActivity.class);
        startActivity(intent);
    }




}