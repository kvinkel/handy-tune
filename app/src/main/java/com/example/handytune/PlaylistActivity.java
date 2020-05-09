package com.example.handytune;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handytune.database.Playlist;
import com.example.handytune.database.PlaylistWithTracks;
import com.example.handytune.database.Track;

import java.util.ArrayList;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity {

    Thread insertThread;
    Thread readThread;
    Thread deleteThread;

    ArrayList<String> arrayList;

    DbRepository dbRepository;
    List<Playlist> listOfPlaylists;
    List<Track> listOfTracks;
    List<PlaylistWithTracks> listOfPlaylistAndTracks;



    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        dbRepository = new DbRepository(getApplicationContext());
        listOfPlaylists = new ArrayList<>();
        listOfPlaylistAndTracks = new ArrayList<>();
        arrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.playlistRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PlaylistAdapter(generatePlaylistForTesting(),getApplicationContext());
        recyclerView.setAdapter(adapter);


        startThreadForReadDataInDatabase();

    }

    @Override
    protected void onStart () {

        super.onStart();

    }


    public void startThreadForDeleteDataInDatabase() {

        //Create a thread
        deleteThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Nuke playlists & Tracks");
                dbRepository.nukePlaylistInDatabase();
                dbRepository.nukeTracksInDatabase();
            }
        });
        deleteThread.start();
    }


    public void startThreadForReadDataInDatabase() {

        //Create a thread
        readThread = new Thread(new Runnable() {
            @Override
            public void run() {

                listOfPlaylistAndTracks = dbRepository.getTrackWithPlaylists();
                listOfPlaylists=dbRepository.getAllPLaylists();

                for (int i = 0; i < listOfPlaylistAndTracks.size(); i++) {
                    System.out.println("There are "+listOfPlaylistAndTracks.get(i).tracks.size() +  " of tracks in: " + listOfPlaylistAndTracks.get(i).playlist.getPlaylistName()+ "***************");
                }
            }
        });
        readThread.start();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
//        startThreadForDeleteDataInDatabase();
//        Stop threads when activity is destroyed
//        deleteThread.interrupt();
        readThread.interrupt();
        dbRepository = null;
    }





    private ArrayList<String> generatePlaylistForTesting() {

         readThread= new Thread(new Runnable() {
            @Override
            public void run() {

                listOfPlaylists = dbRepository.getAllPLaylists();
                for (int i = 0; i < listOfPlaylists.size(); i++) {
                    arrayList.add( listOfPlaylists.get(i).getPlaylistName());
                }
            }
        });
        readThread.start();
        return arrayList;
    }



}
