package com.example.myapplication.checkin_guest.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.checkin_guest.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        LottieAnimationView lottieAnimationView = (LottieAnimationView)findViewById(R.id.loading_image);

        lottieAnimationView.playAnimation();

        lottieAnimationView.loop(true);

        Handler handler = new Handler();

        handler.postDelayed(() -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }, 2000);
    }
}