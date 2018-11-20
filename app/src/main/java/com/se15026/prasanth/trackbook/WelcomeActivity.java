package com.se15026.prasanth.trackbook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    private static int time = 5000; //Assign 5 seconds to time variable
    private Handler myHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        myHandler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, com.se15026.prasanth.trackbook.LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, time);

    }
}
