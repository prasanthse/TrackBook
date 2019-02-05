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
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private Button homeBtn;
    private ImageView imageView;
    private TextView name;
    private TextView noTickets;

    private String phoneNumber;
    private String userName;

    List<BookingInfo> bookingList;
    BookingInfo bookingInfo = new BookingInfo();


    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        homeBtn = (Button) findViewById(R.id.homeBtn);
        imageView = (ImageView) findViewById(R.id.qrImage);
        name = (TextView) findViewById(R.id.userNameHistory);
        noTickets = (TextView) findViewById(R.id.noTicket);

        bookingList = new ArrayList<BookingInfo>();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Bookings");

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            name.setText(extras.getString("userName"));
            setUserName(extras.getString("userName"));
            setPhoneNumber(extras.getString("phoneNumber"));
        }

        noTickets.setVisibility(View.INVISIBLE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren())
                    if ((data.child("PhoneNumber").getValue()).equals(getPhoneNumber())) {

                        bookingInfo.setName((String) data.child("Name").getValue());
                        bookingInfo.setPhoneNumber((String) data.child("PhoneNumber").getValue());
                        bookingInfo.setStartingStation((String) data.child("StartStation").getValue());
                        bookingInfo.setEndStation((String) data.child("EndStation").getValue());
                        bookingInfo.setTime((String) data.child("Time").getValue());
                        bookingInfo.setDate((String) data.child("Date").getValue());
                        bookingInfo.setBookedClass((String) data.child("Class").getValue());
                        bookingInfo.setSeat(Integer.parseInt(data.child("Seats").getValue().toString()));

                        //bookingList.add(bookingInfo);

                        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                        BitMatrix bitMatrix;

                        try {
                            bitMatrix = multiFormatWriter.encode(bookingInfo.toString(), BarcodeFormat.QR_CODE,800,800);
                            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                            imageView.setImageBitmap(bitmap);
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }

                        break;

                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
