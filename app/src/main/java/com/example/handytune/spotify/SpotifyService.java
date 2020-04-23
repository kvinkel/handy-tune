package com.example.handytune.spotify;

import com.example.handytune.spotify.model.Artists;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.Query;

public interface SpotifyService {

    // Example artist search endpoint ../search?q=queen&type=artist
    @GET("/search")
    Call<List<Artists>> artists(@Query("artist") String artist, @Query("type") String type);
    
}
