package com.example.myapplication.checkin_guest.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;


import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.ActivityLoginBinding;
import com.example.myapplication.checkin_guest.viewModel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "LoginActivity";
    private ActivityLoginBinding activityLoginBinding;
    private LoginViewModel mLoginViewModel;
    
    //firebase 로그인 처리를 위한 변수
    private static final int RC_SIGN_IN = 9001;

    private LifecycleOwner mLifecycleOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        mLifecycleOwner = this;

        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mLoginViewModel.setParentContext(this);
    }

    private void init(){
    }

    private void onRequestedSignIn(){
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){

        }
    }
}