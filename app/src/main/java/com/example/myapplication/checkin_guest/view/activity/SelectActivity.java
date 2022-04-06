package com.example.myapplication.checkin_guest.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.view.fragment.selectWindow.Frag_select;
import com.naver.maps.map.MapFragment;

public class SelectActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";


    private Fragment frag_select;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        init();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view2, frag_select).commit();
        FragmentManager fm = getSupportFragmentManager();

    }

    private void init() {
        frag_select = new Frag_select();
    }

}