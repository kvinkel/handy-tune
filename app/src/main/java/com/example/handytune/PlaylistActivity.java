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


        startThreadForInsertPlayLists(1,"Jakobs Playlist");
        startThreadForInsertPlayLists(2,"Kims Playlist");
        startThreadForInsertPlayLists(3,"Frederik Playlist");


        recyclerView = findViewById(R.id.playlistRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PlaylistAdapter(generatePlaylistForTesting(),getApplicationContext());
        recyclerView.setAdapter(adapter);

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
//                dbRepository.insertPlaylist(1, "Jakobs Playlist");

                System.out.println("After insert album to database *********");
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



    @Override
    protected void onDestroy() {
        super.onDestroy();
        startThreadForDeleteDataInDatabase();

//        Stop threads when activity is destroyed
        insertThread.interrupt();
        deleteThread.interrupt();
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
