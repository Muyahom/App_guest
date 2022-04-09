package com.example.myapplication.checkin_guest.view.fragment.mainWindow;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.FragChattingBinding;

public class Frag_chatting extends Fragment {
    private FragChattingBinding fragChattingBinding = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragChattingBinding = DataBindingUtil.inflate(inflater, R.layout.frag_chatting, container, false);
        fragChattingBinding.linearProgress.setVisibility(View.INVISIBLE);

        return fragChattingBinding.getRoot();
    }
}