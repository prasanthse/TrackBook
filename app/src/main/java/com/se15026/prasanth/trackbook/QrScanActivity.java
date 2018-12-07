package com.se15026.prasanth.trackbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class QrScanActivity extends AppCompatActivity {

   /* private SurfaceView cameraPreview;
    private TextView sampleTest;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;

    private final int RequestCameraPermission = 1001;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RequestCameraPermission:
                {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            return;
                        }
                        try {
                            cameraSource.start(cameraPreview.getHolder());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            }
                break;
        }
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan);
/*
        cameraPreview = (SurfaceView) findViewById(R.id.scanQrCode);
        sampleTest = (TextView) findViewById(R.id.sampleText);

        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(640, 480).build();

        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(QrScanActivity.this, new String[]{Manifest.permission.CAMERA}, RequestCameraPermission);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();
                if(qrCodes.size() != 0) {
                    sampleTest.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(1000);
                            sampleTest.setText(qrCodes.valueAt(0).displayValue);
                        }
                    });
                }
            }
        });*/
    }

}
