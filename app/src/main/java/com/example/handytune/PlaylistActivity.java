package com.example.handytune;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handytune.database.Album;

import java.util.ArrayList;

public class PlaylistActivity extends AppCompatActivity {

    Thread workerThread;

    //Semaphore for keeping track of thread
    volatile boolean running = true;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);


        recyclerView = findViewById(R.id.playlistRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PlaylistAdapter(generatePlaylistForTesting(50));
        recyclerView.setAdapter(adapter);

        //Create a thread
        workerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                //Make sure the thread is still supposed to run.
                while (running) {
                    DbRepository dbRepository = new DbRepository(getApplicationContext());
//                    int numberOfAlbums = dbRepository.getNumberAlbums();
//                    int i = numberOfAlbums + 1;
//                    System.out.println("Before insert album to database *********");
//                    dbRepository.insertAlbum(i,100,"Test","testUrl","imageUrl");
//                    System.out.println("Number of Albums: " + dbRepository.getNumberAlbums());
//                    System.out.println("After insert album to database *********");
//                    i++;
////
////                    for (int j = 0; j < numberOfAlbums  ; i++) {
////                        System.out.println("Albums ### "+dbRepository.getAlbumName(j));
////                    }
//
//
//                    dbRepository.nukeAlbum();

                    new Runnable() {
                        @Override
                        public void run() {
                        }
                    };

                    //Have thread sleep for 10 seconds (10.000 ms)
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //start the thread
        workerThread.start();


    }

    private ArrayList<String> generatePlaylistForTesting(int amount) {
        ArrayList<String> arrayList = new ArrayList<>();
        String s = "My playlist ";
        for (int i = 1; i < amount; i++) {
            arrayList.add(s + i);
        }
        return arrayList;
    }
}
