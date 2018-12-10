package com.se15026.prasanth.trackbook;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private TextView name;
    private EditText card;
    private EditText pin;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private DatabaseReference databaseReference;

    private List<String> stationsList;
    private List<String> timeList;
    private ArrayAdapter<String> stationAdapter;
    private ArrayAdapter<CharSequence> seatAmount;

    BookingInfo bookingInfo = new BookingInfo();

    private String startStation;
    private String endStation;
    private String receivedCardNumber = null;
    private String receivedPinNumber = null;

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
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        name = (TextView) findViewById(R.id.userNameBooking);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            name.setText(extras.getString("userName"));
            bookingInfo.setName(name.getText().toString().trim());
        }

        stationsList = new ArrayList<String>();

        stationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stationsList);
        stationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        retrieveStations();//call function to retrieve all the stations in Station object in firebase

        start.setAdapter(stationAdapter);
        end.setAdapter(stationAdapter);

        seatAmount = ArrayAdapter.createFromResource(this, R.array.seatAmount, android.R.layout.simple_spinner_item);
        seatAmount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seats.setAdapter(seatAmount);

        bookingLinks();//call function to call all the relevant onclick functions
    }

    //call function to call all the relevant onclick functions
    public void bookingLinks(){

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkButton();//call function to get the value of selected radio button

                if(date.getText().equals(null) || card.getText().equals(null) || pin.getText().equals(null)){
                    toastMessage("Please fill all the fields");
                }else{
                    Intent intent = new Intent(BookingActivity.this, HomeActivity.class);
                    createAlertBox("Are you sure to submit?", "Your Booking was successful", intent);
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingActivity.this, HomeActivity.class);
                createAlertBox("Are you sure to cancel submit?", "Your Booking was canceled", intent);
            }
        });

        start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                start.setSelection(position);
                String text = parent.getItemAtPosition(position).toString();
                toastMessage(text);
                bookingInfo.setStartingStation(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        end.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                end.setSelection(position);
                String text = parent.getItemAtPosition(position).toString();
                toastMessage(text);
                bookingInfo.setEndStation(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //stationValidation();//call function to check whether the start and end station are same or different
                //time.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dateWindowMaker();//call function to create calender

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

        seats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seats.setSelection(position);
                String text = parent.getItemAtPosition(position).toString();
                toastMessage(text);
                bookingInfo.setSeat(Integer.parseInt(text));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReceivedCardNumber(card.getText().toString().trim());
            }
        });

        pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReceivedPinNumber(pin.getText().toString().trim());
            }
        });
    }

    //function to create toast message
    public void toastMessage(String message){
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    //function to create calender
    public void dateWindowMaker(){

        Calendar cal = Calendar.getInstance();

        int date = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(BookingActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, date);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    //function to retrieve all the stations in Station object in firebase
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

    //function to create alert box
    public void createAlertBox(String message, final String toastMessage,final Intent intent){
        AlertDialog.Builder alert = new AlertDialog.Builder(BookingActivity.this);
        alert.setTitle("Alert");
        alert.setMessage(message);

        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toastMessage(toastMessage);//call toast message function to create toast message
                startActivity(intent);
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.create().show();
    }

    //function to get the value of selected radio button
    public void checkButton(){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

        String classValue = String.valueOf(radioButton.getText());

        bookingInfo.setBookedClass(classValue);
    }

/*
    //function to check whether the start and end station are same or different
    public void stationValidation(){

        if(startStation.equals(endStation) || startStation.equals(null) || endStation.equals(null)){
            toastMessage("Please make sure you select two different stations");
        }else{
            bookingInfo.setStartingStation(getStartStation());
            bookingInfo.setEndStation(getEndStation());
            retrieveTime();//call function to retrieve time table
        }
    }

    //function to retrieve time table
    public void retrieveTime(){

        String start = Integer.toString(retrieveStationNumber(bookingInfo.getStartingStation()));
        String end = Integer.toString(retrieveStationNumber(bookingInfo.getEndStation()));

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

        bookingInfo.setTime(time.getSelectedItem().toString());
    }

    //function to retrieve starting station number
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

    public String getReceivedCardNumber() {
        return receivedCardNumber;
    }

    public void setReceivedCardNumber(String receivedCardNumber) {
        this.receivedCardNumber = receivedCardNumber;
    }

    public String getReceivedPinNumber() {
        return receivedPinNumber;
    }

    public void setReceivedPinNumber(String receivedPinNumber) {
        this.receivedPinNumber = receivedPinNumber;
    }
}
