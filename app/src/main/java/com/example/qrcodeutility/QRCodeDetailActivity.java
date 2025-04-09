package com.example.qrcodeutility;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qrcodeutility.network.QRCodes;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

public class QRCodeDetailActivity extends AppCompatActivity {

    private ImageView imgQRCode;
    private TextView tvQRCodeData;
    private Button btnReuse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_detail);

        imgQRCode = findViewById(R.id.img_qrcode_detail);
        tvQRCodeData = findViewById(R.id.tv_qrcode_data_detail);
        btnReuse = findViewById(R.id.btn_reuse_qr);

        Intent intent = getIntent();
        String qrData = intent.getStringExtra("qrData");

        tvQRCodeData.setText(qrData);
        generateQRCode(qrData);

        btnReuse.setOnClickListener(v -> {
            Intent reuseIntent = new Intent(QRCodeDetailActivity.this, GenerateActivity.class);
            reuseIntent.putExtra("reuseQrData", qrData);
            startActivity(reuseIntent);
        });

    }

    private void generateQRCode(String data) {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 400, 400);
            imgQRCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
