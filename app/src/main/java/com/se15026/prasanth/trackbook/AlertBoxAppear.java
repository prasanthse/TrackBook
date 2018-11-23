package com.se15026.prasanth.trackbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class AlertBoxAppear extends AppCompatActivity {

    private Activity thisActivity;
    private Class nextActivity;

    public AlertBoxAppear(Activity thisActivity, Class nextActivity) {
        this.thisActivity = thisActivity;
        this.nextActivity = nextActivity;
    }

    public Activity getThisActivity() {
        return thisActivity;
    }

    public Class getNextActivity() {
        return nextActivity;
    }

    public void createAlertBox(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getThisActivity());
        alert.setTitle("Sample");
        alert.setMessage("Change?");

        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getThisActivity(), getNextActivity());
                startActivity(intent);
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(), "Nothing Happened", Toast.LENGTH_LONG).show();
            }
        });

        alert.create().show();
    }
}
