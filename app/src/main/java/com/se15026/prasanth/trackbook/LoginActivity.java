package com.se15026.prasanth.trackbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    private Button signUp = (Button)findViewById(R.id.signUpBtn);
    private Button loginBtn = (Button) findViewById(R.id.loginBtn);
    private EditText phoneTxt = (EditText) findViewById(R.id.phoneNumber);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Login");

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

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String phoneDb = dataSnapshot.getValue().toString();
                        String pboneInput = phoneTxt.getText().toString().trim();

                        boolean result = comparission(phoneDb, pboneInput);

                        compareDecision(result);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        ToastMessage objToastSuccess = new ToastMessage("Make sure you are connected to internet / System down",getApplicationContext());
                    }
                });
            }
        });
    }

    private boolean comparission(String original, String given){
        //need to add as loop after working
        if(given.equals(original)){
            return  true;
        }else{
            return false;
        }
    }

    private void compareDecision(boolean result){
        if(result == true){
            ToastMessage objToastSuccess = new ToastMessage("Login Successfull",getApplicationContext());
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }else{
            ToastMessage objToastFailure = new ToastMessage("Login failure! Make sure your phone number is correct or Create a new account",getApplicationContext());
        }
    }

}
