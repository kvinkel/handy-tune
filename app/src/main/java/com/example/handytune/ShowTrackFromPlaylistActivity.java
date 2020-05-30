package com.example.handytune;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.handytune.database.Track;

import java.util.ArrayList;
import java.util.List;

public class ShowTrackFromPlaylistActivity extends AppCompatActivity {

    List<Track> listOfTracks;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tracks_from_playlist);

        listOfTracks =(ArrayList<Track>) getIntent().getSerializableExtra("ListOfTracks");

        recyclerView = findViewById(R.id.showTracksFromPlaylistRecyclerView);
//        recyclerView = findViewById(R.id.playlistRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ShowTrackFromPlaylistAdapter((ArrayList<Track>) listOfTracks,getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart () {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }






}
