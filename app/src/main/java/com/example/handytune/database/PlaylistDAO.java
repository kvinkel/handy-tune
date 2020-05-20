package com.example.handytune.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface PlaylistDAO {
    @Insert
    void insertPlaylist(Playlist playlist);

    @Query("SELECT playlistId,playListName from playlist")
    List<Playlist> getAllPLaylists();

    @Query("DELETE FROM playlist")
    public void nukePlaylist();

    @Transaction
    @Query("SELECT * FROM playlist")
    List<PlaylistWithTracks> getTrackWithPlaylists();
}
