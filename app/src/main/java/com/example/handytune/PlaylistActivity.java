package com.example.handytune;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlaylistActivity extends AppCompatActivity {


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

        adapter = new PlayListAdapter(generatePlaylistForTesting(10));
        recyclerView.setAdapter(adapter);



    }

    private ArrayList<String> generatePlaylistForTesting ( int amount){
        ArrayList<String> arrayList = new ArrayList<>();

        String s = "My playlist ";
        for (int i = 0; i < amount; i++) {
            arrayList.add(s + i);
        }
        return arrayList;
    }
}
