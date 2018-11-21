package com.se15026.prasanth.trackbook;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class ToastMessage extends AppCompatActivity {

    private String message;
    private Context context;

    public ToastMessage(String message, Context context) {
        this.message = message;
        this.context = context;

        createToastMessage();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void createToastMessage(){
        Toast.makeText(getContext(), getMessage(), Toast.LENGTH_SHORT);
    }
}
