package com.example.myapplication.checkin_guest.view.activity;

import static com.google.gson.reflect.TypeToken.get;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
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
        activityLoginBinding.linearProgress.setVisibility(View.INVISIBLE);

        // view model 사용을 위한 초기화 작업
        mLoginViewModel = new ViewModelProvider(this, new ViewModelProvider
                .AndroidViewModelFactory(getApplication())).get(LoginViewModel.class);
        mLoginViewModel.setParentContext(this);

        init();

        //이메일 로그인 버튼 클릭 시
        activityLoginBinding.btnSignInEmail.setOnClickListener(view -> {
            Logger.d(TAG, "Email Login Request");
            //프로그레스바 실행 및 터치 막음
            activityLoginBinding.linearProgress.setVisibility(View.VISIBLE);
            // email과 password를 받아올때 앞뒤 공백을 제거하고 변수에 값을 삽입한다.
            String email = activityLoginBinding.edtId.getText().toString().trim();
            String password = activityLoginBinding.edtPassword.getText().toString().trim();

            try {
            //keyBoard가 올라와 있는 경우 키보드를 내림
                Util.keyboardOff(LoginActivity.this);
            }catch (Exception e){
                Log.d(TAG, "keyBoard state : off");
            }

            //조건 검사 후 로그인 실행
            checkEmailPassword(email, password);
        });

        //구글 로그인 버튼 클릭 시
        activityLoginBinding.signInButton.setOnClickListener(view -> {
            Logger.d(TAG, "Google Login Request");
            mLoginViewModel.onRequestSignInWithGoogle();
        });

        //회원가입 버튼 클릭시
        activityLoginBinding.btnSignUp.setOnClickListener(view -> {
            Logger.d(TAG, "sign up");
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        });

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

    private void checkEmailPassword(String email, String password) {
        if (email.equals("")) {
            Toast.makeText(getApplicationContext(), "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
            activityLoginBinding.linearProgress.setVisibility(View.INVISIBLE);
        } else if (password.equals("")) {
            Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            activityLoginBinding.linearProgress.setVisibility(View.INVISIBLE);
        } else {
            //로그인 실행
            mLoginViewModel.onRequestSignInWithEmail(email, password);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            activityLoginBinding.linearProgress.setVisibility(View.VISIBLE);
            mLoginViewModel.onActivityResult(data);
        }
    }
}