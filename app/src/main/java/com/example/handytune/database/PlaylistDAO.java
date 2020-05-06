package com.example.handytune.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlaylistDAO {
    @Insert
    void insertPlaylist(Playlist playlist);

    @Query("SELECT id,userId,playListName from playlist")
    List<Playlist> getAllPLaylists();

    @Query("DELETE FROM playlist")
    public void nukePlaylist();
}
