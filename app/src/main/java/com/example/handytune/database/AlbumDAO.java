package com.example.handytune.database;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

public interface AlbumDAO {

    @Query("SELECT * FROM user where uid = 1")
    Album getAlbum();

    @Update
    int update(Album album);

    @Insert
    void insert(Album album);

    @Query("SELECT COUNT(*) from album")
    int countAlbums();
}
