package com.se15026.prasanth.trackbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class BookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        bookingLinks();
    }

    public void bookingLinks(){
        Button submitBtn = (Button) findViewById(R.id.submit);
        Button cancelBtn = (Button) findViewById(R.id.cancel);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastMessage objToastBooked = new ToastMessage("Your Booking was successfull",getApplicationContext());

                Intent intent = new Intent(BookingActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToastMessage objToastBookingCancel = new ToastMessage("Booking canceled!",getApplicationContext());

                //Intent intent = new Intent(BookingActivity.this, HomeActivity.class);
                //startActivity(intent);

                AlertBoxAppear alertBoxAppear = new AlertBoxAppear(BookingActivity.this, HomeActivity.class);
            }
        });
    }
}
