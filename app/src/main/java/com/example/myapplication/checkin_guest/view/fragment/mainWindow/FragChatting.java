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
import com.example.myapplication.checkin_guest.databinding.FragChattingBinding;
import com.example.myapplication.checkin_guest.view.activity.LoginActivity;
import com.example.myapplication.checkin_guest.viewModel.MainViewModel;
import com.google.firebase.auth.FirebaseUser;

public class FragChatting extends Fragment {
    private final String TAG = FragChatting.class.getSimpleName();
    private FragChattingBinding fragChattingBinding = null;
    private MainViewModel mainViewModel;

    //사용자 관련 변수
    private FirebaseUser user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragChattingBinding = DataBindingUtil.inflate(inflater, R.layout.frag_chatting, container, false);
        mainViewModel = new ViewModelProvider(this, new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())).get(MainViewModel.class);

        if(mainViewModel.isLogin()){
            //로그인이 되있는 상태라면
            Log.d(TAG, "로그인 돼있음");
            fragChattingBinding.linearNotLogin.setVisibility(View.INVISIBLE);

        }else{
            //아니라면
            Log.d(TAG, "로그인 안돼있음");
            fragChattingBinding.linearProgress.setVisibility(View.INVISIBLE);
            fragChattingBinding.recyclerviewCh.setVisibility(View.INVISIBLE);
        }

        fragChattingBinding.btnLogin.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
        return fragChattingBinding.getRoot();
    }


}