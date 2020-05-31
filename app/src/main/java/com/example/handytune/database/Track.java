package com.example.handytune.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "track")
public class Track implements Serializable {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "trackId")
    private String trackId;
    @ColumnInfo(name = "trackName")
    private String trackName;
    @ColumnInfo(name = "externalTrackUrl")
    private String externalTrackUrl;
    @ColumnInfo(name = "openInAppTrackUrl")
    private String openInAppTrackUrl;
    @ColumnInfo(name = "albumImageUrl")
    private String albumImageUrl;
    @ColumnInfo(name = "belongToPlaylistName")
    private String belongToPlaylistName;


    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getExternalTrackUrl() {
        return externalTrackUrl;
    }

    public void setExternalTrackUrl(String externalTrackUrl) {
        this.externalTrackUrl = externalTrackUrl;
    }

    public String getOpenInAppTrackUrl() {
        return openInAppTrackUrl;
    }

    public void setOpenInAppTrackUrl(String openInAppTrackUrl) {
        this.openInAppTrackUrl = openInAppTrackUrl;
    }

    public String getAlbumImageUrl() {
        return albumImageUrl;
    }

    public void setAlbumImageUrl(String albumImageUrl) {
        this.albumImageUrl = albumImageUrl;
    }

    public String getBelongToPlaylistName() {
        return belongToPlaylistName;
    }

    public void setBelongToPlaylistName(String belongToPlaylistName) {
        this.belongToPlaylistName = belongToPlaylistName;
    }
}
