package com.example.myapplication.checkin_guest.view.fragment.searchWindow;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.FragSearchWindow3Binding;
import com.example.myapplication.checkin_guest.view.activity.SearchResultActivity;


public class Frag_searchWindow3 extends Fragment {
    private FragSearchWindow3Binding fragSearchWindow3Binding = null;
    private int badCount = 0;
    private int badRoomCount = 0;
    private int toiletCount = 0;

    private int checkTypeNum = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragSearchWindow3Binding = DataBindingUtil.inflate(inflater, R.layout.frag_search_window3, container, false);

        fragSearchWindow3Binding.rgb.setOnCheckedChangeListener((radioGroup, i) -> {
            if(i == 0){
                checkTypeNum = 0;
            }else if(i == 1){
                checkTypeNum = 1;
            }else{
                checkTypeNum = 2;
            }
        });

        fragSearchWindow3Binding.btnSearch.setOnClickListener(view -> {

        });

        fragSearchWindow3Binding.btnSkip.setOnClickListener(view -> {

        });

        fragSearchWindow3Binding.btnPlusBad.setOnClickListener(view -> {
            badCount += 1;
            fragSearchWindow3Binding.txtBadCount.setText(badCount);
        });

        fragSearchWindow3Binding.btnMinusBad.setOnClickListener(view -> {
            if (badCount >= 1) {
                badCount -= 1;
                fragSearchWindow3Binding.txtBadCount.setText(badCount);
            }
        });

        fragSearchWindow3Binding.btnPlusBadroom.setOnClickListener(view -> {
            badRoomCount += 1;
            fragSearchWindow3Binding.txtBadCount.setText(badRoomCount);

        });

        fragSearchWindow3Binding.btnMinusBadroom.setOnClickListener(view -> {
            if (badRoomCount >= 1) {
                badRoomCount -= 1;
                fragSearchWindow3Binding.txtBadCount.setText(badRoomCount);
            }
        });

        fragSearchWindow3Binding.btnPlusToilet.setOnClickListener(view -> {
            toiletCount += 1;
            fragSearchWindow3Binding.txtToiletCount.setText(toiletCount);
        });

        fragSearchWindow3Binding.btnMinusToilet.setOnClickListener(view -> {
            if (toiletCount >= 1) {
                toiletCount -= 1;
                fragSearchWindow3Binding.txtToiletCount.setText(toiletCount);
            }
        });

        fragSearchWindow3Binding.btnSearch.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SearchResultActivity.class);
            startActivity(intent);
        });

        return fragSearchWindow3Binding.getRoot();
    }
}