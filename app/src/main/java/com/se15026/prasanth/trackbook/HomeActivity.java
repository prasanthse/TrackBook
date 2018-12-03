package com.se15026.prasanth.trackbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity {

    private ImageButton bookingBtn;
    private ImageButton historyBtn;
    private ImageButton logoutBtn;
    private ImageButton qrScannerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bookingBtn = (ImageButton) findViewById(R.id.booking);
        historyBtn = (ImageButton) findViewById(R.id.history);
        logoutBtn = (ImageButton) findViewById(R.id.logout);
        qrScannerBtn = (ImageButton) findViewById(R.id.qrScanner);

        enterNewActivity();
    }

    public void enterNewActivity(){

        bookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BookingActivity.class);
                startActivity(intent);
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dialog Alert
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

        qrScannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //QR Scanner activity
            }
        });
    }

}
