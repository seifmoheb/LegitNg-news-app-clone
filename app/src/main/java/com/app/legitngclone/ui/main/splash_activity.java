package com.app.legitngclone.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.app.legitngclone.R;

public class splash_activity extends AppCompatActivity {

    private  Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!isFinishing()){

                        startActivity(new Intent(splash_activity.this,MainActivity.class));
                        finish();
                }
            }

        }, 1000);
    }
    @Override
    protected void onPause(){
        super.onPause();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        handler.removeCallbacks(runnable);

    }
}