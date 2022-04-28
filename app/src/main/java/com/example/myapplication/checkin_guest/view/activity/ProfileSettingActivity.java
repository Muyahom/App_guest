package com.example.myapplication.checkin_guest.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.ActivityProfileSettingBinding;
import com.example.myapplication.checkin_guest.util.Util;
import com.example.myapplication.checkin_guest.view.fragment.SignUpForGoogleLogin.FragPhoneNumberCertification;
import com.example.myapplication.checkin_guest.view.fragment.SignUpForGoogleLogin.FragTermOfUse;
import com.example.myapplication.checkin_guest.viewModel.SignUpViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileSettingActivity extends AppCompatActivity {
    private final String TAG = ProfileSettingActivity.class.getSimpleName();
    private ActivityProfileSettingBinding activityProfileSettingBinding;
    private SignUpViewModel signUpViewModel;
    private Fragment fragTermOfUse, fragPhoneNumberCertification;
    private Button btn_next;

    private String email;
    private String phoneNumber;
    private String userUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileSettingBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile_setting);

        signUpViewModel = new ViewModelProvider(this, new ViewModelProvider
                .AndroidViewModelFactory(getApplication())).get(SignUpViewModel.class);

        signUpViewModel.setParentContext(this);

        //UI setting
        Util.setStatusBarColor(this);
        Util.setStatusBarColor(this, 2);
        setSupportActionBar(activityProfileSettingBinding.toolbarSignUp);
        getSupportActionBar().setTitle("프로필 등록");

        init();

        Intent intent = getIntent();
        this.email = intent.getStringExtra("email");
        signUpViewModel.setUser_uid(FirebaseAuth.getInstance().getUid());

        signUpViewModel.getIsLoading().observe(this, data->{
            boolean isLoading = signUpViewModel.getIsLoading().getValue();
            if(isLoading){
                activityProfileSettingBinding.linearProgress.setVisibility(View.VISIBLE);
            }else{
                activityProfileSettingBinding.linearProgress.setVisibility(View.INVISIBLE);
            }
        });

        signUpViewModel.getIsSuccess().observe(this, data->{
            boolean isSuccess = signUpViewModel.getIsSuccess().getValue();
            if(isSuccess){
                activityProfileSettingBinding.lottie.setVisibility(View.VISIBLE);
            }else{
                activityProfileSettingBinding.lottie.setVisibility(View.INVISIBLE);

            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_signUp, fragTermOfUse).commit();
    }

    private void init() {
        fragTermOfUse = new FragTermOfUse();
        fragPhoneNumberCertification = new FragPhoneNumberCertification();
        btn_next = activityProfileSettingBinding.btnNext;
        activityProfileSettingBinding.linearProgress.setVisibility(View.INVISIBLE);
        activityProfileSettingBinding.lottie.setVisibility(View.INVISIBLE);
    }

    public void setMainCheck1(boolean mainCheck1) {
        if (mainCheck1) {
            Log.d(TAG, "이용약관 동의 확인 완료");
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.framelayout_signUp, fragPhoneNumberCertification).commit();
        } else {
            Toast.makeText(getApplicationContext(), "이용약관에 동의하지 않으면 가입이 불가능 합니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void setMainCheck4(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        signUp();
    }

    private void signUp() {
        signUpViewModel.getIsLoading().setValue(true);
        /* 리스너 등록 -> requestSignUp -> 성공 ->
           사용자 정보 map 전달 -> settingProfile -> send_userInfo */
        signUpViewModel.setSignUpListener();
        signUpViewModel.makeUserMap(this.email, this.phoneNumber);
    }

    public Button getBtn_next() {
        return btn_next;
    }

}