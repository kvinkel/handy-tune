package com.example.handytune.spotify;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.example.handytune.SearchActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// RetrofitClient implemented as singleton
public class RetrofitClient {
    private static Retrofit retrofit;
    private static final String URL = "https://api.spotify.com/v1/";
    private static String authToken = "";

    private RetrofitClient() {
    }

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static String getAuthToken() {
        return authToken;
    }

    public static void setAuthToken(String token) {
        RetrofitClient.authToken = "Bearer " + token;
    }

    public static void clearToken() {
        authToken = "";
    }

    public static void noLoginAlert(Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Need Spotify login");
        alertDialog.setMessage("Must be logged in to use search function");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}
