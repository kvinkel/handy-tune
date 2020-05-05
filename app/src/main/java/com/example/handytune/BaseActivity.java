package com.example.handytune;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.handytune.database.Album;
import com.example.handytune.database.AppDatabase;
import com.example.handytune.database.Track;

import java.lang.annotation.Target;

public class BaseActivity extends AppCompatActivity {

    protected  AppDatabase db;
    protected Album albumTest;
    protected Track trackTest;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getAppDatabase(this);
      }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void addToAlbumInDb(int userId, int albumId, String albumName, String url, String imageUrl){
        if(db.albumDAO().countAlbums() == 0){
            albumTest = new Album();
            albumTest.userId = userId;
            albumTest.albumId = albumId;
            albumTest.albumName = "Test";
            albumTest.url = url;
            albumTest.imageUrl=imageUrl;
            db.albumDAO().insert(albumTest);
        }
        else{
            albumTest = db.albumDAO().getAlbum();
        }
    }

    public void addToTrackInDb(int userId, int trackId, String trackName, String url){
        if(db.albumDAO().countAlbums() == 0){
            trackTest = new Track();
            trackTest.userId = userId;
            trackTest.trackId = trackId;
            trackTest.name = trackName;
            albumTest.url = url;
            db.trackDAO().insert(trackTest);
        }
        else{
            albumTest = db.albumDAO().getAlbum();
        }
    }
}
