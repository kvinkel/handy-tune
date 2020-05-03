package com.example.handytune.spotify;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// RetrofitClient implemented as singleton
public class RetrofitClient {
    private  static Retrofit retrofit;
    private static final String URL = "https://api.spotify.com/v1/";

    private RetrofitClient() {}

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
