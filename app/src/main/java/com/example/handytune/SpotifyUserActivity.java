package com.example.handytune;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpotifyUserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlaylistAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);

        recyclerView = findViewById(R.id.userPlaylistView);
        recyclerView.setHasFixedSize(true);

        adapter = new PlaylistAdapter(generatePlaylistForTesting(11));

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<String> generatePlaylistForTesting ( int amount){
        ArrayList<String> arrayList = new ArrayList<>();

        String s = "My playlist ";
        for (int i = 1; i < amount; i++) {
            arrayList.add(s + i);
        }
        return arrayList;
    }

/*        SearchView searchView = (SearchView) findViewById(R.id.userSearchField);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                retroSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }*/


}
