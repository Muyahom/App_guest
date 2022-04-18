package com.example.myapplication.checkin_guest.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.ActivitySmartCardBinding;
import com.example.myapplication.checkin_guest.util.CardService;
import com.example.myapplication.checkin_guest.util.Util;

public class SmartCard extends AppCompatActivity {
    private final String TAG = SmartCard.class.getSimpleName();
    private ActivitySmartCardBinding activitySmartCardBinding;
    private long backKeyPressedTime = 0;
    // 실제로 태그에 성공적으로 연결했는지 여부에 따라 표시될 메시지를 추가
    public static final String Write_Success = "성공적으로 태깅되었습니다.";
    public static final String Write_Failed = "태깅에 실패했습니다.";

    private Thread vibeThread;
    private boolean writeMode;

    private Intent _cardService;

    @Override
    public void onBackPressed() {
        // 기존 뒤로가기 버튼의 기능을 막기위해 주석처리 또는 삭제
        // super.onBackPressed();
        if (writeMode) {
            activitySmartCardBinding.switchBtn.setChecked(false);
        } else if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 스마트키가 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        } else if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            vibeOff();
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySmartCardBinding = DataBindingUtil.setContentView(this, R.layout.activity_smart_card);

        // 처리할 UI 관련 소스코드, 상태바 투명 및 바텀내비게이션 높이에 따른 레이아웃 페딩 설정
        Util.transparency_statusBar(this);
        activitySmartCardBinding.motionLayout.setPadding(0, 0, 0, Util.getBottomNavigationHeight(getApplicationContext()));

        activitySmartCardBinding.switchBtn.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                this.writeMode = true;
                switchIsOn();
            } else {
                this.writeMode = false;
                switchIsOff();
            }
        });

        _cardService = new Intent(this, CardService.class);
        startService(_cardService);

        activitySmartCardBinding.switchBtn.setChecked(false);
    }

    private void switchIsOn() {
        //체크된 상태로 만들 시 코드
        Log.d(TAG, "ON");

        //자연스러운 배경전환
        @SuppressLint("ResourceAsColor") ColorDrawable[] colorDrawables = {new ColorDrawable(R.color.remote_color), new ColorDrawable(Color.BLACK)};
        activitySmartCardBinding.motionLayout.setBackground(null);
        TransitionDrawable transitionDrawable = new TransitionDrawable(colorDrawables);
        activitySmartCardBinding.motionLayout.setBackground(transitionDrawable);
        transitionDrawable.startTransition(1000);

        //진동 수행
        vibeThread = new Thread(() -> {
            try {
                for (;;) {
                    vibeOn();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        vibeThread.start();

    }

    private void switchIsOff() {
        //체크된 상태 취소 시 코드
        Log.d(TAG, "OFF");
        activitySmartCardBinding.motionLayout.setBackground(getDrawable(R.color.white));
        activitySmartCardBinding.motionLayout.setBackground(getDrawable(R.drawable.gradient_main_logo));
        vibeOff();
    }

    private void vibeOn() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE)); // 0.5초간 진동
    }

    private void vibeOff() {
        //진동 끄기
        if (vibeThread != null) {
            vibeThread.interrupt();
        }
    }
}