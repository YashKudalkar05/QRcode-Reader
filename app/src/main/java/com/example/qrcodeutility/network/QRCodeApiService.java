package com.example.qrcodeutility.network;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface QRCodeApiService {

    // Endpoint to save a QR code to the Database
    @POST("qrcodes")
    Call<QRCodes> saveQRCode(@Body QRCodes qrCode);

    // Endpoint to fetch all stored QR codes
    @GET("qrcodes")
    Call<List<QRCodes>> getSavedQRCodes();

    // Endpoint to delete a QR code
    @DELETE("qrcodes/{id}")
    Call<Void> deleteQRCode(@Path("id") String id);



}
