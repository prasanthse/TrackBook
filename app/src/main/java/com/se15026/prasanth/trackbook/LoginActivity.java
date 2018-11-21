package com.se15026.prasanth.trackbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        changeActivity();
    }

    public void changeActivity(){
        Button signUp = (Button)findViewById(R.id.signUpBtn);
        Button loginBtn = (Button) findViewById(R.id.loginBtn);

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
                //database
                ToastMessage objToastSuccess = new ToastMessage("Login Successfull",getApplicationContext());
                ToastMessage objToastFailure = new ToastMessage("Login failure! Make sure your phone number is correct or Create a new account",getApplicationContext());
            }
        });
    }
}
