package com.se15026.prasanth.trackbook;

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

        database = FirebaseDatabase.getInstance().getReference();

        signUpCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alertDialog
                Toast toast = Toast.makeText(getApplicationContext(), "Account creation cancelled", Toast.LENGTH_SHORT);
                toast.show();

                backToHome();
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = nameTxt.getText().toString().trim();
                String userPhoneNumber = phoneTxt.getText().toString().trim();

                DatabaseReference loginRef = database.child("Login");
                loginRef.child(userPhoneNumber).setValue(userName);

                Toast toast = Toast.makeText(getApplicationContext(), "Hi "+userName+", your account is created", Toast.LENGTH_LONG);
                toast.show();

                backToHome();
            }
        });
    }

    private void backToHome(){
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
