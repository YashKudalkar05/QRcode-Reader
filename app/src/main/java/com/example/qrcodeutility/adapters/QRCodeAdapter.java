package com.example.qrcodeutility.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrcodeutility.QRCodeDetailActivity;
import com.example.qrcodeutility.network.QRCodes;
import com.example.qrcodeutility.network.QRCodeApiService;
import com.example.qrcodeutility.network.RetrofitClient;
import com.example.qrcodeutility.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QRCodeAdapter extends RecyclerView.Adapter<QRCodeAdapter.ViewHolder> {
    private List<QRCodes> qrCodes;
    private Context context;
    private QRCodeApiService apiService;

    public QRCodeAdapter(Context context, List<QRCodes> qrCodes) {
        this.context = context;
        this.qrCodes = qrCodes;
        this.apiService = RetrofitClient.getInstance().create(QRCodeApiService.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_qrcode, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QRCodes qrCode = qrCodes.get(position);
        holder.tvQRCodeData.setText(qrCode.getData());

        // View button click
        holder.btnViewUrl.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), QRCodeDetailActivity.class);
            intent.putExtra("qrData", qrCode.getData());
            v.getContext().startActivity(intent);
        });

        // Delete button click
        holder.btnDelete.setOnClickListener(v -> {
            String qrCodeId = qrCode.getId();
            Log.d("QRCodeDebug", "Attempting to delete QR Code with ID: " + qrCodeId);

            if (qrCodeId != null && !qrCodeId.isEmpty()) {
                deleteQRCode(qrCodeId, position);
            } else {
                Toast.makeText(context, "Invalid QR Code ID!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return qrCodes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQRCodeData;
        Button btnViewUrl, btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            tvQRCodeData = itemView.findViewById(R.id.tv_qrcode_data);
            btnViewUrl = itemView.findViewById(R.id.btn_view_url);
            btnDelete = itemView.findViewById(R.id.btn_delete_qr);
        }
    }

    private void deleteQRCode(String qrCodeId, int position) {
        Log.d("QRCodeDebug", "Attempting to delete QR Code ID: " + qrCodeId);

        apiService.deleteQRCode(qrCodeId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("QRCodeDebug", "Delete response code: " + response.code());

                if (response.isSuccessful()) {
                    qrCodes.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "QR Code Deleted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to Delete QR Code: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
