package com.example.a247news.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a247news.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        showDashboard();
    }

    private void showDashboard() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                activityToActivity(MainActivity.class);
                SplashActivity.this.finish();
            }
        }, 2000);
    }
}
