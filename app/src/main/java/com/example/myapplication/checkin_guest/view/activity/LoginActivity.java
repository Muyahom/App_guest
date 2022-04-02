package com.example.myapplication.checkin_guest.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.ActivityLoginBinding;
import com.example.myapplication.checkin_guest.view.LoginView;
import com.example.myapplication.checkin_guest.viewModel.LoginViewModel;
import com.google.firebase.FirebaseApp;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = LoginActivity.class.getSimpleName();
    private ActivityLoginBinding activityLoginBinding;
    private LoginViewModel mLoginViewModel;

    //firebase 로그인 처리를 위한 변수
    private static final int RC_SIGN_IN = 9001;

    private LifecycleOwner mLifecycleOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        mLifecycleOwner = this;
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mLoginViewModel.setParentContext(this);

        init();

        activityLoginBinding.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.d(TAG, "Google Login Request");
                mLoginViewModel.onRequestSignInWithGoogle();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.d(TAG, "자동로그인 기능 실행");
        mLoginViewModel.loadUserData();
    }

    private void init() {
        Logger.addLogAdapter(new AndroidLogAdapter());
        //구글 로그인을 사용하는 경우를 위한 메서드 실행
        mLoginViewModel.setGoogleLoginExecutor();
        mLoginViewModel.setObserveValue(new LoginView(activityLoginBinding.getRoot(), this));

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            mLoginViewModel.onActivityResult(data);
        }
    }
}