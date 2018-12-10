package com.se15026.prasanth.trackbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class HomeActivity extends AppCompatActivity {

    private ImageButton bookingBtn;
    private ImageButton historyBtn;
    private ImageButton logoutBtn;
    private ImageButton qrScannerBtn;

    private String userName;
    private String userPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bookingBtn = (ImageButton) findViewById(R.id.booking);
        historyBtn = (ImageButton) findViewById(R.id.history);
        logoutBtn = (ImageButton) findViewById(R.id.logout);
        qrScannerBtn = (ImageButton) findViewById(R.id.qrScanner);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            setUserName(extras.getString("loginName"));
            setUserPhoneNumber(extras.getString("loginNumber"));
        }

        enterNewActivity();//call all the function which related to onclick events
    }

    //function which has all the onclick events
    public void enterNewActivity(){

        bookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BookingActivity.class);
                intent.putExtra("userName", getUserName());
                intent.putExtra("phoneNumber", getUserPhoneNumber());
                startActivity(intent);
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, HistoryActivity.class);
                intent.putExtra("userName", getUserName());
                intent.putExtra("phoneNumber", getUserPhoneNumber());
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertBox();//call alert box creating function to make sure the logout
            }
        });

        qrScannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               scanProcess();//call function to scan the QR Code
            }
        });
    }

    //function to create alert box
    public void createAlertBox(){
        AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
        alert.setTitle("Alert");
        alert.setMessage("Do you really want to logout?");

        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // android.os.Process.killProcess(android.os.Process.myPid());
                //System.exit(1);
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.create().show();
    }

    //function to scan the QR Code
    public void scanProcess(){
        IntentIntegrator integrator = new IntentIntegrator(HomeActivity.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("..............SCANNING..............");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    //function to get the scan result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(intentResult != null){
            if(intentResult.getContents() == null){
                Toast.makeText(getApplicationContext(), "Scan cancelled", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), intentResult.getContents(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, QrScanActivity.class);
                startActivity(intent);
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
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
}
