package com.example.myapplication.checkin_guest.viewModel.Executor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.orhanobut.logger.Logger;

/*Activity 혹은 Fragment에서 이메일 로그인 버튼을 클릭했을때 로그인을 수행하도록 trigger역할을 하거나 onStart()에서 로그인이 완료되었는지
체크하는 작업등을 하는 executor*/
public class EmailLoginExcutor {
    private final String TAG = EmailLoginExcutor.class.getSimpleName();
    /*view controller에서 이 livedata를 구독하고 FirebaseUser데이터가 유효한 값이면 이미 로그인이 되었다는 의미이므로
    다음 화면으로 진행하는 방향으로 설계를 진행하였다.*/
    private MutableLiveData<FirebaseUser> mFirebaseUserLiveData = new MutableLiveData<>();
    /*에러가 발생했을때 저장하는 data이다. view controller가 구독한다. 어떤 에러가 발생하면 구독중인 view에서 적절한 ui error handling을 하면 된다.
    error code에 따라서 로그인 버튼을 재 노출 혹은 백그라운드로 다시 로그인 트라이를 하는 작업을 하는 방향으로 설계하였다.*/
    private MutableLiveData<Throwable> mThrowableLiveData = new MutableLiveData<>();

    private FirebaseAuth mAuth;

    public EmailLoginExcutor(){
        mAuth = FirebaseAuth.getInstance();
    }

    public void loadUserData(){
        FirebaseUser user = mAuth.getCurrentUser();
        mFirebaseUserLiveData.setValue(user);
    }

    public LiveData<FirebaseUser> getUserLiveData(){
        return mFirebaseUserLiveData;
    }

    public LiveData<Throwable> getThrowableLiveData(){
        return mThrowableLiveData;
    }

    public void signInWithEmail(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                   if(task.isSuccessful()){
                       Logger.d(TAG, "email login success");
                       try{
                           task.getResult(ApiException.class);
                            mFirebaseUserLiveData.setValue(mAuth.getCurrentUser());
                       }catch (ApiException e){
                           mThrowableLiveData.setValue(e);
                           return;
                       }
                   }else{
                       Logger.d(TAG, "email login failed");
                       mThrowableLiveData.setValue(new NullPointerException("등록된 사용자가 아닙니다."));
                   }
                });
    }
}
