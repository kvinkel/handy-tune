package com.example.handytune;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.handytune.database.Album;
import com.example.handytune.database.AppDatabase;

import java.util.List;

public class DbRepository {

    private String DB_NAME = "db_spotify";

    private AppDatabase database;
    public DbRepository(Context context) {
        database = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();
    }

    public void insertAlbum(int userId,
                            int albumId,
                            String albumName,
                            String url, String imageUrl) {

        Album album = new Album();
        album.setUserId(userId);
        album.setAlbumId(albumId);
        album.setAlbumName(albumName);
        album.setUrl(url);
        album.setImageUrl(imageUrl);

        insertAlbum(album);
    }

    public void insertAlbum(final Album album) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.albumDAO().insertAlbum(album);
                return null;
            }
        }.execute();
    }

    public int getNumberAlbums() {
        return database.albumDAO().getNumberOfAlbums();
    }

    public LiveData<Album> getAlbums(int id) {
        return database.albumDAO().getAlbums(id);
    }

    public LiveData<List<Album>> getAllAlbums() {
        return database.albumDAO().getAllAlbums();
    }

    public String getAlbumName(int id){
        return database.albumDAO().getAlbumName(id);
    }

    public void nukeAlbum(){
        database.albumDAO().nukeAlbum();
    }

}
