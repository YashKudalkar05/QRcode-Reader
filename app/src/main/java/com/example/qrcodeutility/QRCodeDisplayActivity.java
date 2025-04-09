package com.example.qrcodeutility;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCodeDisplayActivity extends AppCompatActivity {
    private ImageView qrCodeImageView;
    private TextView qrCodeTextView;
    private Button editQRCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_display);

        qrCodeImageView = findViewById(R.id.imgQRCode);
        qrCodeTextView = findViewById(R.id.txtQRCode);
        editQRCodeButton = findViewById(R.id.btnEditQRCode);

        // Get scanned data from intent
        String qrCodeText = getIntent().getStringExtra("QR_CODE_TEXT");

        if (qrCodeText != null) {
            qrCodeTextView.setText(qrCodeText);

            try {
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.encodeBitmap(qrCodeText, com.google.zxing.BarcodeFormat.QR_CODE, 400, 400);
                qrCodeImageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        editQRCodeButton.setOnClickListener(v -> {
            Intent intent = new Intent(QRCodeDisplayActivity.this, GenerateActivity.class);
            intent.putExtra("QR_CODE_TEXT", qrCodeText);
            startActivity(intent);
        });
    }
}
