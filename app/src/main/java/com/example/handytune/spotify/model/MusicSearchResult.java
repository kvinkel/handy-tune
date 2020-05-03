package com.example.handytune.spotify.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MusicSearchResult {

    @SerializedName("albums")
    @Expose
    private Albums albums;
    @SerializedName("artists")
    @Expose
    private Artists artists;
    @SerializedName("tracks")
    @Expose
    private Tracks tracks;

    public Albums getAlbums() {
        return albums;
    }

    public void setAlbums(Albums albums) {
        this.albums = albums;
    }

    public Artists getArtists() {
        return artists;
    }

    public void setArtists(Artists artists) {
        this.artists = artists;
    }

    public Tracks getTracks() {
        return tracks;
    }

    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }

}