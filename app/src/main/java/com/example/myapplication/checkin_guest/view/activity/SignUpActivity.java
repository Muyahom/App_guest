package com.example.myapplication.checkin_guest.view.activity;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

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
import com.example.myapplication.checkin_guest.viewModel.SignUpViewModel;


public class SignUpActivity extends AppCompatActivity {
    private final String TAG = SignUpActivity.class.getSimpleName();
    private ActivitySignUpBinding activitySignUpBinding;
    private SignUpViewModel signUpViewModel;
    private Fragment fragTermOfUse, fragGetEmail, fragEmailCertification, fragPhoneNumberCertification;
    private Button btn_next;

    //뒤로가기 종료
    private long backKeyPressedTime = 0;

    private String email;
    private String pwd;
    private String phoneNumber;

    @Override
    public void onBackPressed() {
        // 기존 뒤로가기 버튼의 기능을 막기위해 주석처리 또는 삭제
        // super.onBackPressed();
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 회원가입을 종료합니다.", Toast.LENGTH_SHORT).show();
            return;
        } else if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        signUpViewModel = new ViewModelProvider(this, new ViewModelProvider
                .AndroidViewModelFactory(getApplication())).get(SignUpViewModel.class);
        signUpViewModel.setParentContext(this);

        Util.setStatusBarColor(this);
        Util.setStatusBarColor(this, 2);
        setSupportActionBar(activitySignUpBinding.toolbarSignUp);
        getSupportActionBar().setTitle("회원가입");
        init();

        signUpViewModel.getIsLoading().observe(this, data->{
            boolean isLoading = signUpViewModel.getIsLoading().getValue();
            if(isLoading){
                activitySignUpBinding.linearProgress.setVisibility(View.VISIBLE);
            }else{
                activitySignUpBinding.linearProgress.setVisibility(View.INVISIBLE);
            }
        });

        signUpViewModel.getIsSuccess().observe(this, data->{
            boolean isSuccess = signUpViewModel.getIsSuccess().getValue();
            if(isSuccess){
                activitySignUpBinding.lottie.setVisibility(View.VISIBLE);
            }else{
                activitySignUpBinding.lottie.setVisibility(View.INVISIBLE);

            }
        });


        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_signUp, fragTermOfUse).commit();
    }

    private void init() {
        fragTermOfUse = new FragTermOfUse();
        fragGetEmail = new FragGetEmail();
        fragEmailCertification = new FragEmailCertification();
        fragPhoneNumberCertification = new FragPhoneNumberCertification();
        btn_next = activitySignUpBinding.btnNext;
        activitySignUpBinding.linearProgress.setVisibility(View.INVISIBLE);
        activitySignUpBinding.lottie.setVisibility(View.INVISIBLE);
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

    public void setMainCheck4(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        signUp();
    }

    private void signUp() {
        signUpViewModel.getIsLoading().setValue(true);
        /* 리스너 등록 -> requestSignUp -> 성공 ->
           사용자 정보 map 전달 -> settingProfile -> send_userInfo */
        signUpViewModel.setSignUpListener();
        signUpViewModel.requestSignUp(this.email, this.pwd, this.phoneNumber);

    }
}