package com.example.myapplication.checkin_guest.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.ActivityLoginBinding;
import com.example.myapplication.checkin_guest.util.Util;
import com.example.myapplication.checkin_guest.view.view.LoginView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        // 처리할 UI 관련 소스코드, 상태바 투명 및 바텀내비게이션 높이에 따른 레이아웃 페딩 설정
        Util.transparency_statusBar(this);
        activityLoginBinding.constraint.setPadding(0, 0, 0, Util.getBottomNavigationHeight(getApplicationContext()));

        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mLoginViewModel.setParentContext(this);

        init();

        activityLoginBinding.btnSignInEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.d(TAG, "Email Login Request");
                // email과 password를 받아올때 앞뒤 공백을 제거하고 변수에 값을 삽입한다.
                String email = activityLoginBinding.edtId.getText().toString().trim();
                String password = activityLoginBinding.edtPassword.getText().toString().trim();
                //조건 검사 후 로그인 실행
                checkEmailPassword(email, password);
            }
        });

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
        // google 및 email 로그인 여부 확인 
        mLoginViewModel.loadUserData();
    }

    private void init() {
        Logger.addLogAdapter(new AndroidLogAdapter());
        LoginView loginView = new LoginView(activityLoginBinding.getRoot(), this);
        //구글 로그인 viewModel setting
        mLoginViewModel.setGoogleLoginExecutor();
        mLoginViewModel.setObserveValue(loginView);
        //이메일 로그인 viewModel setting
        mLoginViewModel.setmEmailLoginExcutor();
        mLoginViewModel.setObserveValueEmail(loginView);
    }
    
    private void checkEmailPassword(String email, String password){
        if(email.equals("")){
            Toast.makeText(getApplicationContext(), "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
        }else if(password.equals("")){
            Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
        }else{
            //로그인 실행
            mLoginViewModel.onRequestSignInWithEmail(email, password);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            mLoginViewModel.onActivityResult(data);
        }
    }
}