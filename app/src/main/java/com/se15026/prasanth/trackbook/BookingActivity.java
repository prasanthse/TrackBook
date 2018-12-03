package com.se15026.prasanth.trackbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookingActivity extends AppCompatActivity {

    private Button submitBtn;
    private Button cancelBtn;
    private Spinner start;
    private Spinner end;
    private Spinner time;
    private Spinner seats;
    private EditText card;
    private EditText pin;

    private DatabaseReference databaseReference;

    ArrayList<String> stationsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        submitBtn = (Button) findViewById(R.id.submit);
        cancelBtn = (Button) findViewById(R.id.cancel);
        start = (Spinner) findViewById(R.id.startDropdown);
        end = (Spinner) findViewById(R.id.endDropdown);
        time = (Spinner) findViewById(R.id.timeDropdown);
        seats = (Spinner) findViewById(R.id.seatsDropdown);
        card = (EditText)findViewById(R.id.cardNumber);
        pin = (EditText) findViewById(R.id.pinNumber);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        retrieveStations();

        ArrayAdapter<String> stationAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, stationsList);
        stationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        start.setAdapter(stationAdapter);
        end.setAdapter(stationAdapter);

        bookingLinks();
    }

    public void bookingLinks(){

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AlertBox
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

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

       /* end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //end.setAdapter(stationAdapter);
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

    }

    public void retrieveStations(){

        databaseReference.child("Stations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot stations : dataSnapshot.getChildren()){

                    String stationNames = stations.getValue(String.class);
                    stationsList.add(stationNames);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
