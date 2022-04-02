package com.example.myapplication.checkin_guest.viewModel;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.ViewModel;

import java.lang.ref.WeakReference;

public class LoginViewModel extends ViewModel {
    private WeakReference<Activity> mActivityRef;


    //firebase 로그인 처리를 위한 변수
    // LiveData
    private GoogleLoginExecutor mGoogleLoginExecutor;
    private static final int RC_SIGN_IN = 9001;


    public void setParentContext(Activity parentContext){
        mActivityRef = new WeakReference<>(parentContext);
    }

    public void setGoogleLoginExecutor(GoogleLoginExecutor executor){
        mGoogleLoginExecutor = new GoogleLoginExecutor(mActivityRef.get());
    }

    public void setObserveValue(){

    }

    public void onRequestSignIn(){
        Intent signInIntent = mGoogleLoginExecutor.getSignInIntent();
        if(mActivityRef.get() != null){
            mActivityRef.get().startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    public boolean onActivityResult(Intent data){
        mGoogleLoginExecutor.firebaseAuthWithGoogle(data, mActivityRef.get());
        return true;
    }
}
