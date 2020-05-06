package com.example.handytune;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.handytune.database.Album;
import com.example.handytune.database.AppDatabase;
import com.example.handytune.database.Playlist;

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




    public void insertPlaylist(int userId,
                            String playlistName
                        ) {

        Playlist playlist = new Playlist();
        playlist.setUserId(userId);
        playlist.setPlaylistName(playlistName);

        insertPlaylist(playlist);
    }

    public void insertPlaylist(final Playlist playlist) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.playlistDAO().insertPlaylist(playlist);
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

    public List<Album> getAlbumNamesFromUser(int id){
        return database.albumDAO().getAlbumNamesFromUser(id);
    }

    public void nukeAlbum(){
        database.albumDAO().nukeAlbum();
    }

    public List<Playlist> getAllPLaylists(){
        return database.playlistDAO().getAllPLaylists();
    }

    public void nukePlaylist(){
        database.playlistDAO().nukePlaylist();
    }

}
