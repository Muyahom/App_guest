package com.example.myapplication.checkin_guest.view.fragment.mainWindow;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.FragFavoriteBinding;
import com.example.myapplication.checkin_guest.util.Util;
import com.example.myapplication.checkin_guest.viewModel.MainViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Frag_favorite extends Fragment {
    private final String TAG = Frag_favorite.class.getSimpleName();
    private FragFavoriteBinding fragFavoriteBinding = null;
    private MainViewModel mainViewModel;
    private FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragFavoriteBinding = DataBindingUtil.inflate(inflater, R.layout.frag_favorite, container, false);
        new ViewModelProvider(this, new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())).get(MainViewModel.class);

        if(isLogin()){
            //로그인이 되있는 상태라면
            Log.d(TAG, "로그인 돼있음");
            fragFavoriteBinding.linearNotLogin.setVisibility(View.INVISIBLE);

        }else{
            //아니라면
            Log.d(TAG, "로그인 안돼있음");
            fragFavoriteBinding.linearProgress.setVisibility(View.INVISIBLE);
            fragFavoriteBinding.recyclerviewBm.setVisibility(View.INVISIBLE);
        }

        return fragFavoriteBinding.getRoot();
    }

    //로그인 체크
    private boolean isLogin(){
        boolean check = false;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        this.user = mAuth.getCurrentUser();
        if(user != null){
            check = true;
        }
        Log.d(TAG, String.valueOf(check));
        return check;
    }
}