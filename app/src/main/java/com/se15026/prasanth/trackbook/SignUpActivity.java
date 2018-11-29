package com.se15026.prasanth.trackbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private DatabaseReference database;

    private Button signUpCancel = (Button)findViewById(R.id.cancel);
    private Button createAccount = (Button) findViewById(R.id.createAccount);
    private EditText nameTxt = (EditText) findViewById(R.id.name);
    private EditText phoneTxt = (EditText) findViewById(R.id.phone);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        database = FirebaseDatabase.getInstance().getReference();

        signUpCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alertDialog
                backToHome();
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = nameTxt.getText().toString().trim();
                String userPhoneNumber = phoneTxt.getText().toString().trim();

                database.child("Login").child(userPhoneNumber).setValue(userName);

                ToastMessage objToastAccountCreate = new ToastMessage("Hi "+userName+", your account is created",getApplicationContext());
                backToHome();
            }
        });
    }

    private void backToHome(){
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
