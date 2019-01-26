package com.se15026.prasanth.trackbook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
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

        //databaseReference = FirebaseDatabase.getInstance().getReference().child("Bookings");

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            name.setText(extras.getString("userName"));
            setUserName(extras.getString("userName"));
            setPhoneNumber(extras.getString("phoneNumber"));
        }

        //retrieveData();//call function to retrieve bookings information from database

        //for(int i = 0; i<bookingList.size(); i++){
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                //BitMatrix bitMatrix = multiFormatWriter.encode(bookingList.get(i).toString(), BarcodeFormat.QR_CODE,800,800);
                BitMatrix bitMatrix = multiFormatWriter.encode("Track Book", BarcodeFormat.QR_CODE,800,800);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                imageView.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        //}

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
/*
    //function to retrieve bookings information from database
    private void retrieveData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()){
                    if((data.child("PhoneNumber").getValue()).equals(getPhoneNumber())){
                        BookingInfo bookingInfo = new BookingInfo();
                        bookingList.add(bookingInfo);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
*/
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
