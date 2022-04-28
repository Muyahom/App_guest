package com.example.myapplication.checkin_guest.view.fragment.mainWindow;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.FragFavoriteBinding;
import com.example.myapplication.checkin_guest.view.activity.LoginActivity;
import com.example.myapplication.checkin_guest.viewModel.MainViewModel;
import com.google.firebase.auth.FirebaseUser;

public class FragFavorite extends Fragment {
    private final String TAG = FragFavorite.class.getSimpleName();
    private FragFavoriteBinding fragFavoriteBinding = null;
    private MainViewModel mainViewModel;

    //로그인 관련 변수
    private FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragFavoriteBinding = DataBindingUtil.inflate(inflater, R.layout.frag_favorite, container, false);
//        mainViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider
//                .AndroidViewModelFactory(getActivity().getApplication())).get(MainViewModel.class);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        if(mainViewModel.isLogin()){
            //로그인이 되있는 상태라면
            Log.d(TAG, "로그인 돼있음");
            fragFavoriteBinding.linearNotLogin.setVisibility(View.INVISIBLE);

        }else{
            //아니라면
            Log.d(TAG, "로그인 안돼있음");
            fragFavoriteBinding.linearProgress.setVisibility(View.INVISIBLE);
            fragFavoriteBinding.recyclerviewBm.setVisibility(View.INVISIBLE);
        }

        fragFavoriteBinding.btnLogin.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });

        return fragFavoriteBinding.getRoot();
    }
}