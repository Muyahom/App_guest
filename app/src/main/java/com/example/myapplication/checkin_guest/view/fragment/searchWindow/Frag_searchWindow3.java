package com.example.myapplication.checkin_guest.view.fragment.searchWindow;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.FragSearchWindow3Binding;
import com.example.myapplication.checkin_guest.view.activity.SearchResultActivity;
import com.example.myapplication.checkin_guest.view.activity.SelectActivity;


public class Frag_searchWindow3 extends Fragment {
    private FragSearchWindow3Binding fragSearchWindow3Binding = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragSearchWindow3Binding = DataBindingUtil.inflate(inflater, R.layout.frag_search_window3, container, false);

        fragSearchWindow3Binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                startActivity(intent);
            }
        });

        return fragSearchWindow3Binding.getRoot();
    }
}