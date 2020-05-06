package com.example.handytune;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handytune.database.Album;

import java.util.ArrayList;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity {

    Thread insertThread;
    Thread deleteThread;

    DbRepository dbRepository;
    List<Album> listOfAlbums;

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

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PlaylistAdapter(generatePlaylistForTesting(50),getApplicationContext());
        recyclerView.setAdapter(adapter);

        dbRepository = new DbRepository(getApplicationContext());
        listOfAlbums = new ArrayList<>();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Stop threads when activity is destroyed
//        insertThread.interrupt();
//        deleteThread.interrupt();
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
