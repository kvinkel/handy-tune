package com.example.handytune;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handytune.database.PlaylistWithTracks;

import java.util.ArrayList;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity {

    Thread readThread;
    DbRepository dbRepository;
    List<PlaylistWithTracks> listOfPlaylistAndTracks;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        dbRepository = new DbRepository(getApplicationContext());
        listOfPlaylistAndTracks = new ArrayList<>();
        startThreadForReadDataInDatabase();

        //Wait for thread to finish
        try {
            readThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        recyclerView = findViewById(R.id.playlistRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PlaylistAdapter((ArrayList<PlaylistWithTracks>) listOfPlaylistAndTracks, getApplicationContext());
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //TODO Duplicated findes også i SpotifyActivity
    public void startThreadForReadDataInDatabase() {
        //Create a thread
        readThread = new Thread(new Runnable() {
            @Override
            public void run() {

                listOfPlaylistAndTracks = dbRepository.getTrackWithPlaylists();
                for (int i = 0; i < listOfPlaylistAndTracks.size(); i++) {
                    System.out.println("There are " + listOfPlaylistAndTracks.get(i).tracks.size() + " of tracks in: " + listOfPlaylistAndTracks.get(i).playlist.getPlaylistName() + "***************");
                }
            }
        });
        readThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Stop threads when activity is destroyed
        readThread.interrupt();
        dbRepository = null;
    }
}
