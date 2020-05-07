package com.example.handytune;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handytune.database.Album;
import com.example.handytune.database.Playlist;

import java.util.ArrayList;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity {

    Thread insertThread;
    Thread readThread;
    Thread deleteThread;

    DbRepository dbRepository;
    List<Playlist> listOfPlaylists;



    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        dbRepository = new DbRepository(getApplicationContext());
        listOfPlaylists = new ArrayList<>();

        recyclerView = findViewById(R.id.playlistRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PlaylistAdapter(generatePlaylistForTesting(),getApplicationContext());
        recyclerView.setAdapter(adapter);

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
                System.out.println("Nuke playlists");
                dbRepository.nukeAlbum();
                dbRepository.nukePlaylist();
            }
        });
        deleteThread.start();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
//        startThreadForDeleteDataInDatabase();

//        Stop threads when activity is destroyed
//        deleteThread.interrupt();
    }


    ArrayList<String> arrayList = new ArrayList<>();


    private ArrayList<String> generatePlaylistForTesting() {

         readThread= new Thread(new Runnable() {
            @Override
            public void run() {

                listOfPlaylists = dbRepository.getAllPLaylists();
                System.out.println("Number of Playlists " + listOfPlaylists.size() + "***************");
                for (int i = 0; i < listOfPlaylists.size(); i++) {
                    arrayList.add( listOfPlaylists.get(i).getPlaylistName());
                }
            }
        });
        readThread.start();

        System.out.println("Arraylist size: "+  arrayList.size()+"********************");
        return arrayList;
    }



}
