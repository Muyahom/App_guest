package com.example.myapplication.checkin_guest.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.ActivitySignUpBinding;
import com.example.myapplication.checkin_guest.util.Util;
import com.example.myapplication.checkin_guest.view.fragment.signUpWindow.FragEmailCertification;
import com.example.myapplication.checkin_guest.view.fragment.signUpWindow.FragGetEmail;
import com.example.myapplication.checkin_guest.view.fragment.signUpWindow.FragPhoneNumberCertification;
import com.example.myapplication.checkin_guest.view.fragment.signUpWindow.FragTermOfUse;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private final String TAG = SignUpActivity.class.getSimpleName();
    private ActivitySignUpBinding activitySignUpBinding;
    private Fragment fragTermOfUse, fragGetEmail, fragEmailCertification, fragPhoneNumberCertification;
    private Button btn_next;

    private String email;
    private String pwd;
    private String phoneNumber;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        Util.setStatusBarColor(this);
        Util.setStatusBarColor(this, 2);
        setSupportActionBar(activitySignUpBinding.toolbarSignUp);
        getSupportActionBar().setTitle("회원가입");
        init();

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_signUp, fragTermOfUse).commit();
    }

    private void init() {
        fragTermOfUse = new FragTermOfUse();
        fragGetEmail = new FragGetEmail();
        fragEmailCertification = new FragEmailCertification();
        fragPhoneNumberCertification = new FragPhoneNumberCertification();
        btn_next = activitySignUpBinding.btnNext;
        activitySignUpBinding.progressBar.setVisibility(View.INVISIBLE);
    }

    public Button getBtn_next() {
        return btn_next;
    }

    public String getEmail() {
        return email;
    }

    public void setMainCheck1(boolean mainCheck1) {
        if (mainCheck1) {
            Log.d(TAG, "이용약관 동의 확인 완료");
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.framelayout_signUp, fragGetEmail).commit();
        } else {
            Toast.makeText(getApplicationContext(), "이용약관에 동의하지 않으면 가입이 불가능 합니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void setMainCheck2(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.framelayout_signUp, fragEmailCertification).commit();
    }

    public void setMainCheck3() {
        Log.d(TAG, "setMainCheck3");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.framelayout_signUp, fragPhoneNumberCertification).commit();
    }

    public void setMainCheck4(boolean mainCheck4, String phoneNumber) {
        this.phoneNumber = phoneNumber;
        mAuth = FirebaseAuth.getInstance();
        signUp();
    }

    public void signUp() {
        activitySignUpBinding.progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(this.email, this.pwd)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        try {
                            activitySignUpBinding.progressBar.setVisibility(View.INVISIBLE);
                            task.getResult(ApiException.class);
                            user = mAuth.getCurrentUser();
                            Log.d(TAG, user.getUid());
                        } catch (ApiException e) {
                            Toast.makeText(this, "비밀번호 형식을 지켜주세요.", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(this, "등록 성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        activitySignUpBinding.progressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, String.valueOf(task.getException()));
                        Toast.makeText(this, "이미 등록된 이메일 입니다.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}