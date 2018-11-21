package com.se15026.prasanth.trackbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        changeActivity();
    }

    public void changeActivity(){
        Button signUpCancel = (Button)findViewById(R.id.cancel);
        Button createAccount = (Button) findViewById(R.id.createAccount);

        signUpCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastMessage objToastAccountCreate = new ToastMessage("Hi Prasanth, your account is created",getApplicationContext());
            }
        });
    }
}
