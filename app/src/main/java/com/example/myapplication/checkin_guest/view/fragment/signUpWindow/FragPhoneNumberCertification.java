package com.example.myapplication.checkin_guest.view.fragment.signUpWindow;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.FragPhoneNumberCertificationBinding;
import com.example.myapplication.checkin_guest.view.activity.SignUpActivity;

public class FragPhoneNumberCertification extends Fragment {
    private FragPhoneNumberCertificationBinding fragPhonenumbercertificationBinding;
    private String phoneNumber = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragPhonenumbercertificationBinding = DataBindingUtil.inflate(inflater, R.layout.frag_phone_number_certification, container, false);
        ((SignUpActivity) getActivity()).getBtn_next().setText("완료하기");

        ((SignUpActivity) getActivity()).getBtn_next().setOnClickListener(v -> {
            //휴대전화 본인인증 성공한 경우
            this.phoneNumber = fragPhonenumbercertificationBinding.edtCertificationCode.getText().toString();
            ((SignUpActivity) getActivity()).setMainCheck4(phoneNumber);
        });
        return fragPhonenumbercertificationBinding.getRoot();
    }

}