package com.se15026.prasanth.trackbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class BookingActivity extends AppCompatActivity {

    private Button submitBtn;
    private Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        bookingLinks();
    }

    public void bookingLinks(){
        submitBtn = (Button) findViewById(R.id.submit);
        cancelBtn = (Button) findViewById(R.id.cancel);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Your Booking was successful", Toast.LENGTH_SHORT);
                toast.show();

                Intent intent = new Intent(BookingActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Alertbox
                Toast toast = Toast.makeText(getApplicationContext(), "Your Booking was canceled", Toast.LENGTH_SHORT);
                toast.show();

                Intent intent = new Intent(BookingActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
