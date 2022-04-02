package com.example.myapplication.checkin_guest.view.fragment.signUpWindow;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.FragGetEmailBinding;
import com.example.myapplication.checkin_guest.view.activity.SignUpActivity;

import java.util.regex.Pattern;

public class FragGetEmail extends Fragment {
    private FragGetEmailBinding fragGetemailBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragGetemailBinding = DataBindingUtil.inflate(inflater, R.layout.frag_get_email, container, false);

        ((SignUpActivity) getActivity()).getBtn_next().setOnClickListener(v -> {
            signUp();
        });

        return fragGetemailBinding.getRoot();
    }

    private void signUp() {
        String email = fragGetemailBinding.edtEmail.getText().toString().trim();
        String password = fragGetemailBinding.edtPassword.getText().toString().trim();
        String passwordCheck = fragGetemailBinding.edtPasswordCheck.getText().toString().trim();
        boolean check = checkForm(email, password, passwordCheck);
        if (check) {
            ((SignUpActivity) getActivity()).setMainCheck2(email, password);
        }
    }

    private boolean checkForm(String email, String password, String passwordCheck) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        boolean passwordChecked = password.equals(passwordCheck) && password.length()>=6;
        boolean emailChecked = pattern.matcher(email).matches();
        if (passwordChecked && emailChecked) {
            return true;
        } else if (passwordChecked) {
            Toast.makeText(getActivity(), "잘못된 이메일 형식입니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "잘못된 비밀번호 형식입니다.", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}