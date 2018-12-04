package com.se15026.prasanth.trackbook;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BookingActivity extends AppCompatActivity{

    private Button submitBtn;
    private Button cancelBtn;
    private Spinner start;
    private Spinner end;
    private Spinner time;
    private Spinner seats;
    private TextView date;
    private EditText card;
    private EditText pin;

    private DatabaseReference databaseReference;

    List<String> stationsList = new ArrayList<>();
    List<String> timeList = new ArrayList<>();

    BookingInfo bookingInfo = new BookingInfo();

    private String startStation;
    private String endStation;

    private int stationNumber = 1; //to identify the station key
    private String testStationName; //to declare station name

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private static final String TAG = "BookingActivity";

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
        date = (TextView) findViewById(R.id.date);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        retrieveStations();

        ArrayAdapter<String> stationAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, stationsList);
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

        start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedStation = parent.getItemAtPosition(position).toString();
                Toast toast = Toast.makeText(getApplicationContext(), selectedStation, Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        end.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dateWindowMaker();

                mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        Log.d(TAG, "OnDateSet : mm/dd/yy: " + month + "/" + day + "/" + year);
                        String bookedDate = month + "/" + day + "/" + year;
                        date.setText(bookedDate);
                    }
                };
            }
        });
    }

    public void dateWindowMaker(){

        Calendar cal = Calendar.getInstance();

        int date = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(BookingActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, date);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
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

/*
    public void stationValidation(){

        if(startStation.equals(endStation) || startStation.equals(null) || endStation.equals(null)){
            Toast toast = Toast.makeText(getApplicationContext(), "Please make sure you select two different stations", Toast.LENGTH_LONG);
            toast.show();

        }else{

            time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bookingInfo.setStartingStation(getStartStation());
                    bookingInfo.setEndStation(getEndStation());

                    retrieveTime();
                }
            });

        }
    }

    public void retrieveTime(){

        String start = Integer.toString(retrieveStationNumber(getStartStation()));
        String end = Integer.toString(retrieveStationNumber(getEndStation()));

        databaseReference.child("Time").child(start).child(end).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot time : dataSnapshot.getChildren()){
                    String timeSlot = time.getValue(String.class);
                    timeList.add(timeSlot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, timeList);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        time.setAdapter(timeAdapter);

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingInfo.setTime(time.getSelectedItem().toString());
            }
        });

        dateChoose();
    }

    public void dateChoose(){


    }

    public int retrieveStationNumber(String name){

        testStationName = name;

        databaseReference.child("Stations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot stationName : dataSnapshot.getChildren()){
                    if(testStationName.equals(stationName)){
                        break;
                    }else{
                        stationNumber = stationNumber + 1;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return stationNumber;
    }
*/
    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }
}
