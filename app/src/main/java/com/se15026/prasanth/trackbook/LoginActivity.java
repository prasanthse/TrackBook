package com.se15026.prasanth.trackbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    private Button signUp;
    private Button loginBtn;
    private EditText phoneTxt;

    private ArrayList<LoginInfo> loginPhoneNumbers= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUp = (Button)findViewById(R.id.signUpBtn);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        phoneTxt = (EditText) findViewById(R.id.phoneNumber);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Login");

        retrieveData(); //call the function to retrieve all the user login information from Login object in firebase

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comparision(); //call the function to compare given user phone number to firebase
            }
        });
    }

    //function to retrieve all the user login information from Login object in firebase
    private void retrieveData(){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()){

                    String name = data.child("name").getValue(String.class);
                    String number = data.child("number").getValue(String.class);

                    LoginInfo info = new LoginInfo();

                    info.setName(name);
                    info.setNumber(number);

                    loginPhoneNumbers.add(info);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //function to compare given user phone number to firebase
    private void comparision(){

        String numberInput = phoneTxt.getText().toString().trim();
        boolean decision = false;
        String userName = null;
        String phoneNumber = null;

        for (LoginInfo checkInfo : loginPhoneNumbers){
            if(numberInput.equals(checkInfo.getNumber())){
                userName = checkInfo.getName();
                phoneNumber = checkInfo.getNumber();
                decision = true;
                break;
            }
        }

        compareDecision(decision, userName, phoneNumber); //pass comparison result as the parameter into the compare decision function and decide
    }

    //compare the decision and confirm the login status
    private void compareDecision(boolean result, String userName, String phone){
        if(result){
            toastMessage("Login successful"); //call toast message function
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("loginName", userName);
            intent.putExtra("loginNumber", phone);
            startActivity(intent);
        }else{
            toastMessage("Login failure! please check your internet connection or phone number if not the reason please create an account");//call toast message function
        }
    }

    //function to create toast message
    private void toastMessage(String message){
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
