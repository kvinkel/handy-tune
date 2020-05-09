package com.example.handytune;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.handytune.database.Playlist;

public class HomeActivity extends AppCompatActivity {


    Thread insertThread;
    Thread deleteThread;
    DbRepository dbRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        dbRepository = new DbRepository(getApplicationContext());
        startThreadForDeleteDataInDatabase();

        setContentView(R.layout.activity_main);

        findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSearch();
            }
        });

        findViewById(R.id.playlistButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPlaylist();
            }
        });

        findViewById(R.id.userButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSpotifyUser();
            }
        });

        startThreadForInsertPlayLists(1,"Jakobs Playlist");
        startThreadForInsertPlayLists(2,"Kims Playlist");
        startThreadForInsertPlayLists(3,"Frederiks Playlist");

        startThreadForInsertTracks(1,"TestTrack 1", "TestExternalTrackUrl", "TestOpenInAppTrackUrl", "TestAlbumUrl","Jakobs Playlist");
        startThreadForInsertTracks(2,"TestTrack 2", "TestExternalTrackUrl", "TestOpenInAppTrackUrl", "TestAlbumUrl","Kims Playlist");
        startThreadForInsertTracks(3,"TestTrack 3", "TestExternalTrackUrl", "TestOpenInAppTrackUrl", "TestAlbumUrl", "Frederiks Playlist");
        startThreadForInsertTracks(4,"TestTrack 4", "TestExternalTrackUrl", "TestOpenInAppTrackUrl", "TestAlbumUrl","Kims Playlist");
        startThreadForInsertTracks(5,"TestTrack 4", "TestExternalTrackUrl", "TestOpenInAppTrackUrl", "TestAlbumUrl","Kims Playlist");
        startThreadForInsertTracks(6,"TestTrack 4", "TestExternalTrackUrl", "TestOpenInAppTrackUrl", "TestAlbumUrl","Kims Playlist");
        startThreadForInsertTracks(7,"TestTrack 4", "TestExternalTrackUrl", "TestOpenInAppTrackUrl", "TestAlbumUrl","Kims Playlist");


    }

    @Override
    protected void onStart () {

        super.onStart();
    }


    public void goToSearch() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void goToPlaylist() {
        Intent intent = new Intent(this, PlaylistActivity.class);
        startActivity(intent);
    }

    public void goToSpotifyUser() {
        Intent intent = new Intent(this, SpotifyUserActivity.class);
        startActivity(intent);
    }

//TODO For testing**********************************************************************************
    public void startThreadForInsertPlayLists(final int playlistId,final String playListName) {
        //Create a thread
        insertThread = new Thread(new Runnable() {
            @Override
            public void run() {
                dbRepository.insertPlaylist(playlistId, playListName);
            }
        });
        insertThread.start();
    }

    public void startThreadForInsertTracks(final int trackId, final String trackName, final String externalTrackUrl, final String openInAppTrackUrl, final String albumImageUrl, final String belongToPlaylistName  ) {
        //Create a thread
        insertThread = new Thread(new Runnable() {
            @Override
            public void run() {
              dbRepository.insertTrack(trackId,trackName,externalTrackUrl,openInAppTrackUrl,albumImageUrl,belongToPlaylistName);

            }
        });
        insertThread.start();
    }


    public void startThreadForDeleteDataInDatabase() {

        //Create a thread
        deleteThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Playlists and Track");
                dbRepository.nukePlaylistInDatabase();
                dbRepository.nukeTracksInDatabase();
            }
        });
        deleteThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        insertThread.interrupt();
        deleteThread.interrupt();

        dbRepository = null;
    }
}
