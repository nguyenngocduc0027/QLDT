package com.example.qldt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler go_handle_screen = new Handler();
        go_handle_screen.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent push_handle_screen = new Intent(SplashScreenActivity.this,HandleScreenActivity.class);
                startActivity(push_handle_screen);
            }
        },5000);
    }
}