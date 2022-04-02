package com.example.myapplication.checkin_guest.view.fragment.signUpWindow;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.FragEmailCertificationBinding;
import com.example.myapplication.checkin_guest.databinding.FragPhoneNumberCertificationBinding;
import com.example.myapplication.checkin_guest.view.activity.SignUpActivity;

public class FragPhoneNumberCertification extends Fragment {
    private FragPhoneNumberCertificationBinding fragPhonenumbercertificationBinding;
    private String phoneNumber="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragPhonenumbercertificationBinding = DataBindingUtil.inflate(inflater, R.layout.frag_phone_number_certification, container, false);
        ((SignUpActivity)getActivity()).setMainCheck4(true, phoneNumber);
        return fragPhonenumbercertificationBinding.getRoot();
    }
}