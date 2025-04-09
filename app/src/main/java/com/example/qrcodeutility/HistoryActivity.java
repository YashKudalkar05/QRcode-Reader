package com.example.qrcodeutility;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrcodeutility.adapters.QRCodeAdapter;
import com.example.qrcodeutility.network.QRCodes;
import com.example.qrcodeutility.network.QRCodeApiService;
import com.example.qrcodeutility.network.RetrofitClient;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchSavedQRCodes();
    }

    private void fetchSavedQRCodes() {
        QRCodeApiService apiService = RetrofitClient.getInstance().create(QRCodeApiService.class);
        apiService.getSavedQRCodes().enqueue(new Callback<List<QRCodes>>() {
            @Override
            public void onResponse(Call<List<QRCodes>> call, Response<List<QRCodes>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<QRCodes> qrCodes = response.body();

                    recyclerView.setAdapter(new QRCodeAdapter(HistoryActivity.this, qrCodes));
                } else {
                    Toast.makeText(HistoryActivity.this, "Failed to retrieve QR codes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<QRCodes>> call, Throwable t) {
                Toast.makeText(HistoryActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
