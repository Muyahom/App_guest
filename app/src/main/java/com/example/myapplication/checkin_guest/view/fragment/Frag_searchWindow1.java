package com.example.myapplication.checkin_guest.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.FragSearchWindow1Binding;
import com.example.myapplication.checkin_guest.view.activity.SearchActivity;

public class Frag_searchWindow1 extends Fragment {
    private FragSearchWindow1Binding fragSearchWindow1Binding = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragSearchWindow1Binding = DataBindingUtil.inflate(inflater, R.layout.frag_search_window1, container, false);

        fragSearchWindow1Binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 검색 버튼 누를 때 호출
                // 임시 fragment 이동 호출
                ((SearchActivity)getActivity()).move_frag(1);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //검색창에서 글자가 변경이 일어날 때 마다 호출
                return true;
            }
        });

        init();

        return fragSearchWindow1Binding.getRoot();
    }

    private void init(){
        //디자인 문제로 인한 fragment 로딩시 바로 searchView 활성화
        fragSearchWindow1Binding.searchView.setIconified(false);
    }


}