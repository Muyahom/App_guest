package com.example.myapplication.checkin_guest.view.fragment.mainWindow;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.FragMyInfoBinding;
import com.example.myapplication.checkin_guest.view.activity.AccountActivity;
import com.example.myapplication.checkin_guest.view.activity.AlarmActivity;
import com.example.myapplication.checkin_guest.view.activity.HelpActivity;
import com.example.myapplication.checkin_guest.view.activity.LoginActivity;
import com.example.myapplication.checkin_guest.view.activity.MainActivity;
import com.example.myapplication.checkin_guest.view.activity.SmartCard;
import com.example.myapplication.checkin_guest.view.activity.SmartKey;
import com.example.myapplication.checkin_guest.viewModel.MainViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class FragMyInfo extends Fragment {
    private FragMyInfoBinding fragMyInfoBinding = null;
    private Dialog dialog;
    private GoogleSignInClient mGoogleSignInClient;
    private MainViewModel mainViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())).get(MainViewModel.class);

        fragMyInfoBinding = DataBindingUtil.inflate(inflater, R.layout.frag_my_info, container, false);

        // ????????? ????????? ?????? view ??????
        if (mainViewModel.isLogin()) {
            //????????? ??? ???????????? ??????
            fragMyInfoBinding.linearNotLogin.setVisibility(View.INVISIBLE);
        }else{
            //????????? ??? ??? ????????????
            fragMyInfoBinding.linearLogin.setVisibility(View.INVISIBLE);
        }

        fragMyInfoBinding.txtReservation.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SmartCard.class);
            startActivity(intent);
        });
        fragMyInfoBinding.txtLogout.setOnClickListener(view -> {
            showDialog();
        });

        fragMyInfoBinding.btnLogin.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });

        //??????????????????
        fragMyInfoBinding.txtAccountManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AccountActivity.class);
                startActivity(intent);
            }
        });

        fragMyInfoBinding.txtAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AlarmActivity.class);
                startActivity(intent);
            }
        });

        fragMyInfoBinding.txtHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                startActivity(intent);
            }
        });


        return fragMyInfoBinding.getRoot();
    }

    //dialog setting
    private void showDialog() {
        //??????????????? ?????? dialog ??????
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // ????????? ??????

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_logout);
        // Dialog ????????? ?????? ??????
        ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        dialog.show(); // ??????????????? ?????????

        dialog.findViewById(R.id.btn_yes).setOnClickListener(view -> {
            signOut();
            //?????? ????????? main reload
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        dialog.findViewById(R.id.btn_no).setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.findViewById(R.id.btn_exit).setOnClickListener(view -> {
            dialog.dismiss();
        });
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getActivity().getString(com.firebase.ui.auth.R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        mGoogleSignInClient.signOut();
    }

}