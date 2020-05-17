package com.example.handytune.spotify;

import com.example.handytune.spotify.model.MusicSearchResult;
import com.example.handytune.spotify.model.UserSearchResult;
import com.example.handytune.spotify.model.artist.TopTracks;

import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpotifyService {

    // Example artist search endpoint ../search?q=queen&type=artist
    @GET("search")
    Call<MusicSearchResult> searchMusic(@Query("q") String query, @Query("type") String searchType, @Header("Authorization") String token);
    @GET("users/{user_id}")
    Call<UserSearchResult> searchUser(@Path("user_id") String userId, @Header("Authorization") String token);
    @GET("artists/{artist_id}/top-tracks")
    Call<TopTracks> topTracks(@Path("artist_id") String artistId, @Query("country") String country, @Header("Authorization") String token);

}
