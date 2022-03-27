package com.example.myapplication.checkin_guest.view.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.FragSearchWindow2Binding;
import com.google.android.material.datepicker.MaterialDatePicker;

public class Frag_searchWindow2 extends Fragment {
    private FragSearchWindow2Binding fragSearchWindow2Binding = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragSearchWindow2Binding = DataBindingUtil.inflate(inflater, R.layout.frag_search_window2, container, false);
//        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppThemeMaterial);
//        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.dateRangePicker();
        MaterialDatePicker picker = builder.build();
        picker.show(getActivity().getSupportFragmentManager(), picker.toString());
        return fragSearchWindow2Binding.getRoot();
    }
}