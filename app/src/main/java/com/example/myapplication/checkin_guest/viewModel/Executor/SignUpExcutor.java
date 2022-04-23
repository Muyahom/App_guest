package com.example.myapplication.checkin_guest.viewModel.Executor;

import android.app.Activity;
import android.util.Log;

import com.example.myapplication.checkin_guest.callback.SignUpListener;
import com.example.myapplication.checkin_guest.data.FStoreDatabaseAttribute;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class SignUpExcutor {
    private final String TAG = SignUpExcutor.class.getSimpleName();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private SignUpListener signUpListener;
    private FirebaseAuth mAuth;

    public SignUpExcutor() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void setSignUpListener(SignUpListener signUpListener) {
        this.signUpListener = signUpListener;
    }

    public void requestSignUp(String email, String pwd, Activity activity) {
        mAuth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        signUpListener.onSuccessRegisterAuth(task, task.getResult().getUser().getUid());
                    } else {
                        signUpListener.onFailedRegisterAuth(task);
                    }
                });
    }

    //google auth 등록 성공 시
    //firestore에 유저 정보를 등록한다.
    public void send_userInfo(Map<String, Object> user_info, String uid) {
        //그 외 다른 값은 회원가입 시 받지 않으므로 계정정보에서 추가 혹은 수정 기능 지원
        db.collection(FStoreDatabaseAttribute.getInstance().getUSER_COLLECTION())
                .document(uid)
                .set(user_info)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "send_userInfo - success");
                    signUpListener.onSuccessInsertUserInfo();
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "send_userInfo - success");
                    Log.w(TAG, "Error adding document", e);
                    signUpListener.onFailedInsertUserInfo();
                });
    }
}
