package com.se15026.prasanth.trackbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class HistoryActivity extends AppCompatActivity {

    private Button homeBtn;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        homeBtn = (Button) findViewById(R.id.homeBtn);
        listView = (ListView) findViewById(R.id.qrCodeListView);

        backToHome();
    }

    public void backToHome(){

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
