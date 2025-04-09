package com.example.qrcodeutility;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnScan, btnGenerate, btnHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        btnScan = findViewById(R.id.btn_scan);
        btnGenerate = findViewById(R.id.btn_generate);
        btnHistory = findViewById(R.id.btn_history);

        btnScan.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ScanActivity.class)));
        btnGenerate.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, GenerateActivity.class)));
        btnHistory.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, HistoryActivity.class)));
    }
}
