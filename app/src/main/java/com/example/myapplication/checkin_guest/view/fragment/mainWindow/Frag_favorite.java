package com.example.myapplication.checkin_guest.view.fragment.mainWindow;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.FragFavoriteBinding;
import com.example.myapplication.checkin_guest.util.Util;

public class Frag_favorite extends Fragment {
    private FragFavoriteBinding fragFavoriteBinding = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragFavoriteBinding = DataBindingUtil.inflate(inflater, R.layout.frag_favorite, container, false);


        return fragFavoriteBinding.getRoot();
    }
}