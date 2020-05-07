package com.example.handytune;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

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


    public void startThreadForInsertPlayLists(final int userId,final String playListName) {
//    public void startThreadForInsertPlayLists() {

        //Create a thread
        insertThread = new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("Thread run*******");
                System.out.println("Before insert playlist(s) to database *********");
                /*For testing*/
                dbRepository.insertPlaylist(userId, playListName);
                System.out.println("After insert playlist(s)  to database *********");
            }
        });
        insertThread.start();
    }

    public void startThreadForDeleteDataInDatabase() {

        //Create a thread
        deleteThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Nuke playlists");
                dbRepository.nukeAlbum();
                dbRepository.nukePlaylist();
            }
        });
        deleteThread.start();
    }

}
