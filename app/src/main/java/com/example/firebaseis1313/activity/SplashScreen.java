package com.example.firebaseis1313.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firebaseis1313.R;

public class SplashScreen extends AppCompatActivity {

    Animation topAnima;
    ImageView imageView;
    TextView tvLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        topAnima = AnimationUtils.loadAnimation(this, R.anim.top_anima);
        imageView = findViewById(R.id.imgSplash);
        tvLogo = findViewById(R.id.tvLogo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, OnBoradingActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

    }
}