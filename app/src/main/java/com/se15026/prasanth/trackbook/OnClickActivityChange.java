package com.se15026.prasanth.trackbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class OnClickActivityChange extends AppCompatActivity {

    private Button btn;
    private Activity current;
    private Class next;

    public OnClickActivityChange(Button btn, Activity current, Class next) {
        this.btn = btn;
        this.current = current;
        this.next = next;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public Activity getCurrent() {
        return current;
    }

    public void setCurrent(Activity current) {
        this.current = current;
    }

    public Class getNext() {
        return next;
    }

    public void setNext(Class next) {
        this.next = next;
    }

    public void activityChangeOnClick(){
        getBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getCurrent(), getNext());
                startActivity(intent);
            }
        });
    }
}
