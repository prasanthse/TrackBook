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

    private Button temperoryBtn;//This is a temperory one and it will remove after succesfully retrieve time

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

    private String startStationNumber;
    private String endStationNumber;

    private String userName;
    private String userPhoneNumber;
    private String from;
    private String to;
    private Boolean enableTime;

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

        temperoryBtn = (Button) findViewById(R.id.temperoryBtn);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            name.setText(extras.getString("userName"));

            setUserName(extras.getString("userName"));
            setUserPhoneNumber(extras.getString("phoneNumber"));

            bookingInfo.setName(name.getText().toString().trim());
            bookingInfo.setPhoneNumber(extras.getString("phoneNumber"));
        }

        stationsList = new ArrayList<String>();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        retrieveStations();//call function to retrieve all the stations in Station object in firebase

        seatAmount = ArrayAdapter.createFromResource(this, R.array.seatAmount, android.R.layout.simple_spinner_item);
        seatAmount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seats.setAdapter(seatAmount);

        setEnableTime(false);

        //time.setEnabled(false);

        selectStations();

        bookingLinks();//call function to call all the relevant onclick functions

    }

    public void selectStations(){
        start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                start.setSelection(position);
                String text = parent.getItemAtPosition(position).toString();
                setFrom(text);

                retrieveStationNumber(1);
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
                setTo(text);

                retrieveStationNumber(2);

                stationValidation();//call function to check whether the start and end station are same or different

                if(getEnableTime()){
                    bookingInfo.setEndStation(getTo());
                    bookingInfo.setStartingStation(getFrom());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //call function to call all the relevant onclick functions
    public void bookingLinks(){

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkButton();//call function to get the value of selected radio button
                int confirmation = 1;

                if(bookingInfo.getDate() == null || pin.getText().toString().trim().length() == 0 || card.getText().toString().trim().length() == 0){
                    toastMessage("Please fill all the fields");
                }else{

                    bookingInfo.setTime("06:10 AM");

                    Intent intent = new Intent(BookingActivity.this, HomeActivity.class);
                    intent.putExtra("loginName", bookingInfo.getName());
                    intent.putExtra("loginNumber", bookingInfo.getPhoneNumber());
                    createAlertBox("Are you sure to submit?", "Your Booking was successful", intent, confirmation);

                    toastMessage(bookingInfo.toString());
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int confirmation = 0;
                Intent intent = new Intent(BookingActivity.this, HomeActivity.class);
                intent.putExtra("loginName", getUserName());
                intent.putExtra("loginNumber", getUserPhoneNumber());
                createAlertBox("Are you sure to cancel submit?", "Your Booking was canceled", intent, confirmation);
            }
        });

        temperoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMessage("retrieve time is not finished");
                toastMessage("Time is fixed to 06:10AM until fix the error");
            }
        });

        time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "This is not finished yet. So time is fixed to 06:10AM", Toast.LENGTH_LONG).show();
                bookingInfo.setTime("06:10AM");
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
                        Log.d(TAG, "OnDateSet : dd/mm/yy: " + day + "/" + month + "/" + year);
                        String bookedDate = day + "/" + month + "/" + year;
                        date.setText(bookedDate);
                        bookingInfo.setDate(bookedDate);
                    }
                };
            }
        });

        seats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seats.setSelection(position);
                String text = parent.getItemAtPosition(position).toString();
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

    //function to create alert box
    public void createAlertBox(String message, final String toastMessage, final Intent intent, final int confirmation){
        AlertDialog.Builder alert = new AlertDialog.Builder(BookingActivity.this);
        alert.setTitle("Alert");
        alert.setMessage(message);

        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(confirmation == 1){
                    String key = databaseReference.child("Bookings").push().getKey();

                    databaseReference.child("Bookings").child(key).child("Name").setValue(bookingInfo.getName());
                    databaseReference.child("Bookings").child(key).child("PhoneNumber").setValue(bookingInfo.getPhoneNumber());
                    databaseReference.child("Bookings").child(key).child("StartStation").setValue(bookingInfo.getStartingStation());
                    databaseReference.child("Bookings").child(key).child("EndStation").setValue(bookingInfo.getEndStation());
                    databaseReference.child("Bookings").child(key).child("Time").setValue(bookingInfo.getTime());
                    databaseReference.child("Bookings").child(key).child("Date").setValue(bookingInfo.getDate());
                    databaseReference.child("Bookings").child(key).child("Class").setValue(bookingInfo.getBookedClass());
                    databaseReference.child("Bookings").child(key).child("Seats").setValue(bookingInfo.getSeat());
                }

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

    //function to retrieve all the stations in Station object in firebase
    public void retrieveStations(){

        databaseReference.child("Stations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot stations : dataSnapshot.getChildren()){
                    String stationNames = stations.getValue(String.class);
                    stationsList.add(stationNames);
                }

                stationAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, stationsList);
                stationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                start.setAdapter(stationAdapter);
                end.setAdapter(stationAdapter);

                setstartStationNumber("1");
                setEndStationNumber("1");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //function to create toast message
    public void toastMessage(String message){
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    //function to check whether the start and end station are same or different
    public void stationValidation(){

        if(getFrom().equals(getTo())){
            setEnableTime(false);
            toastMessage("Please make sure you select two different stations");
        }else{
            setEnableTime(true);
            //retrieveTime();//call function to retrieve time table
        }
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

    //function to retrieve time table
    public void retrieveTime(){

        databaseReference.child("Time").child(getstartStationNumber()).child(getEndStationNumber()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot time : dataSnapshot.getChildren()){
                    String timeSlot = time.getValue(String.class);
                    timeList.add(timeSlot);
                }

                ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, timeList);
                timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                time.setAdapter(timeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void setStartStation(){

        databaseReference.child("Stations").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot stationName : dataSnapshot.getChildren()){
                    if(getFrom().equals(stationName.getValue())){
                        setstartStationNumber(stationName.getKey());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setEndStation(){
        databaseReference.child("Stations").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot stationName : dataSnapshot.getChildren()){
                    if(getTo().equals(stationName.getValue())){
                        setEndStationNumber(stationName.getKey());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //function to retrieve starting station number
    public void retrieveStationNumber(int num){

        if (num == 1) {
            setStartStation();
        }else{
            setEndStation();
        }

    }

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

    public String getstartStationNumber() {
        return startStationNumber;
    }

    public void setstartStationNumber(String startStationNumber) {
        this.startStationNumber = startStationNumber;
    }

    public String getEndStationNumber() {
        return endStationNumber;
    }

    public void setEndStationNumber(String endStationNumber) {
        this.endStationNumber = endStationNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Boolean getEnableTime() {
        return enableTime;
    }

    public void setEnableTime(Boolean enableTime) {
        this.enableTime = enableTime;
    }
}
