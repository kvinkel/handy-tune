package com.example.handytune;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handytune.database.PlaylistWithTracks;
import com.example.handytune.fragments.CreatePlaylistFragment;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class PlaylistActivity extends AppCompatActivity  {

    private Thread readThread;
    private DbRepository dbRepository;
    private List<PlaylistWithTracks> listOfPlaylistAndTracks;

    private RecyclerView recyclerView;
    private PlaylistAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button goToCreatePlaylistBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        dbRepository = new DbRepository(getApplicationContext());
        listOfPlaylistAndTracks = new ArrayList<>();

        readFromDB();

        recyclerView = findViewById(R.id.playlistRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PlaylistAdapter((ArrayList<PlaylistWithTracks>) listOfPlaylistAndTracks, getApplicationContext());
        recyclerView.setAdapter(adapter);

        goToCreatePlaylistBtn = findViewById(R.id.createPlaylistBtn_in_activity);
        goToCreatePlaylistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String calledFrom = "PlaylistActivity";
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                CreatePlaylistFragment createPlaylistFragment= CreatePlaylistFragment.newInstance(calledFrom);
                transaction.replace(R.id.playlistFrame, createPlaylistFragment).addToBackStack(null);
                transaction.commit();
            }
        });
    }

    public void startThreadForReadDataInDatabase() {
        //Create a thread
        readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                listOfPlaylistAndTracks = dbRepository.getTrackWithPlaylists();
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

    public void readFromDB(){
        startThreadForReadDataInDatabase();
        //Wait for thread to finish
        try {
            readThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateAdapter()
    {
        readFromDB();
        adapter.setPlaylistWithTracks((ArrayList<PlaylistWithTracks>) listOfPlaylistAndTracks);
        adapter.notifyDataSetChanged();
    }
}
