package com.se15026.prasanth.trackbook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class HistoryActivity extends AppCompatActivity {

    private Button homeBtn;
    private ImageView imageView;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        homeBtn = (Button) findViewById(R.id.homeBtn);
        imageView = (ImageView) findViewById(R.id.qrImage);
        name = (TextView) findViewById(R.id.userNameHistory);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            name.setText(extras.getString("userName"));
        }

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
                BitMatrix bitMatrix = multiFormatWriter.encode(name.getText().toString().trim(), BarcodeFormat.QR_CODE,800,800);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

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
