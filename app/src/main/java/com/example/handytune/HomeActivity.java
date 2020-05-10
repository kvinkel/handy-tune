package com.example.handytune;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Random;

public class HomeActivity extends AppCompatActivity {


    Thread insertThread;
    Thread deleteThread;
    DbRepository dbRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        dbRepository = new DbRepository(getApplicationContext());

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


        //TODO For testing ********************************************************************************************
        startThreadForInsertPlayLists(1, "Jakobs Playlist");
        startThreadForInsertPlayLists(2, "Kims Playlist");
        startThreadForInsertPlayLists(3, "Frederiks Playlist");
        startThreadForInsertPlayLists(3, "Frederiks Playlist");

        try {
            insertThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String[] names = {"Jakobs Playlist", "Kims Playlist", "Frederiks Playlist"};
        int playlistInt = 1000;

        for (int i = 0; i < playlistInt; i++) {
            Random random1 = new Random();
            int playlistNameInt = random1.nextInt(3);
            startThreadForInsertTracks(i, "TestTrack "+i, "TestExternalTrackUrl", "TestOpenInAppTrackUrl", "https://i.scdn.co/image/b16064142fcd2bd318b08aab0b93b46e87b1ebf5", names[playlistNameInt]);
        }

        try {
            insertThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        startThreadForDeleteDataInDatabase();

    }

    @Override
    protected void onStart() {

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
    public void startThreadForInsertPlayLists(final int playlistId, final String playListName) {
        //Create a thread
        insertThread = new Thread(new Runnable() {
            @Override
            public void run() {
                dbRepository.insertPlaylist(playlistId, playListName);
            }
        });
        insertThread.start();
    }

    public void startThreadForInsertTracks(final int trackId, final String trackName, final String externalTrackUrl, final String openInAppTrackUrl, final String albumImageUrl, final String belongToPlaylistName) {
        //Create a thread
        insertThread = new Thread(new Runnable() {
            @Override
            public void run() {
                dbRepository.insertTrack(trackId, trackName, externalTrackUrl, openInAppTrackUrl, albumImageUrl, belongToPlaylistName);
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
//        deleteThread.interrupt();
        dbRepository = null;
    }
}
