package com.se15026.prasanth.trackbook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private Button homeBtn;
    private ImageView imageView;
    private TextView name;

    private String phoneNumber;
    private String userName;

    private ArrayList<BookingInfo> bookingList = new ArrayList<>();

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        homeBtn = (Button) findViewById(R.id.homeBtn);
        imageView = (ImageView) findViewById(R.id.qrImage);
        name = (TextView) findViewById(R.id.userNameHistory);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Bookings");

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            name.setText(extras.getString("userName"));
            setUserName(extras.getString("userName"));
            setPhoneNumber(extras.getString("phoneNumber"));
        }

        retrieveData();//call function to retrieve bookings information from database

        for(int i = 0; i<bookingList.size(); i++){
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(bookingList.get(i).toString(), BarcodeFormat.QR_CODE,800,800);
                //BitMatrix bitMatrix = multiFormatWriter.encode("Track Book", BarcodeFormat.QR_CODE,800,800);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                imageView.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }

        backToHome();
    }

    public void backToHome(){

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, HomeActivity.class);
                intent.putExtra("loginName", getUserName());
                intent.putExtra("loginNumber", getPhoneNumber());
                startActivity(intent);
            }
        });
    }

    //function to retrieve bookings information from database
    private void retrieveData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren())
                    if ((data.child("PhoneNumber").getValue()).equals(getPhoneNumber())) {
                        BookingInfo bookingInfo = new BookingInfo();

                        bookingInfo.setName((String) data.child("Name").getValue());
                        bookingInfo.setPhoneNumber((String) data.child("PhoneNumber").getValue());
                        bookingInfo.setStartingStation((String) data.child("StartStation").getValue());
                        bookingInfo.setEndStation((String) data.child("EndStation").getValue());
                        bookingInfo.setTime((String) data.child("Time").getValue());
                        bookingInfo.setDate((String) data.child("Date").getValue());
                        bookingInfo.setBookedClass((String) data.child("Class").getValue());
                        bookingInfo.setSeat(Integer.parseInt(data.child("Seats").getValue().toString()));

                        bookingList.add(bookingInfo);
                        Toast.makeText(getApplicationContext(), bookingInfo.toString(), Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
