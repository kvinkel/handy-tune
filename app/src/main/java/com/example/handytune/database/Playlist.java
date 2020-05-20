package com.example.handytune.database;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "playlist")
public class Playlist implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "playlistId")
    private int playlistId;
    @ColumnInfo(name = "playlistName")
    private String playlistName;

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }
}
