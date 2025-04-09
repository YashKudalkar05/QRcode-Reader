package com.example.qrcodeutility;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qrcodeutility.network.QRCodeApiService;
import com.example.qrcodeutility.network.QRCodes;
import com.example.qrcodeutility.network.RetrofitClient;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenerateActivity extends AppCompatActivity {

    private EditText edtInput;
    private Button btnGenerate, btnSaveQRCode;
    private ImageView imgQRCode;
    private String qrCodeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);

        edtInput = findViewById(R.id.edt_input);
        btnGenerate = findViewById(R.id.btn_generate_qr);
        btnSaveQRCode = findViewById(R.id.btnSaveQRCode);
        imgQRCode = findViewById(R.id.img_qrcode);

        String reusedData = getIntent().getStringExtra("reuseQrData");
        if (reusedData != null) {
            edtInput.setText(reusedData);
        }

        String qrCodeText = getIntent().getStringExtra("QR_CODE_TEXT");
        if (qrCodeText != null) {
            edtInput.setText(qrCodeText);
        }

        btnGenerate.setOnClickListener(v -> generateQRCode());

        btnSaveQRCode.setOnClickListener(v -> saveQRCodeToServer());
    }

    private void generateQRCode() {
        qrCodeText = edtInput.getText().toString().trim();

        if (TextUtils.isEmpty(qrCodeText)) {
            Toast.makeText(this, "Enter text to generate QR code", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            BitMatrix bitMatrix = barcodeEncoder.encode(qrCodeText, BarcodeFormat.QR_CODE, 400, 400);
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imgQRCode.setImageBitmap(bitmap);

            btnSaveQRCode.setVisibility(View.VISIBLE);
        } catch (WriterException e) {
            Toast.makeText(this, "Error generating QR code", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void saveQRCodeToServer() {
        String qrData = edtInput.getText().toString().trim();

        if (TextUtils.isEmpty(qrData)) {
            Toast.makeText(this, "No QR code to save!", Toast.LENGTH_SHORT).show();
            return;
        }

        QRCodeApiService apiService = RetrofitClient.getInstance().create(QRCodeApiService.class);
        QRCodes newQRCode = new QRCodes(qrData);

        apiService.saveQRCode(newQRCode).enqueue(new Callback<QRCodes>() {
            @Override
            public void onResponse(Call<QRCodes> call, Response<QRCodes> response) {
                if (response.isSuccessful()) {
                    btnSaveQRCode.setEnabled(false);
                    btnSaveQRCode.setText("QR Code Saved");
                    Toast.makeText(GenerateActivity.this, "QR Code Saved Successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(GenerateActivity.this, "Error Saving QR Code", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<QRCodes> call, Throwable t) {
                Toast.makeText(GenerateActivity.this, "Request Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
