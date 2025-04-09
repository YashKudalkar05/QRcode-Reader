package com.example.qrcodeutility.network;

import com.google.gson.annotations.SerializedName;

public class QRCodes {
    @SerializedName("_id")

    private String id;
    private String data;
    private String createdAt;

    // Initialize QR data
    public QRCodes(String data) {
        this.data = data;
        this.createdAt = String.valueOf(System.currentTimeMillis());
    }
    public QRCodes(String id, String data, String createdAt) {
        this.id = id;
        this.data = data;
        this.createdAt = createdAt;
    }

    // Default no-argument constructor for RetroFit
    public QRCodes() {}

    public String getId() { return id; }
    public String getData() { return data; }
    public String getCreatedAt() { return createdAt; }
}
