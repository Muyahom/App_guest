package com.example.myapplication.checkin_guest.viewModel;

import static java.lang.Thread.sleep;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.checkin_guest.callback.SignUpListener;
import com.example.myapplication.checkin_guest.view.activity.LoginActivity;
import com.example.myapplication.checkin_guest.view.activity.MainActivity;
import com.example.myapplication.checkin_guest.viewModel.Executor.SignUpExcutor;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignUpViewModel extends ViewModel {
    private final String TAG = SearchViewModel.class.getSimpleName();
    private WeakReference<Activity> mActivity;

    private SignUpExcutor signUpExcutor;

    //실행중인 명령어가 로딩중인지 확인
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    //회원가입이 완료되었는지 확인
    private MutableLiveData<Boolean> isSuccess = new MutableLiveData<Boolean>();


    //사용자로 부터 입력 받는 변수 세팅(비밀번호 제외)
    private String email;
    private String phoneNumber;

    private String user_uid;

    public SignUpViewModel() {
        signUpExcutor = new SignUpExcutor();
    }

    public void setParentContext(Activity parentContext) {
        mActivity = new WeakReference<>(parentContext);
    }

    private void finishActivity() {
        if (mActivity.get() != null)
            mActivity.get().finish();
    }

    /*  google auth 등록 성공 시
        firestore에 유저 정보를 등록한다.  */

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }

    //리스너 등록
    public SignUpListener getSignUpListener() {
        return new SignUpListener() {
            @Override
            public void onSuccessInsertUserInfo() {
                Toast.makeText(mActivity.get(), "회원가입 완료!", Toast.LENGTH_SHORT).show();
                isLoading.setValue(false);
                isSuccess.setValue(true);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(mActivity.get(), MainActivity.class);
                        mActivity.get().startActivity(intent);
                        finishActivity();
                    }
                }, 1000);
            }

            @Override
            public void onFailedInsertUserInfo() {
                isLoading.setValue(false);
                Toast.makeText(mActivity.get(), "network error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccessRegisterAuth(Task task, String uid) {
                try {
                    task.getResult(ApiException.class);
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Log.d(TAG, user.getUid());
                    Toast.makeText(mActivity.get(), "등록 성공", Toast.LENGTH_SHORT).show();
                    user_uid = uid;
                    makeUserMap(email, phoneNumber);
                } catch (ApiException e) {
                    isLoading.setValue(false);
                    Toast.makeText(mActivity.get(), "비밀번호 형식을 지켜주세요.", Toast.LENGTH_SHORT).show();
                } catch (Throwable throwable) {
                    isLoading.setValue(false);
                    Toast.makeText(mActivity.get(), "비밀번호 형식을 지켜주세요.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailedRegisterAuth(Task task) {
                Log.d(TAG, String.valueOf(task.getException()));
                Toast.makeText(mActivity.get(), "이미 등록된 이메일 입니다.", Toast.LENGTH_SHORT).show();
                isLoading.setValue(false);

            }
        };
    }

    public void setSignUpListener() {
        signUpExcutor.setSignUpListener(getSignUpListener());
    }

    public void requestSignUp(String email, String password, String phoneNumber) {
        //사용자로 부터 입력받은 email, phoneNumber 세팅
        this.email = email;
        this.phoneNumber = phoneNumber;

        signUpExcutor.requestSignUp(email, password, mActivity.get());
    }

    private void makeUserMap(String email, String phoneNumber) {
        try {
            Log.d(TAG, "makeUserMap");
            Map<String, Object> user_info = new HashMap<>();
            user_info.put("email", email);
            user_info.put("phoneNumber", phoneNumber);
            //기본 삽입 user info, 랜덤 닉네임은 차후 수정예정.
            user_info.put("bussinessNumber", 0);
            user_info.put("favorites", new ArrayList<String>());
            user_info.put("img_path", "");
            user_info.put("nickName", "성결이");
            user_info.put("penalty", 0);
            user_info.put("phoneNumber", "");
            user_info.put("point", 0);
            user_info.put("register_lodging", new ArrayList<String>());
            user_info.put("reservationList", new ArrayList<String>());
            user_info.put("pushtoken", "0");

            settingProfile(user_info);

        } catch (NullPointerException e) {
            Log.d(TAG, "user info null point exception");
            e.printStackTrace();
        }
    }

    public void settingProfile(Map<String, Object> user_info) {
        //그 외 다른 값은 회원가입 시 받지 않으므로 계정정보에서 추가 혹은 수정 기능 지원
        Log.d(TAG, "SettingProfile");
        signUpExcutor.send_userInfo(user_info, user_uid);
    }
}
