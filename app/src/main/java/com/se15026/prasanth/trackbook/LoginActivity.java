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

    private ArrayList<String> loginPhoneNumbers= new ArrayList<>();

    private String phoneInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUp = (Button)findViewById(R.id.signUpBtn);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        phoneTxt = (EditText) findViewById(R.id.phoneNumber);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Login");

        retrieveData();

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
                comparision();
            }
        });
    }

    private void retrieveData(){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    String name = data.getValue(String.class);
                    loginPhoneNumbers.add(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void comparision(){

        String numberInput = phoneTxt.getText().toString().trim();
        boolean decision = false;

        for (String number : loginPhoneNumbers){
            if(numberInput.equals(number)){
                decision = true;
                Toast toast = Toast.makeText(getApplicationContext(), "" + number, Toast.LENGTH_SHORT);
                toast.show();
                break;
            }
        }

        compareDecision(decision);
    }

    private void compareDecision(boolean result){
        if(result){
            Toast toast = Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT);
            toast.show();

            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }else{
            Toast toast = Toast.makeText(getApplicationContext(), "Login failure", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
