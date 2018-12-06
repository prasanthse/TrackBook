package com.se15026.prasanth.trackbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private DatabaseReference database;

    private Button signUpCancel;
    private Button createAccount;
    private EditText nameTxt;
    private EditText phoneTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpCancel = (Button)findViewById(R.id.cancel);
        createAccount = (Button) findViewById(R.id.createAccount);
        nameTxt = (EditText) findViewById(R.id.name);
        phoneTxt = (EditText) findViewById(R.id.phone);

        database = FirebaseDatabase.getInstance().getReference().child("Login");

        signUpCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertBox(); //call alert box creating function to popup alert box
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = nameTxt.getText().toString().trim();
                String userPhoneNumber = phoneTxt.getText().toString().trim();

                if(userName.equals("") || userPhoneNumber.equals("")){
                    toastMessage("Please fill both the fields");//calling toast message function to create toast message
                }else{
                    database.child(userPhoneNumber).child("name").setValue(userName);
                    database.child(userPhoneNumber).child("number").setValue(userPhoneNumber);

                    toastMessage("Hi "+userName+", your account is created");//calling toast message function to create toast message
                    backToHome();//call back to home function and return to login page
                }
            }
        });
    }

    //function to return to login page
    private void backToHome(){
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    //function to create toast message
    private void toastMessage(String message){
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    //function to create alert box
    public void createAlertBox(){
        AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
        alert.setTitle("Alert");
        alert.setMessage("Do you really wants to cancel the SignUp");

        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toastMessage("Account creation cancelled"); //calling toast message function to create toast message
                backToHome(); //call back to home function and return to login page
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.create().show();
    }
}
