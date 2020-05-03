package com.example.handytune.spotify;

import com.example.handytune.spotify.model.MusicSearchResult;

import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface SpotifyService {

    // Example artist search endpoint ../search?q=queen&type=artist

    // Temporary authorization solution with header
    @Headers({"Authorization: Bearer "})
    @GET("search")
    Call<MusicSearchResult> searchMusic(@Query("q") String query, @Query("type") String searchType);

}
