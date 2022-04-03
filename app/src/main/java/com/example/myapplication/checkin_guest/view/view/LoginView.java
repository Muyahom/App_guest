package com.example.myapplication.checkin_guest.view.view;

import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.callback.ActionListener;
import com.example.myapplication.checkin_guest.callback.ErrorListener;
import com.google.firebase.auth.FirebaseUser;
import com.orhanobut.logger.Logger;


public class LoginView {

    private final String TAG = LoginView.class.getSimpleName();
    private View mView;
    private ActionListener mActionListener;
    private ErrorListener mErrorListener;
    private LifecycleOwner mLifecycleOwner;

    public LoginView(View view, LifecycleOwner lifecycleOwner) {
        this.mView = view;
        this.mLifecycleOwner = lifecycleOwner;
    }

    public void setActionListener(ActionListener actionListener) {
        this.mActionListener = actionListener;
    }

    public void setmErrorListener(ErrorListener errorListener) {
        this.mErrorListener = errorListener;
    }

    public void setProgressInVisible() {
        mView.findViewById(R.id.linear_progress).setVisibility(View.INVISIBLE);
    }

    /*
                          구글 로그인 메서드 모음
                                                                 */
    public void setFirebaseUserLiveData(LiveData<FirebaseUser> liveData) {
        liveData.observe(mLifecycleOwner, data -> {
            if (liveData.getValue() == null) {
                //로그인 실패시
                Logger.d(TAG, "Google Login failed");
                return;
            }
            //로그인 성공시(activity에 notify)
            Logger.d(TAG, "Google Login success");
            mActionListener.NotifySignInGoogleSuccess();
        });
    }

    public void setThrowableUserLiveData(LiveData<Throwable> liveData) {
        liveData.observe(mLifecycleOwner, data -> {
            Logger.d(TAG, data.getMessage());
        });
    }

     /*
                          이메일 로그인 메서드 모음
                                                                 */

    public void setEmailLiveData(LiveData<FirebaseUser> liveData) {
        liveData.observe(mLifecycleOwner, data -> {
            if (liveData.getValue() == null) {
                //로그인 실패시
                Logger.d(TAG, "Email login failed");
                setProgressInVisible();
                return;
            }
            //로그인 성공시 notify
            Logger.d(TAG, "Google Login success");
            mActionListener.NotifySignInEmailSuccess();
            setProgressInVisible();
        });
    }

    public void setThrowableEmailLiveData(LiveData<Throwable> liveData) {
        liveData.observe(mLifecycleOwner, data -> {
            Logger.d(TAG, data.getMessage());
            if (liveData.getValue() instanceof NullPointerException) {
                setProgressInVisible();
                mErrorListener.NotifySignInEmailError();
            }
        });
    }
}
