package com.example.myapplication.checkin_guest.view.fragment.signUpWindow;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.FragTermOfUseBinding;
import com.example.myapplication.checkin_guest.view.activity.SignUpActivity;

public class FragTermOfUse extends Fragment {

    private FragTermOfUseBinding fragTermsofuseBinding;
    private boolean check;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragTermsofuseBinding = DataBindingUtil.inflate(inflater, R.layout.frag_term_of_use, container, false);

        ((SignUpActivity)getActivity()).getBtn_next().setOnClickListener(v -> {
            check();
        });

        return fragTermsofuseBinding.getRoot();
    }

    public void check() {
        this.check = fragTermsofuseBinding.chkAgree.isChecked();
        ((SignUpActivity)getActivity()).setMainCheck1(this.check);
    }
}