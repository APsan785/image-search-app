package com.apsan.imagesearchapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.apsan.imagesearchapp.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashScreenBinding binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            );
        }
        Animation topAnim = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        Animation middleAnime = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

        binding.appname.setAnimation(middleAnime);

        binding.image1.setAnimation(topAnim);
        binding.image2.setAnimation(topAnim);
        binding.image3.setAnimation(topAnim);

        binding.image4.setAnimation(bottomAnim);
        binding.image5.setAnimation(bottomAnim);
        binding.image6.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                finish();
            }
        }, 5000);
    }
}
