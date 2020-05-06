package com.example.handytune.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlbumDAO {

    @Insert
    void insertAlbum(Album album);

    @Query("SELECT * FROM album where userId =:id")
    LiveData<Album> getAlbums(int id);

    @Query("SELECT * FROM album ")
    LiveData<List<Album>> getAllAlbums();

    @Update
    int update(Album album);

    @Query("SELECT COUNT(*) from album")
    int getNumberOfAlbums();

    @Query("SELECT albumName from album where userId =:id")
    String getAlbumName(int id);

    @Query("SELECT id,userId,albumId,albumName,url,imageUrl from album where userId =:id")
    List<Album> getAlbumNamesFromUser(int id);



    @Query("DELETE FROM album")
    public void nukeAlbum();
}
