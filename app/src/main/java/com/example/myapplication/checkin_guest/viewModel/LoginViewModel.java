package com.example.myapplication.checkin_guest.viewModel;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.example.myapplication.checkin_guest.callback.ActionListener;
import com.example.myapplication.checkin_guest.callback.ErrorListener;
import com.example.myapplication.checkin_guest.view.activity.LoginActivity;
import com.example.myapplication.checkin_guest.view.view.LoginView;
import com.example.myapplication.checkin_guest.view.activity.MainActivity;
import com.example.myapplication.checkin_guest.viewModel.Executor.EmailLoginExcutor;
import com.example.myapplication.checkin_guest.viewModel.Executor.GoogleLoginExecutor;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;

public class LoginViewModel extends ViewModel {
    private final String TAG = LoginViewModel.class.getSimpleName();
    private WeakReference<Activity> mActivityRef;

    //firebase 로그인 처리를 위한 변수
    // LiveData
    private GoogleLoginExecutor mGoogleLoginExecutor;
    private static final int RC_SIGN_IN = 9001;

    //이메일 로그인 처리를 위한 변수
    private EmailLoginExcutor mEmailLoginExcutor;


    public LoginViewModel(){
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    // activity setting
    public void setParentContext(Activity parentContext){
        mActivityRef = new WeakReference<>(parentContext);
    }


    public ActionListener getActionListener(){
        return new ActionListener() {
            @Override
            public void NotifySignInGoogleSuccess() {
                Intent intent = new Intent(mActivityRef.get(), MainActivity.class);
                mActivityRef.get().startActivity(intent);
                finishActivity();
            }

            @Override
            public void NotifySignInEmailSuccess() {
                Intent intent = new Intent(mActivityRef.get(), MainActivity.class);
                mActivityRef.get().startActivity(intent);
                finishActivity();
            }
        };
    }

    public ErrorListener getErrorListener(){
        return new ErrorListener() {
            @Override
            public void NotifySignInEmailError() {
                Toast.makeText(mActivityRef.get(), "이메일, 비밀번호를 다시 확인해주세요", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void finishActivity() {
        if (mActivityRef.get() != null) {
            mActivityRef.get().finish();
        }
    }

    /*                       
                          구글 로그인 메서드 모음
                                                                 */
    //구글 로그인을 위한 googleloginexecutor 생성
    public void setGoogleLoginExecutor(){
        mGoogleLoginExecutor = new GoogleLoginExecutor(mActivityRef.get());
    }
    

    // 로그인 및 에러 변수 observe를 위해 변수 세팅
    public void setObserveValue(LoginView loginView){
        loginView.setFirebaseUserLiveData(mGoogleLoginExecutor.getUserLiveData());
        loginView.setThrowableUserLiveData(mGoogleLoginExecutor.getThrowableLiveData());
        loginView.setActionListener(getActionListener());
        loginView.setmErrorListener(getErrorListener());
    }

    //자동로그인 기능을 위한 변수 세팅
    public void loadUserData(){
        mGoogleLoginExecutor.loadUserData();
    }

    //activity에서 구글 로그인 버튼을 클릭한 경우
    public void onRequestSignInWithGoogle(){
        Intent signInIntent = mGoogleLoginExecutor.getSignInIntent();
        if(mActivityRef.get() != null){
            mActivityRef.get().startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    //구글 ui로 부터 로그인을 진행할 이메일을 가져옴
    public boolean onActivityResult(Intent data){
        mGoogleLoginExecutor.firebaseAuthWithGoogle(data, mActivityRef.get());
        Logger.d(TAG, "onActivityResult()" + data.toString());
        return true;
    }

     /*
                          이메일 로그인 메서드 모음
                                                                 */
    public void setmEmailLoginExcutor(){
        mEmailLoginExcutor = new EmailLoginExcutor();
    }

    // 로그인 및 에러 변수 observe를 위해 변수 세팅
    public void setObserveValueEmail(LoginView loginView){
        loginView.setEmailLiveData(mEmailLoginExcutor.getUserLiveData());
        loginView.setThrowableEmailLiveData(mEmailLoginExcutor.getThrowableLiveData());
        loginView.setActionListener(getActionListener());
        loginView.setmErrorListener(getErrorListener());
    }
    //activity에서 이메일 로그인 버튼을 클릭한 경우
    public void onRequestSignInWithEmail(String email, String password){
        mEmailLoginExcutor.signInWithEmail(email, password);
    }


}
