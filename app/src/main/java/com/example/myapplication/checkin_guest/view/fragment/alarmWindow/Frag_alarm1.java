package com.example.myapplication.checkin_guest.view.fragment.alarmWindow;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.FragmentFragAlarm1Binding;
import com.example.myapplication.checkin_guest.databinding.FragmentFragAlarm1BindingImpl;
import com.example.myapplication.checkin_guest.view.activity.PopupActivity;


public class Frag_alarm1 extends Fragment {
    FragmentFragAlarm1BindingImpl fragmentFragAlarm1Binding=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragmentFragAlarm1Binding=DataBindingUtil.inflate(inflater,R.layout.fragment_frag_alarm1, container, false);

        fragmentFragAlarm1Binding.alarm11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PopupActivity.class);
                startActivity(intent);
            }
        });
        return fragmentFragAlarm1Binding.getRoot();



    }
}